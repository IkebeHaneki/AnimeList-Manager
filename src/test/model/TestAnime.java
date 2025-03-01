package model;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import java.lang.ProcessBuilder.Redirect.Type;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class TestAnime {
    private Anime anime;
    
    @BeforeEach
    void runBefore() {
        anime = new Anime("Attack on Titans", 
        Arrays.asList(AnimeType.Action), 
        YearMonth.of(2013, 04), 
        WatchStatus.Completed);

    }

    @Test
    void testConstructor() {
        assertEquals("Attack on Titans", anime.getName());
        assertEquals(Arrays.asList(AnimeType.Action), anime.getTypes());
        assertEquals(1, anime.getTypes().size());
        assertEquals(YearMonth.of(2013, 4), anime.getTime());
        assertEquals(WatchStatus.Completed, anime.getStatus());
    }

    @Test
    void testConstructorSetter() throws Exception {
        anime.setName("Spy Family");
        anime.setStatus("Watching");
        anime.setTime("2018-09");
        anime.setTypes(Arrays.asList(AnimeType.Comedy, AnimeType.Romance));

        assertEquals("Spy Family", anime.getName());
        assertEquals(WatchStatus.Watching, anime.getStatus());
        assertEquals(YearMonth.of(2018, 9), anime.getTime());
        assertEquals(Arrays.asList(AnimeType.Comedy, AnimeType.Romance), anime.getTypes());
    }

    @Test
    void testSetStatus() throws Exception {
        anime.setStatus("plan to watch");
        assertEquals(WatchStatus.Plan_to_watch, anime.getStatus());

        anime.setStatus("Completed");
        assertEquals(WatchStatus.Completed, anime.getStatus());

        try {
            anime.setStatus("Plan"); 
            fail();
        } catch (Exception e) {
            //pass
        }
    }

    @Test
    void testToString() {
        Anime anime = new Anime(
                "One Piece",
                Arrays.asList(AnimeType.Action, AnimeType.Comedy),
                YearMonth.of(1999, 10),
                WatchStatus.Watching
        );
        assertEquals("Anime [name=One Piece, types=[Action, Comedy],"
                + 
                " releaseYearMonth=1999-10, watchStatus=Watching]",  anime.toString());
    }

    @Test
    public void testToJson() {
        // Create an Anime object
        List<AnimeType> types = Arrays.asList(AnimeType.Action, AnimeType.School);
        Anime anime = new Anime("Naruto", types, YearMonth.of(2002, 10), WatchStatus.Watching);

        // Convert it to JSON
        JSONObject json = anime.toJson();

        // Check that the JSON contains all the correct fields
        Assertions.assertEquals("Naruto", json.getString("name"));
        Assertions.assertTrue(json.getJSONArray("types").toList().contains("Action"));
        Assertions.assertTrue(json.getJSONArray("types").toList().contains("School"));
        Assertions.assertEquals("2002-10", json.getString("releaseYearMonth"));
        Assertions.assertEquals("Watching", json.getString("watchStatus"));
    }

    @Test
    public void testFromJson() {
        // Manually create a JSON object that matches an Anime
        JSONObject json = new JSONObject();
        json.put("name", "Your Lie in April");
        json.put("types", Arrays.asList("Romance", "Tragic"));
        json.put("releaseYearMonth", "2014-04");
        json.put("watchStatus", "Completed");

        // Convert JSON to Anime
        Anime anime = Anime.fromJson(json);

        // Verify that the Anime object was constructed correctly
        Assertions.assertEquals("Your Lie in April", anime.getName());
        Assertions.assertTrue(anime.getTypes().contains(AnimeType.Romance));
        Assertions.assertTrue(anime.getTypes().contains(AnimeType.Tragic));
        Assertions.assertEquals(YearMonth.of(2014, 4), anime.getTime());
        Assertions.assertEquals(WatchStatus.Completed, anime.getStatus());
    }
}
