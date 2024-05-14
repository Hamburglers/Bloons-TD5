package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManaTest {

    private Mana mana;
    private static final float INITIAL_MANA = 100.0f;
    private static final float INITIAL_MANA_CAP = 200.0f;
    private static final float INITIAL_MANA_GAINED_PER_SECOND = 5.0f;
    private static final float MANA_POOL_SPELL_INITIAL_COST = 10.0f;
    private static final float MANA_POOL_SPELL_COST_INCREASE_PER_USE = 2.0f;
    private static final float MANA_POOL_SPELL_CAP_MULTIPLIER = 1.1f;
    private static final float MANA_POOL_SPELL_MANA_GAINED_MULTIPLIER = 1.2f;

    @BeforeEach
    public void setUp() {
        // Create the class
        mana = new Mana(INITIAL_MANA, INITIAL_MANA_CAP, INITIAL_MANA_GAINED_PER_SECOND, MANA_POOL_SPELL_INITIAL_COST, MANA_POOL_SPELL_COST_INCREASE_PER_USE, MANA_POOL_SPELL_CAP_MULTIPLIER, MANA_POOL_SPELL_MANA_GAINED_MULTIPLIER);
    }

    @Test
    public void testGetCurrent() {
        // Test getter function
        assertEquals(INITIAL_MANA, mana.getCurrent(), "Expected current mana to be the same as initial mana set.");
    }

    @Test
    public void testGetCap() {
        // Test getter function
        assertEquals(INITIAL_MANA_CAP, mana.getCap(), "Expected mana cap to be the same as initial mana cap set.");
    }

    @Test
    public void testGetPerSecond() {
        // Test getter function
        assertEquals(INITIAL_MANA_GAINED_PER_SECOND, mana.getPerSecond(), "Expected mana gained per second to be the same as initial value set.");
    }

    @Test
    public void testTickMana() {
        // Test mana is incremented when its ticked
        mana.tickMana();
        assertEquals(INITIAL_MANA + INITIAL_MANA_GAINED_PER_SECOND/60, mana.getCurrent(), "Expected current mana to increase by the mana gained per second value.");
        
    }

    @Test
    public void testGetPool() {
        // Test getter function
        assertEquals(MANA_POOL_SPELL_INITIAL_COST, mana.getPool(), "Expected mana pool spell initial cost to be the same as initial value set.");
    }

    @Test
    public void testCastSpell() {
        // Test casting spell deducts correct mana
        float initialMana = mana.getCurrent();
        mana.castSpell();
        assertEquals(initialMana - MANA_POOL_SPELL_INITIAL_COST, mana.getCurrent(), "Expected current mana to decrease by the mana pool spell initial cost after casting spell.");
    }

    @Test
    public void testKillMonster() {
        // Test monster when monster killed gives mana, and that it doesn't overflow
        int manaGainedOnKill = 20;
        float initialMana = mana.getCurrent();
        mana.killMonster(manaGainedOnKill);
        assertEquals(initialMana + manaGainedOnKill, mana.getCurrent(), "Expected current mana to increase by the mana gained on kill value.");

        // Mana shouldn't overflow when monster is killed
        manaGainedOnKill = 20000;
        mana.killMonster(manaGainedOnKill);
        assertEquals(mana.getCap(), mana.getCurrent(), "Expected mana to be the cap if it overflows");

        // Mana stops ticking when cap is reached
        mana.tickMana();
        assertEquals(mana.getCap(), mana.getCurrent(), "Expected mana to be the cap if it overflows");
    }

    @Test
    public void testMinusMana() {
        // Test when subtracted, it doesn't go into negative
        float initialMana = mana.getCurrent();
        float manaToSubtract = 50.0f;
        mana.minusMana(manaToSubtract);
        assertEquals(initialMana - manaToSubtract, mana.getCurrent(), "Expected current mana to decrease by the specified value.");
    }
}
