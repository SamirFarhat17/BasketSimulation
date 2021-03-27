package oracles;

public class BsrOracle extends Oracle {

    // Attributes
    public double bsr;

    // Constructor
    public BsrOracle(String oracleID, String oracleStatus, double bsr) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.bsr = bsr;
    }

    // Getters and setters
    public double getBsr() { return this.bsr; }

    public void setBsr(double bsr) { this.bsr = bsr; }

    // Methods
    public void updateOracle(String date) {
        setOracleStatus("Healthy");
    }
}
