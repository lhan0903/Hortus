package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GardenTest {
    Garden garden1;
    Garden garden2;
    Plant plant1;
    Plant plant2;
    Plant plant3;

    @BeforeEach
    public void setup() {
        plant1 = new Plant("Alice", "Monstera", 2, 5);
        plant2 = new Plant("Bill", "English Ivy", 1, 1);
        plant3 = new Plant("Cole", "Aloe", 5, 8);
        garden1 = new Garden(1);
        garden2 = new Garden(365);
        garden2.addPlant(plant1);
        garden2.addPlant(plant2);
    }

    @Test
    public void testAddPlant() {
        assertEquals(0, garden1.size());
        garden1.addPlant(plant1);
        assertEquals(1, garden1.size());
        assertTrue(garden1.contains(plant1));
    }

    @Test
    public void testRemovePlantByName() throws NullPointerException {
        assertEquals(2, garden2.size());
        assertTrue(garden2.contains(plant1));

        garden2.removePlantByName(plant1.getName());

        assertFalse(garden2.contains(plant1));
        assertEquals(1, garden2.size());
    }

    @Test
    public void testEditPlant(){
        assertEquals(2, garden2.size());
        garden2.editPlant(plant1,"Alicia", "Monstera", 2,5);
        assertEquals(garden2.searchPlantByName("Alicia"), plant1);
    }


    @Test
    public void testSearchPlantByNamePlantFound() {
        assertTrue(garden2.contains(plant1));
        assertEquals(garden2.searchPlantByName("Alice"), plant1);
    }

    @Test
    public void testSearchPlantByNamePlantNotFound() {
        assertFalse(garden1.contains(plant3));
        assertNull(garden2.searchPlantByName("Cole"));
    }

    // --------------- GETTERS TESTS------------------
    @Test
    public void testGetGarden() {
        ArrayList<Plant> myList = new ArrayList<>();
        myList.add(plant1);
        myList.add(plant2);

        assertEquals(myList, garden2.getGarden());
    }

    @Test
    public void testGetInitialDay(){
        assertEquals(garden1.initialDay, garden1.getInitialDay());
    }

    @Test
    public void testIncrementOneDayForGarden() {
        assertEquals(plant1.getNumDaysUntilWater(), 3);
        assertEquals(plant1.getNumDaysLastWatered(), 2);
        assertEquals(plant2.getNumDaysUntilWater(), 0);
        assertEquals(plant2.getNumDaysLastWatered(), 1);

        garden2.incrementOneDayForGarden();

        assertEquals(plant1.getNumDaysUntilWater(), 2);
        assertEquals(plant1.getNumDaysLastWatered(), 3);
        assertEquals(plant2.getNumDaysUntilWater(), -1);
        assertEquals(plant2.getNumDaysLastWatered(), 2);
    }

    @Test
    public void testIncrementInitialDayByOneDay1(){
        assertEquals(garden1.initialDay,1);
        garden1.incrementInitialDayByOne();
        assertEquals(garden1.initialDay,2);
    }

    @Test
    public void testIncrementInitialDayByOneDay365(){
        assertEquals(garden2.initialDay,365);
        garden2.incrementInitialDayByOne();
        assertEquals(garden2.initialDay,1);
    }

    @Test
    public void testSetInitialDay(){
        assertEquals(garden1.initialDay, 1);
        garden1.setInitialDay(5);
        assertEquals(garden1.initialDay,5);
    }

    @Test
    public void testSize() {
        assertEquals(2, garden2.size());
        assertEquals(0, garden1.size());
    }

    @Test
    public void testContains() {
        assertTrue(garden2.contains(plant1));
        assertFalse(garden2.contains(plant3));
        assertFalse(garden1.contains(plant1));
    }
}
