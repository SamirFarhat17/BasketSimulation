package oracles;

public class BufferOracle extends Oracle {

    // Attributes of buffer oracle
    public double bufferHoldings;
    public double totalDebtCeiling;

    // Constructors
    public BufferOracle(String bufferOracle, String oracleStatus, double bufferHoldings, double totalDebtCeiling) {
        this.oracleID = bufferOracle;
        this.oracleStatus = oracleStatus;
        this.bufferHoldings = bufferHoldings;
        this.totalDebtCeiling = totalDebtCeiling;
    }

    // Getters and Setters
    public double getBufferHoldings() { return this.bufferHoldings; }
    public void setBufferHoldings(double bufferHoldings) { this.bufferHoldings = bufferHoldings; }

    public double getTotalDebtCeiling() { return this.totalDebtCeiling; }
    public void setTotalDebtCeiling(double totalDebtCeiling) { this.totalDebtCeiling = totalDebtCeiling; }

}
