package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import processing.core.PImage;

public class TowerTest {

    private Tower tower;
    private static final float X = 10.0f;
    private static final float Y = 20.0f;
    private static final float INITIAL_TOWER_RANGE = 50.0f;
    private static final float INITIAL_TOWER_FIRING_SPEED = 5.0f;
    private static final float INITIAL_TOWER_DAMAGE = 10.0f;
    private static final float TOWER_COST = 100.0f;
    private PImage sprite;

    @BeforeEach
    public void setUp() {
        // Create the class
        sprite = new PImage();
        tower = new Tower(X, Y, sprite, INITIAL_TOWER_RANGE, INITIAL_TOWER_FIRING_SPEED, INITIAL_TOWER_DAMAGE, TOWER_COST);
    }

    @Test
    public void testGetX() {
        // Test getter function
        assertEquals(X, tower.getX(), "Expected X coordinate to match the initialized value.");
    }

    @Test
    public void testGetY() {
        // Test getter function
        assertEquals(Y, tower.getY(), "Expected Y coordinate to match the initialized value.");
    }

    @Test
    public void testGetSprite() {
        // Test getter function
        assertEquals(sprite, tower.getSprite(), "Expected sprite to match the initialized sprite.");
    }

    @Test
    public void testGetRange() {
        // Test getter function
        assertEquals(INITIAL_TOWER_RANGE, tower.getRange(), "Expected tower range to match the initialized value.");
    }

    @Test
    public void testGetSpeed() {
        // Test getter function
        assertEquals(INITIAL_TOWER_FIRING_SPEED, tower.getSpeed(), "Expected tower firing speed to match the initialized value.");
    }

    @Test
    public void testGetDamage() {
        // Test getter function
        assertEquals(INITIAL_TOWER_DAMAGE, tower.getDamage(), "Expected tower damage to match the initialized value.");
    }

    @Test
    public void testGetCost() {
        // Test getter function
        assertEquals(TOWER_COST, tower.getCost(), "Expected tower cost to match the initialized value.");
    }

    @Test
    public void testShoot() {
        // Test logic when tower shoots
        tower.shoot();
        assertEquals(0, tower.getTimeSince(), "Expected time since last shot to be reset to 0 after shooting.");

        for (int i = 0; i < 60; i++) {
            tower.tick();
        }
        assertEquals(1, tower.getTimeSince(), "Expected 1 second to have passed");
    }

    @Test
    public void testUpgradeRange() {
        // Test when tower is upgraded
        float initialRange = tower.getRange();
        tower.upgradeRange();
        assertEquals(initialRange + 32, tower.getRange(), "Expected tower range to increase by 32 after upgrade.");
    }

    @Test
    public void testUpgradeSpeed() {
        // Test when tower is upgraded
        float initialSpeed = tower.getSpeed();
        tower.upgradeSpeed();
        assertEquals(initialSpeed + 0.5, tower.getSpeed(), "Expected tower firing speed to increase by 0.5 after upgrade.");
    }

    @Test
    public void testUpgradeDamage() {
        // Test when tower is upgraded
        float initialDamage = tower.getDamage();
        tower.upgradeDamage();
        assertEquals(initialDamage * 1.5, tower.getDamage(), "Expected tower damage to multiply by 1.5 after upgrade.");
    }

    @Test
    public void testGetRangeNumber() {
        // Test getter function
        assertEquals(1, tower.getRangeNumber(), "Expected initial range number to be 1.");
    }

    @Test
    public void testGetSpeedNumber() {
        // Test getter function
        assertEquals(1, tower.getSpeedNumber(), "Expected initial speed number to be 1.");
    }

    @Test
    public void testGetDamageNumber() {
        // Test getter function
        assertEquals(1, tower.getDamageNumber(), "Expected initial damage number to be 1.");
    }

    @Test
    public void testUpdateSprite() {
        // Test sprite is updated
        PImage newSprite = new PImage();
        tower.updateSprite(newSprite);
        assertEquals(newSprite, tower.getSprite(), "Expected sprite to be updated to the new sprite.");
    }

    @Test
    public void testGetRangeUpgradeCost() {
        // Test getter function
        assertEquals(20, tower.getRangeUpgradeCost(), "Expected initial range upgrade cost to be 20.");
    }

    @Test
    public void testGetSpeedUpgradeCost() {
        // Test getter function
        assertEquals(20, tower.getSpeedUpgradeCost(), "Expected initial speed upgrade cost to be 20.");
    }

    @Test
    public void testGetDamageUpgradeCost() {
        // Test getter function
        assertEquals(20, tower.getDamageUpgradeCost(), "Expected initial damage upgrade cost to be 20.");
    }

    @Test
    public void testGetFirst() {
        // Test getter function
        assertTrue(tower.getFirst(), "Expected initial value of first to be true.");
    }

    @Test
    public void testSetFirst() {
        // Test setter function
        tower.setFirst();
        assertFalse(tower.getFirst(), "Expected value of first to be set to false.");
    }

    @Test
    public void testSetFirstTrue() {
        // Test setter function
        tower.setFirstTrue();
        assertTrue(tower.getFirst(), "Expected value of first to be set to true.");
    }

    @Test
    public void testConstructor() {
        // Test constructor
        PImage testSprite = new PImage();
        Tower testTower = new Tower(testSprite, 50.0f, 5.0f, 10.0f, 100.0f);
        assertNotNull(testTower, "Expected tower object to be initialized.");
    }

    @Test
    public void testSet() {
        // Test setter function
        tower.set(5, 10);
        assertEquals(5, tower.getX(), "Expected x coordinate to be set to 5.");
        assertEquals(10, tower.getY(), "Expected y coordinate to be set to 10.");
    }

    @Test
    public void testFollow() {
        // Test function follows mouse
        tower.follow(5, 10);
        assertEquals(5, tower.getX(), "Expected x coordinate to be updated to 5.");
        assertEquals(10, tower.getY(), "Expected y coordinate to be updated to 10.");
    }
}
