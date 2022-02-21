package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents an array list of Plant objects
public class Garden implements Writable {
    ArrayList<Plant> myGarden;
    int initialDay;

    // constructor
    public Garden(int initialDay) {
        myGarden = new ArrayList<>();
        this.initialDay = initialDay;
    }

    // EFFECTS: adds a plant to the garden
    // MODIFIES: this
    public void addPlant(Plant p) {
        EventLog.getInstance().logEvent(new Event("Added plant: " + p.getName() + " the " + p.getType()));
        myGarden.add(p);
    }

    // EFFECTS: removes the plant with given name from the garden
    // MODIFIES: this
    public void removePlantByName(String name) throws NullPointerException {
        EventLog.getInstance().logEvent(new Event("Removed plant: " + name));
        myGarden.remove(searchPlantByName(name));
    }

    // EFFECTS: edits the plant's name, type, numDaysLastWatered, and wateringFrequency field
    // MODIFIES: this
    public void editPlant(Plant p, String name, String type, int numDaysLastWatered, int wateringFrequency) {
        p.setType(type);
        p.setNumDaysLastWatered(numDaysLastWatered);
        p.setWateringFrequency(wateringFrequency);
        EventLog.getInstance().logEvent(new Event(p.getName()
                + "'s information has been changed!"));
        p.setName(name);

    }

    // EFFECTS: searches through garden and returns the plant object with the given name,
    //          return null if no plant object with given name is found
    public Plant searchPlantByName(String name) {
        for (Plant next : myGarden) {
            if (next.getName().equalsIgnoreCase(name)) {
                return next;
            }
        }
        return null;
    }


    // EFFECTS: increments the countdowns/ups for each plant
    //          (note: countdown/ups are fields that begin with numDays)
    // MODIFIES: Plant
    public void incrementOneDayForGarden() {
        EventLog.getInstance().logEvent(new Event("A day has passed..."));
        for (Plant p : myGarden) {
            p.incrementOneDayForPlant();
        }
    }

    // EFFECTS: returns the size of the garden
    public int size() {
        return myGarden.size();
    }

    // EFFECTS: produces true if garden contains given plant, false otherwise
    public boolean contains(Plant p) {
        return myGarden.contains(p);
    }

    public void incrementInitialDayByOne() {
        initialDay = (initialDay + 1) % 365;
//        if (initialDay == 365) {
//            initialDay = 1;
//        } else {
//            initialDay += 1;
//        }
    }

    // ---------------- GETTER ----------------
    public ArrayList<Plant> getGarden() {
        return myGarden;
    }

    public int getInitialDay() {
        return initialDay;
    }

    // ---------------- SETTER ----------------
    public void setInitialDay(Integer initialDay) {
        this.initialDay = initialDay;
    }

    // ---------------- JSON ----------------

    // This class references code from CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("initialDay", initialDay);
        json.put("myGarden", myGardenToJson());
        return json;
    }


    // EFFECTS: returns things in this garden as a JSON array
    private JSONArray myGardenToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Plant p : myGarden) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}

