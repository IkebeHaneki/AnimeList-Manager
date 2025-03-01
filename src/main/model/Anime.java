package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.StatusException;

// Represents an Anime having a name, list of types(genres), 
// and its release date(yyyy-MM), watch status
public class Anime {
    private String name; // name of the anime
    private List<AnimeType> types; // list of anime types or genres
    private YearMonth yearMonth; // the release date
    private WatchStatus watchstatus; // the watch status

    /*
     * EFFECTS: name of the anime is set to name;
     * Anime types is a list that never be null;
     * YearMonth is the release date of anime, in format of yyyy-MM.
     * Watch Status is the status of the anime(Watching, Completed, Plan to Watch)
     */

    public Anime(String name, List<AnimeType> types, YearMonth yearMonth, WatchStatus watchstatus) {
        // stub
        this.name = name;
        this.types = types;
        this.yearMonth = yearMonth;
        this.watchstatus = watchstatus;
    }

    // EFFECTS: return the name of the anime
    public String getName() {
        return name;
    }

    // MODIFIES:this
    // EFFECTS:Change the anime's name to the given name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS:return the list of types of the anime
    public List<AnimeType> getTypes() {
        return types;
    }

    // MODIFIES:this
    // EFFECTS:set the list of types for anime
    public void setTypes(List<AnimeType> types) {
        this.types = types;
    }

    // EFFECTS:Get the release date and month of the anime
    public YearMonth getTime() {
        return yearMonth;
    }

    // EFFECTS: Set the release year and month for the anime
    // MODIFIES:this
    public void setTime(String time) {
        this.yearMonth = java.time.YearMonth.parse(time, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    // EFFECTS: Get the watch status of the anime
    public WatchStatus getStatus() {
        return watchstatus;
    }

    // EFFECTS:Set the watch status of the anime
    // MODIFIES: this
    public void setStatus(String watchstatus) throws StatusException {
        switch (watchstatus.toLowerCase()) {
            case "watching":
                this.watchstatus = WatchStatus.Watching;
                break;
            case "completed":
                this.watchstatus = WatchStatus.Completed;
                break;
            case "plan to watch":
                this.watchstatus = WatchStatus.Plan_to_watch;
                break;
            default:
                throw new StatusException();
        }
    }

    @Override
    // EFFECTS: returns a string representation of Anime
    public String toString() {
        return "Anime [name=" + name
                + ", types=" + types
                + ", releaseYearMonth=" + yearMonth
                + ", watchStatus=" + watchstatus + "]";
    }

    // EFFECTS: Convert the anime to a Json readable object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);

        JSONArray typesArray = new JSONArray();
        for (AnimeType t : types) {
            typesArray.put(t.name());
        }
        json.put("types", typesArray);

        json.put("releaseYearMonth", yearMonth.toString());

        json.put("watchStatus", watchstatus.name());

        return json;
    }

    //EFFECTS:Reconstruct Animes from a Json Object;
    public static Anime fromJson(JSONObject json) {
        String name = json.getString("name");

        List<AnimeType> typesList = new ArrayList<>();
        JSONArray typesArray = json.getJSONArray("types");
        for (int i = 0; i < typesArray.length(); i++) {
            String typeStr = typesArray.getString(i);
            typesList.add(AnimeType.valueOf(typeStr));
        }

        YearMonth ym = YearMonth.parse(json.getString("releaseYearMonth"));
        WatchStatus ws = WatchStatus.valueOf(json.getString("watchStatus"));

        return new Anime(name, typesList, ym, ws);
    }
}