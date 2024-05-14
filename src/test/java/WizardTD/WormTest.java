package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class WormTest {
    Worm worm;

    @BeforeEach
    public void setUp() {
        // Create the class to be tested
        worm = new Worm(10,20,null,100,2,(float)0.8,32,10);
    }

    @Test
    public void testBeetleInstantiation() {
        // Check class has been instantiated
        assertNotNull(worm, "worm is null");
    }

    @Test
    public void testPositionGetters() {
        // Test position getter functions are correct
        assertEquals(10, worm.getX(), "X position getter is wrong");
        assertEquals(20, worm.getY(), "Y position getter is wrong");
    }

    @Test
    public void testSetPosition() {
        // Test setter works and getter also works
        worm.set(15, 25);
        assertEquals(15, worm.getX(), "X position setter is wrong");
        assertEquals(25, worm.getY(), "Y position setter is wrong");
    }

    @Test
    public void testSpeedMethods() {
        // Test if setter for speed works and getter functions work
        worm.setSpeed(2,3);
        assertEquals(2, worm.getSpeedX(), "X speed getter is wrong");
        assertEquals(3, worm.getSpeedY(), "Y speed getter is wrong");
    }

    @Test
    public void testAttributeGetters(){
        // Test that other getter functions work
        assertEquals(100, worm.getHealth(), "Health incorrect");
        assertEquals(32, worm.getMana(), "Mana incorrect");
        assertEquals(2, worm.getOverallSpeed(), "Overall speed is incorrect");
    }

    @Test
    public void testTickMethod() {
        // Test if tick() function moves entity
        worm.setSpeed(2,2);
        worm.tick();
        assertEquals(12, worm.getX(), "X position wrong after tick");
        assertEquals(22, worm.getY(), "Y position wrong after tick");
    }

    @Test
    public void testHealthMethods() {
        // Check helath changes accordingly to being hit
        assertEquals(100, worm.getCurrentHealth(), "Current health getter is wrong");
        assertFalse(worm.hit(50), "Hit method is wrong");
        assertEquals(100-(50*0.8), worm.getCurrentHealth(), "Current health after hit is wrong");
        assertTrue(worm.hit(200), "worm should be dead");
    }

    @Test
    public void testPathMethods() {
        // Check if path has been set to the class
        ArrayList<Path.Node> path = new ArrayList<>();
        worm.setPath(path);
        assertEquals(path, worm.getPath(), "Path setter or getter is wrong");
    }

    @Test
    public void testSaveAndResetMethods() {
        // Check the class has been reset to its original state after being killed
        worm.savePosition(5, 5);
        worm.saveSpeed(1, 1);
        ArrayList<Path.Node> path = new ArrayList<>();
        worm.savePath(path);
        worm.reset();
        assertEquals(5, worm.getX(), "Reset position X is wrong");
        assertEquals(5, worm.getY(), "Reset position Y is wrong");
        assertEquals(1, worm.getSpeedX(), "Reset speed X is wrong");
        assertEquals(1, worm.getSpeedY(), "Reset speed Y is wrong");
        assertEquals(path, worm.getPath(), "Reset path is wrong");
    }
}
