import json.DataExtraction;
import json.JsonReader;
import oracles.*;
import org.json.JSONArray;
import org.json.JSONObject;
import stakeholders.Governor;
import stakeholders.Keeper;
import stakeholders.User;
import stakeholders.Vault;

import java.io.File;
import java.io.FileNotFoundException;
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
        String date = dates.get(1827-days);
        double totalBasket = Governor.getInitialBasket();

        System.out.println("Initializing basic oracles...");
        
        // Initialize Oracles
        CPIOracle cpiOracle = new CPIOracle("CPIOracle", "Active", date, cpiValue);
        BsrOracle bsrOracle = new BsrOracle("BSROracle", "Active", bsrSeed);
        EmergencyOracle emergencyOracle = new EmergencyOracle("EmergencyOracle", "Active", "Healthy");

        
        System.out.println("Initializing vault oracles...");
        // Vault Oracle
        ArrayList<Vault> vaults = new ArrayList<Vault>();
        VaultManagerOracle vaultManagerOracle = new VaultManagerOracle("VaultManagerOracle", "Active", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, vaults);
        double vaultTotalBasket = 0.0;
        String colatType;
        double vaultTotalXRP = 0.0;
        double vaultTotalBTC = 0.0;
        double vaultTotalETH= 0.0;
        double vaultTotalLINK = 0.0;
        double vaultTotalLTC = 0.0;
        double vaultTotalUSDT = 0.0;
        for(Vault v : VaultManagerOracle.getInitialActiveVaults(basketValue)) {
            vaultTotalBasket += v.bsktMinted;
            colatType = v.collateralType;
            if(colatType.equals("A-XRP")) vaultTotalXRP += v.collateralAmount;
            if(colatType.equals("W-BTC")) vaultTotalBTC += v.collateralAmount;
            if(colatType.equals("ETH")) vaultTotalETH += v.collateralAmount;
            if(colatType.equals("LINK")) vaultTotalLINK += v.collateralAmount;
            if(colatType.equals("P-LTC")) vaultTotalLTC += v.collateralAmount;
            if(colatType.equals("USDT")) vaultTotalUSDT += v.collateralAmount;
            vaults.add(v);
            vaultManagerOracle.addActiveVault(v);
        }
        vaultManagerOracle.setMintedBasket(vaultTotalBasket);
        vaultManagerOracle.setLockedXRP(vaultTotalXRP);
        vaultManagerOracle.setLockedBTC(vaultTotalBTC);
        vaultManagerOracle.setLockedETH(vaultTotalETH);
        vaultManagerOracle.setLockedLINK(vaultTotalLINK);
        vaultManagerOracle.setLockedLTC(vaultTotalLTC);
        vaultManagerOracle.setLockedUSDT(vaultTotalUSDT);
        double totalBSKTTokensMinted = vaultManagerOracle.getMintedBasket() / basketValue;
        vaultManagerOracle.setMintedBasketTokens(totalBSKTTokensMinted);
        
        // Collateral Oracles
        ArrayList<CollateralOracle> collateralOracles= new ArrayList<CollateralOracle>();
        CollateralOracle xrpOracle = new CollateralOracle("A-XRP-Oracle", "Active", "A-XRP", CollateralOracle.fullExchangeXRP.get(date), 3.5, 140.0, vaultTotalXRP*1.5, CollateralOracle.fullExchangeXRP);
        CollateralOracle ethOracle = new CollateralOracle("ETH-Oracle", "Active", "ETH", CollateralOracle.fullExchangeETH.get(date), 5.5, 110.0, vaultTotalETH*3, CollateralOracle.fullExchangeETH);
        collateralOracles.add(ethOracle);
        CollateralOracle btcOracle = new CollateralOracle("W-BTC-Oracle", "Active", "W-BTC", CollateralOracle.fullExchangeBTC.get(date), 4.5, 130.0, vaultTotalBTC*2, CollateralOracle.fullExchangeBTC);
        collateralOracles.add(btcOracle);
        CollateralOracle linkOracle = new CollateralOracle("LINK-Oracle", "Active", "LINK", CollateralOracle.fullExchangeLINK.get(date), 5.5, 120.0, vaultTotalLINK*1.5, CollateralOracle.fullExchangeLINK);
        collateralOracles.add(linkOracle);
        CollateralOracle ltcOracle = new CollateralOracle("P-LTC-Oracle", "Active", "P-LTC", CollateralOracle.fullExchangeLTC.get(date), 2.0, 150.0, vaultTotalLTC*1.5, CollateralOracle.fullExchangeLTC);
        collateralOracles.add(ltcOracle);
        CollateralOracle usdtOracle = new CollateralOracle("USDT-Oracle", "Active", "USDT", CollateralOracle.fullExchangeUSDT.get(date), 0.0, 165, vaultTotalETH*3, CollateralOracle.fullExchangeUSDT);
        collateralOracles.add(usdtOracle);

        
        System.out.println("Initializing keeper and users...");
        // Keeper Initial
        Keeper keeper = new Keeper("Keeper", Keeper.initialKeeper, keeperSeed);

        // Users Initial
        ArrayList<User> userBase = User.getInitialUsers(basketValue, vaults);
        double userTotalBasket = 0.0;
        for(User user : userBase) {
            userTotalBasket += user.getBsktHoldings();
        }

        System.out.println("Initializing buffer oracle...");
        // Buffer Oracle
        BufferOracle bufferOracle = new BufferOracle("BufferOracle", "Active", vaultManagerOracle.getMintedBasket()-userTotalBasket+keeper.getKeeperBskt(), 0.0);

        // Create text file
        String textfile = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Scripting/Simulation-Raw/"+ args[0] + "_" + args[1] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5] + ".txt";
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
        writer.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n" + "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");


        // Tracking statistics
        // Basket
        ArrayList<Double> cpis = new ArrayList<>();
        cpis.add(cpiValue);
        ArrayList<Double> basketMinted = new ArrayList<>();
        basketMinted.add(totalBasket);
        ArrayList<Double> basketPrices = new ArrayList<>();
        basketPrices.add(basketValue);
        ArrayList<Double> basketTokensMinted = new ArrayList<>();
        basketTokensMinted.add(totalBSKTTokensMinted);
        ArrayList<Integer> userPopulations = new ArrayList<>();
        userPopulations.add(userBaseSize);
        ArrayList<Double> keeperTradeVolumes = new ArrayList<>();
        keeperTradeVolumes.add(keeper.getPercentTrading());
        ArrayList<Integer> keeperPercentageHoldings = new ArrayList<>();
        keeperPercentageHoldings.add(keeperSeed);

        // Basket target
        ArrayList<Double> targetPrices = new ArrayList<>();
        targetPrices.add(basketTargetValue);

        // Collateral Health
        double totalDebtCeiling = 0;
        ArrayList<Double> debtCeilingsXRP = new ArrayList<>();
        ArrayList<Double> debtCeilingsBTC = new ArrayList<>();
        ArrayList<Double> debtCeilingsETH = new ArrayList<>();
        ArrayList<Double> debtCeilingsLINK = new ArrayList<>();
        ArrayList<Double> debtCeilingsLTC= new ArrayList<>();
        ArrayList<Double> debtCeilingsUSDT = new ArrayList<>();
        ArrayList<Double> totalDebtCeilings = new ArrayList<>();

        ArrayList<Double> exchangeRatesXRP = new ArrayList<>();
        ArrayList<Double> exchangeRatesBTC = new ArrayList<>();
        ArrayList<Double> exchangeRatesETH = new ArrayList<>();
        ArrayList<Double> exchangeRatesLINK = new ArrayList<>();
        ArrayList<Double> exchangeRatesLTC = new ArrayList<>();
        ArrayList<Double> exchangeRatesUSDT = new ArrayList<>();

        ArrayList<Double> liquidationRatiosXRP = new ArrayList<>();
        ArrayList<Double> liquidationRatiosBTC = new ArrayList<>();
        ArrayList<Double> liquidationRatiosETH = new ArrayList<>();
        ArrayList<Double> liquidationRatiosLINK = new ArrayList<>();
        ArrayList<Double> liquidationRatiosLTC = new ArrayList<>();
        ArrayList<Double> liquidationRatiosUSDT = new ArrayList<>();

        ArrayList<Double> stabilityFeesXRP = new ArrayList<>();
        ArrayList<Double> stabilityFeesBTC = new ArrayList<>();
        ArrayList<Double> stabilityFeesETH = new ArrayList<>();
        ArrayList<Double> stabilityFeesLINK = new ArrayList<>();
        ArrayList<Double> stabilityFeesLTC = new ArrayList<>();
        ArrayList<Double> stabilityFeesUSDT = new ArrayList<>();

        // Auctions
        int flipAuctionCount = 0;
        int flopAuctionCount = 0;

        // BSR
        ArrayList<Double> bsrTrack = new ArrayList<>();
        bsrTrack.add(bsrSeed);


        System.out.println("Going into day loops...");
        String previousDate = date;
        while(days > 0) {

            date = dates.get(1827-days);

            runSimDay(date, basketValue, previousDate, vaultManagerOracle.getMintedBasketTokens(), userSeed, collateralSeed, collateralOracles, bsrOracle, bufferOracle, cpiOracle, emergencyOracle, xrpOracle,
                    btcOracle,  ethOracle,  linkOracle, ltcOracle, usdtOracle, vaultManagerOracle, keeper, userBase, vaultManagerOracle.getActiveVaults(), totalDebtCeiling);

            updateTrackingStatistics(vaultManagerOracle, basketMinted, basketTokensMinted, basketValue, basketPrices, userBase, userPopulations, keeper, keeperTradeVolumes, keeperPercentageHoldings, cpis, cpiOracle,
                    targetPrices, totalDebtCeilings, collateralOracles, debtCeilingsXRP, debtCeilingsBTC, debtCeilingsETH, debtCeilingsLINK, debtCeilingsLTC, debtCeilingsUSDT, exchangeRatesXRP, exchangeRatesBTC,
                    exchangeRatesETH, exchangeRatesLINK, exchangeRatesLTC, exchangeRatesUSDT, liquidationRatiosXRP, liquidationRatiosBTC, liquidationRatiosETH, liquidationRatiosLINK, liquidationRatiosLTC,
                    liquidationRatiosUSDT, stabilityFeesXRP, stabilityFeesBTC, stabilityFeesETH, stabilityFeesLINK, stabilityFeesLTC, stabilityFeesUSDT, bsrOracle, bsrTrack);

            previousDate = date;
            days--;
        }

        writer.close();
        generateFinalCSV(basketMinted, basketTokensMinted, basketPrices, userBase, userPopulations, keeperTradeVolumes, keeperPercentageHoldings, cpis, targetPrices, totalDebtCeilings, collateralOracles,
                debtCeilingsXRP, debtCeilingsBTC, debtCeilingsETH, debtCeilingsLINK, debtCeilingsLTC, debtCeilingsUSDT, exchangeRatesXRP, exchangeRatesBTC, exchangeRatesETH, exchangeRatesLINK, exchangeRatesLTC,
                exchangeRatesUSDT, liquidationRatiosXRP, liquidationRatiosBTC, liquidationRatiosETH, liquidationRatiosLINK, liquidationRatiosLTC, liquidationRatiosUSDT, stabilityFeesXRP, stabilityFeesBTC,
                stabilityFeesETH, stabilityFeesLINK, stabilityFeesLTC, stabilityFeesUSDT, bsrTrack, args);
    }



    private static void runSimDay(String date, double basketPrice, String previousDate, double totalBSKTTokensMinted, double userSeed, double collateralSeed, ArrayList<CollateralOracle> colatOracles,
                                  BsrOracle bsrOracle, BufferOracle bufferOracle, CPIOracle cpiOracle, EmergencyOracle emergencyOracle, CollateralOracle xrpOracle,
                                  CollateralOracle btcOracle,  CollateralOracle ethOracle, CollateralOracle linkOracle,  CollateralOracle ltcOracle, CollateralOracle usdtOracle,
                                  VaultManagerOracle vaultManagerOracle, Keeper keeper, ArrayList<User> userBase, ArrayList<Vault> vaults, double totalDebtCeiling) throws IOException
    {
        System.out.println("___________________________________________________________________________________________________________\n" + date + "\nUpdating basic oracles" );
        bsrOracle.updateOracle(date);
        bufferOracle.updateOracle(date, totalDebtCeiling);
        cpiOracle.updateOracle(date);
        emergencyOracle.updateOracle(date);

        colatOracles.clear();

        System.out.println("Updating colatteral oracles");
        xrpOracle.updateOracle(date);
        colatOracles.add(xrpOracle);
        btcOracle.updateOracle(date);
        colatOracles.add(btcOracle);
        ethOracle.updateOracle(date);
        colatOracles.add(ethOracle);
        linkOracle.updateOracle(date);
        colatOracles.add(linkOracle);
        ltcOracle.updateOracle(date);
        colatOracles.add(ltcOracle);
        usdtOracle.updateOracle(date);
        colatOracles.add(usdtOracle);

        System.out.println("Update vault manager");
        vaultManagerOracle.updateVaults(previousDate, date, vaults, basketPrice, xrpOracle.getFullExchange(), btcOracle.getFullExchange(), ethOracle.getFullExchange(),
                                        linkOracle.getFullExchange(), ltcOracle.getFullExchange(), usdtOracle.getFullExchange());
        vaultManagerOracle.updateOracle(date);
        vaultManagerOracle.checkLiquidations(vaults, colatOracles, basketPrice);

        System.out.println("User creation and interest generation");
        User.generateNewUsers(userBase, userSeed, collateralSeed, basketPrice, vaultManagerOracle);
        User.generateUserCollaterals(userBase, collateralSeed);
        User.generateUserWants(userBase, userSeed, basketPrice, collateralSeed, colatOracles, vaultManagerOracle);
        keeper.generateKeeperWants(date);

        System.out.println("Governorship work\n");
        Governor.analyzeSituation();
        Governor.updateGovernanceParameters();

    }




    private static void updateTrackingStatistics(VaultManagerOracle vaultManagerOracle, ArrayList<Double> basketMinted, ArrayList<Double> basketTokensMinted,
                                                 double basketPrice, ArrayList<Double> basketPrices, ArrayList<User> userBase, ArrayList<Integer> userPopulations,
                                                 Keeper keeper, ArrayList<Double> keeperTradeVolumes, ArrayList<Integer> keeperPercentageHoldings, ArrayList<Double> cpis,
                                                 CPIOracle cpiOracle, ArrayList<Double> targetPrices, ArrayList<Double> totalDebtCeilings, ArrayList<CollateralOracle> collateralOracles,
                                                 ArrayList<Double> debtCeilingXRP, ArrayList<Double> debtCeilingBTC, ArrayList<Double> debtCeilingETH,
                                                 ArrayList<Double> debtCeilingLINK, ArrayList<Double> debtCeilingLTC, ArrayList<Double> debtCeilingUSDT,
                                                 ArrayList<Double> exchangeRateXRP, ArrayList<Double> exchangeRateBTC, ArrayList<Double> exchangeRateETH,
                                                 ArrayList<Double> exchangeRateLINK, ArrayList<Double> exchangeRateLTC, ArrayList<Double> exchangeRateUSDT,
                                                 ArrayList<Double> liquidationRatiosXRP, ArrayList<Double> liquidationRatiosBTC, ArrayList<Double> liquidationRatiosETH,
                                                 ArrayList<Double> liquidationRatiosLINK, ArrayList<Double> liquidationRatiosLTC, ArrayList<Double> liquidationRatiosUSDT,
                                                 ArrayList<Double> stabilityFeesXRP, ArrayList<Double> stabilityFeesBTC, ArrayList<Double> stabilityFeesETH,
                                                 ArrayList<Double> stabilityFeesLINK, ArrayList<Double> stabilityFeesLTC, ArrayList<Double> stabilityFeesUSDT,
                                                 BsrOracle bsrOracle, ArrayList<Double> bsrTrack)
    {
        basketMinted.add(vaultManagerOracle.getMintedBasket());
        basketTokensMinted.add(vaultManagerOracle.getMintedBasketTokens());
        basketPrices.add(basketPrice);
        userPopulations.add(userBase.size());
        keeperTradeVolumes.add(keeper.getPercentTrading());
        keeperPercentageHoldings.add((int) Math.round(keeper.getKeeperBskt()/vaultManagerOracle.getMintedBasket()));
        targetPrices.add(cpiOracle.getCpi()/10);
        cpis.add(cpiOracle.getCpi());

        debtCeilingXRP.add(collateralOracles.get(0).getDebtCeiling());
        debtCeilingBTC.add(collateralOracles.get(1).getDebtCeiling());
        debtCeilingETH.add(collateralOracles.get(2).getDebtCeiling());
        debtCeilingLINK.add(collateralOracles.get(3).getDebtCeiling());
        debtCeilingLTC.add(collateralOracles.get(4).getDebtCeiling());
        debtCeilingUSDT.add(collateralOracles.get(5).getDebtCeiling());
        totalDebtCeilings.add(collateralOracles.get(0).getDebtCeiling() + collateralOracles.get(1).getDebtCeiling() + collateralOracles.get(2).getDebtCeiling() +
                                collateralOracles.get(3).getDebtCeiling() + collateralOracles.get(4).getDebtCeiling() + collateralOracles.get(5).getDebtCeiling());

        exchangeRateXRP.add(collateralOracles.get(0).getExchangeRate());
        exchangeRateBTC.add(collateralOracles.get(1).getExchangeRate());
        exchangeRateETH.add(collateralOracles.get(2).getExchangeRate());
        exchangeRateLINK.add(collateralOracles.get(3).getExchangeRate());
        exchangeRateLTC.add(collateralOracles.get(4).getExchangeRate());
        exchangeRateUSDT.add(collateralOracles.get(5).getExchangeRate());

        liquidationRatiosXRP.add(collateralOracles.get(0).getLiquidationRatio());
        liquidationRatiosBTC.add(collateralOracles.get(1).getLiquidationRatio());
        liquidationRatiosETH.add(collateralOracles.get(2).getLiquidationRatio());
        liquidationRatiosLINK.add(collateralOracles.get(3).getLiquidationRatio());
        liquidationRatiosLTC.add(collateralOracles.get(4).getLiquidationRatio());
        liquidationRatiosUSDT.add(collateralOracles.get(5).getLiquidationRatio());

        stabilityFeesXRP.add(collateralOracles.get(0).getStabilityFee());
        stabilityFeesBTC.add(collateralOracles.get(1).getStabilityFee());
        stabilityFeesETH.add(collateralOracles.get(2).getStabilityFee());
        stabilityFeesLINK.add(collateralOracles.get(3).getStabilityFee());
        stabilityFeesLTC.add(collateralOracles.get(4).getStabilityFee());
        stabilityFeesUSDT.add(collateralOracles.get(5).getStabilityFee());

        bsrTrack.add(bsrOracle.getBsr());
    }


    private static void generateFinalCSV(ArrayList<Double> basketMinted, ArrayList<Double> basketTokensMinted,
                                         ArrayList<Double> basketPrices, ArrayList<User> userBase, ArrayList<Integer> userPopulations,
                                         ArrayList<Double> keeperTradeVolumes, ArrayList<Integer> keeperPercentageHoldings, ArrayList<Double> cpis,
                                         ArrayList<Double> targetPrices, ArrayList<Double> totalDebtCeilings, ArrayList<CollateralOracle> collateralOracles,
                                         ArrayList<Double> debtCeilingXRP, ArrayList<Double> debtCeilingBTC, ArrayList<Double> debtCeilingETH,
                                         ArrayList<Double> debtCeilingLINK, ArrayList<Double> debtCeilingLTC, ArrayList<Double> debtCeilingUSDT,
                                         ArrayList<Double> exchangeRateXRP, ArrayList<Double> exchangeRateBTC, ArrayList<Double> exchangeRateETH,
                                         ArrayList<Double> exchangeRateLINK, ArrayList<Double> exchangeRateLTC, ArrayList<Double> exchangeRateUSDT,
                                         ArrayList<Double> liquidationRatiosXRP, ArrayList<Double> liquidationRatiosBTC, ArrayList<Double> liquidationRatiosETH,
                                         ArrayList<Double> liquidationRatiosLINK, ArrayList<Double> liquidationRatiosLTC, ArrayList<Double> liquidationRatiosUSDT,
                                         ArrayList<Double> stabilityFeesXRP, ArrayList<Double> stabilityFeesBTC, ArrayList<Double> stabilityFeesETH,
                                         ArrayList<Double> stabilityFeesLINK, ArrayList<Double> stabilityFeesLTC, ArrayList<Double> stabilityFeesUSDT,
                                         ArrayList<Double> bsrTrack, String[] args)
    {
        try (PrintWriter writer = new PrintWriter(new File("/home/samir/Documents/Year4/Dissertation/BasketSimulation/Scripting/Simulation-Raw/"
                + args[0] + "_" + args[2] + "_" + args[3] + "_" + args[4] + "_" + args[5]+ ".csv"))) {
            int count = 0;

            StringBuilder sb = new StringBuilder();
            sb.append("CPI,");
            sb.append(',');
            sb.append("BasketPrice,");
            sb.append(',');
            sb.append("TargetPrice,");
            sb.append(',');
            sb.append("BasketMinted,");
            sb.append(',');
            sb.append("DebtCeiling,");
            sb.append(',');
            sb.append("XRPDebtCeiling,");
            sb.append(',');
            sb.append("XRPLR,");
            sb.append(',');
            sb.append("XRPSF,");
            sb.append(',');
            sb.append("XRPExchangeRate,");
            sb.append(',');
            sb.append("BTCDebtCeiling,");
            sb.append(',');
            sb.append("BTCLR,");
            sb.append(',');
            sb.append("BTCSF,");
            sb.append(',');
            sb.append("BTCExchangeRate,");
            sb.append(',');
            sb.append("ETHDebtCeiling,");
            sb.append(',');
            sb.append("ETHLR,");
            sb.append(',');
            sb.append("ETHSF,");
            sb.append(',');
            sb.append("ETHExchangeRate,");
            sb.append(',');
            sb.append("LINKDebtCeiling,");
            sb.append(',');
            sb.append("LINKLR,");
            sb.append(',');
            sb.append("LINKSF,");
            sb.append(',');
            sb.append("LINKExchangeRate,");
            sb.append(',');
            sb.append("LTCDebtCeiling,");
            sb.append(',');
            sb.append("LTCLR,");
            sb.append(',');
            sb.append("LTCSF,");
            sb.append(',');
            sb.append("LTCExchangeRate,");
            sb.append(',');
            sb.append("USDTDebtCeiling,");
            sb.append(',');
            sb.append("USDTLR,");
            sb.append(',');
            sb.append("USDTSF,");
            sb.append(',');
            sb.append("USDTExchangeRate,");
            sb.append(',');
            sb.append("BSR,");
            sb.append(',');
            sb.append("UserBaseSize,");
            sb.append(',');
            sb.append("KeeperHoldingPercentage,");
            sb.append(',');
            sb.append("KeeperTradePercentage,");
            sb.append('\n');

            for(int i = 0; i < basketMinted.size(); i++) {
                sb.append(cpis.get(i));
                sb.append(',');
                sb.append(basketPrices.get(i));
                sb.append(',');
                sb.append(targetPrices.get(i));
                sb.append(',');
                sb.append(basketMinted.get(i));
                sb.append(',');
                sb.append(totalDebtCeilings.get(i));
                sb.append(',');
                sb.append(debtCeilingXRP.get(i));
                sb.append(',');
                sb.append(liquidationRatiosXRP.get(i));
                sb.append(',');
                sb.append(stabilityFeesXRP.get(i));
                sb.append(',');
                sb.append(exchangeRateXRP.get(i));
                sb.append(',');
                sb.append(debtCeilingBTC.get(i));
                sb.append(',');
                sb.append(liquidationRatiosBTC.get(i));
                sb.append(',');
                sb.append(stabilityFeesBTC.get(i));
                sb.append(',');
                sb.append(exchangeRateBTC.get(i));
                sb.append(',');
                sb.append(debtCeilingETH.get(i));
                sb.append(',');
                sb.append(liquidationRatiosETH.get(i));
                sb.append(',');
                sb.append(stabilityFeesETH.get(i));
                sb.append(',');
                sb.append(exchangeRateETH.get(i));
                sb.append(',');
                sb.append(debtCeilingLINK.get(i));
                sb.append(',');
                sb.append(liquidationRatiosLINK.get(i));
                sb.append(',');
                sb.append(stabilityFeesLINK.get(i));
                sb.append(',');
                sb.append(exchangeRateLINK.get(i));
                sb.append(',');
                sb.append(debtCeilingLTC.get(i));
                sb.append(',');
                sb.append(liquidationRatiosLTC.get(i));
                sb.append(',');
                sb.append(stabilityFeesLTC.get(i));
                sb.append(',');
                sb.append(exchangeRateLTC.get(i));
                sb.append(',');
                sb.append(debtCeilingUSDT.get(i));
                sb.append(',');
                sb.append(liquidationRatiosUSDT.get(i));
                sb.append(',');
                sb.append(stabilityFeesUSDT.get(i));
                sb.append(',');
                sb.append(exchangeRateUSDT.get(i));
                sb.append(',');
                sb.append(bsrTrack.get(i));
                sb.append(',');
                sb.append(userPopulations.get(i));
                sb.append(',');
                sb.append(keeperPercentageHoldings.get(i));
                sb.append(',');
                sb.append(keeperTradeVolumes.get(i));
                sb.append('\n');
            }

            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
