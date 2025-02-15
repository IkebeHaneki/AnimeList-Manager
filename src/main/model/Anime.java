package model;

import java.time.YearMonth;
import java.util.List;

//import javax.sound.sampled.AudioFileFormat.Type;

// Represents an Anime having a name, list of types(genres), 
// and its release date(yyyy-MM), watch status
public class Anime {
    


    /*
     * EFFECTS: name of the anime is set to name; 
     * Anime types is a list that never be null;
     * YearMonth is the release date of anime, in format of yyyy-MM.
     * Watch Status is the status of the anime(Watching, Completed, Plan to Watch)
     */

    public Anime() {
        //stub
    }

    
    //EFFECTS: return the name of the anime
    public String getName() {
        return null;
    }

    
    //MODIFIES:this
    //EFFECTS:Change the anime's name to the given name
    public void setName(String name) {
    } 

    // Get the types of the anime
    //EFFECTS:return the list of types of the anime
    public List<AnimeType> getTypes() {
        return null;
    }

    
    //MODIFIES:this
    //EFFECTS:set the list of types for anime
    public void setTypes(List<AnimeType> types) {
    }

    //EFFECTS:Get the release date and month of the anime
    public YearMonth getTime() {
        return null;
    }

    //EFFECTS: Set the release year and month for the anime
    //MODIFIES:this
    public void setTime(String time) {
    }

    //EFFECTS: Get the watch status of the anime
    public WatchStatus getStatus() {
        return null;
    }

    //EFFECTS:Set the watch status of the anime
    // MODIFIES: this
    public void setStatus(String watchstatus) {
    }
}