package WizardTD;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PImage;
import processing.core.PApplet;

public class FireballTest {

    private Fireball<Moveable> fireball;
    private PImage mockImage;

    @BeforeEach
    public void setUp() {
        // Create all the variables and classes to be used
        mockImage = new PImage();
        MoveableMock mockEntity = new MoveableMock();
        fireball = new Fireball<>(mockImage, 5.0f, mockEntity, 10.0f);
    }

    @Test
    public void testTick() {
        // Test tick() function updates the fireballs position
        fireball.set(0, 0);
        fireball.tick();

        // Fireball moves towards the mock entity's position
        assertTrue(fireball.getX() > 0);
        assertTrue(fireball.getY() > 0);
    }

    @Test
    public void testGetMagnitude() {
        // Test magnitude function works correctly
        fireball.set(0, 0);
        float magnitude = fireball.getMagnitude();

        // The expected magnitude is the distance between (0,0) and (50,50)
        // Must add 8 since enemy location is the top left corner of its sprite, so must aim towards the center
        float expectedMagnitude = (float) Math.sqrt(58 * 58 + 58 * 58);
        assertEquals(expectedMagnitude, magnitude);
    }

    @Test
    public void testGetDamage() {
        // Test damage getter works
        float damage = fireball.getDamage();
        assertEquals(10, damage, "Expected damage to be the same as initial damage set.");
    }
    // Create the class to test
    private static class MoveableMock implements Moveable {
        private float x = 50, y = 50;
        private float speedX = 5, speedY = 5;

        @Override
        public List<Path.Node> getPath() {
            return null;
        }

        @Override
        public void setPath(List<Path.Node> path) {}

        @Override
        public void set(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        @Override
        public float getSpeedX() {
            return speedX;
        }

        @Override
        public float getSpeedY() {
            return speedY;
        }

        @Override
        public void setSpeed(float xSpeed, float ySpeed) {
            this.speedX = xSpeed;
            this.speedY = ySpeed;
        }

        @Override
        public float getOverallSpeed() {
            return (float) Math.sqrt(speedX * speedX + speedY * speedY);
        }

        @Override
        public void tick() {}

        @Override
        public void draw(PApplet p) {}

        @Override
        public float getCurrentHealth() {
            return 100;
        }

        @Override
        public float getHealth() {
            return 100;
        }

        @Override
        public boolean hit(float damage) {
            return true;
        }

        @Override
        public int getMana() {
            return 50;
        }

        @Override
        public void reset() {}
    }
}
