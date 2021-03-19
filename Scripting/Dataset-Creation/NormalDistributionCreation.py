import math
import random
import sys
import numpy as np

cpi = sys.argv[1]
bskt_value = float(float(cpi) / 10)
days = int(sys.argv[2])
user_base_size = int(sys.argv[3])
user_gen_seed = sys.argv[4]
keeper_gen_seed = int(sys.argv[5])
colat_gen_seed = int(sys.argv[6])
bsr_gen_seed = float(sys.argv[7])

def round_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.ceil(n * multiplier) / multiplier

# Users
user_base = (np.random.normal(12500, 5000, 200))
for i in range(0,user_base.size):
    if user_base[i] < 0:
        user_base[i] = 1000 + random.randint(0, 1000)
    user_base[i] = round_up(user_base[i])

print(user_base)
users = user_base[random.randint(0,user_base.size)]

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


