package persistence;

import model.Account;
import model.StockMarket;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representations of StockMarket and Account to file
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of StockMarket and Account to file
    // This method references code from this website:
    // https://stackoverflow.com/questions/2403132/merge-concat-multiple-jsonobjects-in-java
    public void write(StockMarket sm, Account a) {
        JSONObject jsonSM = sm.toJson();
        JSONObject jsonAcc = a.toJson();

        JSONObject combined = new JSONObject(jsonSM, JSONObject.getNames(jsonSM));
        for (String key : JSONObject.getNames(jsonAcc)) {
            combined.put(key, jsonAcc.get(key));
        }

        saveToFile(combined.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes String to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
