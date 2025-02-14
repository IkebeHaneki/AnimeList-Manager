package model;

import java.util.ArrayList;
import java.util.List;

public class AnimeList {
    private List<Anime> animes;
     // A New anime List

    public AnimeList() {
        this.animes = new ArrayList<>();
    }

    // Add a anime in the list
    public void addAnime(Anime anime) {
        animes.add(anime);
    }

    //Delete the chosen anime from list
    public void removeAnime(Anime anime) {
        animes.remove(anime);
    }

    // Search the animes that users want by the types they want
    public List<Anime> searchByTypes(List<AnimeType> types) {
        List<Anime> result = new ArrayList<>();
        for (Anime anime : animes) {
            if (anime.getTypes().containsAll(types)) {
                result.add(anime);
            }
        }
        return result;
    }
    
}
