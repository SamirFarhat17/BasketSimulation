package oracles;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;
import stakeholders.User;
import stakeholders.Vault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class VaultManagerOracle extends Oracle {
    // Attributes of vault system
    public double mintedBasket;
    public double mintedBasketTokens;
    public double lockedXRP;
    public double lockedBTC;
    public double lockedETH;
    public double lockedLINK;
    public double lockedLTC;
    public double lockedUSDT;
    public ArrayList<Vault> activeVaults;
    public ArrayList<Vault> auctionVaults;

    // Constructor
    public VaultManagerOracle(String oracleID, String oracleStatus, double mintedBasket, double mintedBasketTokens, double lockedXRP, double lockedBTC,
                              double lockedETH, double lockedLINK, double lockedLTC, double lockedUSDT, ArrayList<Vault> activeVaults) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.mintedBasket = mintedBasket;
        this.mintedBasketTokens = mintedBasketTokens;
        this.lockedXRP = lockedXRP;
        this.lockedBTC = lockedBTC;
        this.lockedETH = lockedETH;
        this.lockedLINK = lockedLINK;
        this.lockedLTC = lockedLTC;
        this.lockedUSDT = lockedUSDT;
        this.activeVaults = activeVaults;
        this.auctionVaults = new ArrayList<Vault>();
    }

    // Getters and Setters
    public double getMintedBasket() { return this.mintedBasket; }
    public void setMintedBasket(double mintedBasket) { this.mintedBasket = mintedBasket; }

    public double getMintedBasketTokens() { return this.mintedBasketTokens; }
    public void setMintedBasketTokens(double mintedBasketTokens) { this.mintedBasketTokens = mintedBasketTokens; }

    public double getLockedXRP() { return this.lockedXRP; }
    public void setLockedXRP(double locked) { this.lockedXRP = locked; }

    public double getLockedBTC() { return this.lockedBTC; }
    public void setLockedBTC(double locked) { this.lockedBTC = locked; }

    public double getLockedETH() { return this.lockedETH; }
    public void setLockedETH(double locked) { this.lockedETH = lockedBTC; }

    public double getLockedLINK() { return this.lockedLINK; }
    public void setLockedLINK(double locked) { this.lockedLINK = locked; }

    public double getLockedLTC() { return this.lockedLTC; }
    public void setLockedLTC(double locked) { this.lockedLTC = locked; }

    public double getLockedUSDT() { return this.lockedUSDT; }
    public void setLockedUSDT(double locked) { this.lockedUSDT = locked; }

    public ArrayList<Vault> getActiveVaults() { return this.activeVaults; }

    public ArrayList<Vault> getAuctionVaults() { return this.auctionVaults; }

    public void addAuctionVault(Vault vault) { this.auctionVaults.add(vault); }
    public void removeAuctionVault(Vault vault) { this.auctionVaults.remove(vault); }

    public void addActiveVault(Vault vault) { this.activeVaults.add(vault); }
    public void removeActiveVault(Vault vault) { this.activeVaults.remove(vault); }


    // Variables

    // Methods
    public static ArrayList<Vault> getInitialActiveVaults(double basketPrice) throws IOException {
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
            ownerID = value.getString("Owner");
            minted = value.getDouble("Minted");
            colatType = value.getString("Collateral Type");
            colatAmount = value.getDouble("Collateral Amount");
            currVault = new Vault(vaultID, ownerID,true, minted, minted/basketPrice, colatType, colatAmount);
            allVaults.add(currVault);
        }

        return allVaults;
    }

    public static void addActiveVault(ArrayList<Vault> vs, Vault v) {
        vs.add(v);
    }

    public void updateVaults(String previousDate, String date, ArrayList<Vault> vaults, double basketPrice, HashMap<String,Double> fullExchangeXRP,
                             HashMap<String,Double> fullExchangeBTC, HashMap<String,Double> fullExchangeETH, HashMap<String,Double> fullExchangeLINK,
                             HashMap<String,Double> fullExchangeLTC, HashMap<String,Double> fullExchangeUSDT)
    {
        for(Vault vault : vaults) {

            double exchange = 1, exchangeOld = 1, colatAmount = 0;
            String lockedColat = vault.getCollateralType();
            if(lockedColat.equals("A-XRP")) {
                exchangeOld = fullExchangeXRP.get(previousDate);
                exchange = fullExchangeXRP.get(date);
                setLockedXRP(getLockedXRP() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedXRP(getLockedXRP() + colatAmount);
            }
            if(lockedColat.equals("W-BTC")) {
                exchangeOld = fullExchangeBTC.get(previousDate);
                exchange = fullExchangeBTC.get(date);
                setLockedBTC(getLockedBTC() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedBTC(getLockedBTC() + colatAmount);
            }
            if(lockedColat.equals("ETH")) {
                exchangeOld = fullExchangeETH.get(previousDate);
                exchange = fullExchangeETH.get(date);
                setLockedETH(getLockedETH() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedETH(getLockedETH() + colatAmount);
            }
            if(lockedColat.equals("LINK")) {
                exchangeOld = fullExchangeLINK.get(previousDate);
                exchange = fullExchangeLINK.get(date);
                setLockedLINK(getLockedLINK() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedLINK(getLockedLINK() + colatAmount);
            }
            if(lockedColat.equals("P-LTC")) {
                exchangeOld = fullExchangeLTC.get(previousDate);
                exchange = fullExchangeLTC.get(date);
                setLockedLTC(getLockedLTC() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedLTC(getLockedLTC() + colatAmount);
            }
            if(lockedColat.equals("USDT")) {
                exchangeOld = fullExchangeUSDT.get(previousDate);
                exchange = fullExchangeUSDT.get(date);
                setLockedUSDT(getLockedUSDT() - vault.getCollateralAmount());
                colatAmount = vault.getCollateralAmount() * (exchange/exchangeOld);
                setLockedUSDT(getLockedUSDT() + colatAmount);
            }
            vault.setCollateralAmount(colatAmount);
            vault.setBsktMinted(vault.getBsktTokensMinted()/basketPrice);
        }
    }

    public void checkLiquidations(VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase, ArrayList<Vault> vaults, ArrayList<CollateralOracle> collateralOracles, double basketPrice) {
        String colatType;
        double colatAmount;
        CollateralOracle collateralOracle = collateralOracles.get(0);
        Vault vault;

        for(int i = 0; i < vaults.size(); i++) {
            vault = vaultManagerOracle.getActiveVaults().get(i);
            colatType = vault.getCollateralType();
            colatAmount = vault.getCollateralAmount();

            for(CollateralOracle colatOracle: collateralOracles) {
                if(colatOracle.getCollateralType().equals(colatType)) collateralOracle = colatOracle; break;
            }

            if(colatAmount < vault.getBsktTokensMinted() * basketPrice * (collateralOracle.getLiquidationRatio()/100)) {
                liquidateVault(userBase, vault);
                removeActiveVault(vault);
                addAuctionVault(vault);
                if(colatType.equals("A-XRP")) setLockedXRP(getLockedXRP() - colatAmount);
                if(colatType.equals("W-BTC")) setLockedBTC(getLockedBTC() - colatAmount);
                if(colatType.equals("ETH")) setLockedETH(getLockedETH() - colatAmount);
                if(colatType.equals("LINK")) setLockedLINK(getLockedLINK() - colatAmount);
                if(colatType.equals("P-LTC")) setLockedLTC(getLockedLTC() - colatAmount);
                if(colatType.equals("USDT")) setLockedUSDT(getLockedUSDT() - colatAmount);
            }
        }

    }

    public static void liquidateVault(ArrayList<User> userBase, Vault vault) {
        String userID = vault.ownerID;
        ArrayList<Vault> userVaults;
        
        for(User user: userBase) {
            if(user.getUserID().equals(userID)) {
                userVaults = user.getVaults();
                userVaults.remove(vault);
                user.setVaults(userVaults);
                user.addFees("Liquidation Fee", vault.bsktMinted*0.12);
                break;
            }
        }
    }

}
