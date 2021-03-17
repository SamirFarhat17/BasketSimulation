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