package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.StatusException;

public class TestAnime {
    private Anime anime;

    @BeforeEach
    void runBefore() {
        anime = new Anime(
                "Attack on Titans",
                Arrays.asList(AnimeType.Action),
                YearMonth.of(2013, 4),
                WatchStatus.Completed);
    }

    @Test
    void testConstructor() {
        assertEquals("Attack on Titans", anime.getName());
        assertEquals(Arrays.asList(AnimeType.Action), anime.getTypes());
        assertEquals(YearMonth.of(2013, 4), anime.getTime());
        assertEquals(WatchStatus.Completed, anime.getStatus());
    }

    @Test
    void testSetters() throws StatusException {
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
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () -> anime.setName(" "));
        assertThrows(IllegalArgumentException.class, () -> anime.setTypes(List.of()));
        assertThrows(StatusException.class, () -> anime.setStatus("Plan"));
        assertThrows(UnsupportedOperationException.class, () -> anime.getTypes().add(AnimeType.Sports));
    }

    @Test
    void testJsonRoundTrip() {
        JSONObject json = anime.toJson();
        Anime restored = Anime.fromJson(json);

        assertEquals(anime.getName(), restored.getName());
        assertEquals(anime.getTypes(), restored.getTypes());
        assertEquals(anime.getTime(), restored.getTime());
        assertEquals(anime.getStatus(), restored.getStatus());
    }
}
