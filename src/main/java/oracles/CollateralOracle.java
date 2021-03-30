package oracles;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class CollateralOracle extends Oracle {

    // Attributes of Collateral Oracles
    private final String collateralType;
    private double exchangeRate;
    private double stabilityFee;
    private double liquidationRatio;
    private double debtCeiling;
    private final HashMap<String,Double> fullExchange;

    //  Constructor
    public CollateralOracle(String oracleID, String oracleStatus, String collateralType, double exchangeRate,
                            double stabilityFee, double liquidationRatio, double debtCeiling, HashMap<String,Double> fullExchange) throws IOException {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.collateralType = collateralType;
        this.exchangeRate = exchangeRate;
        this.stabilityFee = stabilityFee;
        this.liquidationRatio = liquidationRatio;
        this.debtCeiling = debtCeiling;
        this.fullExchange = fullExchange;
    }

    // Getters and Setters
    public String getCollateralType() { return this.collateralType; }

    public double getExchangeRate() { return this.exchangeRate; }
    public void setExchangeRate(double exchangeRate) { this.exchangeRate = exchangeRate; }

    public double getStabilityFee() { return  this.stabilityFee; }
    public void setStabilityFee(double stabilityFee) { this.stabilityFee = stabilityFee; }

    public double getLiquidationRatio() { return this.liquidationRatio; }
    public void setLiquidationRatio(double newLiquidationRatio) {this.liquidationRatio = newLiquidationRatio; }

    public double getDebtCeiling() { return this.debtCeiling; }
    public void setDebtCeiling(double newDebtCeiling) { this.debtCeiling = newDebtCeiling; }

    public HashMap<String,Double> getFullExchange() { return this.fullExchange; }

    // Variables of Interest
    public static String[] collateralTypes = {"A-XRP", "ETH", "LINK", "W-BTC", "USDT", "P-LTC"};
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

    public void updateOracle(String date) {
        if(collateralType.equals("A-XRP")) setExchangeRate(fullExchangeXRP.get(date));
        if(collateralType.equals("W-BTC")) setExchangeRate(fullExchangeBTC.get(date));
        if(collateralType.equals("ETH")) setExchangeRate(fullExchangeETH.get(date));
        if(collateralType.equals("LINK")) setExchangeRate(fullExchangeLINK.get(date));
        if(collateralType.equals("P-LTC")) setExchangeRate(fullExchangeLTC.get(date));
        if(collateralType.equals("USDT")) setExchangeRate(fullExchangeUSDT.get(date));
    }
}
