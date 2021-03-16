import json
import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import main


data_path = "/../../Data/Oracle-Data/"
wrappedBTC_data_path = os.path.join(os.getcwd() + data_path + 'W_BTC_GBP.csv')
wrappedBTC = pd.read_csv(wrappedBTC_data_path)

dates = wrappedBTC['Date'].values
values = wrappedBTC['Open'].values

index = 0
oracle_BTC = {}
for date in dates:
    oracle_BTC[date] = []
    oracle_BTC[date].append(
        {
            'date': dates[index],
            'Value': values[index]
        }
    )
    index = index + 1

with open(os.getcwd() + data_path + "oracle_BTC.json", "w") as write_file:
    json.dump(oracle_BTC, write_file)