package oracles;

import java.util.HashMap;

public class CPIOracle extends Oracle{
    // Attributes for Module
    String date;
    double cpi;

    // Constructor
    public CPIOracle(String oracleID, String oracleStatus, String date, double cpi) {
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

}
