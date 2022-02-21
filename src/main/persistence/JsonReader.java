package persistence;

import model.Garden;
import model.Plant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.stream.Stream;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads garden from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads garden from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Garden read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGarden(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses garden from JSON object and returns it
    private Garden parseGarden(JSONObject jsonObject) {
        Calendar currentDay = Calendar.getInstance();

        int initialDay = jsonObject.getInt("initialDay");

        Garden g = new Garden(currentDay.get(Calendar.DAY_OF_YEAR));
        addPlants(g, jsonObject);

        // updates watering times
        while (currentDay.get(Calendar.DAY_OF_YEAR) != initialDay) {
            System.out.println(currentDay.get(Calendar.DAY_OF_YEAR));
            System.out.println(initialDay);
            g.incrementOneDayForGarden();
            initialDay = (initialDay + 1) % 365;
        }
        return g;
    }


    // MODIFIES: g
    // EFFECTS: parses plants from JSON object and adds them to garden
    private void addPlants(Garden g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("myGarden");
        for (Object json : jsonArray) {
            JSONObject nextPlant = (JSONObject) json;
            addPlant(g, nextPlant);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses plant from JSON object and adds it to garden
    private void addPlant(Garden g, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int numDaysLastWatered = jsonObject.getInt("numDaysLastWatered");
        int wateringFrequency = jsonObject.getInt("wateringFrequency");

        Plant plant = new Plant(name, type, numDaysLastWatered, wateringFrequency);
        plant.setNumTimeWatered(jsonObject.getInt("numTimeWatered"));
        g.addPlant(plant);
    }
}
