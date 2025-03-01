package persistence;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.YearMonth;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {

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

   // @Test
   // public void testReaderNonExistentFile() {
   //     JsonReader reader = new JsonReader("./data/noSuchFile.json");
   //     assertThrows(IOException.class, () -> {
   //         reader.read(); // should throw IOException
   //     });
   // }

    @Test
    public void testReaderGeneralAnimeList() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralAnimeList.json");
        try {
            AnimeList list = reader.read();
            assertEquals(2, list.getAnimes().size(),
                    "Loaded list should contain 2 anime.");

            Anime a1 = list.getAnimes().get(0);
            assertEquals("Naruto", a1.getName());
            assertTrue(a1.getTypes().contains(AnimeType.Action));
            assertEquals(YearMonth.of(2002, 10), a1.getTime());
            assertEquals(WatchStatus.Watching, a1.getStatus());

            Anime a2 = list.getAnimes().get(1);
            assertEquals("Your Lie in April", a2.getName());
            assertTrue(a2.getTypes().contains(AnimeType.Tragic));
            assertEquals(YearMonth.of(2014, 4), a2.getTime());
            assertEquals(WatchStatus.Completed, a2.getStatus());

        } catch (IOException e) {
            fail("Couldn't read from file: " + e.getMessage());
        }
    }

}
