import math
import os
import random
import sys
from string import ascii_uppercase
from random import choice
import numpy as np
import pandas as pd
import json
import re

cpi = sys.argv[1]
bskt_value = float(float(cpi) / 10)
days = int(sys.argv[2])
user_base_size = int(sys.argv[3])
user_gen_seed = int(sys.argv[4])
keeper_gen_seed = int(sys.argv[5])
colat_gen_seed = int(sys.argv[6])
bsr_gen_seed = float(sys.argv[7])

colat_data_path = "/../../Data/Oracle-Data/"
dates_data_path = os.path.join(os.getcwd() + colat_data_path + 'GBP-USD.csv')
frame = pd.read_csv(dates_data_path)
dates = frame['Date'].values

vault_index = 0
vault_distro = np.random.normal(0.4, 0.4, 1000)

for vault in vault_distro:
    if vault < 0:
        vault_distro[vault_index] = vault_distro[vault_index] * -1
    vault_distro[vault_index] = round(vault_distro[vault_index])
    vault_index = vault_index + 1


def user_vault_creation(user_basket_amount, colats):
    out = ""
    num_vaults = vault_distro[random.randint(0, vault_distro.size-1)]
    out = out + " " + str(num_vaults)
    if num_vaults > 0:
        for i in range(int(num_vaults)):
            out = out + "_" + colats[random.randint(0, len(colats)-1)]
            quant = random.uniform(user_basket_amount * 1.2, user_basket_amount * 1.5)
            out = out + "_" + str(quant)
    return out


def generate_colat(exchange, x):
    if random.randint(1, 3) == 3:
        return x[random.randint(1, x.size-1)] * random.random() / exchange
    else:
        return 0


def round_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.ceil(n * multiplier) / multiplier


date = dates[days-1828]


# Users
user_names = []
index = 0
user_basket = (np.random.normal(user_gen_seed, user_gen_seed / 5, user_base_size))
for user in user_basket:
    if user_basket[index] < 0:
        user_basket[index] = user_basket[index] * -1
    index = index + 1
for user in user_basket:
    user_names.append(''.join(choice(ascii_uppercase) for i in range(15)))


# Collateral exchange rates
col_index = 0
collaterals = ['A-XRP', 'ETH', 'LINK', 'W-BTC', 'USDT', 'P-LTC']
collaterals_distribution = np.random.normal(colat_gen_seed, colat_gen_seed / 3, user_base_size)
for col in collaterals_distribution:
    if col < 0:
        collaterals_distribution[col_index] = collaterals_distribution[col_index] * -1

with open(os.getcwd() + colat_data_path + 'oracle_A_XRP.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_xrp = float(non_decimal.sub('',str(data[date])))
    print(exchange_xrp)

with open(os.getcwd() + colat_data_path + 'oracle_ETH.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_eth = float(non_decimal.sub('',str(data[date])))

with open(os.getcwd() + colat_data_path + 'oracle_BTC.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_btc = float(non_decimal.sub('', str(data[date])))

with open(os.getcwd() + colat_data_path + 'oracle_LNK.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_link = float(non_decimal.sub('', str(data[date])))

with open(os.getcwd() + colat_data_path + 'oracle_P_LTC.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_ltc = float(non_decimal.sub('', str(data[date])))

with open(os.getcwd() + colat_data_path + 'oracle_USDT.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_usdt = float(non_decimal.sub('', str(data[date])))

user_xrp = []
user_eth = []
user_link = []
user_btc = []
user_usdt = []
user_ltc = []

for user in user_basket:
    user_xrp.append(generate_colat(exchange_xrp, collaterals_distribution))
    user_eth.append(generate_colat(exchange_eth, collaterals_distribution))
    user_link.append(generate_colat(exchange_link, collaterals_distribution))
    user_btc.append(generate_colat(exchange_btc, collaterals_distribution))
    user_usdt.append(generate_colat(exchange_usdt, collaterals_distribution))
    user_ltc.append(generate_colat(exchange_ltc, collaterals_distribution))


# User data creation
data_path = "/../../Data/User-Data/"

user_data = {}
user_index = 0
for user in user_basket:
    user_data[user_names[user_index]] = []
    user_data[user_names[user_index]].append(
        {
            "Basket Holdings": user,
            "W-BTC Holdings": user_btc[user_index],
            "ETH Holdings": user_eth[user_index],
            "P-LTC Holdings": user_ltc[user_index],
            "USDT Holdings": user_usdt[user_index],
            "LNK Holdings": user_link[user_index],
            "A-XRP Holdings": user_xrp[user_index],
            "Vault Info": user_vault_creation(user, collaterals)
        }
    )
    user_index = user_index + 1

with open(os.getcwd() + data_path + "Users_Initial.json", "w") as write_file:
    json.dump(user_data, write_file)

data_path = "/../../Data/Keeper-Data/"

# Keepers
total_user_basket = 0
for user in user_basket:
    total_user_basket = total_user_basket + user
keepers_holdings = total_user_basket * keeper_gen_seed/100

keeper_data = {}
keeper_data['Info'] = []
keeper_data['Info'].append(
    {
        "Basket Holdings": keepers_holdings,
        "W-BTC Holdings": 0,
        "ETH Holdings": 0,
        "P-LTC Holdings": 0,
        "USDT Holdings": 0,
        "LNK Holdings": 0,
        "A-XRP Holdings": 0,
    }
)

with open(os.getcwd() + data_path + "Keeper_Initial.json", "w") as write_file:
    json.dump(keeper_data, write_file)

data_path = "/../../Data/Vault-Data/"

# Complete basket
total_basket = total_user_basket + keepers_holdings
placeholder = total_basket
bskt_minted_distro = np.random.normal(user_gen_seed, user_gen_seed/5, 2000)
minted_index = 0
for minted in bskt_minted_distro:
    if minted < 0:
        bskt_minted_distro[minted_index] = 0
    minted_index = minted_index + 1

vault_data = {}

while placeholder > 0:
    vault_id = ''.join(choice(ascii_uppercase) for i in range(16))
    owner = user_names[random.randint(0, len(user_names)-1)]
    bskt_minted = bskt_minted_distro[random.randint(0, bskt_minted_distro.size-1)]
    placeholder = placeholder - bskt_minted
    vault_colat_type = collaterals[random.randint(0, len(collaterals)-1)]
    vault_colat_quant = bskt_minted*random.uniform(1.3, 1.6)
    vault_data[vault_id] = []
    vault_data[vault_id].append(
        {
            "owner": owner,
            "Minted": bskt_minted,
            "Colatteral Type": vault_colat_type,
            "Collateral Amount": vault_colat_quant
        }
    )

with open(os.getcwd() + data_path + "Vault_Initial.json", "w") as write_file:
    json.dump(vault_data, write_file)


# BSR and Total Basket
data_path = "/../../Data/BSR-Data/"

bsr_data = {}
bsr_data['Info'] = []
bsr_data['Info'].append(
    {
        "Value": total_basket,
        "BSR": bsr_gen_seed
    }
)

with open(os.getcwd() + data_path + "Governance_Initial.json", "w") as write_file:
    json.dump(bsr_data, write_file)
