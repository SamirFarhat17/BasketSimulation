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
import java.util.Random;

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
    private static int count = 0;
    private static Random randomizer;

    // Methods
    public static double getInitialBasket() throws IOException {
        String govDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Governance-Data/Governance_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(govDataPath);
        JSONArray result = fullJson.getJSONArray("Info");
        JSONObject value = result.getJSONObject(0);

        return value.getDouble("Value");
    }

    public static void changeBSR(double targetPrice, double basketPrice, BsrOracle bsrOracle) {
        count++;
        if (count == 30) {
            count = 0;
            if(basketPrice > targetPrice) {
                bsrOracle.setBsr(3.5 + Math.random() * (7 - 3.5));
            }
            else {
                bsrOracle.setBsr(0 + Math.random() * (3.5 - 0));
            }
        }
    }

    public static void updateDebtCeilings(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                                  CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        if(count == 28) {
            for (CollateralOracle collateralOracle : collateralOracles) {
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
    }


    public static void updateStabilityFees(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                          CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        double currentLocked;
        double currentCeiling;
        if(count == 28) {
            for(CollateralOracle collateralOracle : collateralOracles) {
                if(collateralOracle.getCollateralType().equals("A-XRP")) {
                    currentLocked = vaultManagerOracle.getLockedXRP();
                    currentCeiling = xrpOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) xrpOracle.setStabilityFee(xrpOracle.getStabilityFee() + Math.random() * ((xrpOracle.getStabilityFee()/5)- 0));
                    else if(xrpOracle.getStabilityFee() > 0.5) xrpOracle.setStabilityFee(xrpOracle.getStabilityFee() - Math.random() * ((xrpOracle.getStabilityFee()/5)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("W-BTC")) {
                    currentLocked = vaultManagerOracle.getLockedBTC();
                    currentCeiling = btcOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) btcOracle.setStabilityFee(btcOracle.getStabilityFee() + 0 + Math.random() * ((btcOracle.getStabilityFee()/5)- 0));
                    else if(btcOracle.getStabilityFee() > 0.5) btcOracle.setStabilityFee(btcOracle.getStabilityFee() - Math.random() * ((btcOracle.getStabilityFee()/5)- 0));
                    continue;
                }
                if (collateralOracle.getCollateralType().equals("ETH")) {
                    currentLocked = vaultManagerOracle.getLockedETH();
                    currentCeiling = ethOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) ethOracle.setStabilityFee(ethOracle.getStabilityFee() + Math.random() * ((ethOracle.getStabilityFee()/5)- 0));
                    else if(ethOracle.getStabilityFee() > 0.5) ethOracle.setStabilityFee(ethOracle.getStabilityFee() - Math.random() * ((ethOracle.getStabilityFee()/5)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("LINK")) {
                    currentLocked = vaultManagerOracle.getLockedLINK();
                    currentCeiling = linkOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) linkOracle.setStabilityFee(linkOracle.getStabilityFee() + Math.random() * ((linkOracle.getStabilityFee()/5)- 0));
                    else if(linkOracle.getStabilityFee() > 0.5) linkOracle.setStabilityFee(linkOracle.getStabilityFee() - Math.random() * ((linkOracle.getStabilityFee()/5)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("P-LTC")) {
                    currentLocked = vaultManagerOracle.getLockedLTC();
                    currentCeiling = ltcOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) ltcOracle.setStabilityFee(ltcOracle.getStabilityFee() + Math.random() * ((ltcOracle.getStabilityFee()/5)- 0));
                    else if(ltcOracle.getStabilityFee() > 0.5) ltcOracle.setStabilityFee(ltcOracle.getStabilityFee() - Math.random() * ((ltcOracle.getStabilityFee()/5)- 0));
                }
            }
        }
    }


    public static void updateLiquidationRatios(CollateralOracle xrpOracle, CollateralOracle btcOracle, CollateralOracle ethOracle,CollateralOracle linkOracle, CollateralOracle ltcOracle,
                                           CollateralOracle usdtOracle, ArrayList<CollateralOracle> collateralOracles, VaultManagerOracle vaultManagerOracle, ArrayList<User> userBase) {
        double currentLocked;
        double currentCeiling;
        if(count == 28) {
            for(CollateralOracle collateralOracle : collateralOracles) {
                if(collateralOracle.getCollateralType().equals("A-XRP")) {
                    currentLocked = vaultManagerOracle.getLockedXRP();
                    currentCeiling = xrpOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) xrpOracle.setLiquidationRatio(xrpOracle.getLiquidationRatio() - Math.random() * ((xrpOracle.getLiquidationRatio()/20)- 0));
                    else if(xrpOracle.getLiquidationRatio() < 110) xrpOracle.setLiquidationRatio(xrpOracle.getLiquidationRatio() + Math.random() * ((xrpOracle.getLiquidationRatio()/20)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("W-BTC")) {
                    currentLocked = vaultManagerOracle.getLockedBTC();
                    currentCeiling = btcOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) btcOracle.setLiquidationRatio(btcOracle.getLiquidationRatio() - Math.random() * ((btcOracle.getLiquidationRatio()/20)- 0));
                    else if(btcOracle.getLiquidationRatio() < 110) btcOracle.setLiquidationRatio(btcOracle.getLiquidationRatio() + Math.random() * ((btcOracle.getLiquidationRatio()/20)- 0));
                    continue;
                }
                if (collateralOracle.getCollateralType().equals("ETH")) {
                    currentLocked = vaultManagerOracle.getLockedETH();
                    currentCeiling = ethOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) ethOracle.setLiquidationRatio(ethOracle.getLiquidationRatio() - Math.random() * ((ethOracle.getLiquidationRatio()/20)- 0));
                    else if(ethOracle.getLiquidationRatio() < 110) ethOracle.setLiquidationRatio(ethOracle.getLiquidationRatio() + Math.random() * ((ethOracle.getLiquidationRatio()/20)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("LINK")) {
                    currentLocked = vaultManagerOracle.getLockedLINK();
                    currentCeiling = linkOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) linkOracle.setLiquidationRatio(linkOracle.getLiquidationRatio() - Math.random() * ((linkOracle.getLiquidationRatio()/20)- 0));
                    else if(linkOracle.getLiquidationRatio() < 110) linkOracle.setLiquidationRatio(linkOracle.getLiquidationRatio() + Math.random() * ((ethOracle.getLiquidationRatio()/20)- 0));
                    continue;
                }
                if(collateralOracle.getCollateralType().equals("P-LTC")) {
                    currentLocked = vaultManagerOracle.getLockedLTC();
                    currentCeiling = ltcOracle.getDebtCeiling();
                    if (currentLocked >= 0.5 * currentCeiling) ltcOracle.setLiquidationRatio(ltcOracle.getLiquidationRatio() - Math.random() * ((ltcOracle.getLiquidationRatio()/20)- 0));
                    else if(ltcOracle.getLiquidationRatio() < 110) ltcOracle.setLiquidationRatio(ltcOracle.getLiquidationRatio() + Math.random() * ((ltcOracle.getLiquidationRatio()/20)- 0));
                }
            }
        }
    }

}
