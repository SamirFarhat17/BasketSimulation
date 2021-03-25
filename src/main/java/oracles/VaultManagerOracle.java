package oracles;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;
import stakeholders.Vault;

import java.io.IOException;
import java.util.ArrayList;

public class VaultManagerOracle extends Oracle {
    // Attributes of vault system
    public double mintedBasket;
    public double lockedXRP;
    public double lockedBTC;
    public double lockedETH;
    public double lockedLINK;
    public double lockedLTC;
    public double lockedUSDT;
    public ArrayList<Vault> activeVaults;
    public ArrayList<Vault> auctionVaults;

    // Constructor
    public VaultManagerOracle(String oracleID, String oracleStatus, double mintedBasket, double lockedXRP, double lockedBTC,
                              double lockedETH, double lockedLINK, double lockedLTC, double lockedUSDT, ArrayList<Vault> activeVaults) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.mintedBasket = mintedBasket;
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
    public static ArrayList<Vault> initialActiveVaults;

    static {
        try {
            initialActiveVaults = getInitialActiveVaults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Methods
    private static ArrayList<Vault> getInitialActiveVaults() throws IOException {
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
            currVault = new Vault(vaultID, ownerID,true, minted, colatType, colatAmount);
            allVaults.add(currVault);
        }

        return allVaults;
    }

    public static void addActiveVault(ArrayList<Vault> vs, Vault v) {
        vs.add(v);
    }

    public static void updateVaults() {

    }

    public static void checkLiquidations() {

    }

    public static void liquidateVault() {

    }

}
