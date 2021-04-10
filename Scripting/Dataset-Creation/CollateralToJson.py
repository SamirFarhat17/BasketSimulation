import json
import os
import pandas as pd


data_path = "/../../Data/Oracle-Data/"

gbp_data_path = os.path.join(os.getcwd() + data_path + 'GBP-USD.csv')
gbp = pd.read_csv(gbp_data_path)
dates = gbp['Date'].values
gbp_values = gbp['Open'].values

# W-BTC
wrappedBTC_data_path = os.path.join(os.getcwd() + data_path + 'W-BTC-USD.csv')
wrappedBTC = pd.read_csv(wrappedBTC_data_path)
btc_values = wrappedBTC['Open'].values

btc_index = 0
oracle_BTC = {}
for date in dates:
    oracle_BTC[date] = []
    oracle_BTC[date].append(
        {
            'Value': btc_values[btc_index] * (1/gbp_values[btc_index])
        }
    )
    btc_index = btc_index + 1

with open(os.getcwd() + data_path + "oracle_BTC.json", "w") as write_file:
    json.dump(oracle_BTC, write_file)

# ETH
eth_data_path = os.path.join(os.getcwd() + data_path + 'ETH-USD.csv')
eth = pd.read_csv(eth_data_path)
eth_values = eth['Open'].values

eth_index = 0
oracle_ETH = {}
for date in dates:
    oracle_ETH[date] = []
    oracle_ETH[date].append(
        {
            'Value': eth_values[eth_index] * (1/gbp_values[eth_index])
        }
    )
    eth_index = eth_index + 1

with open(os.getcwd() + data_path + "oracle_ETH.json", "w") as write_file:
    json.dump(oracle_ETH, write_file)

# LTC
ltc_data_path = os.path.join(os.getcwd() + data_path + 'P-LTC-USD.csv')
ltc = pd.read_csv(ltc_data_path)
ltc_values = ltc['Open'].values

ltc_index = 0
oracle_LTC = {}
for date in dates:
    oracle_LTC[date] = []
    oracle_LTC[date].append(
        {
            'Value': ltc_values[ltc_index] * (1/gbp_values[ltc_index])
        }
    )
    ltc_index = ltc_index + 1

with open(os.getcwd() + data_path + "oracle_P_LTC.json", "w") as write_file:
    json.dump(oracle_LTC, write_file)

# LINK
lnk_data_path = os.path.join(os.getcwd() + data_path + 'LINK-USD.csv')
lnk = pd.read_csv(lnk_data_path)
lnk_values = lnk['Open'].values

lnk_index = 0
oracle_LNK = {}
for date in dates:
    oracle_LNK[date] = []
    oracle_LNK[date].append(
        {
            'Value': lnk_values[lnk_index] * (1/gbp_values[lnk_index])
        }
    )
    lnk_index = lnk_index + 1

with open(os.getcwd() + data_path + "oracle_LNK.json", "w") as write_file:
    json.dump(oracle_LNK, write_file)

# XRP
a_xrp_data_path = os.path.join(os.getcwd() + data_path + 'A-XRP-USD.csv')
a_xrp = pd.read_csv(a_xrp_data_path)
a_xrp_values = a_xrp['Open'].values

a_xrp_index = 0
oracle_A_XRP = {}
for date in dates:
    oracle_A_XRP[date] = []
    oracle_A_XRP[date].append(
        {
            'Value': a_xrp_values[a_xrp_index] * (1/gbp_values[a_xrp_index])
        }
    )
    a_xrp_index = a_xrp_index + 1

with open(os.getcwd() + data_path + "oracle_A_XRP.json", "w") as write_file:
    json.dump(oracle_A_XRP, write_file)

# USDT
usdt_data_path = os.path.join(os.getcwd() + data_path + 'USDT-USD.csv')
usdt = pd.read_csv(usdt_data_path)
usdt_values = usdt['Open'].values

usdt_index = 0
oracle_USDT = {}
for date in dates:
    oracle_USDT[date] = []
    oracle_USDT[date].append(
        {
            'Value': usdt_values[usdt_index] * (1/gbp_values[usdt_index])
        }
    )
    usdt_index = usdt_index + 1

with open(os.getcwd() + data_path + "oracle_USDT.json", "w") as write_file:
    json.dump(oracle_USDT, write_file)

# GBP
gbp_data_path = os.path.join(os.getcwd() + data_path + 'GBP-USD.csv')
gbp = pd.read_csv(gbp_data_path)
gbp_values = gbp['Open'].values

gbp_index = 0
oracle_GBP = {}
for date in dates:
    oracle_GBP[date] = []
    oracle_GBP[date].append(
        {
            'Value': gbp_values[gbp_index] * (1/gbp_values[gbp_index])
        }
    )
    gbp_index = gbp_index + 1

with open(os.getcwd() + data_path + "oracle_GBP.json", "w") as write_file:
    json.dump(oracle_GBP, write_file)