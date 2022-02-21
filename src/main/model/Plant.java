package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a plant with a name, type, number of days last watered, number of days
// until next watering, watering frequency (in days), and number of times watered since the
// usage of this application
public class Plant implements Writable {
    private String name;
    private String type;
    private int numDaysLastWatered;
    private int numDaysUntilWater;
    private int wateringFrequency;
    private int numTimeWatered = 0;

    // constructor
    // REQUIRES: all plant names are unique, numDaysLastWatered >=0, wateringFrequency > 0
    public Plant(String name, String type, int numDaysLastWatered, int wateringFrequency) {
        this.name = name;
        this.type = type;
        this.numDaysLastWatered = numDaysLastWatered;
        this.wateringFrequency = wateringFrequency;
        updateNumDaysUntilWater();
    }


    // EFFECTS: sets num of days last watered to 0 and num of days back to the watering frequency,
    //          increase num of times watered since app usage by 1
    // MODIFIES: this
    public void water() {
        numDaysLastWatered = 0;
        updateNumDaysUntilWater();
        numTimeWatered++;
        EventLog.getInstance().logEvent(new Event(this.getName() + " has been watered."));
    }

    // EFFECTS: updates plant watering status after the passage of one day by counting down num of days
    //          until next watering by 1 AND counting up num of days last watered by 1
    // MODIFIES: this
    public void incrementOneDayForPlant() {
        numDaysLastWatered++;
        updateNumDaysUntilWater();
    }

    // EFFECTS: updates the number of days until next watering for the plant
    // MODIFIES: this
    public void updateNumDaysUntilWater() {
        numDaysUntilWater = wateringFrequency - numDaysLastWatered;
    }
    //----------------------- GETTERS ------------------------------

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getNumDaysLastWatered() {
        return numDaysLastWatered;
    }

    public int getNumDaysUntilWater() {
        return numDaysUntilWater;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public int getNumTimeWatered() {
        return numTimeWatered;
    }

    //------------------------ SETTERS -----------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumDaysLastWatered(int numDaysLastWatered) {
        this.numDaysLastWatered = numDaysLastWatered;
        updateNumDaysUntilWater();
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
        updateNumDaysUntilWater();
    }

    public void setNumTimeWatered(int numTimeWatered) {
        this.numTimeWatered = numTimeWatered;
    }

    //-------------------------- JSON ---------------------------------

    // This class references code from CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("numDaysLastWatered", numDaysLastWatered);
        json.put("numDaysUntilWater", numDaysUntilWater);
        json.put("wateringFrequency", wateringFrequency);
        json.put("numTimeWatered", numTimeWatered);

        return json;
    }

}
