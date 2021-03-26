import json.DataExtraction;
import oracles.*;
import stakeholders.Governor;
import stakeholders.Keeper;
import stakeholders.User;
import stakeholders.Vault;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;




public class Simulation {


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
        for(Vault v : VaultManagerOracle.initialActiveVaults) {
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
        ArrayList<CollateralOracle> collateralOracles= new ArrayList<CollateralOracle>();
        CollateralOracle xrpOracle = new CollateralOracle("A-XRP-Oracle", "Active", "A-XRP", CollateralOracle.fullExchangeXRP.get(date), 3.5, 140.0, vaultTotalXRP*1.5);
        collateralOracles.add(xrpOracle);
        CollateralOracle ethOracle = new CollateralOracle("ETH-Oracle", "Active", "ETH", CollateralOracle.fullExchangeETH.get(date), 5.5, 110.0, vaultTotalETH*3);
        collateralOracles.add(ethOracle);
        CollateralOracle btcOracle = new CollateralOracle("W-BTC-Oracle", "Active", "W-BTC", CollateralOracle.fullExchangeBTC.get(date), 4.5, 130.0, vaultTotalBTC*2);
        collateralOracles.add(btcOracle);
        CollateralOracle linkOracle = new CollateralOracle("LINK-Oracle", "Active", "LINK", CollateralOracle.fullExchangeLINK.get(date), 5.5, 120.0, vaultTotalLINK*1.5);
        collateralOracles.add(linkOracle);
        CollateralOracle ltcOracle = new CollateralOracle("P-LTC-Oracle", "Active", "P-LTC", CollateralOracle.fullExchangeLTC.get(date), 2.0, 150.0, vaultTotalLTC*1.5);
        collateralOracles.add(ltcOracle);
        CollateralOracle usdtOracle = new CollateralOracle("USDT-Oracle", "Active", "USDT", CollateralOracle.fullExchangeUSDT.get(date), 0.0, 165, vaultTotalETH*3);
        collateralOracles.add(usdtOracle);

        System.out.println("Initializing keeper and users...");
        // Keeper Initial
        Keeper keeper = new Keeper("Keeper", Keeper.initialKeeper, keeperSeed);

        // Users Initial
        ArrayList<User> userBase = User.userList;
        double userTotalBasket = 0.0;
        for(User user : userBase) {
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
        // Basket
        ArrayList<Double> basketMinted = new ArrayList<>();
        basketMinted.add(totalBasket);
        ArrayList<Double> basketPrices = new ArrayList<>();
        basketPrices.add(basketValue);
        ArrayList<Integer> userPopulations = new ArrayList<>();
        userPopulations.add(userBaseSize);
        ArrayList<Double> keeperTradeVolumes = new ArrayList<>();
        ArrayList<Integer> keeperPercentageHoldings = new ArrayList<>();
        keeperPercentageHoldings.add(keeperSeed);
        // Basket target
        ArrayList<Double> targetPrices = new ArrayList<>();
        targetPrices.add(basketTargetValue);
        // Collateral Health
        double totalDebtCeiling = 0;
        HashMap<String,Double> debtCeilings = new HashMap<String,Double>();
        HashMap<String,Double> exchangeRates = new HashMap<String,Double>();
        for(CollateralOracle o : collateralOracles ) {
            totalDebtCeiling += o.getDebtCeiling();
            debtCeilings.put(o.collateralType, o.getDebtCeiling());
            exchangeRates.put(o.collateralType, o.getExchangeRate());
        }
        // Auctions
        int flipAuctionCount = 0;
        int flopAuctionCount = 0;
        // BSR
        ArrayList<Double> bsrTrack = new ArrayList<>();
        bsrTrack.add(bsrSeed);


        System.out.println("Going into day loops...");

        while(days > 1) {
            date = dates.get(1828-days);
            runSimDay(date, userSeed, collateralSeed, collateralOracles, bsrOracle, bufferOracle, cpiOracle, emergencyOracle);

            days--;
        }

        writer.println(args[0] + "-" + args[2] + "-" + args[3] + "-" + args[4] + "-" + args[5]);

        writer.close();
    }

    private static void runSimDay(String date, double userSeed, double collateralSeed, ArrayList<CollateralOracle> colatOracles,
             BsrOracle bsrOracle, BufferOracle bufferOracle, CPIOracle cpiOracle, EmergencyOracle emergencyOracle /*xrpOracle,
    btcOracle, ethOracle, linkOracle, ltcOracle, usdtOracle, vaultManagerOracle, keeper, userBase */) {
        bsrOracle.updateOracle(date);
        bufferOracle.updateOracle(date);
        VaultManagerOracle.updateVaults();
        VaultManagerOracle.checkLiquidations();
        User.generateUserWants(User.userList, userSeed, collateralSeed, colatOracles);
        Keeper.generateKeeperWants();
        User.generateNewUsers();
        Governor.analyzeSituation();
        Governor.updateGovernanceParameters();
    }

    private static void updateTrackingStatistics() {

    }

}
