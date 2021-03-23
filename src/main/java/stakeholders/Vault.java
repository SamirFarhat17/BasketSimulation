package stakeholders;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vault {

    // Attribute of Vaults
    public String vaultID;
    public String ownerID;
    public Boolean status;
    public String collateralType;
    public double collateralAmount;
    public double bsktMinted;

    // Constructor
    public Vault(String vaultID, String ownerID, Boolean status,  double minted, String collateralType,
                 double collateralAmount) {
        this.vaultID = vaultID;
        this.ownerID = ownerID;
        this.status = status;
        this.bsktMinted = minted;
        this.collateralType = collateralType;
        this.collateralAmount = collateralAmount;
    }

    // Getters and Setters
    public String getVaultID() {
        return this.vaultID;
    }

    public String getOwnerID() {
        return this.ownerID;
    }

    public Boolean getStatus() {
        return this.status;
    }
    public void setStatus(boolean status) { this.status = status; }

    public String getCollateralType() { return this.collateralType; }

    public double getCollateralAmount() {
        return this.collateralAmount;
    }

    public double getBsktMinted() {
        return this.bsktMinted;
    }

    // Variables
    public static ArrayList<Vault> allActiveVaults;

    static {
        try {
            allActiveVaults = getAllActiveVaults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods
    private static ArrayList<Vault> getAllActiveVaults() throws IOException {
        String vaultDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Vault-Data/Vault_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(vaultDataPath);
        ArrayList<Vault> allVaults = new ArrayList<Vault>();

        Vault currVault;
        String vaultID;
        String ownerID;
        double minted;
        String colatType;
        double colatAmount;

        for(String key : fullJson.keySet()) {
            // JSON operations
            JSONArray result = fullJson.getJSONArray(key);
            JSONObject value = result.getJSONObject(0);
            vaultID = key;
            ownerID = value.getString("owner");
            minted = value.getDouble("Minted");
            colatType = value.getString("Collateral Type");
            colatAmount = value.getDouble("Collateral Amount");
            currVault = new Vault(vaultID, ownerID,true, minted, colatType, colatAmount);
            allVaults.add(currVault);
        }

        return allVaults;
    }

    public static void addActiveVault(ArrayList<Vault> vs, Vault v) {
        vs.add(v);
    }

}
