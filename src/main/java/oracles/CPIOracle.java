package oracles;

import json.DataExtraction;
import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class CPIOracle extends Oracle {
    public HashMap<String,Double> totalCPI = getCpiDataFromJson();
    // Attributes for Module
    String date;
    double cpi;

    // Constructor
    public CPIOracle(String oracleID, String oracleStatus, String date, double cpi) throws IOException {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.date = date;
        this.cpi = cpi;
    }

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getCpi() { return cpi; }
    public void setCpi(double cpi) {this.cpi = cpi; }


    // Methods
    // CPI data date-by-date
    public static HashMap<String, Double> getCpiDataFromJson() throws IOException {
        HashMap<String, Double> cpiData = new HashMap<>();
        String path = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/CPI-Data/cpi_Daily.json";

        JSONObject fullJson = JsonReader.readJsonFromFile(path);

        for(String key : fullJson.keySet()) {
            // JSON operations
            JSONArray result = fullJson.getJSONArray(key);
            JSONObject value = result.getJSONObject(0);
            double v = value.getDouble("Value");
            cpiData.put(key, v);
        }

        return cpiData;
    }

}
