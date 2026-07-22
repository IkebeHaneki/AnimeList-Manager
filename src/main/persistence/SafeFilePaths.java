package persistence;

import java.nio.file.Path;
import java.util.regex.Pattern;

// Resolves user-provided list names inside the application data directory.
public final class SafeFilePaths {
    private static final int MAX_NAME_LENGTH = 80;
    private static final Pattern SAFE_NAME = Pattern.compile("[\\p{L}\\p{N}][\\p{L}\\p{N} _-]*");

    private SafeFilePaths() {
    }

    public static Path resolveDataFile(String input) {
        if (input == null) {
            throw new IllegalArgumentException("File name is required.");
        }

        String name = input.trim();
        if (name.toLowerCase().endsWith(".json")) {
            name = name.substring(0, name.length() - ".json".length()).trim();
        }

        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH || !SAFE_NAME.matcher(name).matches()) {
            throw new IllegalArgumentException(
                    "Use 1-80 letters, numbers, spaces, hyphens, or underscores for the file name.");
        }

        Path dataDirectory = Path.of("data").toAbsolutePath().normalize();
        Path resolved = dataDirectory.resolve(name + ".json").normalize();
        if (!resolved.startsWith(dataDirectory)) {
            throw new IllegalArgumentException("File must stay inside the data directory.");
        }
        return resolved;
    }
}
