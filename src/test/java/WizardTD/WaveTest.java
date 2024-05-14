package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class WaveTest {
    Wave wave;
    Monster gremlin;
    Monster worm;
    Monster beetle;

    @BeforeEach
    public void setUp() {
        // Create all the classes
        gremlin = new Monster("gremlin", 60, 2, 1, 10, 3);
        worm = new Monster("worm", 100, 1, 0.5f, 40, 4);
        beetle = new Monster("beetle", 60, 1.5f, 0.5f, 50, 3);
        
        List<Monster> monsters = Arrays.asList(gremlin, worm, beetle);
        wave = new Wave(8, 3, monsters, 10);
    }

    @Test
    public void testGetDuration() {
        // Test getter function
        assertEquals(8, wave.getDuration());
    }

    @Test
    public void testGetPreWavePause() {
        // Test getter function
        assertEquals(3, wave.getPreWavePause());
    }

    @Test
    public void testGetMonsters() {
        // Test getter function
        List<Monster> expectedMonsters = Arrays.asList(gremlin, worm, beetle);
        assertEquals(expectedMonsters, wave.getMonsters());
    }

    @Test
    public void testGetTotalQuantity() {
        // Test getter function
        assertEquals(10, wave.getTotalQuantity());
    }

    @Test
    public void testSetQuantity() {
        // Test setter function
        wave.setQuantity(15);
        assertEquals(15, wave.getTotalQuantity());
    }
}
