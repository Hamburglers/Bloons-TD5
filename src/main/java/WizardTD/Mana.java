package WizardTD;


/**
 * Represents the mana system in the WizardTD game.
 */
public class Mana {
    private float initial_mana_cap, initial_mana_gained_per_second, mana_pool_spell_initial_cost, mana_pool_spell_cost_increase_per_use, mana_pool_spell_cap_multiplier, mana_pool_spell_mana_gained_multiplier;
    private float currentMana;
    private int quantityCasted = 0;

    /**
     * Constructor for the Mana class.
     *
     * @param initial_mana Initial amount of mana.
     * @param initial_mana_cap Maximum amount of mana that can be held.
     * @param initial_mana_gained_per_second Amount of mana gained per second.
     * @param mana_pool_spell_initial_cost Initial cost of the mana pool spell.
     * @param mana_pool_spell_cost_increase_per_use Increase in cost of the mana pool spell with each use.
     * @param mana_pool_spell_cap_multiplier Multiplier for the mana cap when the mana pool spell is used.
     * @param mana_pool_spell_mana_gained_multiplier Multiplier for the mana gained when the mana pool spell is used.
     */
    public Mana(float initial_mana, float initial_mana_cap, float initial_mana_gained_per_second, float mana_pool_spell_initial_cost, float mana_pool_spell_cost_increase_per_use, float mana_pool_spell_cap_multiplier, float mana_pool_spell_mana_gained_multiplier){
        this.currentMana = initial_mana;
        this.initial_mana_cap = initial_mana_cap;
        this.initial_mana_gained_per_second = initial_mana_gained_per_second;
        this.mana_pool_spell_initial_cost = mana_pool_spell_initial_cost;
        this.mana_pool_spell_cost_increase_per_use = mana_pool_spell_cost_increase_per_use;
        this.mana_pool_spell_cap_multiplier = mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_mana_gained_multiplier = mana_pool_spell_mana_gained_multiplier;
    }

    public float getCurrent(){
        return this.currentMana;
    }

    public float getCap(){
        return this.initial_mana_cap;
    }

    public float getPerSecond(){
        return this.initial_mana_gained_per_second;
    }

    /**
     * Increases the current mana based on the mana gained per second.
     */
    public void tickMana(){
        if (this.currentMana < initial_mana_cap){
            this.currentMana += this.initial_mana_gained_per_second/60;
            return;
        }
    }

    public float getPool(){
        return this.mana_pool_spell_initial_cost;
    }
    /**
     * Function to handle logic when spell is casted
     */
    public void castSpell(){
        this.quantityCasted++;
        this.currentMana -= this.mana_pool_spell_initial_cost;
        this.initial_mana_cap *= mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_initial_cost += mana_pool_spell_cost_increase_per_use;
        return;
    }

    /**
     * Function to handle logic when monster is killed
     * Mana is added after a corresponding multiplier to the current mana
     * @param manaGainedOnKill
     */
    public void killMonster(int manaGainedOnKill){
        float totalMultiplier = 1 + (mana_pool_spell_mana_gained_multiplier - 1) * quantityCasted;
        if (this.currentMana + manaGainedOnKill * totalMultiplier > this.initial_mana_cap){
            this.currentMana = this.initial_mana_cap;
            return;
        }
        this.currentMana += manaGainedOnKill * totalMultiplier;
    }

    /**
     * Decreases the current mana by a specified amount.
     *
     * @param x Amount to decrease the current mana by.
     */
    public void minusMana(float x){
        this.currentMana -= x;
    }
}
