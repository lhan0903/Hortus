package persistence;

import model.Garden;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Garden g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderEmptyGarden() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGarden.json");
        try {
            Garden g = reader.read();
            assertEquals(1,g.getInitialDay());
            assertEquals(0,g.size());
        } catch (IOException e) {
            fail("Exception shouldn't be thrown");
        }
    }

    @Test
    public void testReaderGeneralGarden(){
        JsonReader reader = new JsonReader("./data/testReaderGeneralGarden.json");
        try {
            Garden g = reader.read();
            ArrayList<Plant> plants = g.getGarden();

            assertEquals(1, g.getInitialDay());
            assertEquals(2,plants.size());

           checkPlant("Albert", "Snake Plant", 2, 5,2,
                   plants.get(0));
           checkPlant("Dorothy","Fiddle Leaf Fig", 3,3,0,
                   plants.get(1));
        } catch(IOException e) {
            fail("Exception should not be thrown!");
        }
    }
}
