package stakeholders;

import json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Keeper {
    // Attributes of Keepers
    public String keeperID;
    private double basketPossessed;
    private double percentTrading;

    //Constructor
    public Keeper(String id, double bskt, double percentTrading) throws IOException {
        this.keeperID = id;
        this.basketPossessed = bskt;
        this.percentTrading = percentTrading;
    }

    // Getters and Setters
    public String getKeeperID() { return this.keeperID; }

    public double getKeeperBskt() { return this.basketPossessed; }
    public void setKeeperBskt(int bskt) { this.basketPossessed = bskt; }

    // Variables
    public static double initialKeeper;

    static {
        try {
            initialKeeper = getInitialHoldings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Methods
    private static double getInitialHoldings() throws IOException {
        String vaultDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Keeper-Data/Keeper_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(vaultDataPath);

        JSONArray result = fullJson.getJSONArray("Info");
        JSONObject value = result.getJSONObject(0);

        double basketHoldings = value.getDouble("Basket Holdings");

        return basketHoldings;
    }

    public static void bidAuctions() {

    }


}
