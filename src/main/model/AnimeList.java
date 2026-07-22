package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.StatusException;

// Represents a list of anime.
public class AnimeList {
    private final List<Anime> animes;

    public AnimeList() {
        animes = new ArrayList<>();
    }

    public void addAnime(Anime anime) {
        addAnime(anime, true);
    }

    private void addAnime(Anime anime, boolean logEvent) {
        Anime checkedAnime = Objects.requireNonNull(anime, "anime");
        animes.add(checkedAnime);
        if (logEvent) {
            EventLog.getInstance().logEvent(new Event("Added anime: " + checkedAnime.getName()));
        }
    }

    public boolean removeAnime(Anime anime) {
        boolean removed = animes.remove(anime);
        if (removed) {
            EventLog.getInstance().logEvent(new Event("Removed anime: " + anime.getName()));
        }
        return removed;
    }

    public List<Anime> searchByTypes(List<AnimeType> types) {
        Objects.requireNonNull(types, "types");
        List<Anime> result = new ArrayList<>();
        for (Anime anime : animes) {
            if (anime.getTypes().containsAll(types)) {
                result.add(anime);
            }
        }
        return result;
    }

    public List<Anime> searchByTime(YearMonth time) {
        Objects.requireNonNull(time, "time");
        List<Anime> result = new ArrayList<>();
        for (Anime anime : animes) {
            if (anime.getTime().equals(time)) {
                result.add(anime);
            }
        }
        return result;
    }

    public boolean updateStatus(Anime anime, WatchStatus status) {
        if (!animes.contains(anime)) {
            return false;
        }

        WatchStatus checkedStatus = Objects.requireNonNull(status, "status");
        if (anime.getStatus() == checkedStatus) {
            return false;
        }

        anime.setStatus(checkedStatus);
        EventLog.getInstance().logEvent(
                new Event("Updated status of " + anime.getName() + " to " + checkedStatus));
        return true;
    }

    public boolean updateStatus(Anime anime, String status) throws StatusException {
        return updateStatus(anime, WatchStatus.fromInput(status));
    }

    public List<Anime> getAnimes() {
        return Collections.unmodifiableList(animes);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray animeArray = new JSONArray();
        for (Anime anime : animes) {
            animeArray.put(anime.toJson());
        }
        json.put("animes", animeArray);
        return json;
    }

    public static AnimeList fromJson(JSONObject root) {
        AnimeList list = new AnimeList();
        JSONArray animeArray = root.getJSONArray("animes");
        for (int i = 0; i < animeArray.length(); i++) {
            list.addAnime(Anime.fromJson(animeArray.getJSONObject(i)), false);
        }
        return list;
    }
}
