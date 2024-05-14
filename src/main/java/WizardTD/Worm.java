package WizardTD;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.*;


/**
 * The Worm class represents a worm monster in the WizardTD game.
 * The worm is a type of monster that can move along a path and has its own
 * unique attributes and behaviors. The class provides methods to get and set
 * the worm's attributes, draw the worm, update its position, and handle
 * its interactions with other game entities.
 */
public class Worm extends Monster implements Moveable {
    private float x;
    private float y;
    private PImage sprite;
    private float xvel;
    private float yvel;
    private List<Path.Node> path;
    private float currentHealth;

    // Instance variables to store the initial values
    private float initialX;
    private float initialY;
    private List<Path.Node> savedPath;
    private float saveX, saveY;


    /**
     * Constructor to initialize a worm object with the given attributes.
     * 
     * @param x The x-coordinate of the worm's initial position.
     * @param y The y-coordinate of the worm's initial position.
     * @param sprite The image representing the worm.
     * @param hp The worm's health points.
     * @param speed The worm's movement speed.
     * @param armour The worm's armor value.
     * @param manaGainedOnKill The amount of mana gained when the worm is killed.
     * @param quantity The number of worms.
     */
    public Worm(int x, int y, PImage sprite, float hp, float speed, float armour, int manaGainedOnKill, int quantity) {
        // Call the super constructor of the Monster class with the correct arguments
        super("worm", hp, speed, armour, manaGainedOnKill, quantity);
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.xvel = 0;
        this.yvel = 0;
        this.currentHealth = hp;
    }

    /**
     * Updates the position of the beetle based on its speed.
     */
    public void tick() {
        this.x += this.xvel;
        this.y += this.yvel;
    }

    /**
     * Draws the beetle on the given PApplet canvas.
     * 
     * @param app The PApplet canvas on which the beetle is drawn.
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(float x, float y) {
        this.xvel = x;
        this.yvel = y;
    }

    public float getSpeedX() {
        return this.xvel;
    }

    public float getSpeedY() {
        return this.yvel;
    }

    public void setPath(List<Path.Node> path) {
        this.path = path;
    }

    public List<Path.Node> getPath() {
        return this.path;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getOverallSpeed() {
        return this.speed;
    }

    public void setOverallSpeed(float newSpeed) {
        this.speed = newSpeed;
    }

    public float getCurrentHealth() {
        return this.currentHealth;
    }

    public float getHealth() {
        return this.hp;
    }
    /**
     * Applies damage to the entity and checks if its health drops to zero or below.
     *
     * @param damage The amount of damage to be inflicted on the entity.
     * @return true if the entity's health drops to zero or below after taking the damage; false otherwise.
     */
    public boolean hit(float damage) {
        this.currentHealth -= damage*this.armour;
        if (this.currentHealth <= 0) {
            return true;
        }
        return false;
    }

    public int getMana() {
        return this.manaGainedOnKill;
    }

    public void reset() {
        this.x = initialX;
        this.y = initialY;
        this.path = new ArrayList<>(savedPath); // Reset the path to the saved path
        this.xvel = saveX;
        this.yvel = saveY;
    }

    public void savePath(List<Path.Node> path) {
        this.savedPath = new ArrayList<>(path); // Save a copy of the path
    }
    public void savePosition(float x, float y){
        this.initialX = x;
        this.initialY = y;
    }
    public void saveSpeed(float x, float y){
        this.saveX = x;
        this.saveY = y;
    }
}    
