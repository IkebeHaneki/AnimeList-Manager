package model;

import java.time.YearMonth;
import java.util.List;

import exception.StatusException;

//import javax.sound.sampled.AudioFileFormat.Type;

// Represents an Anime having a name, list of types(genres), 
// and its release date(yyyy-MM), watch status
public class Anime {
    private String name;   // name of the anime
    private List<AnimeType> types;  //list of anime types or genres
    private YearMonth yearMonth;    // the release date
    private WatchStatus watchstatus;  //the watch status


    /*
     * EFFECTS: name of the anime is set to name; 
     * Anime types is a list that never be null;
     * YearMonth is the release date of anime, in format of yyyy-MM.
     * Watch Status is the status of the anime(Watching, Completed, Plan to Watch)
     */

    public Anime(String name, List<AnimeType> types, YearMonth yearMonth, WatchStatus watchstatus) {
        //stub
        this.name = name;
        this.types = types;
        this.yearMonth = yearMonth;
        this.watchstatus = watchstatus;
    }

    
    //EFFECTS: return the name of the anime
    public String getName() {
        return name;
    }

    
    //MODIFIES:this
    //EFFECTS:Change the anime's name to the given name
    public void setName(String name) {
        this.name = name;
    }  

    
    //EFFECTS:return the list of types of the anime
    public List<AnimeType> getTypes() {
        return types;
    }

    
    //MODIFIES:this
    //EFFECTS:set the list of types for anime
    public void setTypes(List<AnimeType> types) {
        this.types = types;
    }


    //EFFECTS:Get the release date and month of the anime
    public YearMonth getTime() {
        return yearMonth;
    }

    //EFFECTS: Set the release year and month for the anime
    //MODIFIES:this
    public void setTime(String time) {
        this.yearMonth = java.time.YearMonth.parse(time, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    //EFFECTS: Get the watch status of the anime
    public WatchStatus getStatus() {
        return watchstatus;
    }

    //EFFECTS:Set the watch status of the anime
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
    //EFFECTS: returns a string representation of Anime
    public String toString() {
        return "Anime [name=" + name
            + ", types=" + types
            + ", releaseYearMonth=" + yearMonth
            + ", watchStatus=" + watchstatus + "]";
    }
}