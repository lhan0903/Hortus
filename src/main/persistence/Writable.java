package persistence;

import org.json.JSONObject;

// This interface references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: return this as JSON object
    JSONObject toJson();
}
