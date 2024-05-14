package WizardTD;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.*;


/**
 * The Gremlin class represents a gremlin monster in the WizardTD game.
 * The gremlin is a type of monster that can move along a path and has its own
 * unique attributes and behaviors. The class provides methods to get and set
 * the gremlin's attributes, draw the gremlin, update its position, and handle
 * its interactions with other game entities.
 */
public class Gremlin extends Monster implements Moveable {
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
    private int counter = 0;

    private boolean isDying, dead = false;
    protected PImage[] gremlin_death_sprites = new PImage[5];


    /**
     * Constructor to initialize a Gremlin object with the given attributes.
     * 
     * @param x The x-coordinate of the gremlin's initial position.
     * @param y The y-coordinate of the gremlin's initial position.
     * @param sprite The image representing the gremlin.
     * @param hp The gremlin's health points.
     * @param speed The gremlin's movement speed.
     * @param armour The gremlin's armor value.
     * @param manaGainedOnKill The amount of mana gained when the gremlin is killed.
     * @param quantity The number of gremlins.
     */
    public Gremlin(int x, int y, PImage sprite, float hp, float speed, float armour, int manaGainedOnKill, int quantity) {
        // Call the super constructor of the Monster class with the correct arguments
        super("gremlin", hp, speed, armour, manaGainedOnKill, quantity);
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.xvel = 0;
        this.yvel = 0;
        this.currentHealth = hp;
    }

    /**
     * Updates the position of the gremlin based on its speed.
     */
    public void tick() {
        this.x += this.xvel;
        this.y += this.yvel;
    }

    /**
     * Draws the gremlin on the given PApplet canvas.
     * 
     * @param app The PApplet canvas on which the gremlin is drawn.
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
            isDying = true;
            return true;
        }
        return false;
    }

    public int getMana() {
        return this.manaGainedOnKill;
    }

    public void reset() {
        this.isDying = false;
        this.dead = false;
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
    /**
     * Retrieves the dying status of the entity.
     *
     * @return true if the entity is in the process of dying; false otherwise.
     */
    public boolean getDying(){
        return this.isDying;
    }

    /**
     * Updates and checks the dying animation frames for the entity.
     * The method increments the counter and updates the sprite based on the counter's value.
     * Once the dying animation is complete, the entity is marked as dead.
     *
     * @return true if the entity is still in the dying animation; false if the entity has completed the dying animation and is dead.
     */
    public boolean isDying(){
        counter++;
        if (counter < 4){
            this.sprite = gremlin_death_sprites[0];
            return true;
        } else if (counter < 8){
            this.sprite = gremlin_death_sprites[1];
            return true;
        } else if (counter < 12){
            this.sprite = gremlin_death_sprites[2];
            return true;
        } else if (counter < 16){
            this.sprite =  gremlin_death_sprites[3];
            return true;
        } else if (counter < 20){
            this.sprite = gremlin_death_sprites[4];
            return true;
        } else{
            dead = true;
            return false;
        }
    }

    /**
     * Retrieves the dead status of the entity.
     *
     * @return true if the entity is dead; false otherwise.
     */
    public boolean getDead(){
        return this.dead;
    }
    
    public void setSprites(PImage[] sprites){
        for (int i = 0; i < sprites.length; i++){
            gremlin_death_sprites[i] = sprites[i];
        }
        return;
    }
}    
