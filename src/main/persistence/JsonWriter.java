// Citation: This class is based on the sample persistence code provided in the
// CPSC 121 (University of British Columbia) Json Serialization Demo.
// Modifications have been made to fit the structure and requirements
// of my own application.

package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

// Represents a writer that writes JSON representation of game progress to file
public class JsonWriter {
    
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer; 
    
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }
    
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }
    
    // MODIFIES: this
    // EFFECTS: writes JSON representation of the game to file
    public void write(Writable object) {
        JSONObject json = object.toJson();
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
