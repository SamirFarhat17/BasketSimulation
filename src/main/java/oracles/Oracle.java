package oracles;

import java.lang.reflect.Constructor;

public class Oracle {
    // Attributes of Oracles
    public String oracleID;
    public String oracleStatus;

    // Getters and Setters
    public String getOracleID() { return this.oracleID; }

    public String getOracleStatus() { return this.oracleStatus; }
    public void setOracleStatus(String status) {
        this.oracleStatus = status;
    }

    public void updateOracle() {}
}
