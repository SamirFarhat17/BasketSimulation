import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

    // User attributes
    public String userID;
    public ArrayList<Vault> vaults;
    public HashMap<String, Double> collaterals;
    public double basketHoldings;
    public HashMap<String,Double> feesOwed;

    // Constructor
    public User(String userID, ArrayList<Vault> vaults, HashMap<String,Double> collaterals,
                double basketHoldings, HashMap<String,Double> feesOwed) {
        this.userID = userID;
        this.vaults = vaults;
        this.collaterals = collaterals;
        this.basketHoldings = basketHoldings;
        this.feesOwed = feesOwed;
    }

    // Getters and Setters
    public String getUserID() { return this.userID; }

    public ArrayList<Vault> getVaults() { return this.vaults; }
    public ArrayList<Vault> setVaults(ArrayList<Vault> vaults) { this.vaults = vaults; }
    public ArrayList<Vault> appendVault(Vault v) {
        this.vaults.add(v);
        return this.vaults;
    }

    

}
