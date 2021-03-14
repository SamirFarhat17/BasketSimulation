

public class Vault {

    // Attribute of Vaults
    public String vaultID;
    public String ownerID;
    public Boolean status;
    public int age;
    public String collateralType;
    public double collateralAmount;
    public double bsktMinted;
    public double stabilityFee;
    public double liquidationRatio;

    // Constructor
    public Vault(String vaultID, String ownerID, Boolean status, String collateralType,
                 double collateralAmount, double minted, double stabilityFee, double liquidationRatio) {
        this.vaultID = vaultID;
        this.ownerID = ownerID;
        this.status = status;
        this.age = 0;
        this.collateralType = collateralType;
        this.collateralAmount = collateralAmount;
        this.bsktMinted = minted;
        this.stabilityFee = stabilityFee;
        this.liquidationRatio = liquidationRatio;
    }

    // Getters and Setters
    public String getVaultID() {
        return this.vaultID;
    }

    public String getOwnerID() {
        return this.ownerID;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollateralType() {
        return this.collateralType;
    }

    public double getCollateralAmount() {
        return this.collateralAmount;
    }

    public double getBsktMinted() {
        return this.bsktMinted;
    }

    public double getLiquidationRatio() {
        return this.liquidationRatio;
    }


}
