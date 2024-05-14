package WizardTD;
import java.util.*;

/**
 * Represents a wave of monsters in the WizardTD game.
 */
public class Wave {
    private float duration;
    private float preWavePause;
    private List<Monster> monsters;
    private int totalQuantity = 0;

    /**
     * Constructor to initialize a wave with its duration, pause before the wave, list of monsters, and total quantity.
     *
     * @param duration Duration of the wave.
     * @param preWavePause Duration of the pause before the wave starts.
     * @param monsters List of monsters in the wave.
     * @param totalQuantity Total number of monsters in the wave.
     */
    public Wave(float duration, float preWavePause, List<Monster> monsters, int totalQuantity) {
        this.duration = duration;
        this.preWavePause = preWavePause;
        this.monsters = monsters;
        this.totalQuantity = totalQuantity;
    }

    public float getDuration() {
        return duration;
    }

    public float getPreWavePause() {
        return preWavePause;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setQuantity(int givenQuantity) {
        this.totalQuantity = givenQuantity;
    }
}

