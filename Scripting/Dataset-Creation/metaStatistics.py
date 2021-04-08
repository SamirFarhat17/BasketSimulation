datapath = "../Simulation-Configs/holders/"

textfile = ""

string1 = '"CPI":'
string2 = '"Days in Sim":'
string3 = '"User-base Size":'
string4 = '"User Generation Seed":'
string5 = '"Keeper generation Seed":'
string6 = '"Collateral Generation Seed":'
string7 = '"BSR Generation Seed":'

dates = {"1500", "1200", "900", "600", "300"}

user_base_size = {"10000", "50000", "200000", "500000", "1000000", "1500000"}

basket_holdings = {"500", "1000", "2000", "3500", "5000"}

savings_rate = {"0", "3", "6", "9", "10.5", "12"}

keeper_holdings = {"4", "8", "12", "16"}

for date in dates:
    for user in user_base_size:
        for basket in basket_holdings:
            for rate in savings_rate:
                for holding in keeper_holdings:
                    outF = open(datapath + "days" + date + "_userbase" + user + "_bsktseed" + basket + "_keeperseed" + holding + "_bsr" + rate + ".txt", "w")
                    outF.write(string1 + "100" + "\n")
                    outF.write(string2 + date + "\n")
                    outF.write(string3 + user + "\n")
                    outF.write(string4 + basket + "\n")
                    outF.write(string5 + holding + "\n")
                    outF.write(string6 + str(int(user)/4) + "\n")
                    outF.write(string7 + rate + "\n")
                    outF.close()
