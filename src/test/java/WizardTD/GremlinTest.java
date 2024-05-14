package WizardTD;

import processing.core.PImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GremlinTest {
    Gremlin gremlin;

    @BeforeEach
    public void setUp() {
        // Create the class to be tested
        gremlin = new Gremlin(10,20,null,100,2,(float)0.8,32,10);
    }

    @Test
    public void testGremlinInstantiation() {
        // Check class has been instantiated
        assertNotNull(gremlin, "Gremlin is null");
    }

    @Test
    public void testPositionGetters() {
        // Test position getter functions are correct
        assertEquals(10, gremlin.getX(), "X position getter is wrong");
        assertEquals(20, gremlin.getY(), "Y position getter is wrong");
    }

    @Test
    public void testSetPosition() {
        // Test setter works and getter also works
        gremlin.set(15, 25);
        assertEquals(15, gremlin.getX(), "X position setter is wrong");
        assertEquals(25, gremlin.getY(), "Y position setter is wrong");
    }

    @Test
    public void testSpeedMethods() {
        // Test if setter for speed works and getter functions work
        gremlin.setSpeed(2,3);
        assertEquals(2, gremlin.getSpeedX(), "X speed getter is wrong");
        assertEquals(3, gremlin.getSpeedY(), "Y speed getter is wrong");
    }

    @Test
    public void testAttributeGetters(){
        // Test that other getter functions work
        assertEquals(100, gremlin.getHealth(), "Health incorrect");
        assertEquals(32, gremlin.getMana(), "Mana incorrect");
        assertEquals(2, gremlin.getOverallSpeed(), "Overall speed is incorrect");
    }

    @Test
    public void testTickMethod() {
        // Test if tick() function moves entity
        gremlin.setSpeed(2,2);
        gremlin.tick();
        assertEquals(12, gremlin.getX(), "X position wrong after tick");
        assertEquals(22, gremlin.getY(), "Y position wrong after tick");
    }

    @Test
    public void testHealthMethods() {
        // Check helath changes accordingly to being hit
        assertEquals(100, gremlin.getCurrentHealth(), "Current health getter is wrong");
        assertFalse(gremlin.hit(50), "Hit method is wrong");
        assertEquals(100-(50*0.8), gremlin.getCurrentHealth(), "Current health after hit is wrong");
    }

    @Test
    public void testDyingMethods() {
        // Test if gremlin is dying when taking fatal damage
        assertFalse(gremlin.getDying(), "Gremlin should not be dying initially");
        gremlin.hit(200); // Exceeding the health to make it dying
        assertTrue(gremlin.getDying(), "Gremlin should be dying after hit");
    }

    @Test
    public void testPathMethods() {
        // Check if path has been set to the class
        ArrayList<Path.Node> path = new ArrayList<>();
        gremlin.setPath(path);
        assertEquals(path, gremlin.getPath(), "Path setter or getter is wrong");
    }

    @Test
    public void testSetSprites() {
        // Test the death animation sprites are set
        PImage[] testSprites = {new PImage(), new PImage(), new PImage(), new PImage(), new PImage()};
        gremlin.setSprites(testSprites);
        
        for (int i = 0; i < testSprites.length; i++) {
            assertSame(testSprites[i], gremlin.gremlin_death_sprites[i], "Sprite at index " + i + " does not match");
        }
    }

    @Test
    public void testSaveAndResetMethods() {
        // Check the class has been reset to its original state after being killed
        gremlin.savePosition(5, 5);
        gremlin.saveSpeed(1, 1);
        ArrayList<Path.Node> path = new ArrayList<>();
        gremlin.savePath(path);
        gremlin.reset();
        assertEquals(5, gremlin.getX(), "Reset position X is wrong");
        assertEquals(5, gremlin.getY(), "Reset position Y is wrong");
        assertEquals(1, gremlin.getSpeedX(), "Reset speed X is wrong");
        assertEquals(1, gremlin.getSpeedY(), "Reset speed Y is wrong");
        assertEquals(path, gremlin.getPath(), "Reset path is wrong");
    }

    @Test
    public void testDeathMethods() {
        // Test the death animation sequence logic
        assertFalse(gremlin.getDead(), "Gremlin should not be dead initially");
        gremlin.hit(1);
        assertFalse(gremlin.getDead(), "Gremlin took non leath damage and is dead");
        gremlin.hit(200); // Exceeding the health to make it dying
        // Gremlin is not dead as it needs to go through death animation
        assertFalse(gremlin.getDead());
        // Make it go through 20 frames of death animation
        // Should be true since started dying animation
        assertTrue(gremlin.isDying());
        // Increment 20 frames of isDying
        for (int i = 0; i < 18; i++) {
            if (i<4) {
                assertTrue(gremlin.isDying());
            } else if (i < 8) {
                assertTrue(gremlin.isDying());
            } else if (i < 16) {
                assertTrue(gremlin.isDying());
            } else if (i < 20) { 
                assertTrue(gremlin.isDying());
            }
        } 
        // 20 frames passed gremlin no longer in dying animation
        assertFalse(gremlin.isDying());
        // gremlin is now dead
        assertTrue(gremlin.getDead(), "Gremlin should be dead after isDying method");
    }
}
