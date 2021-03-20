import math
import os
import random
import sys
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

def generate_colat(seed, exchange):
    if random.randint(1, 3) == 3:
        return collaterals_distribution[random.randint(1,collaterals_distribution.size)]
    else:
        return 0


def round_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.ceil(n * multiplier) / multiplier


date = dates[days-1828]

collaterals = ['A-XRP', 'ETH', 'LINK', 'W-BTC', 'USDT', 'P-LTC']
collaterals_distribution = np.random.normal(colat_gen_seed, colat_gen_seed / 3, user_base_size)

# Users
user_basket = (np.random.normal(user_gen_seed, user_gen_seed / 5, user_base_size))

with open(os.getcwd() + colat_data_path + 'oracle_A_XRP.json') as json_file:
    data = json.load(json_file)
    non_decimal = re.compile(r'[^\d.]+')
    exchange_xrp = float(non_decimal.sub('',str(data[date])))
    print(exchange_xrp)

user_xrp = []
user_eth = []
user_link = []
user_btc = []
user_usdt = []
user_ltc = []

for user in user_basket:
    user_xrp.append(generate_colat(colat_gen_seed,exchange_xrp))

# Keepers
total_user_basket = 0
for user in user_basket:
    total_user_basket = total_user_basket + user
keepers_holdings = total_user_basket * keeper_gen_seed/100
# Complete basket
total_basket = total_user_basket + keepers_holdings

# BSR
bsr = bsr_gen_seed


# Vault parameters


