import json.DataExtraction;
import oracles.*;
import stakeholders.Governor;
import stakeholders.Keeper;
import stakeholders.User;
import stakeholders.Vault;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Queue;

public class Simulation {

    private static void runSimDay() {

    }

    public static void main(String[] args) throws IOException {

        // Check arguments
        if(args.length != 7) {
            System.out.println("Incorrect or missing run.sh configurations");
            System.exit(0);
        }

        System.out.println("Gathering dates for sim...");
        // Datasets
        ArrayList<String> dates = DataExtraction.makeDates();

        // Initialise arguments
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
        double totalBasket = Governor.getInitialBasket();

        System.out.println("Initializing basic oracles...");
        // Initialize Oracles
        CPIOracle cpiOracle = new CPIOracle("CPIOracle", "Active", date, cpiValue);
        BsrOracle bsrOracle = new BsrOracle("BSROracle", "Active", bsrSeed);
        EmergencyOracle emergencyOracle = new EmergencyOracle("EmergencyOracle", "Active", "Healthy");

        System.out.println("Initializing vault oracles...");
        // Vault Oracle
        ArrayList<Vault> vaults = new ArrayList<Vault>();
        VaultManagerOracle vaultManagerOracle = new VaultManagerOracle("VaultManagerOracle", "Active", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, vaults);
        double vaultTotalBasket = 0.0;
        String colatType;
        double vaultTotalXRP = 0.0;
        double vaultTotalBTC = 0.0;
        double vaultTotalETH= 0.0;
        double vaultTotalLINK = 0.0;
        double vaultTotalLTC = 0.0;
        double vaultTotalUSDT = 0.0;
        for(Vault v : Vault.initialActiveVaults) {
            vaultTotalBasket += v.bsktMinted;
            colatType = v.collateralType;
            if(colatType.equals("A-XRP")) vaultTotalXRP += v.collateralAmount;
            if(colatType.equals("W-BTC")) vaultTotalBTC += v.collateralAmount;
            if(colatType.equals("ETH")) vaultTotalETH += v.collateralAmount;
            if(colatType.equals("LINK")) vaultTotalLINK += v.collateralAmount;
            if(colatType.equals("P-LTC")) vaultTotalLTC += v.collateralAmount;
            if(colatType.equals("USDT")) vaultTotalUSDT += v.collateralAmount;
            vaultManagerOracle.addActiveVault(v);
        }
        vaultManagerOracle.setMintedBasket(vaultTotalBasket);
        vaultManagerOracle.setLockedXRP(vaultTotalXRP);
        vaultManagerOracle.setLockedBTC(vaultTotalBTC);
        vaultManagerOracle.setLockedETH(vaultTotalETH);
        vaultManagerOracle.setLockedLINK(vaultTotalLINK);
        vaultManagerOracle.setLockedLTC(vaultTotalLTC);
        vaultManagerOracle.setLockedUSDT(vaultTotalUSDT);

        // Collateral Oracles
        CollateralOracle xrpOracle = new CollateralOracle("A-XRP-Oracle", "Active", "A-XRP", CollateralOracle.fullExchangeXRP.get(date), 3.5, 140.0, vaultTotalXRP*1.5);
        CollateralOracle ethOracle = new CollateralOracle("ETH-Oracle", "Active", "ETH", CollateralOracle.fullExchangeETH.get(date), 5.5, 110.0, vaultTotalETH*3);
        CollateralOracle btcOracle = new CollateralOracle("W-BTC-Oracle", "Active", "W-BTC", CollateralOracle.fullExchangeBTC.get(date), 4.5, 130.0, vaultTotalBTC*2);
        CollateralOracle linkOracle = new CollateralOracle("LINK-Oracle", "Active", "LINK", CollateralOracle.fullExchangeLINK.get(date), 5.5, 120.0, vaultTotalLINK*1.5);
        CollateralOracle ltcOracle = new CollateralOracle("P-LTC-Oracle", "Active", "P-LTC", CollateralOracle.fullExchangeLTC.get(date), 2.0, 150.0, vaultTotalLTC*1.5);
        CollateralOracle usdtOracle = new CollateralOracle("USDT-Oracle", "Active", "USDT", CollateralOracle.fullExchangeUSDT.get(date), 0.0, 165, vaultTotalETH*3);

        System.out.println("Initializing keeper and users...");
        // Keeper Initial
        Keeper keeper = new Keeper("Keeper", Keeper.initialKeeper, keeperSeed);

        // Users Initial
        ArrayList<User> initialUsers = User.userList;
        double userTotalBasket = 0.0;
        for(User user : initialUsers) {
            userTotalBasket += user.getBsktHoldings();
        }

        System.out.println("Initializing buffer oracle...");
        // Buffer Oracle
        BufferOracle bufferOracle = new BufferOracle("BufferOracle", "Active", vaultManagerOracle.getMintedBasket()-userTotalBasket+keeper.getKeeperBskt(), 0.0);

        // Create text file
        String textfile = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Scripting/Simulation-Raw/"+ args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5] + ".txt";
        PrintWriter writer = new PrintWriter(textfile, "UTF-8");

        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Initial Conditions");
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Date: " + date);
        writer.println("Consumer Price Index: " + cpiValue);
        writer.println("BSKT Value: " + basketValue);
        writer.println("BSKT Target Peg: " + basketTargetValue);
        writer.println("Total Basket in Market" + totalBasket/basketValue);
        writer.println("User Base Population: " + userBaseSize);
        writer.println("User Base Basket Holdings: " + userTotalBasket);
        writer.println("Keeper Total Basket: " + keeper.getKeeperBskt());
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Oracles");
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println(bsrOracle.getOracleID() + " - " +bsrOracle.getOracleStatus());
        writer.println(bufferOracle.getOracleID() + " - " +bufferOracle.getOracleStatus());
        writer.println(cpiOracle.getOracleID() + " - " + cpiOracle.getOracleStatus());
        writer.println(emergencyOracle.getOracleID() + " - " + emergencyOracle.getOracleStatus());
        writer.println(vaultManagerOracle.getOracleID() + " - " + vaultManagerOracle.getOracleStatus());
        writer.println(xrpOracle.getOracleID() + " - " + xrpOracle.getOracleStatus());
        writer.println(btcOracle.getOracleID() + " - " + btcOracle.getOracleStatus());
        writer.println(ethOracle.getOracleID() + " - " + ethOracle.getOracleStatus());
        writer.println(linkOracle.getOracleID() + " - " + linkOracle.getOracleStatus());
        writer.println(ltcOracle.getOracleID() + " - " + ltcOracle.getOracleStatus());
        writer.println(usdtOracle.getOracleID() + " - " + usdtOracle.getOracleStatus());
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Vaults");
        writer.println("-------------------------------------------------------------------------------------------------");
        writer.println("Number of Vaults: " + vaultManagerOracle.getActiveVaults().size());
        writer.println("Collateral holdings in vaults along with exchange rates, stability fees and liquidation ratios:");
        writer.println("A-XRP: " + vaultTotalXRP + " Exchange Rate: " + xrpOracle.getExchangeRate() + " Stability Fee: " + xrpOracle.getStabilityFee() + " Liquidation Ratio " + xrpOracle.getLiquidationRatio() + "%");
        writer.println("W-BTC: " + vaultTotalBTC + " Exchange Rate: " + btcOracle.getExchangeRate() + " Stability Fee: " + btcOracle.getStabilityFee() + " Liquidation Ratio " + btcOracle.getLiquidationRatio() + "%");
        writer.println("ETH: " + vaultTotalETH + " Exchange Rate: " + ethOracle.getExchangeRate() + " Stability Fee: " + ethOracle.getStabilityFee() + " Liquidation Ratio " + ethOracle.getLiquidationRatio() + "%");
        writer.println("LINK: " + vaultTotalLINK + " Exchange Rate: " + linkOracle.getExchangeRate() + " Stability Fee: " + linkOracle.getStabilityFee() + " Liquidation Ratio " + linkOracle.getLiquidationRatio() + "%");
        writer.println("P-LTC: " + vaultTotalLTC + " Exchange Rate: " + ltcOracle.getExchangeRate() + " Stability Fee: " + ltcOracle.getStabilityFee() + " Liquidation Ratio " + ltcOracle.getLiquidationRatio() + "%");
        writer.println("USDT: " + vaultTotalUSDT + " Exchange Rate: " + usdtOracle.getExchangeRate() + " Stability Fee: " + usdtOracle.getStabilityFee() + " Liquidation Ratio " + usdtOracle.getLiquidationRatio() + "%");
        writer.println("-------------------------------------------------------------------------------------------------- \n " + "__________________________________________________________________________________________________");


        // Tracking statistics
        ArrayList<Double> basketMinted = new ArrayList<>();
        basketMinted.add(totalBasket);
        ArrayList<Double> basketPrices = new ArrayList<>();
        basketPrices.add(basketValue);
        ArrayList<Double> targetPrices = new ArrayList<>();
        targetPrices.add(basketTargetValue);
        ArrayList<Double> debtCeilings = new ArrayList<>();
        int auctionCount = 0;


        System.out.println("Going into day loops...");

        while(days > 1) {
            date = dates.get(1828-days);
            runSimDay();

            days--;
        }

        writer.println(args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5]);

        writer.close();
    }
}
