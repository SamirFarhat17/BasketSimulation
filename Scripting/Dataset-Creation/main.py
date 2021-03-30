# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec']

# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    #exec(compile(open('CpiToJson.py', "rb").read(), 'CpiToJson.py', 'exec'))
    #exec(compile(open('CollateralToJson.py', "rb").read(), 'CollateralToJson.py', 'exec'))
    #exec(compile(open('NormalDistributionCreation.py', "rb").read(), 'NormalDistributionCreation.py', 'exec'))
    #exec(compile(open('OutputProcessing.py', "rb").read(), 'OutputProcessing.py', 'exec'))

    '''import psutil
    import os
    import numpy as np
    import pandas as pd
    import matplotlib.pyplot as plt
    import seaborn as sns
    import sklearn
    import plotly.graph_objects as go
    from plotly.colors import n_colors

    headerColor = 'grey'
    rowEvenColor = 'lightgrey'
    rowOddColor = 'lightgoldenrodyellow'

    fig = go.Figure(data=[go.Table(
        header=dict(
            values=['<b>Parameter</b>', '<b>Specification</b>', '<b>Reasoning</b>', '<b>Effect</b>'],
            line_color='white',
            fill_color=headerColor,
            align=['center'],
            font=dict(color='white', size=14)
        ),
        cells=dict(
            values=[

                [
                    'Simulation start date',
                    'User Base Size',
                    'User Initial BSKT Holdings Seed',
                    'BSKT Savings Rate',
                    'Liquidation Ratios and Stability Fees',
                    'User Vault Opening Frequency and User Trading Frequency Seed',
                    'Keeper BSKT Holdings Seed'
                ],

                [
                    'From 2016-01-01 to 2020-01-01 spaced out over 5 different configurations',
                    '10,000 to 1,500,000 spaced out over 15 different configurations',
                    '500 to 5000 spaced out over 5 configurations. Randomly selected for each user with a normal distribution, with a strong left-tail bias,  where the mean is decided by the seed, and the variance is randomly chosen between one to a fifth of the seed.',
                    '0% to 12 % spaced out over 6 configurations',
                    'Between 110% and 160% for liquidation ratios and 0% to 7% as determined by system during simulation runs',
                    'Normal distribution with mean 12.5 and a standard deviation of 4.33',
                    '4% to 16% spaced over 3 configurations',
                ],

                [
                    'The timeframe in which simulations take place were selected to ensure a diverse collateral arsenal with which to mint BSKT. Ethereum currencies are a contemporary construction and it is rare to find crypocurrencies that were established before 2016.',
                    'The quantity of users was selected to be applicabe towards the userbase of the Maker Foundation. These user base sizes reflect the user base size of Dai from its earliest stages all the way to its state now',
                    'This parameter strongly reflects the data available from the Maker Foundation. The average normally has a few hundred to a few thousand and decreasingly fewer users who have tens of thousands or hundreds of thousands of Dai. The high left-tail bias reflects the fact that very large numbers of users will have only tens of Dai while high amount owners are rarer.',
                    'The starting BSR reflects the different values that the DSR has taken with the Maker Foundation.'
                    'These ranges for the collateralization parallel those which are seen with Dai. These are determined by governance(Like in Dai) during the simulation, so it changes dynamically.',
                    'According to the data sets of the Oasis Exchange, the platform most commonly used for opening Dai vaults, users typically create a new vault every 15 days on average, and close them every 11 days on average. So this distribution is reflective of the Maker Doundation, and is chosen randomly for all users withion the normal distribution. Moreover, only about a third of Dai users gained Dai through opening a vault (most just buy Dai with other currencies), which is reflected in the simulation as well.',
                    'This parameter reflects the typical holdings of keeper entities throughout the life-cycle of Dai.',
                ],

                [
                    'The start date affects the sequence of cpis and collateral exchange rates, since this is the data that was taken directly from available records. This means that scripts can be chosen to specifically avoid or focus on the behavior of BSKT during periods where the CPI and/or collateral price is highly volatile or more consistent  ',
                    'The size of the userbase affects numerous aspects in the real-world that are reflected in the simulation. One of these is that the adoption of new users is proportional to the size of the user-base. Another natural effect is that BSKT minted increases proportionally with the user base, which means that debt cielings, trade volume, locked collateral, vault openings, and vault liquidations all change relatively linearly with the user base.',
                    'The amount of BSKT held by users affects the amount that is traded and leads to increased debt ceilings for collaterals. ',
                    'The BSR affects the trading volume and the ratio at which users hold collateral and BSKT as well as the frequency at which collateral and BSKT is traded, e.g if the BSR is higher than the incentive to hold on to basket is higher. the BSR changes dynamically with governance decisions to affect supply and demand to target the ideal BSKT value.',
                    'The liquidation ratios and stability fees change dynamically during simulation. Currencies which demonstrate higher volatility are held to higher liquidation ratios but lower stability fees. More stable currencies generally have lower liquidation ratios, as it is less likely for a currency to not be sufficent to cover the minted basket. However, higher stability fees disincentivezes high stability currencies, improving the diversity of currencies that back BSKT.',
                    'This seed affects trading frequency and vault activity to make it best mimic behaviours observed in real markets.',
                    'This parameter affects the influence keepers exert over the system, essentially skewing the way in which keeper movement affects the supply and demand curve.',
                ],

            ],
            line_color='white',
            # 2-D list of colors for alternating rows
            fill_color=[[rowOddColor, rowEvenColor, rowOddColor, rowEvenColor,
                         rowOddColor, rowEvenColor, rowOddColor, rowEvenColor, rowOddColor, rowEvenColor, rowOddColor]],
            align=['left'],
            font=dict(color='black', size=14),
        ))
    ])
    fig.update_layout(width=1200, height=1600)

    fig.show()'''
