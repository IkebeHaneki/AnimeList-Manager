package model;

import java.time.YearMonth;
import java.util.List;

public class Anime {
    private String name;
    private List<AnimeType> types;
    private YearMonth yearMonth;
    private WatchStatus watchstatus;
    
    public Anime(String name, List<AnimeType> types, YearMonth yearMonth, WatchStatus watchstatus) {
        this.name = name;
        this.types = types;
        this.yearMonth = yearMonth;
        this.watchstatus = watchstatus;
    }

    // Get the name of the Anime
    public String getName() {
        return name;
    }

    // Set up the anime's name
    public void setName(String name) {
        this.name = name;
    } 

    // Get the types of the anime
    public List<AnimeType> getTypes() {
        return types;
    }

    // Set the types of the anime
    public void setTypes(List<AnimeType> types) {
        this.types = types;
    }

    // Get the release date and month of the anime
    public YearMonth getTime() {
        return yearMonth;
    }

    // Set the release year and month for the anime
    public void setTime(YearMonth time) {
        this.yearMonth = time;
    }

    // Get the watch status of the anime
    public WatchStatus getStatus() {
        return watchstatus;
    }

    // Set the watch status of the anime
    public void setStatus(WatchStatus watchstatus) {
        this.watchstatus = watchstatus;
    }

    @Override
    public String toString() {
        return "Anime [name=" + name
            + ", types=" + types
            + ", releaseYearMonth=" + yearMonth
            + ", watchStatus=" + watchstatus + "]";
    }
}