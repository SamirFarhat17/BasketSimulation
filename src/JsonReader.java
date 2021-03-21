import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    private static String readAll(Reader read) throws IOException {
        int ref;
        StringBuilder builder = new StringBuilder();
        while ((ref = read.read()) != -1) {
            builder.append((char) ref);
        }
        return builder.toString();
    }

    public static String readJsonFromFile(String file) throws IOException {
        File initialFile = new File(file);
        InputStream stream = new FileInputStream(initialFile);

        try {
            BufferedReader bfRead = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            return readAll(bfRead);
        }
        finally {
            stream.close();
        }

    }
}
