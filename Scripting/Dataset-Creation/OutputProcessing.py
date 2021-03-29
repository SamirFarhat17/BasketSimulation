import os
import sys
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import sklearn
import plotly.graph_objects as go
from plotly.colors import n_colors

args = sys.argv
output_data_path = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Scripting/Simulation-Raw/" + args[0] + "_" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5]+ ".csv"
print(output_data_path)