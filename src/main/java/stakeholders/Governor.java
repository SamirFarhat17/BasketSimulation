package stakeholders;

import json.JsonReader;
import oracles.BsrOracle;
import oracles.CollateralOracle;
import oracles.VaultManagerOracle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Governor {

    // Individual stakeholders.Governor Attributes
    private final String governorID;
    private String governorStatus;

    // Constructor
    public Governor(String governorID, String governorStatus) {
        this.governorID = governorID;
        this.governorStatus = governorStatus;
    }

    // Getters and Setters
    public String getGovernorID() { return this.governorID; }

    public String getGovernorStatus() {return this.governorStatus; }
    public void setGovernorStatus(String governorStatus) { this.governorStatus = governorStatus; }


    // Variables

    // Methods
    public static double getInitialBasket() throws IOException {
        String govDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Governance-Data/Governance_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(govDataPath);
        JSONArray result = fullJson.getJSONArray("Info");
        JSONObject value = result.getJSONObject(0);

        return value.getDouble("Value");
    }

    public static void changeBSR(double targetPrice, double basketPrice, BsrOracle bsrOracle) {
        if(basketPrice > targetPrice)bsrOracle.setBsr(bsrOracle.getBsr() + bsrOracle.getBsr()/10);
        else if (basketPrice < targetPrice) bsrOracle.setBsr(bsrOracle.getBsr() - bsrOracle.getBsr()/10);
        if(bsrOracle.getBsr() > 9) bsrOracle.setBsr(3.5);
        else if(bsrOracle.getBsr() < 1) bsrOracle.setBsr(4);
    }

    public static void updateDebtCeilings(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                                  CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        for(CollateralOracle collateralOracle: collateralOracles) {
            if(collateralOracle.getCollateralType().equals("A-XRP") && vaultManagerOracle.getLockedXRP() > 0.75 * xrpOracle.getDebtCeiling()) {
                xrpOracle.setDebtCeiling(vaultManagerOracle.getLockedXRP() + (vaultManagerOracle.getLockedXRP() * Math.random()));
                continue;
            }
            if(collateralOracle.getCollateralType().equals("W-BTC") && vaultManagerOracle.getLockedBTC() > 0.75 * btcOracle.getDebtCeiling()) {
                btcOracle.setDebtCeiling(vaultManagerOracle.getLockedBTC() + (vaultManagerOracle.getLockedBTC() * Math.random()));
                continue;
            }
            if(collateralOracle.getCollateralType().equals("ETH") && vaultManagerOracle.getLockedETH() > 0.75 * ethOracle.getDebtCeiling()) {
                ethOracle.setDebtCeiling(vaultManagerOracle.getLockedETH() + (vaultManagerOracle.getLockedETH() * Math.random()));
                continue;
            }
            if(collateralOracle.getCollateralType().equals("LINK") && vaultManagerOracle.getLockedLINK() > 0.75 * linkOracle.getDebtCeiling()) {
                linkOracle.setDebtCeiling(vaultManagerOracle.getLockedLINK() + (vaultManagerOracle.getLockedLINK() * Math.random()));
                continue;
            }
            if(collateralOracle.getCollateralType().equals("P-LTC") && vaultManagerOracle.getLockedLTC() > 0.75 * ltcOracle.getDebtCeiling()) {
                ltcOracle.setDebtCeiling(vaultManagerOracle.getLockedLTC() + (vaultManagerOracle.getLockedLTC() * Math.random()));
                continue;
            }
            if(collateralOracle.getCollateralType().equals("USDT") && vaultManagerOracle.getLockedUSDT() > 0.75 * usdtOracle.getDebtCeiling()) {
                usdtOracle.setDebtCeiling(vaultManagerOracle.getLockedUSDT() + (vaultManagerOracle.getLockedUSDT() * Math.random()));
            }

        }
    }


    public static void updateStabilityFees(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                          CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        for(CollateralOracle collateralOracle: collateralOracles) {

        }
    }


    public static void updateLiquidationRatios(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                           CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        for(CollateralOracle collateralOracle: collateralOracles) {

        }
    }

}
