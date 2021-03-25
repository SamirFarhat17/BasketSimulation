package stakeholders;

import json.JsonReader;
import oracles.CollateralOracle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Governor {

    // Individual stakeholders.Governor Attributes
    public String governorID;
    public String governorStatus;
    public double governorToken;
    public Boolean voteCheck;
    public String voteDecision;

    // Constructor
    public Governor(String governorID, String governorStatus, double governorToken) {
        this.governorID = governorID;
        this.governorStatus = governorStatus;
        this.governorToken = governorToken;
        this.voteCheck = false;
        this.voteDecision = "";
    }

    // Getters and Setters
    public String getGovernorID() { return this.governorID; }

    public String getGovernorStatus() {return this.governorStatus; }
    public void setGovernorStatus(String governorStatus) { this.governorStatus = governorStatus; }

    public double getGovernorToken() { return this.governorToken; }
    public void setGovernorToken(double governorToken) { this.governorToken = governorToken; }

    public Boolean getGovernorVote() { return this.voteCheck; }
    public void setGovernorVote(Boolean hasVoted) { this.voteCheck = hasVoted; }

    public String getVoteDecision() { return this.voteDecision; }
    public void setVoteDecision(String decision) { this.voteDecision = decision; }

    // Variables


    // Methods
    public static double getInitialBasket() throws IOException {
        String govDataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Governance-Data/Governance_Initial.json";
        JSONObject fullJson = JsonReader.readJsonFromFile(govDataPath);
        JSONArray result = fullJson.getJSONArray("Info");
        JSONObject value = result.getJSONObject(0);

        return value.getDouble("Value");
    }

    public static void analyzeSituation() {

    }

    public static void changeBSR() {

    }

    public static void changeCollateralStats() {

    }

}
