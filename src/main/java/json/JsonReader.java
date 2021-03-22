package json;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.json.JSONObject;

public class JsonReader {
    private static String readAll(Reader read) throws IOException {
        int ref;
        StringBuilder builder = new StringBuilder();
        while ((ref = read.read()) != -1) {
            builder.append((char) ref);
        }
        return builder.toString();
    }

    public static JSONObject readJsonFromFile(String file) throws IOException {
        File initialFile = new File(file);

        try (InputStream stream = new FileInputStream(initialFile)) {
            BufferedReader bfRead = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String jsonString = readAll(bfRead);
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        }

    }
}
