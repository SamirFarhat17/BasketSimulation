package json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    private static String readAll(Reader read) throws IOException {
        int ref;
        StringBuilder builder = new StringBuilder();
        while ((ref = read.read()) != -1) {
            builder.append((char) ref);
        }
        return builder.toString();
    }

    public static JsonObject readJsonFromFile(String file) throws IOException {
        File initialFile = new File(file);
        InputStream stream = new FileInputStream(initialFile);

        try {
            BufferedReader bfRead = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String jsonString = readAll(bfRead);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonString);
            JsonObject obj = element.getAsJsonObject();
            return obj;
        }
        finally {
            stream.close();
        }

    }
}
