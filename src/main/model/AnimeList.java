package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AnimeList {
    
    //EFFECT:creates a new list that no anime contained
    //MODIFIES:this
    public AnimeList() {
        //stub
    }

    
    //EFFECTS:add the anime into the anime list
    public void addAnime() {
        //stub
    }

    //EFFECTS: remove the anime from the list
    public void removeAnime() {
        //stub
    }

    //EFFECTES: Return the list of anime which contains the types chosen by user
    public List<Anime> searchByTypes() {
        return null;
    }

    //EFFECTS: Search the animes that users want by the release year and month
    public List<Anime> searchByTime() {
        return null;
    }

    //EFFECTS:update the anime status when it alrady added in the list
    //REQUIRE: anime should already in the list

    public void updateStatus() {
        //stub
    }

    //EFFECT: return the anime list
    public List<Anime> getAnimes() {
        return null;
    }
    
}
