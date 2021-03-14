
public class Keeper {
    // Attributes of Keepers
    public String keeperID;
    private double basketPossessed;

    //Constructor
    public Keeper(String id, int bskt) {
        this.keeperID = id;
        this.basketPossessed = bskt;
    }

    // Getters and Setters
    public String getKeeperID() { return this.keeperID; }

    public double getKeeperBskt() { return this.basketPossessed; }

    public void setKeeperBskt(int bskt) { this.basketPossessed = bskt; }


}
