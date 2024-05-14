package WizardTD;
import processing.core.PImage;
import processing.core.PApplet;


/**
 * The Fireball class represents a fireball entity in the WizardTD game.
 * The fireball is targeted towards a moveable entity and moves in the direction
 * of the entity until it reaches its target. The class provides methods to
 * get and set the fireball's attributes, draw the fireball, and update its position.
 * 
 * @param <T> A type that extends the Moveable interface. Represents the target entity.
 */
public class Fireball<T extends Moveable> {
    private T entity;
    private PImage sprite; 
    private float overallSpeed, x, y, initial_tower_damage;

    /**
     * Constructor to initialize a Fireball object with the given attributes.
     * 
     * @param sprite The image representing the fireball.
     * @param overallSpeed The speed of the fireball.
     * @param entity The target entity towards which the fireball is directed.
     * @param initial_tower_damage The initial damage value of the fireball.
     */
    public Fireball(PImage sprite, float overallSpeed, T entity, float initial_tower_damage){
        this.sprite = sprite;
        this.overallSpeed = overallSpeed;
        this.entity = entity;
        this.initial_tower_damage = initial_tower_damage;
    }

    /**
     * Draws the fireball on the given PApplet canvas.
     * 
     * @param app The PApplet canvas on which the fireball is drawn.
     */
    public void draw(PApplet app){
        app.image(this.sprite, x,y);
    }

    /**
     * Sets the position of the fireball.
     * 
     * @param x The x-coordinate of the fireball.
     * @param y The y-coordinate of the fireball.
     */
    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setSpeed(float speed) {
        this.overallSpeed = speed;
    }

    /**
     * Updates the position of the fireball based on its speed and direction towards the target entity.
     */
    public void tick() {
        float dx = entity.getX()+8 - this.x;  // Difference in x
        float dy = entity.getY()+8 - this.y;  // Difference in y
    
        // Calculate the magnitude of the direction vector
        float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
    
        // Normalize the direction vector
        float dirX = dx / magnitude;
        float dirY = dy / magnitude;
    
        // Update the position using the normalized direction and speed
        this.x += overallSpeed * dirX;
        this.y += overallSpeed * dirY;
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public float getDamage(){
        return this.initial_tower_damage;
    }

    /**
     * Calculates and returns the magnitude of the direction vector towards the target entity.
     * 
     * @return The magnitude of the direction vector.
     */
    public float getMagnitude(){
        float dx = entity.getX()+8 - this.x;  // Difference in x
        float dy = entity.getY()+8 - this.y;  // Difference in y
    
        // Calculate the magnitude of the direction vector
        float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
        return magnitude;
    }
}
