package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

// Represents a writer that writes JSON representation of AnimeList to file
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    // MODIFIES: this
    public JsonWriter(String filepath) {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of json object to file
    public void write(JSONObject json) {
       //stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        //stub
    }


    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
       //stub
    }

}
