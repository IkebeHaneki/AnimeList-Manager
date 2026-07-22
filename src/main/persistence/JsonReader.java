package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

import model.AnimeList;

// Reads an anime list from JSON.
public class JsonReader {
    private final Path source;

    public JsonReader(String source) {
        this.source = Path.of(source);
    }

    public AnimeList read() throws IOException {
        try {
            String jsonData = Files.readString(source, StandardCharsets.UTF_8);
            return AnimeList.fromJson(new JSONObject(jsonData));
        } catch (RuntimeException exception) {
            throw new IOException("Invalid anime list data in " + source, exception);
        }
    }
}
