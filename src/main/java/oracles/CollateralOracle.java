package oracles;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class CollateralOracle extends Oracle {

    // Attributes of Collateral Oracles
    public String collateralType;
    public double exchangeRate;
    public double stabilityFee;
    public double liquidationRatio;
    public double debtCeiling;

    //  Constructor
    public CollateralOracle(String oracleID, String oracleStatus, String collateralType, double exchangeRate,
                            double stabilityFee, double liquidationRatio, double debtCeiling) throws IOException {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.collateralType = collateralType;
        this.exchangeRate = exchangeRate;
        this.stabilityFee = stabilityFee;
        this.liquidationRatio = liquidationRatio;
        this.debtCeiling = debtCeiling;
    }

    // Getters and Setters
    public String getCollateralType() { return this.collateralType; }

    public double getExchangeRate() { return this.exchangeRate; }

    public double getStabilityFee() { return  this.stabilityFee; }

    public double getLiquidationRatio() { return this.liquidationRatio; }

    public void setLiquidationRatio(double newLiquidationRatio) {this.liquidationRatio = newLiquidationRatio; }

    public double getDebtCeiling() { return this.debtCeiling; }

    public void setDebtCeiling(double newDebtCeiling) { this.debtCeiling = newDebtCeiling; }

    // Methods for vault maintenance
    public double getCollateralEquivalent(double minted) {
        return this.exchangeRate * minted;
    }

    // Variables of Interest
    public String[] collateraltypes = {"A-XRP", "ETH", "LINK", "W-BTC", "USDT", "P-LTC"};
    public static HashMap<String,Double> fullExchangeXRP;

    static {
        try {
            fullExchangeXRP = getExchangeDataFromJson("A_XRP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,Double> fullExchangeETH;

    static {
        try {
            fullExchangeETH = getExchangeDataFromJson("ETH");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,Double> fullExchangeLINK;

    static {
        try {
            fullExchangeLINK = getExchangeDataFromJson("LNK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,Double> fullExchangeLTC;

    static {
        try {
            fullExchangeLTC = getExchangeDataFromJson("P_LTC");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,Double> fullExchangeUSDT;

    static {
        try {
            fullExchangeUSDT = getExchangeDataFromJson("USDT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,Double> fullExchangeBTC;

    static {
        try {
            fullExchangeBTC = getExchangeDataFromJson("BTC");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Methods
    // Get exchange rate date-by-date
    public static HashMap<String, Double> getExchangeDataFromJson(String dataset) throws IOException {
        HashMap<String, Double> exchangeData = new HashMap<>();
        String path = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Oracle-Data/oracle_" + dataset + ".json";

        JSONObject fullJson = JsonReader.readJsonFromFile(path);

        for(String key : fullJson.keySet()) {
            // JSON operations
            JSONArray result = fullJson.getJSONArray(key);
            JSONObject value = result.getJSONObject(0);
            double v = value.getDouble("Value");
            exchangeData.put(key, v);
        }

        return exchangeData;
    }


}
