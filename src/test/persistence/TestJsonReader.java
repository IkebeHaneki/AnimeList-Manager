package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;

public class TestJsonReader {

    @TempDir
    Path temporaryDirectory;

    @Test
    void testReaderNonExistentFile() {
        Path missing = temporaryDirectory.resolve("missing.json");
        assertThrows(IOException.class, () -> new JsonReader(missing.toString()).read());
    }

    @Test
    void testReaderRejectsInvalidJson() throws IOException {
        Path invalid = temporaryDirectory.resolve("invalid.json");
        Files.writeString(invalid, "{not valid json");
        assertThrows(IOException.class, () -> new JsonReader(invalid.toString()).read());
    }

    @Test
    void testReaderLoadsValidList() throws IOException {
        Path file = temporaryDirectory.resolve("valid.json");
        AnimeList source = new AnimeList();
        source.addAnime(new Anime(
                "Naruto",
                List.of(AnimeType.Action),
                YearMonth.of(2002, 10),
                WatchStatus.Watching));
        new JsonWriter(file.toString()).write(source);

        AnimeList restored = new JsonReader(file.toString()).read();

        assertEquals(1, restored.getAnimes().size());
        assertEquals("Naruto", restored.getAnimes().get(0).getName());
    }
}
