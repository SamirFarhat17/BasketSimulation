import math
import random
from os import truncate

import numpy as np

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


