public class CollateralOracle extends Oracle {

    public String[] collateraltypes = {"A-XRP", "ETH", "LINK", "W-BTC", "USDT", "P-LTC"};

    // Attributes of Collateral Oracles
    public String collateralType;
    public double exchangeRate;
    public double stabilityFee;
    public double liquidationRatio;
    public double debtCeiling;

    //  Constructor
    public CollateralOracle(String oracleID, String oracleStatus, String collateralType, double exchangeRate,
                            double stabilityFee, double liquidationRatio, double debtCeiling) {
        this.oracleID = oracleID;
        this.oracleStatus = oracleStatus;
        this.collateralType = collateralType;
        this.exchangeRate = exchangeRate;
        this.stabilityFee = stabilityFee;
        this.liquidationRatio = liquidationRatio;
        this.debtCeiling = debtCeiling;
    }

    // Getters and Setters
    public String getCollateralType() { return this.collateralType; }

    public double getExchangeRate() { return this.exchangeRate; }

    public double getStabilityFee() { return  this.stabilityFee; }

    public double getLiquidationRatio() { return this.liquidationRatio; }

    public void setLiquidationRatio(double newLiquidationRatio) {this.liquidationRatio = newLiquidationRatio; }

    public double getDebtCeiling() { return this.debtCeiling; }

    public void setDebtCeiling(double newDebtCeiling) { this.debtCeiling = newDebtCeiling; }

    // Methods for vault maintenance
    public double getCollateralEquivalent(double minted) {
        return this.exchangeRate * minted;
    }

}
