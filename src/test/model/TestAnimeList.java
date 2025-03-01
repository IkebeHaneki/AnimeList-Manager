package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import exception.StatusException;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
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


    @Test
    public void testToJson() {
        AnimeList animes = new AnimeList();
        Anime anime1 = new Anime("Naruto",
                Arrays.asList(AnimeType.Action, AnimeType.School),
                YearMonth.of(2002, 10),
                WatchStatus.Watching);
        Anime anime2 = new Anime("Your Lie in April",
                Arrays.asList(AnimeType.Romance, AnimeType.Tragic),
                YearMonth.of(2014, 4),
                WatchStatus.Completed);

        animes.addAnime(anime1);
        animes.addAnime(anime2);

        JSONObject json = animes.toJson();

        Assertions.assertTrue(json.has("animes"), "JSON should have 'animes' key");

        Assertions.assertEquals(2, json.getJSONArray("animes").length(),
                "Should contain 2 anime in the 'animes' array");

        JSONObject firstAnimeJson = json.getJSONArray("animes").getJSONObject(0);
        Assertions.assertEquals("Naruto", firstAnimeJson.getString("name"));

        JSONObject secondAnimeJson = json.getJSONArray("animes").getJSONObject(1);
        Assertions.assertEquals("Your Lie in April", secondAnimeJson.getString("name"));
    }


    @Test
    public void testFromJson() {
        JSONObject root = new JSONObject();
        JSONObject anime1Json = new JSONObject();
        anime1Json.put("name", "Naruto");
        anime1Json.put("types", Arrays.asList("Action", "School"));
        anime1Json.put("releaseYearMonth", "2002-10");
        anime1Json.put("watchStatus", "Watching");

        JSONObject anime2Json = new JSONObject();
        anime2Json.put("name", "Your Lie in April");
        anime2Json.put("types", Arrays.asList("Romance", "Tragic"));
        anime2Json.put("releaseYearMonth", "2014-04");
        anime2Json.put("watchStatus", "Completed");

        root.put("animes", Arrays.asList(anime1Json, anime2Json));
        AnimeList loaded = AnimeList.fromJson(root);

        Anime a1 = loaded.getAnimes().get(0);
        Assertions.assertEquals("Naruto", a1.getName());
        Assertions.assertTrue(a1.getTypes().contains(AnimeType.Action));
        Assertions.assertEquals(YearMonth.of(2002, 10), a1.getTime());
        Assertions.assertEquals(WatchStatus.Watching, a1.getStatus());

        Anime a2 = loaded.getAnimes().get(1);
        Assertions.assertEquals("Your Lie in April", a2.getName());
        Assertions.assertTrue(a2.getTypes().contains(AnimeType.Tragic));
        Assertions.assertEquals(YearMonth.of(2014, 4), a2.getTime());
        Assertions.assertEquals(WatchStatus.Completed, a2.getStatus());
    }

}
