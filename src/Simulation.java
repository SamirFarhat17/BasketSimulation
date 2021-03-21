import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import oracles.*;

public class Simulation {
    public double basketVal;
    public int totalBasket;
    public int totalGovernance;
    public int auctionCount = 0;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        if(args.length != 7) {
            System.out.println("Incorrect or missing run.sh configurations");
            System.exit(0);
        }

        // initialise arguments
        double cpiValue = Double.parseDouble(args[0]);
        double days = Integer.parseInt(args[1]);
        int userBaseSize = Integer.parseInt(args[2]);
        int userSeed = Integer.parseInt(args[3]);
        int keeperSeed = Integer.parseInt(args[4]);
        double collateralSeed = Double.parseDouble(args[5]);
        double bsrSeed = Double.parseDouble(args[6]);

        double basketValue = cpiValue/10;
        String date = "";

        // Create text file
        String textfile = "../Scripting/Simulation-Raw/"+ args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5] + ".txt";
        PrintWriter writer = new PrintWriter(textfile, "UTF-8");

        writer.println("Initial Conditions");
        writer.println("Date: " + date);
        writer.println("Consumer Price Index: " + cpiValue);
        writer.println("BSKT Value: " + basketValue);
        writer.println("User Base Population: " + userBaseSize);





        while(days > 0) {


            days--;
        }

        writer.println(args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5]);

        writer.close();
    }
}
