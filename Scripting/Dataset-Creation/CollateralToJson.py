import json
import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import main


data_path = "/../../Data/Oracle-Data/"

gbp_data_path = os.path.join(os.getcwd() + data_path + 'GBP-USD.csv')
gbp = pd.read_csv(gbp_data_path)
dates = gbp['Date'].values
gbp_values = gbp['Open'].values

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