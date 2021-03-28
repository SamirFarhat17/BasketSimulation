package stakeholders;

import json.DataExtraction;
import json.JsonReader;
import oracles.CollateralOracle;
import oracles.Oracle;
import oracles.VaultManagerOracle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User {

    // stakeholders.User attributes
    public String userID;
    public ArrayList<Vault> vaults;
    public HashMap<String, Double> collaterals;
    public double bsktHoldings;
    public HashMap<String,Double> feesOwed;
    public double desiredBasket;
    public HashMap<String, Double> colatWanted;

    // Constructor
    public User(String userID, ArrayList<Vault> vaults, HashMap<String,Double> collaterals,
                double bsktHoldings, HashMap<String,Double> feesOwed, double desiredBasket, HashMap<String,Double> colatWanted) throws IOException {
        this.userID = userID;
        this.vaults = vaults;
        this.collaterals = collaterals;
        this.bsktHoldings = bsktHoldings;
        this.feesOwed = feesOwed;
        this.desiredBasket = desiredBasket;
        this.colatWanted = colatWanted;
    }

    // Getters and Setters
    public String getUserID() { return this.userID; }

    public ArrayList<Vault> getVaults() { return this.vaults; }
    public void setVaults(ArrayList<Vault> vaults) { this.vaults = vaults; }

    public ArrayList<Vault> appendVault(Vault v) {
        this.vaults.add(v);
        return this.vaults;
    }

    public HashMap<String,Double> getCollaterals() { return this.collaterals; }
    public void setCollaterals(HashMap<String,Double> collaterals) { this.collaterals = collaterals; }
    // Add collaterals without duplicates
    public HashMap<String,Double> addCollaterals(String collateralType, double amount) {
        if(!this.collaterals.containsKey(collateralType)) {
            this.collaterals.put(collateralType, amount);
        }
        else {
            this.collaterals.replace(collateralType, this.collaterals.get(collateralType)+amount);
        }
        return this.collaterals;
    }

    public double getBsktHoldings() { return this.bsktHoldings; }
    public void setBsktHoldings(double bsktHoldings) {this.bsktHoldings = bsktHoldings;}


    public HashMap<String,Double> getFeesOwed() { return this.feesOwed; }
    public void setFeesOwed(HashMap<String,Double> feesOwed) { this.feesOwed = feesOwed;}
    // Add fees without conflict
    public HashMap<String,Double> addFees(String feeType, double amount) {
        if(!this.collaterals.containsKey(feeType)) {
            this.collaterals.put(feeType, amount);
        }
        else {
            this.collaterals.replace(feeType, this.collaterals.get(feeType)+amount);
        }
        return this.collaterals;
    }

    public double getDesiredBasket() { return this.desiredBasket; }
    public void setDesiredBasket(double desiredBasket) { this.desiredBasket = desiredBasket; }

    public HashMap<String,Double> getColatWanted() { return this.colatWanted; }
    public void setColatWanted(HashMap<String,Double> colatWanted) { this.colatWanted = colatWanted; }
    public HashMap<String,Double> addCollateralWanted(String collateralType, double amount) {
        if(!this.colatWanted.containsKey(collateralType)) {
            this.colatWanted.put(collateralType, amount);
        }
        else {
            this.colatWanted.replace(collateralType, this.colatWanted.get(collateralType)+amount);
        }
        return this.colatWanted;
    }

    // Variables

    // Methods
    public static ArrayList<User> getInitialUsers() throws IOException {
        String usersDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/User-Data/Users_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(usersDataPath);
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Vault> vaults = VaultManagerOracle.initialActiveVaults;

        for(String key: fullJson.keySet()) {
            User currUser;
            String currUserID;
            double currBasketHoldings;
            double currDesiredBasket;
            ArrayList<Vault> currVaults = new ArrayList<Vault>();
            HashMap<String,Double> currColatWanted = new HashMap<String,Double>();
            HashMap<String,Double> currCollaterals = new HashMap<String,Double>();
            HashMap<String, Double> currFeesOwed = new HashMap<String, Double>();

            JSONArray result = fullJson.getJSONArray(key);
            JSONObject value = result.getJSONObject(0);
            currUserID = key;

            for(Vault vault : vaults) {
                if(vault.ownerID.equals(key)) currVaults.add(vault);
            }
            currBasketHoldings = value.getDouble("Basket Holdings");
            currCollaterals.put("W-BTC", value.getDouble("W-BTC Holdings"));
            currCollaterals.put("ETH", value.getDouble("ETH Holdings"));
            currCollaterals.put("P-LTC", value.getDouble("P-LTC Holdings"));
            currCollaterals.put("USDT", value.getDouble("USDT Holdings"));
            currCollaterals.put("LINK", value.getDouble("LINK Holdings"));
            currCollaterals.put("A-XRP", value.getDouble("A-XRP Holdings"));

            currFeesOwed.put("Stability Fee", 0.0);
            currFeesOwed.put("Liquidation Fee", 0.0);

            currDesiredBasket = 0.0;

            currColatWanted.put("W-BTC", 0.0);
            currColatWanted.put("ETH", 0.0);
            currColatWanted.put("P-LTC", 0.0);
            currColatWanted.put("USDT", 0.0);
            currColatWanted.put("LINK", 0.0);
            currColatWanted.put("A-XRP", 0.0);

            currUser = new User(currUserID, currVaults, currCollaterals, currBasketHoldings, currFeesOwed, currDesiredBasket, currColatWanted);

            users.add(currUser);

        }

        return users;
    }

    public static void generateStabilityFees(User user, ArrayList<CollateralOracle> oracles) {
        String type;
        double amount;
        double multiplier = 0.0;
        Oracle oracle;
        for(Vault v : user.getVaults()) {
            type = v.getCollateralType();
            for( CollateralOracle o : oracles) {
                if(o.getCollateralType().equals(type)) multiplier = o.stabilityFee;
            }
            amount = v.getCollateralAmount();
            user.addFees("Stability Fee", amount*multiplier/(100*365));
        }
    }

    public static void generateUserWants(ArrayList<User> userList, double userSeed, double collateralSeed, ArrayList<CollateralOracle> colatOracles) {
        boolean entered;

        for(User u : userList) {
            entered = false;
            Random rn = new Random();
            int status = rn.nextInt(15) + 1;
            int vaultDescision = rn.nextInt(3);
            HashMap<String,Double> userColats = new HashMap<>();

            if(u.bsktHoldings > 0 && status == 12 && !entered) {
                u.setDesiredBasket(userSeed * (0 + Math.random() * (1)));
                entered = true;
                if(vaultDescision == 2) {
                    userColats = u.getCollaterals();
                    for(String colat : DataExtraction.shuffleArray(CollateralOracle.collateralTypes)) {
                        if(userColats.get(colat) > u.getDesiredBasket() * 1.5) {
                            Vault.openVault(DataExtraction.generateVaultID(), u.userID, true, colat, u.getDesiredBasket() * 1.5, u.getDesiredBasket());
                        }
                    }
                }
            }

            else if((status == 11 || status == 10) && !entered) {
                u.setDesiredBasket(userSeed * (0 + Math.random() * (2)));
                entered = true;
            }

            if(u.bsktHoldings > 0 && status == 13 && !entered) {
                u.addCollaterals(CollateralOracle.collateralTypes[rn.nextInt(6)], collateralSeed* (0 + Math.random() * (2)));
                entered = true;
            }

            else if(u.bsktHoldings > 0 && (status == 4 || status == 5) && !entered) {

            }
        }
    }

    public static void generateNewUsers() {

    }

    public static void payFee() {

    }

}
