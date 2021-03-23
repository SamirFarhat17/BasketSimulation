import json.DataExtraction;
import oracles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Queue;

public class Simulation {

    public static void main(String[] args) throws IOException {

        if(args.length != 7) {
            System.out.println("Incorrect or missing run.sh configurations");
            System.exit(0);
        }

        // Datasets
        ArrayList<String> dates = DataExtraction.makeDates();

        // initialise arguments
        double cpiValue = Double.parseDouble(args[0]);
        int days = Integer.parseInt(args[1]);
        int userBaseSize = Integer.parseInt(args[2]);
        int userSeed = Integer.parseInt(args[3]);
        int keeperSeed = Integer.parseInt(args[4]);
        double collateralSeed = Double.parseDouble(args[5]);
        double bsrSeed = Double.parseDouble(args[6]);

        double basketValue = cpiValue/10;
        double basketTargetValue = basketValue;
        String date = dates.get(1828-days);
        double totalBasket = 285860313.95431495;
        int totalGovernance;
        int auctionCount = 0;


        // Initialize Oracles
        CPIOracle cpiOracle = new CPIOracle("CPIOracle", "Active", date, cpiValue);
        BsrOracle bsrOracle = new BsrOracle("BSROracle", "Active", bsrSeed);
        EmergencyOracle emergencyOracle = new EmergencyOracle("EmergencyOracle", "Active", "Healthy");
        BufferOracle bufferOracle = new BufferOracle("BufferOracle", "Active", totalBasket, totalBasket * 1.3);
        CollateralOracle xrpOracle = new CollateralOracle("A-XRP-Oracle", "Active", "A-XRP", CollateralOracle.fullExchangeXRP.get(date), 3.5, 130.0, 0.0);

        // Create text file
        String textfile = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Scripting/Simulation-Raw/"+ args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5] + ".txt";
        PrintWriter writer = new PrintWriter(textfile, "UTF-8");

        writer.println("Initial Conditions");
        writer.println("Date: " + date);
        writer.println("Consumer Price Index: " + cpiValue);
        writer.println("BSKT Value: " + basketValue);
        writer.println("Total Basket in Market" + totalBasket/basketValue);
        writer.println("User Base Population: " + userBaseSize);
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Collateral holdings in vaults:");
        writer.println("A-XRP: ");
        writer.println("W-BTC: ");
        writer.println("ETH: ");
        writer.println("LINK: ");
        writer.println("P-LTC: ");
        writer.println("USDT: ");
        writer.println("--------------------------------------------------------------------------------------------------");





        while(days > 0) {


            days--;
        }

        writer.println(args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5]);

        writer.close();
    }
}
