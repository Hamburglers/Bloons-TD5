package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MonsterTest {
    Monster monster;

    @BeforeEach
    public void setUp() {
        // Create the class
        monster = new Monster("gremlin", 60, 2, 1, 10, 3);
    }

    @Test
    public void testGetType() {
        // Test getter function
        assertEquals("gremlin", monster.getType());
    }

    @Test
    public void testGetHP() {
        // Test getter function
        assertEquals(60, monster.getHP());
    }

    @Test
    public void testSetHP() {
        // Test setter function
        monster.setHP(70);
        assertEquals(70, monster.getHP());
    }

    @Test
    public void testGetSpeed() {
        // Test getter function
        assertEquals(2, monster.getSpeed());
    }

    @Test
    public void testSetSpeed() {
        // Test setter function
        monster.setSpeed(2.5f);
        assertEquals(2.5f, monster.getSpeed());
    }

    @Test
    public void testGetArmour() {
        // Test getter function
        assertEquals(1, monster.getArmour());
    }

    @Test
    public void testSetArmour() {
        // Test setter function
        monster.setArmour(1.5f);
        assertEquals(1.5f, monster.getArmour());
    }

    @Test
    public void testGetManaGainedOnKill() {
        // Test getter function
        assertEquals(10, monster.getManaGainedOnKill());
    }

    @Test
    public void testGetQuantity() {
        // Test getter function
        assertEquals(3, monster.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        // Test setter function{
        monster.setQuantity(4);
        assertEquals(4, monster.getQuantity());
    }

    // Since the following methods are skeleton methods with no functionality, 
    // we can just call them to ensure no exceptions are thrown.
    @Test
    public void testSkeletonMethods() {
        // Test the empty methods
        monster.savePath(new ArrayList<>());
        monster.savePosition(1.0f, 2.0f);
        monster.saveSpeed(1.0f, 2.0f);
        monster.setPath(new ArrayList<>());
        monster.set(1.0f, 2.0f);
        monster.setSpeed(1.0f, 2.0f);
    }
}
