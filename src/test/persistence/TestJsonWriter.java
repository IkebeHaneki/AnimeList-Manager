package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;

public class TestJsonWriter {

    @TempDir
    Path temporaryDirectory;

    @Test
    void testWriterAndReaderRoundTrip() throws IOException {
        AnimeList list = sampleList();
        Path destination = temporaryDirectory.resolve("anime-list.json");

        new JsonWriter(destination.toString()).write(list);
        AnimeList restored = new JsonReader(destination.toString()).read();

        assertEquals(2, restored.getAnimes().size());
        assertEquals(list.getAnimes().get(0).getName(), restored.getAnimes().get(0).getName());
        assertEquals(list.getAnimes().get(0).getTypes(), restored.getAnimes().get(0).getTypes());
        assertEquals(list.getAnimes().get(1).getStatus(), restored.getAnimes().get(1).getStatus());
    }

    @Test
    void testWriterRejectsInvalidParent() throws IOException {
        Path parentFile = temporaryDirectory.resolve("not-a-directory");
        Files.writeString(parentFile, "content");
        Path destination = parentFile.resolve("anime-list.json");

        assertThrows(
                IOException.class,
                () -> new JsonWriter(destination.toString()).write(sampleList()));
    }

    private AnimeList sampleList() {
        AnimeList list = new AnimeList();
        list.addAnime(new Anime(
                "Naruto",
                Arrays.asList(AnimeType.Action, AnimeType.School),
                YearMonth.of(2002, 10),
                WatchStatus.Watching));
        list.addAnime(new Anime(
                "Your Lie in April",
                Arrays.asList(AnimeType.Romance, AnimeType.Tragic),
                YearMonth.of(2014, 4),
                WatchStatus.Completed));
        return list;
    }
}
