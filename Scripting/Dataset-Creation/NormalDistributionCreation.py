import math
from os import truncate

import numpy as np

def round_up(n, decimals=0):
    multiplier = 10 ** decimals
    return math.ceil(n * multiplier) / multiplier

# Users
mu, sigma = 50000, 12500

user_base = (np.random.normal(mu, sigma, 200))
for i in range(0,user_base.size):
    user_base[i] = round_up(user_base[i])

print(user_base)

# BSR
bsr = (np.random.normal(2, 0.5, 200))
print(bsr)
for i in range(0,bsr.size):
    bsr[i] = round_up(bsr[i], 1)

print(bsr)

