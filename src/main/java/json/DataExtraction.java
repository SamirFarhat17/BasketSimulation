package json;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvException;

public class DataExtraction {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static ArrayList<String> makeDates() throws IOException, CsvException {
        String dataPath = "/home/samir/Documents/Year4/Dissertation/BasketSimulation/Data/Oracle-Data/GBP-USD.csv";
        CSVReader reader = new CSVReader(new FileReader(dataPath));
        String [] nextLine;
        int lineNumber = 0;
        ArrayList<String> dates = new ArrayList<String>();

        while ((nextLine = reader.readNext()) != null) {
            lineNumber++;

            dates.add(nextLine[0]);
        }

        dates.remove(0);

        return dates;
    }

    public static String[] shuffleArray(String[] ar) {
        String[] output = ar;
        Random rnd = new Random();
        for (int i = output.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = output[index];
            output[index] = output[i];
            output[i] = a;
        }
        return output;
    }

    public static String generateUserID() {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(15);
        for(int i = 0; i < 15; i++)
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        return sb.toString();
    }

    public static String generateVaultID() {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(16);
        for(int i = 0; i < 16; i++)
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        return sb.toString();
    }

    public static double checks(double d) {
        if (d < 0) return d *-1;
        else return d;
    }

}
