
public class Simulation {
    public double basketVal;
    public int totalBasket;
    public int totalGovernance;
    public int auctionCount = 0;

    public static void main(String[] args) {

        if(args.length != 7) {
            System.out.println("Incorrect or missing run.sh configurations");
            System.exit(0);
        }

        System.out.println("Hello World");
    }
}
