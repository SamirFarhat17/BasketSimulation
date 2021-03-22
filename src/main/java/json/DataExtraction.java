package json;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.util.HashMap;


public class DataExtraction {
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
