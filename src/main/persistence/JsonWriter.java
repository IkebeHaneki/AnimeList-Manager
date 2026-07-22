package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import model.AnimeList;

// Writes an anime list to JSON using an atomic replacement where supported.
public class JsonWriter {
    private static final int INDENT = 4;
    private final Path destination;

    public JsonWriter(String filePath) {
        destination = Path.of(filePath).toAbsolutePath().normalize();
    }

    public void write(AnimeList list) throws IOException {
        Path parent = destination.getParent();
        if (parent == null) {
            throw new IOException("Destination must have a parent directory.");
        }

        Files.createDirectories(parent);
        Path temporaryFile = Files.createTempFile(parent, destination.getFileName().toString(), ".tmp");
        try {
            Files.writeString(
                    temporaryFile,
                    list.toJson().toString(INDENT),
                    StandardCharsets.UTF_8);
            moveIntoPlace(temporaryFile);
        } finally {
            Files.deleteIfExists(temporaryFile);
        }
    }

    private void moveIntoPlace(Path temporaryFile) throws IOException {
        try {
            Files.move(
                    temporaryFile,
                    destination,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
