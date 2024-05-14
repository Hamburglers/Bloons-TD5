package WizardTD;
import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents a tower in the WizardTD game.
 */
public class Tower {
    private float x,y,initial_tower_range, initial_tower_firing_speed, initial_tower_damage, tower_cost;
    private PImage sprite;
    private float time_since_last_shot = 0;
    private int numberRange = 1, numberSpeed = 1, numberDamage = 1;
    private boolean first = true;

    /**
     * Constructor to initialize a tower with its position, sprite, and attributes.
     *
     * @param x X-coordinate of the tower.
     * @param y Y-coordinate of the tower.
     * @param sprite Image representing the tower.
     * @param initial_tower_range Initial range of the tower.
     * @param initial_tower_firing_speed Initial firing speed of the tower.
     * @param initial_tower_damage Initial damage dealt by the tower.
     * @param tower_cost Cost of the tower.
     */
    public Tower(float x, float y, PImage sprite, float initial_tower_range, float initial_tower_firing_speed, float initial_tower_damage, float tower_cost){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.initial_tower_range = initial_tower_range;
        this.initial_tower_firing_speed = initial_tower_firing_speed;
        this.initial_tower_damage = initial_tower_damage;
        this.tower_cost = tower_cost;
    }

    /**
     * Constructor to initialize a tower without a specified position.
     * The default position is set to (-100, -100).
     *
     * @param sprite Image representing the tower.
     * @param initial_tower_range Initial range of the tower.
     * @param initial_tower_firing_speed Initial firing speed of the tower.
     * @param initial_tower_damage Initial damage dealt by the tower.
     * @param tower_cost Cost of the tower.
     */
    public Tower(PImage sprite, float initial_tower_range, float initial_tower_firing_speed, float initial_tower_damage, float tower_cost){
        this(-100,-100, sprite, initial_tower_range, initial_tower_firing_speed, initial_tower_damage, tower_cost);
    }

    /**
     * Draws the tower on the mouse
     *
     * @param x float x position of mouse
     * @param y float y position of mouse
     */
    public void follow(float x, float y){
        this.x = x;
        this.y = y;
    }
        
    /**
     * Increments the time since last shot
     */
    public void tick(){
        time_since_last_shot++;
    }

    /**
     * Draws the tower on the game screen.
     *
     * @param app PApplet instance to draw the tower.
     */
    public void draw(PApplet app){
        app.image(this.sprite, this.x, this.y);
    }

    public void set(int x, int y){
        this.x=x;
        this.y=y;
    }

    public float getX(){
        return this.x;
    }
    
    public float getY(){
        return this.y;
    }

    public PImage getSprite(){
        return sprite;
    }

    public float getRange(){
        return initial_tower_range;
    }

    public float getSpeed(){
        return initial_tower_firing_speed;
    }

    public float getDamage(){
        return initial_tower_damage;
    }

    public float getCost(){
        return tower_cost;
    }

    public float getTimeSince(){
        return this.time_since_last_shot/60;
    }

    public void shoot(){
        this.time_since_last_shot = 0;
        return;
    }

    public void upgradeRange(){
        this.numberRange++;
        initial_tower_range += 32;
    }

    public void upgradeSpeed(){
        this.numberSpeed++;
        initial_tower_firing_speed += 0.5;
    }

    public void upgradeDamage(){
        this.numberDamage++;
        initial_tower_damage *= 1.5;
    }

    public int getRangeNumber(){
        return numberRange;
    }

    public int getSpeedNumber(){
        return numberSpeed;
    }

    public int getDamageNumber(){
        return numberDamage;
    }

    public void updateSprite(PImage sprite2){
        this.sprite = sprite2;
    }

    public float getRangeUpgradeCost(){
        return (20 + 10*(numberRange-1));
    }

    public float getSpeedUpgradeCost(){
        return (20 + 10*(numberSpeed-1));
    }    

    public float getDamageUpgradeCost(){
        return (20 + 10*(numberDamage-1));
    }
    
    /**
     * Returns the status of whether it was just placed
     * @return true if tower is just placed
     */
    public boolean getFirst(){
        return first;
    }

    public void setFirst(){
        first = false;
        return;
    }

    public void setFirstTrue(){
        first = true;
        return;
    }
}
