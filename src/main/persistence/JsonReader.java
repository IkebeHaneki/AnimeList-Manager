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

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Anime List from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AnimeList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAnimeList(jsonObject);

    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Files.lines(Paths.get(source), StandardCharsets.UTF_8)
                .forEach(line -> contentBuilder.append(line));
        return contentBuilder.toString();
    }

    // EFFECTS: parses Animelist from JSON object and returns it
    private AnimeList parseAnimeList(JSONObject jsonObject) {
        AnimeList list = new AnimeList();
        parseAnimes(jsonObject, list);
        return list;
    }

    // MODIFIES: list
    // EFFECTS: parses thingies from JSON object and adds them to anime list
    private void parseAnimes(JSONObject jsonObject, AnimeList list) {
        JSONArray animesArray = jsonObject.getJSONArray("animes");
        for (Object obj : animesArray) {
            JSONObject animeJson = (JSONObject) obj;
            parseAnime(animeJson, list);
        }
    }

    private void parseAnime(JSONObject animeJson, AnimeList list) {
        String name = animeJson.getString("name");
        JSONArray typesArray = animeJson.getJSONArray("types");
        List<AnimeType> types = new ArrayList<>();

        for (Object t : typesArray) {
            String typeStr = (String) t;
            types.add(AnimeType.valueOf(typeStr));
        }

        YearMonth releaseYM = YearMonth.parse(animeJson.getString("releaseYearMonth"));
        WatchStatus status = WatchStatus.valueOf(animeJson.getString("watchStatus"));

        Anime anime = new Anime(name, types, releaseYM, status);
        list.addAnime(anime);
    }

}
