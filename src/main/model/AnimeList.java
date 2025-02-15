package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import exception.StatusException;

public class AnimeList {

    private List<Anime> animes;
    
    //EFFECT:creates a new list that no anime contained
    //MODIFIES:this
    
    public AnimeList() {
        this.animes = new ArrayList<>();
    }

    
    //EFFECTS:add the anime into the anime list
    public void addAnime(Anime anime) {
        //stub
        animes.add(anime);
    }

    //EFFECTS: remove the anime from the list
    public void removeAnime(Anime anime) {
        //stub
        animes.remove(anime);
    }
    
    
    //EFFECTES: Return the list of anime which contains the types chosen by user
    
    public List<Anime> searchByTypes(List<AnimeType> types) {
        List<Anime> result = new ArrayList<>();
        for (Anime anime : animes) {
            if (anime.getTypes().containsAll(types)) {
                result.add(anime);
            }
        }
        return result;
    }

    //EFFECTS: Search the animes that users want by the release year and month
    public List<Anime> searchByTime(YearMonth time) {
        List<Anime> result = new ArrayList<>();
        for (Anime anime : animes) {
            if (anime.getTime().equals(time)) {
                result.add(anime);
            }
        }
        return result;
    }

    //EFFECTS:update the anime status when it alrady added in the list
    //REQUIRE: anime should already in the list
    
    public void updateStatus(Anime anime, String status) throws StatusException {
        if (animes.contains(anime)) {
            anime.setStatus(status);
        }
    }

    //EFFECT: return the anime list
    public List<Anime> getAnimes() {
        return animes;
    }
    
}
