package WizardTD;

import java.util.List;

/**
 * The Monster class represents a monster entity in the WizardTD game.
 * Each monster has attributes such as type, health points (hp), speed, armour,
 * mana gained upon being killed, and quantity. The class provides methods to
 * get and set these attributes. Additionally, there are methods related to the
 * monster's path and position that are intended to be overridden by subclasses.
 */
public class Monster {
    protected String type;
    protected float hp;
    protected float speed;
    protected float armour;
    protected int manaGainedOnKill;
    protected int quantity;

    /**
     * Constructor to initialize a Monster object with the given attributes.
     * 
     * @param type Type of the monster.
     * @param hp Health points of the monster.
     * @param speed Speed of the monster.
     * @param armour Armour value of the monster.
     * @param manaGainedOnKill Mana gained when the monster is killed.
     * @param quantity Quantity of the monster.
     */
    public Monster(String type, float hp, float speed, float armour, int manaGainedOnKill, int quantity) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.quantity = quantity;
    }

    // Getter methods for each attribute
    public String getType() {
        return type;
    }

    public float getHP() {
        return hp;
    }
    public void setHP(float hp) {
        this.hp = hp;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float givenSpeed) {
        this.speed = givenSpeed;
    }

    public float getArmour() {
        return armour;
    }
    public void setArmour(float givenArmour) {
        this.armour = givenArmour;
    }

    public int getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int givenQuant) {
        this.quantity = givenQuant;
    }

    /**
     * Placeholder method to save the path of the monster. Intended to be overridden by subclasses.
     * 
     * @param path List of nodes representing the path.
     */
    public void savePath(List<Path.Node> path) {
        // Placeholder implementation
    }

    /**
     * Placeholder method to save the position of the monster. Intended to be overridden by subclasses.
     * 
     * @param x X-coordinate of the monster.
     * @param y Y-coordinate of the monster.
     */
    public void savePosition(float x, float y) {
        // Placeholder implementation
    }

    /**
     * Placeholder method to save the speed of the monster. Intended to be overridden by subclasses.
     * 
     * @param x Speed in the X-direction.
     * @param y Speed in the Y-direction.
     */
    public void saveSpeed(float x, float y) {
        // Placeholder implementation
    }

    /**
     * Placeholder method to set the path of the monster. Intended to be overridden by subclasses.
     * 
     * @param path List of nodes representing the path.
     */
    public void setPath(List<Path.Node> path) {
        // Placeholder implementation
    }

    /**
     * Placeholder method to set the position of the monster. Intended to be overridden by subclasses.
     * 
     * @param x X-coordinate of the monster.
     * @param y Y-coordinate of the monster.
     */
    public void set(float x, float y) {
        // Placeholder implementation
    }

    /**
     * Placeholder method to set the speed of the monster. Intended to be overridden by subclasses.
     * 
     * @param x Speed in the X-direction.
     * @param y Speed in the Y-direction.
     */
    public void setSpeed(float x, float y) {
        // Placeholder implementation
    }
}
