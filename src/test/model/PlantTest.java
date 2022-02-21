package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {
    Plant plant1;
    Plant plant2;
    Plant plant3;

    @BeforeEach
    public void setup() {
        plant1 = new Plant("Albert", "Snake Plant", 1, 7);
        plant2 = new Plant("Ben", "Monstera", 2, 2);
        plant3 = new Plant("Cole", "Chinese Evergreen", 8, 6);
    }

    @Test
    public void testWater() {
        assertTrue(plant2.getNumDaysUntilWater() <= 0);
        assertEquals(0, plant2.getNumTimeWatered());
        plant2.water();

        assertEquals(0, plant2.getNumDaysLastWatered());
        assertEquals(plant2.getNumDaysUntilWater(), plant2.getWateringFrequency());
        assertEquals(1, plant2.getNumTimeWatered());
    }

    @Test
    public void testIncrementOneDayForPlant() {
        assertEquals(plant1.getNumDaysUntilWater(), 6);
        assertEquals(plant1.getNumDaysLastWatered(), 1);

        plant1.incrementOneDayForPlant();

        assertEquals(plant1.getNumDaysUntilWater(), 5);
        assertEquals(plant1.getNumDaysLastWatered(), 2);
    }


    //------------------------------------------------------------
    @Test
    public void testSetName() {
        assertEquals(plant1.getName(), "Albert");
        plant1.setName("Alina");
        assertEquals(plant1.getName(), "Alina");
    }

    @Test
    public void testGetType() {
        assertEquals(plant1.getType(), "Snake Plant");
    }

    @Test
    public void testSetType() {
        assertEquals(plant1.getType(), "Snake Plant");
        plant1.setType("Lavender");
        assertEquals(plant1.getType(), "Lavender");
    }

    @Test
    public void testSetWateringFrequency() {
        assertEquals(plant1.getWateringFrequency(), 7);
        plant1.setWateringFrequency(2);
        assertEquals(plant1.getWateringFrequency(), 2);
    }

    @Test
    public void testSetNumDaysLastWatered() {
        assertEquals(plant1.getNumDaysLastWatered(), 1);
        plant1.setNumDaysLastWatered(2);
        assertEquals(plant1.getNumDaysLastWatered(), 2);

    }
}