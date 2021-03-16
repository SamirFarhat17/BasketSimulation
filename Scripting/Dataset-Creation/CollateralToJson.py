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