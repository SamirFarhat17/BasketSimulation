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

    public double getCollateralAmount() { return this.collateralAmount; }

    public double getBsktMinted() {
        return this.bsktMinted;
    }

    // Methods
    public static void openVault(String vaultID, String userID, Boolean status, String colatType, double colatAmount, double basketMinted) {

    }

}
