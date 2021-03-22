import json
import os
import pandas as pd
import matplotlib.pyplot as plt
import main

data_path = "../../Data/CPI-Data/"

cpi_data_path = os.path.join(os.getcwd(), data_path, 'ExtensiveCPI.csv')
cpi_data = pd.read_csv(cpi_data_path)

dates = cpi_data['Title'].values
nums = []

month = 0
year = 1988

n = []
m = 1988.0

for date in dates:
    n.append(m)
    m = m + 0.0833333333333

cpi_monthly = cpi_data['CPI INDEX 00: ALL ITEMS 2015=100'].values

fig, cpi_fig = plt.subplots(figsize=(15, 7))
cpi_fig.plot(n, cpi_monthly)

cpi_fig.set_ylim([0, 150])

plt.title('Monthly Consumer Price Index Since 1988')
plt.xlabel('Time')
plt.ylabel('CPI')

plt.show()
#plt.savefig('MonthlyCPI.png')

index = 0
json_cpi = {}
for year in range(1988, 2021):
    json_cpi[year] = []
    for month in main.months:
        json_cpi[year].append(
            {
                'Month': month,
                'Value': cpi_monthly[index]
            }
        )
        index = index + 1

with open(os.getcwd() + '/' + data_path + "cpi_Monthly.json", "w") as write_file:
    json.dump(json_cpi, write_file)

alt_data_path = "/../../Data/oracles.Oracle-Data/"

gbp_data_path = os.path.join(os.getcwd() + alt_data_path + 'GBP-USD.csv')
gbp = pd.read_csv(gbp_data_path)
dates = gbp['Date'].values
cpi_daily = []

for date in dates:
    # 2016
    if "2016-01" in date:
        cpi_daily.append(99.5)
    if "2016-02" in date:
        cpi_daily.append(99.8)
    if "2016-03" in date:
        cpi_daily.append(100.2)
    if "2016-04" in date:
        cpi_daily.append(100.2)
    if "2016-05" in date:
        cpi_daily.append(100.4)
    if "2016-06" in date:
        cpi_daily.append(100.6)
    if "2016-07" in date:
        cpi_daily.append(100.6)
    if "2016-08" in date:
        cpi_daily.append(100.9)
    if "2016-09" in date:
        cpi_daily.append(101.1)
    if "2016-10" in date:
        cpi_daily.append(101.2)
    if "2016-11" in date:
        cpi_daily.append(101.4)
    if "2016-12" in date:
        cpi_daily.append(101.9)

    # 2017
    if "2017-01" in date:
        cpi_daily.append(101.4)
    if "2017-02" in date:
        cpi_daily.append(102.1)
    if "2017-03" in date:
        cpi_daily.append(102.5)
    if "2017-04" in date:
        cpi_daily.append(102.9)
    if "2017-05" in date:
        cpi_daily.append(103.3)
    if "2017-06" in date:
        cpi_daily.append(103.3)
    if "2017-07" in date:
        cpi_daily.append(103.2)
    if "2017-08" in date:
        cpi_daily.append(103.8)
    if "2017-09" in date:
        cpi_daily.append(104.1)
    if "2017-10" in date:
        cpi_daily.append(104.2)
    if "2017-11" in date:
        cpi_daily.append(104.6)
    if "2017-12" in date:
        cpi_daily.append(104.9)

    #2018
    if "2018-01" in date:
        cpi_daily.append(104.4)
    if "2018-02" in date:
        cpi_daily.append(104.9)
    if "2018-03" in date:
        cpi_daily.append(105.0)
    if "2018-04" in date:
        cpi_daily.append(105.4)
    if "2018-05" in date:
        cpi_daily.append(105.8)
    if "2018-06" in date:
        cpi_daily.append(105.8)
    if "2018-07" in date:
        cpi_daily.append(105.8)
    if "2018-08" in date:
        cpi_daily.append(106.5)
    if "2018-09" in date:
        cpi_daily.append(106.6)
    if "2018-10" in date:
        cpi_daily.append(106.7)
    if "2018-11" in date:
        cpi_daily.append(107.0)
    if "2018-12" in date:
        cpi_daily.append(107.1)

    # 2019
    if "2019-01" in date:
        cpi_daily.append(106.3)
    if "2019-02" in date:
        cpi_daily.append(106.8)
    if "2019-03" in date:
        cpi_daily.append(107.0)
    if "2019-04" in date:
        cpi_daily.append(107.6)
    if "2019-05" in date:
        cpi_daily.append(107.9)
    if "2019-06" in date:
        cpi_daily.append(107.9)
    if "2019-07" in date:
        cpi_daily.append(107.9)
    if "2019-08" in date:
        cpi_daily.append(108.4)
    if "2019-09" in date:
        cpi_daily.append(108.5)
    if "2019-10" in date:
        cpi_daily.append(108.3)
    if "2019-11" in date:
        cpi_daily.append(108.5)
    if "2019-12" in date:
        cpi_daily.append(108.5)

    # 2020
    if "2020-01" in date:
        cpi_daily.append(108.2)
    if "2020-02" in date:
        cpi_daily.append(108.6)
    if "2020-03" in date:
        cpi_daily.append(108.6)
    if "2020-04" in date:
        cpi_daily.append(108.5)
    if "2020-05" in date:
        cpi_daily.append(108.5)
    if "2020-06" in date:
        cpi_daily.append(108.6)
    if "2020-07" in date:
        cpi_daily.append(109.1)
    if "2020-08" in date:
        cpi_daily.append(108.6)
    if "2020-09" in date:
        cpi_daily.append(109.1)
    if "2020-10" in date:
        cpi_daily.append(109.1)
    if "2020-11" in date:
        cpi_daily.append(108.9)
    if "2020-12" in date:
        cpi_daily.append(109.2)


index = 0
daily_json_cpi = {}
for date in dates:
    daily_json_cpi[date] = []
    daily_json_cpi[date].append(
        {
            'Value': cpi_daily[index]
        }
    )
    index = index + 1


with open(os.getcwd() + '/' + data_path + "cpi_Daily.json", "w") as write_file:
    json.dump(daily_json_cpi, write_file)