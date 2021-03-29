import os
import shutil
import sys
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import sklearn
import plotly.graph_objects as go
from plotly.colors import n_colors


def graph_one(x, y, title, x_title, y_title):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, x)

    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/" + title + ".png")


def graph_two(w, x, y, title, x_title, y_title, legend_1, legend_2):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, w)
    actual_plot.plot(y, x)

    plt.legend([legend_1, legend_2], loc='upper left')
    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/" + title + ".png")


def graph_three(v, w, x, y, title, x_title, y_title, legend_1, legend_2, legend_3):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, v)
    actual_plot.plot(y, w)
    actual_plot.plot(y, x)

    plt.legend([legend_1, legend_2, legend_3], loc='upper left')
    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/" + title + ".png")


def graph_four(u, v, w, x, y, title, x_title, y_title, legend_1, legend_2, legend_3, legend_4):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, u)
    actual_plot.plot(y, v)
    actual_plot.plot(y, w)
    actual_plot.plot(y, x)

    plt.legend([legend_1, legend_2, legend_3, legend_4], loc='upper left')
    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/" + title + ".png")


def graph_six(s, t, u, v, w, x, y, title, x_title, y_title, legend_1, legend_2, legend_3, legend_4, legend_5, legend_6):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, s)
    actual_plot.plot(y, t)
    actual_plot.plot(y, u)
    actual_plot.plot(y, v)
    actual_plot.plot(y, w)
    actual_plot.plot(y, x)

    plt.legend([legend_1, legend_2, legend_3, legend_4, legend_5, legend_6], loc='upper left')
    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig(
        "../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" +
        args[6] + "/" + title + ".png")


def graph_seven(r, s, t, u, v, w, x, y, title, x_title, y_title, legend_1, legend_2, legend_3, legend_4, legend_5, legend_6, legend_7):
    fig, actual_plot = plt.subplots(figsize=(15, 7))
    actual_plot.plot(y, r)
    actual_plot.plot(y, s)
    actual_plot.plot(y, t)
    actual_plot.plot(y, u)
    actual_plot.plot(y, v)
    actual_plot.plot(y, w)
    actual_plot.plot(y, x)

    plt.legend([legend_1, legend_2, legend_3, legend_4, legend_5, legend_6, legend_7], loc='upper left')
    plt.xlabel(x_title)
    plt.ylabel(y_title)

    plt.savefig("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" +args[6] + "/" + title + ".png")


args = sys.argv
x_axis = []
days = int(args[2])

for i in range(days):
    x_axis.append(i)


output_data_path = "../Simulation-Raw/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6]+ ".csv"
output_pandas = pd.read_csv(output_data_path)
shutil.rmtree("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/")
os.makedirs("../Simulation-Processed/" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + "_" + args[6] + "/")

cpi = output_pandas['CPI'].values
target_price = output_pandas['TargetPrice'].values
basket_price = output_pandas['BasketPrice'].values

graph_three(cpi, target_price, basket_price, x_axis, 'Actual_Basket_Value_vs_Ideal_basket_Value_and_CPI', 'Price(GBP)', 'Days', 'CPI', 'Target Price', 'Basket Price')

basket_minted = output_pandas['BasketMinted'].values
basket_tokens_minted = output_pandas['BasketTokensMinted'].values
user_size = output_pandas['UserBaseSize'].values

graph_three(basket_minted, basket_tokens_minted, user_size, x_axis, 'Total_Basket_and_Basket_Tokens', 'Days', 'Quantity(Tokens, GBP and People)', 'Total Basket Value',
            'Basket Tokens Minted', 'User-Base Size')

debt_ceiling = output_pandas['DebtCeiling'].values
xrp_debt_ceiling = output_pandas['XRPDebtCeiling'].values
btc_debt_ceiling = output_pandas['BTCDebtCeiling'].values
eth_debt_ceiling = output_pandas['ETHDebtCeiling'].values
link_debt_ceiling = output_pandas['LINKDebtCeiling'].values
ltc_debt_ceiling = output_pandas['LTCDebtCeiling'].values
usdt_debt_ceiling = output_pandas['USDTDebtCeiling'].values

graph_seven(debt_ceiling, xrp_debt_ceiling, btc_debt_ceiling, eth_debt_ceiling, link_debt_ceiling, ltc_debt_ceiling, usdt_debt_ceiling, x_axis, 'Debt_Ceilings', 'Days',
           'GBP', 'Total', 'A-XRP', 'W-BTC', 'ETH', 'P-LINK', 'LTC', 'USDT')

xrp_exchange_rates = output_pandas['XRPExchangeRate'].values
btc_exchange_rates = output_pandas['BTCExchangeRate'].values
eth_exchange_rates = output_pandas['ETHExchangeRate'].values
link_exchange_rates = output_pandas['LINKExchangeRate'].values
ltc_exchange_rates = output_pandas['LTCExchangeRate'].values
usdt_exchange_rates = output_pandas['USDTExchangeRate'].values

graph_seven(basket_price, xrp_exchange_rates, btc_exchange_rates, eth_exchange_rates, link_exchange_rates, ltc_exchange_rates, usdt_exchange_rates, x_axis, 'Exchange_Rates',
           'Days','GBP', 'BSKT', 'A-XRP', 'W-BTC', 'ETH', 'P-LINK', 'LTC', 'USDT')

xrp_sfs = output_pandas['XRPSF'].values
btc_sfs = output_pandas['BTCSF'].values
eth_sfs = output_pandas['ETHSF'].values
link_sfs = output_pandas['LINKSF'].values
ltc_sfs = output_pandas['LTCSF'].values
usdt_sfs = output_pandas['USDTSF'].values

graph_six(xrp_sfs, btc_sfs, eth_sfs, link_sfs, ltc_sfs, usdt_sfs, x_axis, 'Savings_Rates', 'Days', '%', 'A-XRP', 'W-BTC', 'ETH', 'P-LINK', 'LTC', 'USDT')

xrp_lrs = output_pandas['XRPLR'].values
btc_lrs = output_pandas['BTCLR'].values
eth_lrs = output_pandas['ETHLR'].values
link_lrs = output_pandas['LINKLR'].values
ltc_lrs = output_pandas['LTCLR'].values
usdt_lrs = output_pandas['USDTLR'].values

graph_six(xrp_lrs, btc_lrs, eth_lrs, link_lrs, ltc_lrs, usdt_lrs, x_axis, 'Liquidation_Ratios', 'Days', '%', 'A-XRP', 'W-BTC', 'ETH', 'P-LINK', 'LTC', 'USDT')

keeper_holdings = output_pandas['KeeperHoldingPercentage'].values
keeper_tradings = output_pandas['KeeperTradePercentage'].values

graph_two(keeper_holdings, keeper_tradings, x_axis, 'Keeper_Statistics', 'Days', '%', 'Keeper Holdings Percentage', 'Keeper Trade Percentage')

