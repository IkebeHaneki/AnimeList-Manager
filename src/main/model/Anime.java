package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.StatusException;

// Represents an anime with a name, genres, release month, and watch status.
public class Anime {
    private String name;
    private List<AnimeType> types;
    private YearMonth yearMonth;
    private WatchStatus watchStatus;

    public Anime(String name, List<AnimeType> types, YearMonth yearMonth, WatchStatus watchStatus) {
        setName(name);
        setTypes(types);
        setTime(yearMonth);
        setStatus(watchStatus);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String normalized = Objects.requireNonNull(name, "name").trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Anime name cannot be empty.");
        }
        this.name = normalized;
    }

    public List<AnimeType> getTypes() {
        return Collections.unmodifiableList(types);
    }

    public void setTypes(List<AnimeType> types) {
        Objects.requireNonNull(types, "types");
        if (types.isEmpty()) {
            throw new IllegalArgumentException("Anime must have at least one type.");
        }
        if (types.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Anime types cannot contain null.");
        }
        this.types = new ArrayList<>(types);
    }

    public YearMonth getTime() {
        return yearMonth;
    }

    public void setTime(String time) {
        setTime(YearMonth.parse(time, DateTimeFormatter.ofPattern("yyyy-MM")));
    }

    public void setTime(YearMonth yearMonth) {
        this.yearMonth = Objects.requireNonNull(yearMonth, "yearMonth");
    }

    public WatchStatus getStatus() {
        return watchStatus;
    }

    public void setStatus(WatchStatus watchStatus) {
        this.watchStatus = Objects.requireNonNull(watchStatus, "watchStatus");
    }

    public void setStatus(String watchStatus) throws StatusException {
        setStatus(WatchStatus.fromInput(watchStatus));
    }

    @Override
    public String toString() {
        return "Anime [name=" + name
                + ", types=" + types
                + ", releaseYearMonth=" + yearMonth
                + ", watchStatus=" + watchStatus + "]";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);

        JSONArray typesArray = new JSONArray();
        for (AnimeType type : types) {
            typesArray.put(type.name());
        }
        json.put("types", typesArray);
        json.put("releaseYearMonth", yearMonth.toString());
        json.put("watchStatus", watchStatus.name());
        return json;
    }

    public static Anime fromJson(JSONObject json) {
        String name = json.getString("name");

        List<AnimeType> typesList = new ArrayList<>();
        JSONArray typesArray = json.getJSONArray("types");
        for (int i = 0; i < typesArray.length(); i++) {
            typesList.add(AnimeType.valueOf(typesArray.getString(i)));
        }

        YearMonth releaseDate = YearMonth.parse(json.getString("releaseYearMonth"));
        WatchStatus status = WatchStatus.valueOf(json.getString("watchStatus"));
        return new Anime(name, typesList, releaseDate, status);
    }
}
