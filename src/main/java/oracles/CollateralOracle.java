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
    public HashMap<String,Double> fullExchangeXRP = getExchangeDataFromJson("A_XRP") ;
    public HashMap<String,Double> fullExchangeETH = getExchangeDataFromJson("ETH") ;
    public HashMap<String,Double> fullExchangeLINK = getExchangeDataFromJson("LNK") ;
    public HashMap<String,Double> fullExchangeLTC = getExchangeDataFromJson("P_LTC") ;
    public HashMap<String,Double> fullExchangeUSDT = getExchangeDataFromJson("USDT") ;
    public HashMap<String,Double> fullExchangeBTC = getExchangeDataFromJson("BTC") ;
    

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
            System.out.println(key + ": " + v);
            exchangeData.put(key, v);
        }

        return exchangeData;
    }


}
