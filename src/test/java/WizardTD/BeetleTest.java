package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BeetleTest {
    Beetle beetle;

    @BeforeEach
    public void setUp() {
        // Create the class to be tested
        beetle = new Beetle(10,20,null,100,2,(float)0.8,32,10);
    }

    @Test
    public void testBeetleInstantiation() {
        // Check class has been instantiated
        assertNotNull(beetle, "Beetle is null");
    }

    @Test
    public void testPositionGetters() {
        // Test position getter functions are correct
        assertEquals(10, beetle.getX(), "X position getter is wrong");
        assertEquals(20, beetle.getY(), "Y position getter is wrong");
    }

    @Test
    public void testSetPosition() {
        // Test setter works and getter also works
        beetle.set(15, 25);
        assertEquals(15, beetle.getX(), "X position setter is wrong");
        assertEquals(25, beetle.getY(), "Y position setter is wrong");
    }

    @Test
    public void testSpeedMethods() {
        // Test if setter for speed works and getter functions work
        beetle.setSpeed(2,3);
        assertEquals(2, beetle.getSpeedX(), "X speed getter is wrong");
        assertEquals(3, beetle.getSpeedY(), "Y speed getter is wrong");
    }

    @Test
    public void testAttributeGetters(){
        // Test that other getter functions work
        assertEquals(100, beetle.getHealth(), "Health incorrect");
        assertEquals(32, beetle.getMana(), "Mana incorrect");
        assertEquals(2, beetle.getOverallSpeed(), "Overall speed is incorrect");
    }

    @Test
    public void testTickMethod() {
        // Test if tick() function moves entity
        beetle.setSpeed(2,2);
        beetle.tick();
        assertEquals(12, beetle.getX(), "X position wrong after tick");
        assertEquals(22, beetle.getY(), "Y position wrong after tick");
    }

    @Test
    public void testHealthMethods() {
        // Check health changes accordingly to being hit
        assertEquals(100, beetle.getCurrentHealth(), "Current health getter is wrong");
        assertFalse(beetle.hit(50), "Hit method is wrong");
        assertEquals(100-(50*0.8), beetle.getCurrentHealth(), "Current health after hit is wrong");
        assertTrue(beetle.hit(200), "Beetle should be dead");
    }

    @Test
    public void testPathMethods() {
        // Check if path has been set to the class
        ArrayList<Path.Node> path = new ArrayList<>();
        beetle.setPath(path);
        assertEquals(path, beetle.getPath(), "Path setter or getter is wrong");
    }

    @Test
    public void testSaveAndResetMethods() {
        // Check the class has been reset to its original state after being killed
        beetle.savePosition(5, 5);
        beetle.saveSpeed(1, 1);
        ArrayList<Path.Node> path = new ArrayList<>();
        beetle.savePath(path);
        beetle.reset();
        assertEquals(5, beetle.getX(), "Reset position X is wrong");
        assertEquals(5, beetle.getY(), "Reset position Y is wrong");
        assertEquals(1, beetle.getSpeedX(), "Reset speed X is wrong");
        assertEquals(1, beetle.getSpeedY(), "Reset speed Y is wrong");
        assertEquals(path, beetle.getPath(), "Reset path is wrong");
    }
}
