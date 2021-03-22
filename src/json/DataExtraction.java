package json;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class DataExtraction {
    public static HashMap<String, Double> getCpiDataFromJson() throws IOException {
        HashMap<String, Double> cpiData = new HashMap<>();
        String path = System.getProperty("user.dir") + "/../Data/CPI-Data/cpi_Daily.json";
        JsonObject jsonObject = JsonReader.readJsonFromFile(path);

        Set<String> keys = jsonObject.keySet();

        for(String key : keys) {
            //System.out.println(key + " relates to " + jsonObject.get(key));
            cpiData.put(key, 3.6);
        }

        return cpiData;
    }
}
