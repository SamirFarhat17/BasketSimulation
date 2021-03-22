package stakeholders;

import java.util.ArrayList;
import java.util.HashMap;

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
                double bsktHoldings, HashMap<String,Double> feesOwed, double desiredBasket, HashMap<String,Double> colatWanted) {
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

}
