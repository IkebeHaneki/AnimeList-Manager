package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;

// Represents a reader that reads Anime List from JSON data stored in file
public class JsonReader {


    public JsonReader() {
        //stub
    }

    // EFFECTS: reads Anime List from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AnimeList read() throws IOException {
    return null;
        
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile() throws IOException {
        //stub
        return null;
    }

    // EFFECTS: parses Animelist from JSON object and returns it
    private AnimeList parseAnimeList() {
        return null;
    }

    // MODIFIES: list
    // EFFECTS: parses thingies from JSON object and adds them to anime list
    private void parseAnimes() {
        //stub
    }

    private void parseAnime() {
        //stub
    }

}
