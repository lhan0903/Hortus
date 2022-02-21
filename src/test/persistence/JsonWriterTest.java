package persistence;

import model.Garden;
import model.Plant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {
    // Strategy in designing tests for JsonWriter:
    // 1) write data to a file
    // 2) use the reader to read it back in and check
    //    that we read in a copy of what was written out
    Plant p1;
    Plant p2;

    @BeforeEach
    public void setup() {
        p1 = new Plant("Albert", "Snake Plant", 2,5);
        p1.setNumTimeWatered(2);

        p2 = new Plant("Dorothy", "Fiddle Leaf Fig", 3,3);
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            Garden g = new Garden(1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyGarden() {
        try {
            Garden g = new Garden(1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGarden.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGarden.json");
            g = reader.read();
            assertEquals(1, g.getInitialDay());
            assertEquals(0,g.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    public void testWriterGeneralGarden() {
        try {
            Garden g = new Garden(1);
            g.addPlant(p1);
            g.addPlant(p2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGarden.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGarden.json");
            g = reader.read();
            assertEquals(1,g.getInitialDay());
            assertEquals(2, g.size());
            checkPlant("Albert","Snake Plant", 2,5,
                    2, p1);
            checkPlant("Dorothy", "Fiddle Leaf Fig", 3,3,
                    0, p2);
        } catch (IOException e) {
            fail("Exception should not have been thrown!");
        }
    }
}
