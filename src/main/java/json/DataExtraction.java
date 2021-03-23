package json;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvException;

public class DataExtraction {

    public static ArrayList<String> makeDates() throws IOException {
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

}
