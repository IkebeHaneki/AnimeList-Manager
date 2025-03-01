package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Arrays;


import org.junit.jupiter.api.Test;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;

public class TestJsonWriter {

    Anime anime1 = new Anime(
            "Naruto",
            Arrays.asList(AnimeType.Action, AnimeType.School),
            YearMonth.of(2002, 10),
            WatchStatus.Watching);
    Anime anime2 = new Anime(
            "Your Lie in April",
            Arrays.asList(AnimeType.Romance, AnimeType.Tragic),
            YearMonth.of(2014, 4),
            WatchStatus.Completed);

    @Test
    public void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegalFileName.json");
        assertThrows(FileNotFoundException.class, () -> {
            writer.open(); 
        });
    }

    @Test
    public void testWriterGeneralAnimeList() {
        try {
            AnimeList list = new AnimeList();
            list.addAnime(anime1);
            list.addAnime(anime2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAnimeList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAnimeList.json");
            AnimeList loadedList = reader.read();

            assertEquals(2, loadedList.getAnimes().size());

            Anime loadedAnime1 = loadedList.getAnimes().get(0);
            checkSameAnime(loadedAnime1, anime1);
            Anime loadedAnime2 = loadedList.getAnimes().get(1);
            checkSameAnime(loadedAnime2, anime2);

        } catch (IOException e) {
            fail();
        }
    }

    private void checkSameAnime(Anime check, Anime expect) {
        assertEquals(check.getName(), expect.getName());
        assertEquals(check.getTypes().size(), check.getTypes().size());
        assertEquals(check.getTime(), expect.getTime());
        assertEquals(check.getStatus(), expect.getStatus());
    }
}
