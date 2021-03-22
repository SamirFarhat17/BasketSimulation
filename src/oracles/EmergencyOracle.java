package oracles;

public class EmergencyOracle extends Oracle {
    // Attributes
    public String healthStatus;

    //Constructors
    public EmergencyOracle(String oracleID, String oracleStatus, String healthStatus) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.healthStatus = healthStatus;
    }

    // Getters and Setters
    public String getHealthStatus() { return this.healthStatus; }

    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
}
