import math
import random
import sys
import numpy as np

cpi = sys.argv[1]
bskt_value = float(float(cpi) / 10)
days = int(sys.argv[2])
user_base_size = int(sys.argv[3])
user_gen_seed = int(sys.argv[4])
keeper_gen_seed = int(sys.argv[5])
colat_gen_seed = int(sys.argv[6])
bsr_gen_seed = float(sys.argv[7])

def round_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.ceil(n * multiplier) / multiplier

day = days-1828

collaterals = ['A-XRP', 'ETH', 'LINK', 'W-BTC', 'USDT', 'P-LTC']

# Users
user_base = (np.random.normal(user_gen_seed, user_gen_seed/5, user_base_size))

# Keepers
total_user_basket = 0
for user in user_base:
    total_user_basket = total_user_basket + user
keepers_holdings = (total_user_basket) * keeper_gen_seed/100
# Complete basket
total_basket = total_user_basket + keepers_holdings

print(user_base)

# Total Basket
total_basket = (np.random.normal(50000000000,))

# BSR
bsr = (np.random.normal(4, 2, 200))
for i in range(0,bsr.size):
    if bsr[i] < 0:
        bsr[i] = 0
    bsr[i] = round_up(bsr[i], 1)

print(bsr)


# Collateral parameters


