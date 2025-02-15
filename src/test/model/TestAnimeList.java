package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import exception.StatusException;

import org.junit.jupiter.api.BeforeEach;

public class TestAnimeList {
    private Anime anime1;
    private Anime anime2;
    private Anime anime3;
    private AnimeList testAnimeList;

    @BeforeEach
    public void runBefore() {
        testAnimeList = new AnimeList();

        anime1 = new Anime("Naruto",
                Arrays.asList(AnimeType.Adventure, AnimeType.Action),
                YearMonth.of(2002, 10), WatchStatus.Plan_to_watch);

        anime2 = new Anime("Your Lie in April",
                Arrays.asList(AnimeType.Romance, AnimeType.Tragic),
                YearMonth.of(2014, 4),
                WatchStatus.Completed);
            
        anime3 = new Anime("Demon Slayer",
                Arrays.asList(AnimeType.Action, AnimeType.Adventure),
                YearMonth.of(2019, 4),
                WatchStatus.Watching);

    }

    @Test
    public void testAddAnime() {

        assertEquals(0, testAnimeList.getAnimes().size());

        testAnimeList.addAnime(anime1);
        assertEquals(1, testAnimeList.getAnimes().size());

        testAnimeList.addAnime(anime2);
        assertEquals(2, testAnimeList.getAnimes().size());


        testAnimeList.addAnime(anime3);
        assertEquals(3, testAnimeList.getAnimes().size());
        assertTrue(testAnimeList.getAnimes().contains(anime3));
    }

    @Test
    public void testRemoveAnime() {
        testAnimeList.addAnime(anime1);
        testAnimeList.addAnime(anime2);

        assertEquals(2, testAnimeList.getAnimes().size());
        assertTrue(testAnimeList.getAnimes().contains(anime1));
        assertTrue(testAnimeList.getAnimes().contains(anime2));

        testAnimeList.removeAnime(anime1);
        assertEquals(1, testAnimeList.getAnimes().size());
        assertFalse(testAnimeList.getAnimes().contains(anime1));
        assertTrue(testAnimeList.getAnimes().contains(anime2));
    }

    @Test
    public void testSearchByTypes() {
        testAnimeList.addAnime(anime1);
        testAnimeList.addAnime(anime2);
        testAnimeList.addAnime(anime3);

        List<Anime> result = testAnimeList.searchByTypes(Arrays.asList(AnimeType.Adventure));
        assertTrue(result.contains(anime1));
        assertTrue(result.contains(anime3));
        assertFalse(result.contains(anime2));

        List<Anime> result2 = testAnimeList.searchByTypes(Arrays.asList(AnimeType.Romance,AnimeType.Tragic));
        assertTrue(result2.contains(anime2));
        assertFalse(result2.contains(anime1));
        assertFalse(result2.contains(anime3));


    }

    @Test
    public void testSearchByTime() {
        testAnimeList.addAnime(anime1);
        testAnimeList.addAnime(anime2);
        testAnimeList.addAnime(anime3);

        List<Anime> result = testAnimeList.searchByTime(YearMonth.of(2002, 10));
        assertTrue(result.contains(anime1));
        assertFalse(result.contains(anime2));
        assertFalse(result.contains(anime3));


        List<Anime> result2 = testAnimeList.searchByTime(YearMonth.of(2014, 5));
        assertTrue(result2.isEmpty());
    }

    @Test
    public void testUpdateStatus() throws StatusException {
        testAnimeList.addAnime(anime1);
        testAnimeList.addAnime(anime2);

        testAnimeList.updateStatus(anime1, "Watching");
        assertEquals(WatchStatus.Watching, anime1.getStatus());

        testAnimeList.updateStatus(anime3, "Completed");
        assertEquals(WatchStatus.Watching, anime3.getStatus());


        
    }
}
