package oracles;

import oracles.Oracle;

public class EmergencyOracle extends Oracle {
    // Attributes
    private String healthStatus;
    private double sales;

    //Constructors
    public EmergencyOracle(String oracleID, String oracleStatus, String healthStatus, double sales) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.healthStatus = healthStatus;
        this.sales = sales;
    }

    // Getters and Setters
    public String getHealthStatus() { return this.healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public double getSales() { return this.sales; }
    public void setSales(double sales) { this.sales = sales; }


}
