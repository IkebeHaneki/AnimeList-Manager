package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.AnimeList;

// Represents a writer that writes JSON representation of Anime List to file
public class JsonWriter {
    private PrintWriter writer;
    private String filePath;
    private static final int TAB = 4;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String filepath) {
        this.filePath = filepath;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(filePath);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of json object to file
    public void write(AnimeList list) {
        JSONObject json = list.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
