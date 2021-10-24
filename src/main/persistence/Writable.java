package persistence;

import org.json.JSONObject;

// Includes behaviours for writable objects
public interface Writable {
    // EFFECTS: returns this as JSON Object
    JSONObject toJson();
}
