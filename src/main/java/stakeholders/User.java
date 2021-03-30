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
    public double bsktTokens;
    public double bsktHoldings;
    public HashMap<String,Double> feesOwed;
    public double desiredBasket;
    public HashMap<String, Double> colatWanted;

    // Constructor
    public User(String userID, ArrayList<Vault> vaults, HashMap<String,Double> collaterals, double bsktHoldings,
                double bsktTokens, HashMap<String,Double> feesOwed, double desiredBasket, HashMap<String,Double> colatWanted) throws IOException {
        this.userID = userID;
        this.vaults = vaults;
        this.collaterals = collaterals;
        this.bsktHoldings = bsktHoldings;
        this.bsktTokens = bsktTokens;
        this.feesOwed = feesOwed;
        this.desiredBasket = desiredBasket;
        this.colatWanted = colatWanted;
    }

    // Getters and Setters
    public String getUserID() { return this.userID; }

    public ArrayList<Vault> getVaults() { return this.vaults; }
    public void setVaults(ArrayList<Vault> vaults) { this.vaults = vaults; }
    public void addVault(Vault vault) { this.vaults.add(vault); }

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
            this.collaterals.replace(collateralType, amount);
        }
        return this.collaterals;
    }

    public double getBsktHoldings() { return this.bsktHoldings; }
    public void setBsktHoldings(double bsktHoldings) {this.bsktHoldings = bsktHoldings;}

    public double getBsktTokens() { return this.bsktHoldings; }
    public void setBsktTokens(double bsktHoldings) { this.bsktHoldings = bsktHoldings; }

    public HashMap<String,Double> getFeesOwed() { return this.feesOwed; }
    public void setFeesOwed(HashMap<String,Double> feesOwed) { this.feesOwed = feesOwed;}
    // Add fees without conflict
    public void addFees(String feeType, double amount) {
        if(!this.feesOwed.containsKey(feeType)) {
            this.feesOwed.put(feeType, amount);
        }
        else {
            this.feesOwed.replace(feeType, amount);
        }
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
            this.colatWanted.replace(collateralType, amount);
        }
        return this.colatWanted;
    }


    // Variables
    ArrayList<User> buyerList = new ArrayList<>();
    ArrayList<User> sellerList = new ArrayList<>();
    public static String[] collateralTypes = {"A-XRP", "ETH", "LINK", "W-BTC", "USDT", "P-LTC"};


    // Methods
    public static ArrayList<User> getInitialUsers(double basketPrice, ArrayList<Vault> vaults) throws IOException {
        String usersDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/User-Data/Users_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(usersDataPath);
        ArrayList<User> users = new ArrayList<User>();

        for(String key: fullJson.keySet()) {
            User currUser;
            String currUserID;
            double currBasketHoldings;
            double currBasketTokens;
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
            currBasketTokens = currBasketHoldings/basketPrice;
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

            currUser = new User(currUserID, currVaults, currCollaterals, currBasketHoldings, currBasketTokens, currFeesOwed, currDesiredBasket, currColatWanted);

            users.add(currUser);

        }

        return users;
    }


    public void generateStabilityFees(ArrayList<CollateralOracle> oracles) {
        String type;
        double amount;
        double multiplier = 0.0;
        Oracle oracle;
        for(Vault v : getVaults()) {
            type = v.getCollateralType();
            for( CollateralOracle o : oracles) {
                if(o.getCollateralType().equals(type)) multiplier = o.stabilityFee;
            }
            amount = v.getCollateralAmount();
            addFees("Stability Fee", amount*multiplier/(100*365));
        }
    }


    public static void generateUserWants(ArrayList<User> userList, ArrayList<User> sellers, ArrayList<User> buyers, double userSeed, double basketPrice,
                                         double collateralSeed, ArrayList<CollateralOracle> colatOracles, VaultManagerOracle vaultManagerOracle) {
        boolean entered;
        sellers.clear();
        buyers.clear();

        int buyerNum = 0;
        int sellerNum = 0;
        double basketSale = 0;
        double basketBuy = 0;


        for(User u : userList) {
            u.generateStabilityFees(colatOracles);
            /*
            System.out.println("ID: " + u.getUserID() + "\nBasket Holdings: " + u.getBsktHoldings() + "\nDesired Basket: " + u.getDesiredBasket()
                    + "\nCollateral Holdings: " + u.getCollaterals().get("A-XRP") + " " + u.getCollaterals().get("W-BTC")  + " " + u.getCollaterals().get("ETH") + " "
                    + u.getCollaterals().get("LINK") + " " + u.getCollaterals().get("P-LTC") + " " + u.getCollaterals().get("USDT")  + "\nWanted Collateral: "
                    + u.getColatWanted().get("A-XRP") + " " + u.getColatWanted().get("W-BTC")  + " " + u.getColatWanted().get("ETH") + " " + u.getColatWanted().get("LINK")
                    + " " + u.getColatWanted().get("P-LTC") + " " + u.getColatWanted().get("USDT"));
            */
            entered = false;
            Random rn = new Random();
            int status = rn.nextInt(50) + 1;
            int vaultDescision = rn.nextInt(3);
            HashMap<String,Double> userColats = new HashMap<>();

            if(u.getBsktHoldings() < 0) {
                u.setDesiredBasket(u.getBsktHoldings() * -1);
                buyers.add(u);
                buyerNum++;
                basketBuy += u.getDesiredBasket();
                entered = true;
            }

            if(u.bsktHoldings > 0 && status == 12 && !entered) {
                u.setDesiredBasket(userSeed * (0 + Math.random() * (1)));
                entered = true;
                if(vaultDescision == 2) {
                    userColats = u.getCollaterals();
                    for(String colat : DataExtraction.shuffleArray(CollateralOracle.collateralTypes)) {
                        if(userColats.get(colat) > u.getDesiredBasket() * 1.5) {
                            Vault.openVault(u, u.getUserID(), u.getDesiredBasket(), u.getDesiredBasket()/basketPrice, colat, u.getDesiredBasket() * 1.5, vaultManagerOracle);
                        }
                    }
                }
                else {
                    buyers.add(u);
                    buyerNum++;
                    basketBuy += u.getDesiredBasket();
                }
            }

            if(u.bsktHoldings > 0 && status == 13 && !entered) {
                String colat = CollateralOracle.collateralTypes[rn.nextInt(6)];
                if(!u.getVaults().isEmpty()) {
                    vaultManagerOracle.closeVault(u, u.getVaults().get(0), basketPrice);
                }
                else u.addCollateralWanted(colat, u.getColatWanted().get(colat) + collateralSeed * (0 + Math.random() * (2)));
                sellers.add(u);
                sellerNum++;
                basketSale += u.getColatWanted().get(colat);
                entered = true;
            }

            else if(u.bsktHoldings > 0 && (status == 4 || status == 5) && !entered) {
                String colat = CollateralOracle.collateralTypes[rn.nextInt(6)];
                u.addCollateralWanted(colat, u.bsktHoldings * (0 + Math.random() * (1)));
                sellers.add(u);
                sellerNum++;
                basketSale += u.getColatWanted().get(colat);
                entered = true;
            }
            /*
            System.out.println("buyerNum: " + buyerNum);
            System.out.println("basketBuy: " + basketBuy);
            System.out.println("sellerNum: " + sellerNum);
            System.out.println("basketSale: " + basketSale);
            */

            /*
            System.out.println("Basket Holdings: " + u.getBsktHoldings() + "\nDesired Basket: " + u.getDesiredBasket()
                    + "\nCollateral Holdings: " + u.getCollaterals().get("A-XRP") + " " + u.getCollaterals().get("W-BTC")  + " " + u.getCollaterals().get("ETH") + " "
                    + u.getCollaterals().get("LINK") + " " + u.getCollaterals().get("P-LTC") + " " + u.getCollaterals().get("USDT")  + "\nWanted Collateral: "
                    + u.getColatWanted().get("A-XRP") + " " + u.getColatWanted().get("W-BTC")  + " " + u.getColatWanted().get("ETH") + " " + u.getColatWanted().get("LINK")
                    + " " + u.getColatWanted().get("P-LTC") + " " + u.getColatWanted().get("USDT"));
            */
        }
    }


    public static void generateNewUsers(ArrayList<User> userBase, double userSeed, double collateralSeed, double basketPrice, VaultManagerOracle vaultManagerOracle) throws IOException {
        String[] collaterals = CollateralOracle.collateralTypes;
        System.out.println(userBase.size());
        int userBaseSize = userBase.size();
        Random rn = new Random();
        int newUsers = (int)Math.log(rn.nextInt(userBaseSize/10) + userBaseSize/30);
        int selector;
        User user;

        Random userSeedRandom = new Random();

        String userID;
        ArrayList<Vault> userVaults = new ArrayList<>();
        HashMap<String,Double> userCollaterals = new HashMap<>();
        double userBsktTokens;
        double userBsktMinted;
        HashMap<String, Double> feesOwed = new HashMap<String, Double>();
        feesOwed.put("Stability Fee", 0.0);
        feesOwed.put("Liquidation Fee", 0.0);
        double desiredBasket = 0.0;
        HashMap<String, Double> collateralsWanted = new HashMap<>();
        collateralsWanted.put("W-BTC", 0.0);
        collateralsWanted.put("ETH", 0.0);
        collateralsWanted.put("P-LTC", 0.0);
        collateralsWanted.put("USDT", 0.0);
        collateralsWanted.put("LINK", 0.0);
        collateralsWanted.put("A-XRP", 0.0);


        for(int i = 0; i < newUsers; i++) {
            userID = DataExtraction.generateUserID();

            userBsktMinted = userSeedRandom.nextGaussian() * (userSeed/5) + userSeed;
            userBsktTokens = userBsktMinted/basketPrice;

            for(String colat : collaterals) {
                if(userSeedRandom.nextInt(4) == 2) userCollaterals.put(colat, userSeedRandom.nextGaussian() * collateralSeed/5 + collateralSeed);
                else userCollaterals.put(colat, 0.0);
            }

            rn = new Random();
            selector = rn.nextInt(5);

            user = new User(userID, userVaults, userCollaterals, userBsktMinted, userBsktTokens, feesOwed, desiredBasket, collateralsWanted);

            if(selector == 3) {
                Vault.openVault(user, userID,  userBsktMinted, userBsktTokens, collaterals[userSeedRandom.nextInt(5)], userBsktMinted * 1.5, vaultManagerOracle);
            }
            else {
                user.setDesiredBasket(userBsktMinted);
            }
            userBase.add(user);
        }

    }


    public static void buyBasket(User buyer, User seller, double payment, double basketPrice, String collateralType) {
        buyer.setBsktHoldings(buyer.getBsktHoldings() + payment);
        buyer.setBsktTokens(buyer.getBsktTokens() + payment/basketPrice);
        buyer.setDesiredBasket(buyer.getDesiredBasket() - payment);
        buyer.addCollaterals(collateralType, buyer.getCollaterals().get(collateralType) - payment);

        seller.setBsktHoldings(seller.getBsktHoldings() - payment);
        seller.setBsktTokens(seller.getBsktTokens() - payment/basketPrice);
        seller.addCollateralWanted(collateralType, seller.getColatWanted().get(collateralType) - payment);
        seller.addCollaterals(collateralType, seller.getCollaterals().get(collateralType) + payment);

    }


    public static void generateUserCollaterals(ArrayList<User> userBase, double collateralSeed) {
        String[] collaterals = CollateralOracle.collateralTypes;
        HashMap<String,Double> userCollaterals;
        Random userSeedRandom = new Random();

        for(User u : userBase) {
            userCollaterals = u.getCollaterals();
            for(String colat : collaterals) {
                if(userSeedRandom.nextInt(20) == 2) userCollaterals.put(colat, userCollaterals.get(colat) + userSeedRandom.nextGaussian() * collateralSeed/20 + collateralSeed/5);
                else userCollaterals.put(colat, 0.0);
            }
            u.setCollaterals(userCollaterals);
        }

    }


    public void payStabilityFee(double basketPrice) {
        double stabilityFee = getFeesOwed().get("Stability Fee");
        setBsktHoldings(getBsktHoldings() - stabilityFee);
        setBsktTokens(getBsktTokens() - stabilityFee/basketPrice);
        addFees("Stability Fee", getFeesOwed().get("Stability Fee") - stabilityFee);
     }


    public void payLiquidationFee(double basketPrice) {
        double liquidationFee = getFeesOwed().get("Liquidation Fee");
        setBsktHoldings(getBsktHoldings() - liquidationFee);
        setBsktTokens(getBsktTokens() - liquidationFee/basketPrice);
        addFees("Liquidation Fee", getFeesOwed().get("Liquidation Fee") - liquidationFee);
    }

}
