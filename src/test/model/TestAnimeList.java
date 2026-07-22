package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAnimeList {
    private Anime anime1;
    private Anime anime2;
    private Anime anime3;
    private AnimeList animeList;

    @BeforeEach
    void runBefore() {
        animeList = new AnimeList();
        anime1 = new Anime(
                "Naruto",
                Arrays.asList(AnimeType.Adventure, AnimeType.Action),
                YearMonth.of(2002, 10),
                WatchStatus.Plan_to_watch);
        anime2 = new Anime(
                "Your Lie in April",
                Arrays.asList(AnimeType.Romance, AnimeType.Tragic),
                YearMonth.of(2014, 4),
                WatchStatus.Completed);
        anime3 = new Anime(
                "Demon Slayer",
                Arrays.asList(AnimeType.Action, AnimeType.Adventure),
                YearMonth.of(2019, 4),
                WatchStatus.Watching);
    }

    @Test
    void testAddAndRemoveAnime() {
        animeList.addAnime(anime1);
        animeList.addAnime(anime2);

        assertEquals(2, animeList.getAnimes().size());
        assertTrue(animeList.removeAnime(anime1));
        assertFalse(animeList.removeAnime(anime3));
        assertEquals(List.of(anime2), animeList.getAnimes());
        assertThrows(UnsupportedOperationException.class, () -> animeList.getAnimes().clear());
    }

    @Test
    void testSearchByTypes() {
        addAllAnime();

        assertEquals(
                List.of(anime1, anime3),
                animeList.searchByTypes(List.of(AnimeType.Adventure)));
        assertEquals(
                List.of(anime2),
                animeList.searchByTypes(List.of(AnimeType.Romance, AnimeType.Tragic)));
    }

    @Test
    void testSearchByTime() {
        addAllAnime();

        assertEquals(
                List.of(anime1),
                animeList.searchByTime(YearMonth.of(2002, 10)));
        assertTrue(animeList.searchByTime(YearMonth.of(2014, 5)).isEmpty());
    }

    @Test
    void testUpdateStatusReportsWhetherItChanged() {
        animeList.addAnime(anime1);

        assertTrue(animeList.updateStatus(anime1, WatchStatus.Watching));
        assertEquals(WatchStatus.Watching, anime1.getStatus());
        assertFalse(animeList.updateStatus(anime1, WatchStatus.Watching));
        assertFalse(animeList.updateStatus(anime3, WatchStatus.Completed));
        assertEquals(WatchStatus.Watching, anime3.getStatus());
    }

    @Test
    void testJsonRoundTripDoesNotLoseData() {
        addAllAnime();

        JSONObject json = animeList.toJson();
        AnimeList restored = AnimeList.fromJson(json);

        assertEquals(3, restored.getAnimes().size());
        assertEquals("Naruto", restored.getAnimes().get(0).getName());
        assertEquals(anime2.getTypes(), restored.getAnimes().get(1).getTypes());
        assertEquals(anime3.getStatus(), restored.getAnimes().get(2).getStatus());
    }

    private void addAllAnime() {
        animeList.addAnime(anime1);
        animeList.addAnime(anime2);
        animeList.addAnime(anime3);
    }
}
