package persistence;

import model.Plant;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {

    protected void checkPlant(String name, String type,
                              int numDaysLastWatered, int wateringFrequency,
                              int numTimeWatered, Plant plant) {
        assertEquals(name, plant.getName());
        assertEquals(type, plant.getType());
        assertEquals(numDaysLastWatered, plant.getNumDaysLastWatered());
        assertEquals(wateringFrequency, plant.getWateringFrequency());
        assertEquals(numTimeWatered, plant.getNumTimeWatered());
    }


}
