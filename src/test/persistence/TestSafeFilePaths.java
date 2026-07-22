package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class TestSafeFilePaths {

    @Test
    void testValidNameResolvesInsideDataDirectory() {
        Path result = SafeFilePaths.resolveDataFile("summer list.json");
        Path dataDirectory = Path.of("data").toAbsolutePath().normalize();

        assertTrue(result.startsWith(dataDirectory));
        assertEquals("summer list.json", result.getFileName().toString());
    }

    @Test
    void testUnsafeNamesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> SafeFilePaths.resolveDataFile("../outside"));
        assertThrows(IllegalArgumentException.class, () -> SafeFilePaths.resolveDataFile(""));
        assertThrows(IllegalArgumentException.class, () -> SafeFilePaths.resolveDataFile("a/b"));
    }
}
