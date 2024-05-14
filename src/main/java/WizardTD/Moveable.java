package WizardTD;
import java.util.*;
import processing.core.PApplet;

/**
 * The Moveable interface defines the contract for all entities in the WizardTD game
 * that have the capability to move on the game board. This includes methods for getting
 * and setting the entity's path, position, speed, health, and other related attributes.
 * Implementing this interface ensures that the entity adheres to a consistent set of 
 * behaviors associated with movement.
 */
public interface Moveable {
    public List<Path.Node> getPath();
    public void setPath(List<Path.Node> path);
    public void set(float x, float y);
    public float getX();
    public float getY();
    public float getSpeedX();
    public float getSpeedY();
    public void setSpeed(float xSpeed, float ySpeed);
    public float getOverallSpeed();
    public void tick();
    public void draw(PApplet p);
    public float getCurrentHealth();
    public float getHealth();
    public boolean hit(float damage);
    public int getMana();
    public void reset();
}
