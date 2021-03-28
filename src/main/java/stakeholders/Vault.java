package stakeholders;

import json.DataExtraction;
import json.JsonReader;
import oracles.VaultManagerOracle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vault {

    // Attribute of Vaults
    public final String vaultID;
    public final String ownerID;
    public Boolean status;
    public double bsktMinted;
    public final double bsktTokensMinted;
    public String collateralType;
    public double collateralAmount;

    // Constructor
    public Vault(String vaultID, String ownerID, Boolean status, double minted, double mintedTokens, String collateralType, double collateralAmount) {
        this.vaultID = vaultID;
        this.ownerID = ownerID;
        this.status = status;
        this.bsktMinted = minted;
        this.bsktTokensMinted = mintedTokens;
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
    public void setCollateralAmount(double collateralAmount) { this.collateralAmount = collateralAmount; }

    public double getBsktMinted() {
        return this.bsktMinted;
    }
    public void setBsktMinted(double bsktMinted) { this.bsktMinted = bsktMinted; }

    public double getBsktTokensMinted() { return this.bsktTokensMinted; }

    // Methods
    public static void openVault(User user, String userID, double basketMinted, double bsktTokensMinted, String colatType, double colatAmount, VaultManagerOracle vaultManagerOracle) {
        String vaultID = DataExtraction.generateVaultID();
        boolean status = true;

        Vault vault = new Vault(vaultID, userID, status, basketMinted, bsktTokensMinted, colatType, colatAmount);

        user.setDesiredBasket(user.getDesiredBasket() - basketMinted);
        user.setBsktTokens(user.getBsktTokens() + bsktTokensMinted);
        user.setBsktHoldings(user.getBsktHoldings() + basketMinted);

        user.addCollaterals(colatType, user.getCollaterals().get(colatType) - colatAmount);

        vaultManagerOracle.setMintedBasketTokens(vaultManagerOracle.getMintedBasketTokens() + bsktTokensMinted);
        vaultManagerOracle.setMintedBasket(vaultManagerOracle.getMintedBasket() + basketMinted);
        vaultManagerOracle.addActiveVault(vault);

        if(colatType.equals("A-XRP")) vaultManagerOracle.setLockedXRP(vaultManagerOracle.getLockedXRP() + colatAmount);
        if(colatType.equals("W-BTC")) vaultManagerOracle.setLockedBTC(vaultManagerOracle.getLockedBTC() + colatAmount);
        if(colatType.equals("ETH")) vaultManagerOracle.setLockedETH(vaultManagerOracle.getLockedETH() + colatAmount);
        if(colatType.equals("LINK")) vaultManagerOracle.setLockedLINK(vaultManagerOracle.getLockedLINK() + colatAmount);
        if(colatType.equals("P-LTC")) vaultManagerOracle.setLockedLTC(vaultManagerOracle.getLockedLTC() + colatAmount);
        if(colatType.equals("USDT")) vaultManagerOracle.setLockedUSDT(vaultManagerOracle.getLockedUSDT() + colatAmount);

        user.addVault(vault);
    }

}
