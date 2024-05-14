package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import java.io.*;
import java.util.*;

/**
 * Handles the main logic, and brings together all the other classes
 */
public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    protected PImage shrub, house, grass, path0, path1, path2, path3;
    //private int frame_count = 0;
    protected int time_since_spawned = 0, time_since_last_wave = 0;
    private PImage gremlin_image, beetle_image, worm_image, fireball, gremlin1, gremlin2, gremlin3, gremlin4, gremlin5;
    private PImage tower0, tower1, tower2;
    protected List<String> map;
    protected List<Wave> waveList;
    protected int counter,  wave_number = 0;
    protected List<Monster> monsterList;
    protected int gremlin_spawned = 0, beetle_spawned = 0, worm_spawned = 0;
    protected List<Gremlin> gremlin_array = new ArrayList<>();
    protected List<Beetle> beetle_array = new ArrayList<>();
    protected List<Worm> worm_array = new ArrayList<>();
    protected PImage[] gremlinSprites;

    private Path pathFinder = new Path();
    // Protected for testing purposes
    protected int[][] grid = new int[20][20];
    protected int[] goal;
    protected int wizard_house_right1, wizard_house_row_number1;
    protected Map<Integer, List<Integer>> startpos = new HashMap<>();

    protected Mana mana;

    protected boolean isPaused = false, fastforward = false;
    protected boolean hoverSpeed, hoverPause, hoverTower, hoverURange, hoverUSpeed, hoverUDamage, hoverPool;
    protected boolean clickTower = false, clickURange = false, clickUSpeed = false, clickUDamage = false;
    protected boolean game_over = false, game_win = false, game_start = false, endless_start = false;
    protected boolean hoverNormalStart = false, hoverEndlessStart = false;
    protected List<Monster> endlessMonsters;
    protected boolean intialise = true;

    protected List<Tower> placedTowers = new ArrayList<>();
    protected List<Pair> pair = new ArrayList<>();
    private float range, speed, damage, cost;
    protected Tower tower;
    protected int mouseX, mouseY;

    protected List<Fireball<? extends Moveable>> fireballs = new ArrayList<>();
    private int update = 1;
    private float armour_multiplier, hp_multiplier, speed_multipler;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Returns a random key-value pair from the provided map.
     * The map's keys are integers, and the associated values are lists of integers.
     * The method randomly selects a key from the map and then randomly selects
     * a value from the list associated with that key.
     *
     * @param map The map from which to retrieve a random entry.
     * @return A random key-value pair from the map.
     */
    public static Map.Entry<Integer, Integer> getRandomEntry(Map<Integer, List<Integer>> map) {
        Random random = new Random();
    
        // Get a random key from the map
        List<Integer> keyList = new ArrayList<>(map.keySet());
        int randomKeyIndex = random.nextInt(keyList.size());
        Integer randomKey = keyList.get(randomKeyIndex);
    
        // Get a random value from the list associated with the random key
        List<Integer> values = map.get(randomKey);
        int randomValueIndex = random.nextInt(values.size());
        Integer randomValue = values.get(randomValueIndex);
    
        // Return the random key-value pair as a Map.Entry
        return new AbstractMap.SimpleEntry<>(randomKey, randomValue);
    }

    /**
     * Determines the type of tile a tower is currently on based on its x and y coordinates.
     *
     * @param x The x-coordinate of the tower.
     * @param y The y-coordinate of the tower.
     * @return A string indicating the type of tile ("path", "shrub", "grass", or "" if outside the map).
     */
    public String findTile(int x, int y) {
        int coordX = (int) ((x / 32));
        int coordY = (int) ((y - 40)/32);
        if (coordX < 0 || coordX >= 20 || coordY < 0 || coordY >= 20 || y < 40) {
            return "";  // Coordinates are outside the map
        } else if (grid[coordY][coordX] == 0) {
            return "path";
        } else if (grid[coordY][coordX] == 1) {
            return "shrub";
        } else if (grid[coordY][coordX] == -1) {
            return "wizard";
        } else{
            return "grass";
        }
    }

    /**
     * Loads the game menu by drawing action bars on the screen.
     */
    public void loadMenu() {
        // Brown action bars
        noStroke();
        fill(135, 115, 74);
        rect(0,0,640,40);
        rect(640,0,120,680);
        stroke(0);

        // Buttons on side bar
        // Pause
        if (isPaused) {
            fill(255,255,0);
        } else if (hoverPause) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,105,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("P ",655, 137);
        strokeWeight(1);
        textSize(12);
        text("PAUSE",695, 125);

        // Fastforward
        if (fastforward) {
            fill(255,255,0);
        } else if (hoverSpeed) {
            fill(169,169,169);
        } else {
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,50,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("FF",655, 82);
        strokeWeight(1);
        textSize(12);
        text("2x speed",695, 70);

        // Build tower
        if (clickTower) {
            fill(255,255,0);
        } else if (hoverTower) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,160,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("T ",655, 192);
        strokeWeight(1);
        textSize(12);
        text("Build",695, 180);
        text("tower",695, 195);
        if (hoverTower) {
            fill(255,255,255);
            rect(575,160,65,20);
            fill(0,0,0);
            text("Cost:" + (int)tower.getCost(), 577,175);
        }
        // Upgrade range
        if (clickURange) {
            fill(255,255,0);
        } else if (hoverURange) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,215,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("U1",655, 247);
        strokeWeight(1);
        textSize(12);
        text("Upgrade",695, 235);
        text("range",695, 250);
        // Upgrade speed
        if (clickUSpeed) {
            fill(255,255,0);
        } else if (hoverUSpeed) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,270,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("U2",655, 302);
        strokeWeight(1);
        textSize(12);
        text("Upgrade",695, 290);
        text("speed",695, 305);
        // Upgrade damage
        if (clickUDamage) {
            fill(255,255,0);
        } else if (hoverUDamage) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,325,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("U3",655, 357);
        strokeWeight(1);
        textSize(12);
        text("Upgrade",695, 345);
        text("damage",695, 360);
        // Mana pool
        if (hoverPool) {
            fill(169,169,169);
        } else{
            fill(135, 115, 74);
        }
        strokeWeight(3);
        rect(650,380,40,40);
        fill(0, 0, 0);
        textSize(25);
        text("M ",655, 412);
        strokeWeight(1);
        textSize(12);
        text("Mana pool",695, 400);
        text("cost: " + (int)this.mana.getPool(),695, 415);
        if (hoverPool) {
            fill(255,255,255);
            rect(575,380,65,20);
            fill(0,0,0);
            text("Cost:" + (int)this.mana.getPool(), 577,395);
        }

        float fill_percent = this.mana.getCurrent() / this.mana.getCap();
        // Mana text
        textSize(20);
        fill(0, 0, 0);
        text("MANA:",320, 30);

        fill(255,255,255);
        rect(400+fill_percent*320,12,320-fill_percent*320,20);

        // Progress bar slider
        fill(0,0,0);
        rect(400+fill_percent*320,12,3,20);

        // Progress bar filler
        fill(0,255,255);
        rect(400,12,fill_percent*320,20);

        // Mana count
        textSize(20);
        fill(0, 0, 0);
        text((int)this.mana.getCurrent() + " / " + (int)this.mana.getCap(),500, 30);
    }

    /**
     * Moves entities within the game environment.
     * <p>
     * This method is responsible for updating the positions of entities, handling their interactions
     * with other game elements, and managing their states. It is called once every draw() cycle, 
     * which is approximately 60 times per second.
     * </p>
     * <p>
     * The method handles various game mechanics, such as:
     * - Gremlin death animations
     * - Tower shooting mechanics and fireball interactions
     * - Movement of monsters along a path
     * - Health bar visualizations
     * - Game over conditions and visuals
     * </p>
     *
     * @param <T> A type that extends the Moveable interface, representing entities that can move.
     * @param entities A list of entities of type T that need to be moved.
     */
    public <T extends Moveable> void moveEntities(List<T> entities) {
        final float threshold = 1.0f; // Proximity threshold to a path node
        List<T> entitiesToRemove = new ArrayList<>();
        for (T entity : entities) {
            try{
                // Health bar
                noStroke();

                // Green bar for current health
                fill(50, 205, 50);
                float percentage = entity.getCurrentHealth() / entity.getHealth();
                // If percentage is negative, set it to 0 (visual bug)
                if (percentage < 0)
                    percentage = 0;
                rect(entity.getX() + entity.getSpeedX() - 8, entity.getY() + entity.getSpeedY() - 6, 35 * percentage, 2);

                // Red bar for lost health
                fill(255, 0, 0);
                rect(entity.getX() + entity.getSpeedX() - 8 + 35 * percentage, entity.getY() + entity.getSpeedY() - 6, 35 * (1 - percentage), 2);

                stroke(0);
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
            if (isPaused) {
                entity.draw(this);
                continue;
            }
            if (entity != null) {
                // Death animation for gremlin
                if (entity instanceof Gremlin) {
                    Gremlin gremlinEntity = (Gremlin) entity;
                    if (gremlinEntity.getDying()) {
                        boolean done = gremlinEntity.isDying();
                        if (!done) {
                            entitiesToRemove.add(entity);
                            continue;
                        } else{
                            try {
                                entity.draw(this);
                            } catch (NullPointerException e) {
                                println("Graphics context not yet initialized. Skipping visualisations.");
                            }
                            continue;
                        }
                    }
                }
                // Make towers shoot, and create a new Fireball object and add to a list
                List<Fireball<? extends Moveable>> fireballsToRemove = new ArrayList<>();
                int subSteps = Math.max(1, (int) (4 * entity.getOverallSpeed())); // Adjust sub-steps based on speed
                for (Tower t: placedTowers) {
                    if (1/t.getSpeed() <= t.getTimeSince() && Math.pow(Math.pow((entity.getX()+16-(t.getX()*32+16)),2) + Math.pow((entity.getY()+16-(t.getY()*32+40+16)),2),0.5) <= t.getRange()) {
                        t.shoot();
                        Fireball<T> ball = new Fireball<>(fireball, 5, entity, t.getDamage());
                        ball.set(t.getX()*32+16, t.getY()*32+40+16);
                        fireballs.add(ball);
                    }
                }
                // Loop through Fireball list, check if its close to an enemy
                for (Fireball<? extends Moveable> f: fireballs) {
                    if (Math.abs(f.getX()-entity.getX())<=32 && Math.abs(f.getY()-entity.getY())<=32 && !entitiesToRemove.contains(entity)) {
                        boolean death = entity.hit(f.getDamage());
                        // If the locked on enemy is dead, remove the enemy
                        if (death) {
                            if (entity instanceof Gremlin) {
                                Gremlin gremlinEntity = (Gremlin) entity;
                                gremlinEntity.isDying();
                                mana.killMonster(entity.getMana());
                                continue;
                            }
                            entitiesToRemove.add(entity);
                            mana.killMonster(entity.getMana());
                            continue;
                        }
                        // Remove any leftover fireballs still locked onto that enemy
                        fireballsToRemove.add(f);
                    }
                }
                // Remove all the fireballs in the list, as modifying the list while its in a for loop will error
                fireballs.removeAll(fireballsToRemove);
                // Move the monsters in 4 substeps, so they do not overshoot the path when they are fast
                for (int step = 0; step < subSteps; step++) {
                    List<Path.Node> currentPath = entity.getPath();
                    // If theres still a path in the enemy, continue
                    if (!currentPath.isEmpty()) {
                        Path.Node nextPath = currentPath.get(0);
                        float nextPathX = nextPath.x * 32 + 5;
                        float nextPathY = nextPath.y * 32 + 40 + 5;
    
                        float dx = nextPathX - entity.getX();
                        float dy = nextPathY - entity.getY();
                        // If its within the threshold to turn, stop the current instruction and skip to the next
                        if (Math.abs(dx) < threshold && Math.abs(dy) < threshold) {
                            currentPath.remove(0);
                            entity.setPath(currentPath);
                            if (!currentPath.isEmpty()) {
                                nextPath = currentPath.get(0);
                                dx = nextPath.x * 32 + 5 - entity.getX();
                                dy = nextPath.y * 32 + 40 + 5 - entity.getY();
                            }
                        }
                        // Adjust the speed for the next intstruction
                        float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
                        float dirX = dx / magnitude;
                        float dirY = dy / magnitude;
    
                        entity.setSpeed(dirX * entity.getOverallSpeed() / subSteps, dirY * entity.getOverallSpeed() / subSteps);
                        entity.set(entity.getX() + entity.getSpeedX(), entity.getY() + entity.getSpeedY());

                        // This condition if the monster gets close to the wizard house
                    } else if (Math.abs(entity.getX() - this.wizard_house_right1) <= 32 && Math.abs(entity.getY() - this.wizard_house_row_number1) <= 15) {
                        // Reduce the mana equivalent of its health
                        mana.minusMana(entity.getCurrentHealth());
                        // Reset monster
                        entity.reset();
                        // If no more mana, game is over
                        if (mana.getCurrent() <= 0) {
                            game_over = true;
                            isPaused = true;
                        }
                    }
                }
                // Tick all the enemies then draw
                entity.tick();
                try {
                    entity.draw(this);
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
                if (game_over) {
                    // Game over screen
                    try{
                        noStroke();
                        fill(220,20,60,40);
                        rect(0,40,640,640);
                        stroke(0);
                        fill(0,0,0);
                        textSize(50);
                        textAlign(CENTER, CENTER);
                        text("Game Over", 320,200);
                        text("Press 'r' to retry", 320,250);

                        // Mana bar
                        fill(255,255,255);
                        rect(400,12,320,20);

                        // Progress bar slider
                        fill(0,0,0);
                        rect(400,12,3,20);

                        // Mana count
                        textAlign(LEFT);
                        textSize(20);
                        fill(0, 0, 0);
                        text(0 + " / " + (int)this.mana.getCap(),500, 30);
                    } catch (NullPointerException e) {
                        println("Graphics context not yet initialized. Skipping visualisations.");
                    }
                    return;
                }
            }
        }
        // Add all the entities that have died in this list, which is removed at the end of the iteration
        // as modifying the list during iteration will error
        entities.removeAll(entitiesToRemove);
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        try {
            frameRate(FPS);
            // Load images during setup
            this.shrub = this.loadImage("src/main/resources/WizardTD/shrub.png");
            this.house = this.loadImage("src/main/resources/WizardTD/wizard_house.png");
            this.grass = this.loadImage("src/main/resources/WizardTD/grass.png");
            this.path0 = this.loadImage("src/main/resources/WizardTD/path0.png");
            this.path1 = this.loadImage("src/main/resources/WizardTD/path1.png");
            this.path2 = this.loadImage("src/main/resources/WizardTD/path2.png");
            this.path3 = this.loadImage("src/main/resources/WizardTD/path3.png");
            this.gremlin_image = this.loadImage("src/main/resources/WizardTD/gremlin.png");
            this.beetle_image = this.loadImage("src/main/resources/WizardTD/beetle.png");
            this.beetle_image.resize(23, 20);
            this.worm_image = this.loadImage("src/main/resources/WizardTD/worm.png");
            this.tower0 = this.loadImage("src/main/resources/WizardTD/tower0.png");
            this.tower1 = this.loadImage("src/main/resources/WizardTD/tower1.png");
            this.tower2 = this.loadImage("src/main/resources/WizardTD/tower2.png");
            this.fireball = this.loadImage("src/main/resources/WizardTD/fireball.png");
            this.gremlin1 = this.loadImage("src/main/resources/WizardTD/gremlin1.png");
            this.gremlin2 = this.loadImage("src/main/resources/WizardTD/gremlin2.png");
            this.gremlin3 = this.loadImage("src/main/resources/WizardTD/gremlin3.png");
            this.gremlin4 = this.loadImage("src/main/resources/WizardTD/gremlin4.png");
            this.gremlin5 = this.loadImage("src/main/resources/WizardTD/gremlin5.png");
            gremlinSprites = new PImage[5];
            gremlinSprites[0] = this.gremlin1;
            gremlinSprites[1] = this.gremlin2;
            gremlinSprites[2] = this.gremlin3;
            gremlinSprites[3] = this.gremlin4;
            gremlinSprites[4] = this.gremlin5;
            // Try opening config path and extracting value from layout
            JSONObject json = loadJSONObject(this.configPath);
            String level_name = json.getString("layout");
            File level = new File(level_name);
            Scanner scan = new Scanner(level);

            // Read everything into a list
            this.map = new ArrayList<>();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                while (line.length() < 20) {
                    line += " "; // append spaces until the length is 20
                }
                this.map.add(line);
            }
            scan.close();
            // Increment number of frames passed
            this.waveList = new ArrayList<>();

            // Loop through each list in waves
            JSONArray waves = json.getJSONArray("waves");
            for (int i = 0; i < waves.size(); i++) {
                // Extract values
                JSONObject waveObj = waves.getJSONObject(i);
                float duration = waveObj.getFloat("duration");
                float preWavePause = waveObj.getFloat("pre_wave_pause");
                JSONArray monstersArray = waveObj.getJSONArray("monsters");
                
                int total = 0;
                this.monsterList = new ArrayList<>();
                for (int j = 0; j < monstersArray.size(); j++) {
                    JSONObject monsterObj = monstersArray.getJSONObject(j);
                    String type = monsterObj.getString("type");
                    float hp = monsterObj.getFloat("hp");
                    float speed = monsterObj.getFloat("speed");
                    float armour = monsterObj.getFloat("armour");
                    int manaGainedOnKill = monsterObj.getInt("mana_gained_on_kill");
                    int quantity = monsterObj.getInt("quantity");

                    // For every monster in each wave, Monster object created, and added to a list
                    Monster monster = new Monster(type, hp, speed, armour, manaGainedOnKill, quantity);
                    monsterList.add(monster);
                    total += quantity;
                }
                // When all monsters have been added to a list, the list is added to the Wave object
                // Wave object is added to a list
                Wave wave = new Wave(duration, preWavePause, monsterList, total);
                this.waveList.add(wave);
            }
            // Extract values
            this.mana = new Mana(json.getFloat("initial_mana"), json.getFloat("initial_mana_cap"), json.getFloat("initial_mana_gained_per_second"),json.getFloat("mana_pool_spell_initial_cost"),json.getFloat("mana_pool_spell_cost_increase_per_use"),json.getFloat("mana_pool_spell_cap_multiplier"),json.getFloat("mana_pool_spell_mana_gained_multiplier"));
            range = json.getFloat("initial_tower_range");
            speed = json.getFloat("initial_tower_firing_speed");
            damage = json.getFloat("initial_tower_damage");
            cost = json.getFloat("tower_cost");
            this.tower = new Tower(tower0, range, speed, damage, cost);
            // Instantiate the grid for pathfinding
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = 2;
                }
            }
            // Endless mode stats
            waves = json.getJSONArray("endless_monsters");
            endlessMonsters = new ArrayList<>();
            for (int i = 0; i < waves.size(); i++) {
                JSONObject monsterObj = waves.getJSONObject(i);
                String type = monsterObj.getString("type");
                float hp = monsterObj.getFloat("hp");
                float speed = monsterObj.getFloat("speed");
                float armour = monsterObj.getFloat("armour");
                int manaGainedOnKill = monsterObj.getInt("mana_gained_on_kill");
                Monster monster = new Monster(type, hp, speed, armour, manaGainedOnKill, 1);
                endlessMonsters.add(monster);
            }
            this.armour_multiplier = json.getFloat("armour_multiplier");
            this.hp_multiplier = json.getFloat("hp_multiplier");
            this.speed_multipler = json.getFloat("speed_multipler");
        } catch (FileNotFoundException e) {
            return;
        }
    }

    /**
     * Initializes a monster with a specified starting position, path, and speed.
     * <p>
     * This method determines a path for the monster to follow using the pathFinder. If no path is found,
     * it selects a random starting position until a valid path is identified. Once a path is determined,
     * the monster's initial position and speed are set based on its starting coordinates.
     * </p>
     * <p>
     * The method also saves the original path, position, and speed of the monster for future reference,
     * especially if the monster is banished and needs to be repositioned.
     * </p>
     *
     * @param <T> A type that extends the Monster interface, representing game monsters.
     * @param monster The monster instance to be initialized.
     * @param x The x-coordinate of the monster's starting position.
     * @param y The y-coordinate of the monster's starting position.
     * @param speed The initial speed of the monster.
     */
    public <T extends Monster> void initiateMonster(T monster, int x, int y, float speed) {
        // Path finding
        List<Path.Node> path;
        while (true) {
            Path.Node start = pathFinder.new Node(x,y);
            Path.Node goal = pathFinder.new Node(this.goal[1], this.goal[0]);
            path = pathFinder.findPath(grid, start, goal);
            if (path != null) {
                break;
            }
            Map.Entry<Integer, Integer> randomEntry = getRandomEntry(startpos);
            x = randomEntry.getKey();
            y = randomEntry.getValue();
        }
        // Set original start position, and save it to that monster class for when its banished
        if (x == 0) {
            monster.savePath(path);
            monster.savePosition(x * 32 - 32, y * 32 + 40 + 5);
            monster.saveSpeed(speed, 0);

            monster.setPath(path);
            monster.set(x * 32 - 32, y * 32 + 40 + 5);
            monster.setSpeed(speed, 0);
        }else if (y == 0) {
            monster.savePath(path);
            monster.savePosition(x * 32 + 5, y * 32 + 40 - 5);
            monster.saveSpeed(0, speed);

            monster.setPath(path);
            monster.set(x * 32 + 5, y * 32 + 40 - 5);
            monster.setSpeed(0, speed);
        } else if (y == 19) {
            monster.savePath(path);
            monster.savePosition(x * 32, y * 32 + 40 +5);
            monster.saveSpeed(0, -speed);

            monster.setPath(path);
            monster.set(x * 32, y * 32 + 40 +5);
            monster.setSpeed(0, -speed);
        } else {
            monster.savePath(path);
            monster.savePosition(x * 32 + 32, y * 32 + 40 + 6);
            monster.saveSpeed(-speed, 0);

            monster.setPath(path);
            monster.set(x * 32 + 32, y * 32 + 40 + 6);
            monster.setSpeed(-speed, 0);
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed() {
        // If user press r on game win or game end screen
        if (game_over || game_win) {
            if (keyCode == 82) {
                // Reset everything and call setup again
                gremlin_spawned = 0;
                beetle_spawned = 0;
                worm_spawned = 0;
                gremlin_array = new ArrayList<>();
                beetle_array = new ArrayList<>();
                worm_array = new ArrayList<>();wave_number = 0;
                pathFinder = new Path();
                grid = new int[20][20];
                isPaused = false;
                fastforward = false;
                clickTower = false;
                clickURange = false;
                clickUSpeed = false;
                clickUDamage = false;
                placedTowers = new ArrayList<>();
                pair = new ArrayList<>();
                fireballs = new ArrayList<>();
                time_since_spawned = 0;
                time_since_last_wave = 0;
                game_over = false;
                game_win = false;
                intialise = true;
                game_start = false;
                endless_start = false;
                try {
                    setup();
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
            } else{
                return;
            }
        }
        // If user clicks p, toggle the boolean opposite
        if (keyCode == 80) {
            isPaused = !isPaused;
        }
        // If user clicks f, toggle the boolean opposite
        if (keyCode == 70) {
            fastforward = !fastforward;
            // If its fastforward, update everything to be faster
            if (fastforward == false) {
                update = 1;
                for (Gremlin gremlin: gremlin_array) {
                    gremlin.setOverallSpeed((float) 0.5*gremlin.getOverallSpeed());
                    gremlin.setSpeed(2*gremlin.getSpeedX(), 2*gremlin.getSpeedY());
                }
                for (Beetle beetle: beetle_array) {
                    beetle.setOverallSpeed((float) 0.5*beetle.getOverallSpeed());
                    beetle.setSpeed(2*beetle.getSpeedX(), 2*beetle.getSpeedY());
                }
                for (Worm worm: worm_array) {
                    worm.setOverallSpeed((float) 0.5*worm.getOverallSpeed());
                    worm.setSpeed(2*worm.getSpeedX(), 2*worm.getSpeedY());
                }
                for (Fireball<? extends Moveable> fireball: fireballs) {
                    fireball.setSpeed(5);
                }
            } else {
                for (Gremlin gremlin: gremlin_array) {
                    gremlin.setOverallSpeed(2*gremlin.getOverallSpeed());
                    gremlin.setSpeed(2*gremlin.getSpeedX(), 2*gremlin.getSpeedY());
                }
                for (Beetle beetle: beetle_array) {
                    beetle.setOverallSpeed(2*beetle.getOverallSpeed());
                    beetle.setSpeed(2*beetle.getSpeedX(), 2*beetle.getSpeedY());
                }
                for (Worm worm: worm_array) {
                    worm.setOverallSpeed(2*worm.getOverallSpeed());
                    worm.setSpeed(2*worm.getSpeedX(), 2*worm.getSpeedY());
                }
                for (Fireball<? extends Moveable> fireball: fireballs) {
                    fireball.setSpeed(10);
                }
                update = 2;
            }
        }
        
        // If the user clicks t
        if (keyCode == 84) {
            clickTower = !clickTower;
        }
        // If the user clicks 1
        if (keyCode == 49) {
            clickURange = !clickURange;
        }
        // If the user clicks 2
        if (keyCode == 50) {
            clickUSpeed = !clickUSpeed;
        }
        // If the user clicks 3
        if (keyCode == 51) {
            clickUDamage = !clickUDamage;
        }
        // If the user clicks m
        if (keyCode == 77) {
            // If the user has enough mana, cast the spell
            // else do nothing
            if (this.mana.getCurrent() >= this.mana.getPool()) {
                this.mana.castSpell();
            }
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Get current position of mouse and save to a variable
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        // Boolean values that change depending on the position of the mouse
        hoverSpeed = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 50 && mouseY <= 50+40;
        hoverPause = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 105 && mouseY <= 105+40;
        hoverTower = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 160 && mouseY <= 160+40;
        hoverURange = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 215 && mouseY <= 215+40;
        hoverUSpeed = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 270 && mouseY <= 270+40;
        hoverUDamage = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 325 && mouseY <= 325+40;
        hoverPool = mouseX >= 650 && mouseX <= 650+40 && mouseY >= 380 && mouseY <= 380+40;
        hoverNormalStart = mouseX >= 380-110 && mouseX <= 380+110 && mouseY >= 450-25 && mouseY <= 450+25;
        hoverEndlessStart = mouseX >= 380-110 && mouseX <= 380+110 && mouseY >= 550-25 && mouseY <= 550+25;
    }

    /**
     * Receive mouse pressed signal from the keyboard.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // If the mouse hovers over the normal start button and clicks
        if (hoverNormalStart) {
            game_start = true;
        }
        // If the mouse hovers over the endless start button and clicks
        if (hoverEndlessStart) {
            endless_start = true;
        }
        // If the game hasn't started or is on the win/end screen, disable any other keybinds
        if (!game_start && !endless_start)
            return;
        if (game_over || game_win) {
            return;
        }
        // These conditions if the mouse is hovering over the buttons in the action bar
        // for the purpose of making the button grey when being hovered over
        if (hoverPause) {
            isPaused = !isPaused;
        }
        if (hoverSpeed) {
            fastforward = !fastforward;

            // If its fastforward, update everything to be faster
            if (fastforward == false) {
                update = 1;
                for (Gremlin gremlin: gremlin_array) {
                    gremlin.setOverallSpeed((float) 0.5*gremlin.getOverallSpeed());
                    gremlin.setSpeed(2*gremlin.getSpeedX(), 2*gremlin.getSpeedY());
                }
                for (Beetle beetle: beetle_array) {
                    beetle.setOverallSpeed((float) 0.5*beetle.getOverallSpeed());
                    beetle.setSpeed(2*beetle.getSpeedX(), 2*beetle.getSpeedY());
                }
                for (Worm worm: worm_array) {
                    worm.setOverallSpeed((float) 0.5*worm.getOverallSpeed());
                    worm.setSpeed(2*worm.getSpeedX(), 2*worm.getSpeedY());
                }
                for (Fireball<? extends Moveable> fireball: fireballs) {
                    fireball.setSpeed(5);
                }
            } else {
                for (Gremlin gremlin: gremlin_array) {
                    gremlin.setOverallSpeed(2*gremlin.getOverallSpeed());
                    gremlin.setSpeed(2*gremlin.getSpeedX(), 2*gremlin.getSpeedY());
                }
                for (Beetle beetle: beetle_array) {
                    beetle.setOverallSpeed(2*beetle.getOverallSpeed());
                    beetle.setSpeed(2*beetle.getSpeedX(), 2*beetle.getSpeedY());
                }
                for (Worm worm: worm_array) {
                    worm.setOverallSpeed(2*worm.getOverallSpeed());
                    worm.setSpeed(2*worm.getSpeedX(), 2*worm.getSpeedY());
                }
                for (Fireball<? extends Moveable> fireball: fireballs) {
                    fireball.setSpeed(10);
                }
                update = 2;
            }
        }

        if (hoverTower) {
            clickTower = !clickTower;
        }
        // If the user clicks a tower, and it hasn't been placed
        if (clickTower && tower.getFirst()) {
            int upgradeCost = 20; // Cost for each upgrade
            float totalCost = tower.getCost(); // Initial cost is the cost of the tower
            String tileType = findTile(e.getX(), e.getY());
            // Check if the current tile is grass and the user has enough mana
            if (tileType.equals("grass") && mana.getCurrent() >= totalCost) {
                // Save the x,y coordinates to a Pair class
                Pair pairToCheck = new Pair((int) ((e.getX() / 32)), (int) ((e.getY() - 40) / 32));

                // If the values already exist, then a tower is already there, and the condition is not met
                if (pair.isEmpty() || !pair.contains(pairToCheck)) {
                    // If it isn't, add the pair to the list so next time this value will be checked as well
                    pair.add(pairToCheck);
                    tower.setFirst();
                    // Check for upgrades in the specific order and apply them if enough mana is available
                    if (clickURange && mana.getCurrent() >= totalCost + upgradeCost) {
                        tower.upgradeRange();
                        totalCost += upgradeCost;
                    }
                    
                    if (clickUSpeed && mana.getCurrent() >= totalCost + upgradeCost) {
                        tower.upgradeSpeed();
                        totalCost += upgradeCost;
                    }
                    
                    if (clickUDamage && mana.getCurrent() >= totalCost + upgradeCost) {
                        tower.upgradeDamage();
                        totalCost += upgradeCost;
                    }
                    // Update sprite for all level 1
                    if (tower.getRangeNumber() >= 2 && tower.getSpeedNumber() >= 2&& tower.getDamageNumber() >= 2) {
                        tower.updateSprite(tower1);
                    }
                    // Deduct the total cost from the current mana
                    mana.minusMana(totalCost);
                    // Add the tower to a list
                    placedTowers.add(tower);
                    tower.setFirst();
                    tower.set((int) ((e.getX() / 32)), (e.getY() - 40) / 32);
                    // Create a new tower for the next placement
                    this.tower = new Tower(tower0, range, speed, damage, cost);
                }
            }
        }
        
        if (hoverURange) {
            clickURange = !clickURange;
        }
        if (hoverUSpeed) {
            clickUSpeed = !clickUSpeed;
        }
        if (hoverUDamage) {
            clickUDamage = !clickUDamage;
        }
        if (hoverPool) {
            if (this.mana.getCurrent() >= this.mana.getPool()) {
                this.mana.castSpell();
            }
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        // Game main menu
        if (!game_start && !endless_start) {
            try {
                // Make the background all grass png first
                for (int x = 0; x < width; x += 32) {
                    for (int y = 0; y < height; y += 32) {
                        image(grass, x, y);
                    }
                }
                rectMode(CORNER);
                textMode(CORNER);
                textSize(40);
                fill(135, 115, 74);
                text("Low", 150+70,150);
                text("Budget", 200+70,200);
                rotate(radians(15));
                rect(260+50+70,210-60,150,100);
                fill(99,153,196);
                text("Bloons", 250+15+50+70,250-60);
                textSize(56);
                fill(183,81,40);
                text("TD 5", 253+15+50+70,300-60);
                rotate(radians(340));
                rotate(radians(30));
                textSize(40);
                text("LBBTD5!", 500,-40);
                fill(255,255,255);
                rotate(radians(335));
                rectMode(CENTER);
                if (hoverNormalStart) {
                    fill(169,169,169);
                }
                rect(380,450,200+20,50,10);
                textSize(20);
                fill(255,255,255);
                if (hoverEndlessStart) {
                    fill(169,169,169);
                }
                rect(380,550,200+20,50,10);
                fill(0,0,0);
                text("Start Normal Mode",380-90,450+5);
                text("Start ENDLESS Mode",380-90-5,550+5);
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
            return;
        } else if (endless_start && intialise) {
            // If the user clicks endless start
            intialise = false;
            int total = 0;
            // endlessMonsters is the blueprint for all the monsters to spawn, the attributes are modified after every wave
            for (Monster m: endlessMonsters) {
                total += m.getQuantity();
            }
            waveList = new ArrayList<>();
            waveList.add(new Wave(8,3,new ArrayList<>(endlessMonsters),total));
        }
        try{
            rectMode(CORNER);
            textMode(CORNER);
            textSize(20);
            textAlign(LEFT);
        } catch(NullPointerException e) {
            println("Graphics context not yet initialized. Skipping visualisations.");
        }

        if (isPaused) {
            if (game_over || game_win) {
                return;
            }
        }
        // Set background colour to brown
        try{
            background(135, 115, 74);
        } catch(NullPointerException e) {
            println("Graphics context not yet initialized. Skipping visualisations.");
        }
        for (Tower t: placedTowers) {
            t.tick();
            if (fastforward) {
                t.tick();
            }
        }

        // Set original position of the map
        int row_number = 40;
        int pixels_right = 0;
        int wizard_house_right = 0;
        int wizard_house_row_number = 0;

        // wizard house rotation
        int angle = 0;
        // Handle spawning the background
        for (int j = 0; j < min(this.map.size(),20); j++) {
            char[] array = this.map.get(j).toCharArray();
            // Handle rendering the map
            for (int i = 0; i < min(array.length,20); i++) {
                // Get a list of possible entrance points
                if (j == 0 && array[i] == 'X' || j >= 1 && j < this.map.size() -1 && i == 0 && array[i] == 'X' 
                || j >= 1 && j < this.map.size() -1 && i == array.length-1 && array[i] == 'X' 
                || j == this.map.size()-1 && array[i] == 'X') {
                    // Check if the map contains the key
                    if (!startpos.containsKey(i)) {
                        // If not, create a new list and put it in the map with the key
                        startpos.put(i, new ArrayList<>());
                    }
                    // Add the value to the list associated with the key
                    startpos.get(i).add(j);
                }
                // Check for each character
                if (array[i] == ' ') {
                    // Next line for pathfinding, updating a 2d map
                    grid[j][i] = 2;
                    try {
                        this.image(this.grass, pixels_right, row_number);
                    } catch (NullPointerException e) {
                        println("Graphics context not yet initialized. Skipping visualisations.");
                    }
                }else if (array[i] == 'S') {
                    // Next line for pathfinding, updating a 2d map
                    grid[j][i] = 1;
                    try{
                    this.image(this.shrub, pixels_right, row_number);
                    } catch (NullPointerException e) {
                        println("Graphics context not yet initialized. Skipping visualisations.");
                    }
                } else if (array[i] == 'X') {
                    // Next line for pathfinding, updating a 2d map
                    grid[j][i] = 0;
                    // Handle top border
                    if (i != 0 && i != 19 && j == 0) {
                        char left = array[i - 1];
                        char right = array[i + 1];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', right == 'X', true, down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    // Handle bottom border
                    } else if (i != 0 && i != 19 && j == 19) {
                        char left = array[i - 1];
                        char right = array[i + 1];
                        char up = map.get(j-1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', right == 'X', up == 'X', true), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    // Handle corner
                    } else if (i == 0 && j == 0) {
                        char right = array[i + 1];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(true, right == 'X', true, down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    } else if (i == 0 && j == 19) {
                        char right = array[i + 1];
                        char up = map.get(j-1).toCharArray()[i];
                        try {
                            this.image(pathTile(true, right == 'X', up == 'X', true), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    } else if (i == 19 && j == 0) {
                        char left = array[i - 1];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', true, true, down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    } else if (i == 19 && j == 19) {
                        char left = array[i - 1];
                        char up = map.get(j-1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', true, up == 'X', true), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    // Handle the different rotations of the path by checking the surrounding tiles
                    } else if (i > 0 && i < 19) {
                        char left = array[i - 1];
                        char right = array[i + 1];
                        char up = map.get(j-1).toCharArray()[i];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', right == 'X', up == 'X', down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    // When tiles are on right border
                    } else if (i > 0){
                        char left = array[i - 1];
                        char up = map.get(j-1).toCharArray()[i];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(left == 'X', true, up == 'X', down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    // When tiles are on left border
                    } else {
                        char right = array[i + 1];
                        char up = map.get(j-1).toCharArray()[i];
                        char down = map.get(j+1).toCharArray()[i];
                        try {
                            this.image(pathTile(true, right == 'X', up == 'X', down == 'X'), pixels_right, row_number);
                        } catch (NullPointerException e) {
                            println("Graphics context not yet initialized. Skipping visualisations.");
                        }
                    }
                    
                } else if (array[i] == 'W') {
                    // Save coordinates of wizard house to the 2d map
                    grid[j][i] = -1;
                    goal = new int[2];
                    goal[0] = j;
                    goal[1] = i; // Set the goal on the 2d map
                    // Handle the rotation of the map, pointing towards the path
                    if (i > 0 && i < 19) {
                        char left = array[i - 1];
                        char right = array[i + 1];
                        char up;
                        if (row_number > 40) {
                            up = map.get(j-1).toCharArray()[i];
                        } else {
                            up = ' ';
                        }
                        if (right == 'X') {
                            angle = 0;
                        } else if (left == 'X') {
                            angle = 180;
                        } else if (up == 'X') {
                            angle = 270;
                        } else {
                            angle = 90;
                        }
                    } else {
                        angle = 0;
                    }
                    // If wizard house, saved the coordinates after subtractin since its 48x48
                    wizard_house_row_number = row_number - 6;
                    wizard_house_right = pixels_right - 4;
                    this.wizard_house_row_number1 = wizard_house_row_number;
                    this.wizard_house_right1 = wizard_house_right; 
                } else {
                    try{
                        this.image(this.grass, pixels_right, row_number);
                    } catch (NullPointerException e) {
                        println("Graphics context not yet initialized. Skipping visualisations.");
                    }
                }
                // Increment pixels to the right after each value in the array
                pixels_right += 32;
            }
            // Set pixels back to 0, increment vertical alignment by 32 pixels
            pixels_right = 0;
            row_number += 32;
        }
        try{
            // First render a grass underneath the wizard house
            this.image(this.grass, wizard_house_right + 4, wizard_house_row_number + 6);
            // Finally render the house, so it overlaps on top rather than under
            // Also rotate the image by specified angle found before
            this.image(rotateImageByDegrees(this.house,angle), wizard_house_right-2, wizard_house_row_number);
        } catch (NullPointerException e) {
            println("Graphics context not yet initialized. Skipping visualisations.");
        }
        // Tower follows cursor
        if (clickTower) {
            tower.follow(mouseX,mouseY);
            tower.draw(this);
        }
        // Loading placed towers
        for (Tower t: placedTowers) {
            float towerX = t.getX();
            float towerY = t.getY();
            PImage towerSprite = t.getSprite();
            try{
                this.image(towerSprite, (int) (towerX*32), (int) ((towerY*32) + 40));
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
            // Check if mouse is hovering over the tower
            if (mouseX >= towerX*32 && mouseX <= towerX*32+32 && mouseY >= towerY*32+32 && mouseY <= towerY*32+40+32) {
                try{
                    noFill();
                    stroke(255, 255, 0,200);
                    ellipse(t.getX()*32+16, t.getY()*32+40+16,t.getRange()*2,t.getRange()*2);
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
                // Check for upgrades and apply them if enough mana is available
                if (mousePressed && t.getFirst()) {
                    if (clickURange && mana.getCurrent() >= t.getRangeUpgradeCost()) {
                        mana.minusMana(t.getRangeUpgradeCost());
                        t.upgradeRange();
                    } 
                    
                    if (clickUSpeed && mana.getCurrent() >= t.getSpeedUpgradeCost()) {
                        mana.minusMana(t.getSpeedUpgradeCost());
                        t.upgradeSpeed();
                    }
                    
                    if (clickUDamage && mana.getCurrent() >= t.getDamageUpgradeCost()) {
                        mana.minusMana(t.getDamageUpgradeCost());
                        t.upgradeDamage();
                    }
                    if (t.getRangeNumber() >= 2 && t.getSpeedNumber() >= 2&& t.getDamageNumber() >= 2) {
                        t.updateSprite(tower1);
                    }
                    if (t.getRangeNumber() >= 3 && t.getSpeedNumber() >= 3 && t.getDamageNumber() >= 3) {
                        t.updateSprite(tower2);
                    }
                }
                mousePressed = false;
            }
            // Dont allow accidentally upgrading twice
            t.setFirstTrue();
            // Draw visual upgrades
            int rangeUpgrade = t.getRangeNumber()-1;
            int speedUpgrade = t.getSpeedNumber()-1;
            int damageUpgrade = t.getDamageNumber()-1;
            try{
                noFill();
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
            if (rangeUpgrade >= 1 && speedUpgrade >= 1 && damageUpgrade >= 1) {
                if (rangeUpgrade >= 2 && speedUpgrade >= 2 && damageUpgrade >= 2) {
                    rangeUpgrade -= 2;
                    speedUpgrade -= 2;
                    damageUpgrade -= 2;
                } else {
                    rangeUpgrade -= 1;
                    speedUpgrade -= 1;
                    damageUpgrade -= 1;
                }
            }
            // Continuously add visual upgrades depending how many, with the difference being the indent
            int indent = 0;
            try{
                for (int i = 0; i < speedUpgrade; i++) {
                    stroke(136 ,179 ,249);
                    rectMode(CENTER);
                    rect(towerX*32+16, towerY*32 + 40+16, 20+indent,20+indent,5);
                    indent += 1;
                    rectMode(CORNER);
                }
                indent = 0;
                for (int i = 0; i < rangeUpgrade; i++) {
                    stroke(255,0,255);
                    ellipse(towerX*32+indent, towerY*32 + 40,4,4);
                    indent += 6;
                }
                indent = 0;
                for (int i = 0; i < damageUpgrade; i++) {
                    fill(255,0,255);
                    textSize(8);
                    text("X", towerX*32-1+indent, towerY*32 + 40+32);
                    indent += 6;
                }
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
        }
        // Move the fireballs
        List<Fireball<? extends Moveable>> fireballsToRemove = new ArrayList<>();
        for (Fireball<? extends Moveable> f: fireballs) {
            if (Math.abs(f.getMagnitude()) <= 10) {
                fireballsToRemove.add(f);
            } else{
                if (!isPaused) {
                    f.tick();
                }
                try{
                    f.draw(this);
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
            }
        }
        fireballs.removeAll(fireballsToRemove);

        try{
            loadMenu();
        } catch (NullPointerException e) {
            println("Graphics context not yet initialized. Skipping visualisations.");
        }

        // Mana bar
        if (!isPaused) {
            this.mana.tickMana();
            if (fastforward) {
                this.mana.tickMana();
            }
        }

        // Handle the upgrade cost GUI
        if (clickURange || clickUSpeed || clickUDamage) {
            for (Tower t : placedTowers) {
                float floatX = t.getX();
                float floatY = t.getY();
                try{
                    if (isMouseOverTower(floatX, floatY, mouseX, mouseY)) {
                        drawUpgradeGUI(t);
                    }
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
            }
        }
        
        // This condition if all waves are completed
        if (wave_number == waveList.size()) {
            // Continue remaining enemies of previous wave
            // If all the arrays are empty, then the game is won
            if (gremlin_array.isEmpty() && beetle_array.isEmpty()&& worm_array.isEmpty()) {
                game_win = true;
                isPaused = true;
                try{
                    noStroke();
                    fill(127,255,0,40);
                    rect(0,40,640,640);
                    stroke(0);
                    fill(0,0,0);
                    textSize(50);
                    textAlign(CENTER, CENTER);
                    text("You Win!", 320,200);
                    text("Press 'r' to start over", 320,250);
                } catch (NullPointerException e) {
                    println("Graphics context not yet initialized. Skipping visualisations.");
                }
            }
            // Otherwise move all the entities again
            moveEntities(gremlin_array);
            moveEntities(beetle_array);
            moveEntities(worm_array);
            return;
        }
        // Get the current wave
        Wave current_wave = waveList.get(wave_number);
        try{    
            textSize(20);
        } catch (NullPointerException e) {
            println("Graphics context not yet initialized. Skipping visualisations.");
        }
        // If the prewave pause is not met, return
        if (time_since_last_wave < current_wave.getPreWavePause() * 60) {
            if (!isPaused) 
                time_since_last_wave += update;
            
            moveEntities(gremlin_array);
            moveEntities(beetle_array);
            moveEntities(worm_array);
            try{
                fill(0, 0, 0);
                text("Wave " + (wave_number+1) + " starts: " + (int)(current_wave.getPreWavePause()-(time_since_last_wave/60)), 20, 30);
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
            return;
        // Check if theres still waves
        // However if endless mode is activated, then the current wave will always be the last
        // And a wave will be added to the list at the end of the current wave
        } else if (wave_number != waveList.size()-1 || endless_start) {
            if (!isPaused) {
                counter += update;
            }
            try{
                fill(0, 0, 0);
                if (endless_start)
                    text("Wave " + (wave_number+2) + " starts: " + (int)(current_wave.getDuration()+current_wave.getPreWavePause()-(counter/60)), 20, 30); 
                else
                    text("Wave " + (wave_number+2) + " starts: " + (int)(current_wave.getDuration()+waveList.get(wave_number+1).getPreWavePause()-(counter/60)), 20, 30);
            } catch (NullPointerException e) {
                println("Graphics context not yet initialized. Skipping visualisations.");
            }
        }
        
        // Get the current waves monsters
        List<Monster> monster_list = current_wave.getMonsters();
        // Randomly get a monster
        int randomIndex = random.nextInt(monster_list.size());
        Monster random_Monster = monster_list.get(randomIndex);
        float newSpeed = random_Monster.getSpeed() * update;
        if (!isPaused) { 
            // Check the type of the monster, and enter the corresponding condition
            
            if ("gremlin".equals(random_Monster.getType())) {
                // Check if it has been enough time to spawn (duration / quantity)
                if (gremlin_spawned < random_Monster.getQuantity() && this.time_since_spawned >= (current_wave.getDuration() * 60) / current_wave.getTotalQuantity()) {
                    Map.Entry<Integer, Integer> randomEntry = getRandomEntry(startpos);
                    this.time_since_spawned = 0;
                    // Create a new Gremlin object
                    Gremlin gremlin = new Gremlin(0, 0, gremlin_image, random_Monster.getHP(), newSpeed, random_Monster.getArmour(), random_Monster.getManaGainedOnKill(), random_Monster.getQuantity());
                    gremlin.setSprites(gremlinSprites);
                    gremlin_spawned++;
                    // Initialize the Gremlin object based on conditions
                    initiateMonster(gremlin, randomEntry.getKey(), randomEntry.getValue(), newSpeed);
                    this.gremlin_array.add(gremlin);
                } else {
                    // If it hasn't been enough time, increment time
                    this.time_since_spawned += update;
                }
                
                // If the quantity has been met, remove the monster from the index so it will not be selected again
                if (random_Monster.getQuantity() == gremlin_spawned) {
                    monster_list.remove(randomIndex);
                }
            } else if (random_Monster.getType().equals("beetle")) {
                if (beetle_spawned < random_Monster.getQuantity() && this.time_since_spawned >= (current_wave.getDuration() * 60) /  current_wave.getTotalQuantity()) {
                    Map.Entry<Integer, Integer> randomEntry = getRandomEntry(startpos);
                    this.time_since_spawned = 0;
                    // Create a new Beetle object
                    Beetle beetle = new Beetle(0, 0, beetle_image, random_Monster.getHP(), newSpeed, random_Monster.getArmour(), random_Monster.getManaGainedOnKill(), random_Monster.getQuantity());
                    beetle_spawned++;
                    // Initialize the Beetle object based on conditions
                    initiateMonster(beetle, randomEntry.getKey(), randomEntry.getValue(), newSpeed);
                    this.beetle_array.add(beetle);
                } else {
                    this.time_since_spawned += update;
                }
                if (random_Monster.getQuantity() == beetle_spawned) {
                    monster_list.remove(randomIndex);
                }
            } else {
                if (worm_spawned < random_Monster.getQuantity() && this.time_since_spawned >= (current_wave.getDuration() * 60) /  current_wave.getTotalQuantity()) {
                    Map.Entry<Integer, Integer> randomEntry = getRandomEntry(startpos);
                    this.time_since_spawned = 0;
                    // Create a new Worm object
                    Worm worm = new Worm(0, 0, worm_image, random_Monster.getHP(), newSpeed, random_Monster.getArmour(), random_Monster.getManaGainedOnKill(), random_Monster.getQuantity());
                    worm_spawned++;
                    // Initialize the Worm object based on conditions
                    initiateMonster(worm, randomEntry.getKey(), randomEntry.getValue(), newSpeed);
                    this.worm_array.add(worm);
                } else {
                    this.time_since_spawned += update;
                }
                if (random_Monster.getQuantity() == worm_spawned) {
                    monster_list.remove(randomIndex);
                }
            }
        }
        // Move all the entities
        moveEntities(gremlin_array);
        moveEntities(beetle_array);
        moveEntities(worm_array);
        // If the monster list is empty, increment the wave number
        if (monster_list.isEmpty()) {
            wave_number++;
            // For endless mode, create copies of the first wave, except make monsters stronger
            if (endless_start) {
                int totalQuantity = 0;
                // Loop through all the monsters and make them stronger, then add to a list of the next wave
                for (Monster m : this.endlessMonsters) {
                    m.setArmour((float) (m.getArmour()*armour_multiplier));
                    m.setHP((float) (m.getHP()*hp_multiplier));
                    m.setSpeed((float) (m.getSpeed()*speed_multipler));
                    // This condition if too many monsters, make them much stronger instead
                    // the game can only spawn 1 every frame
                    if (m.getQuantity()*2 >= 32) {
                        m.setArmour((float) (m.getArmour()*0.7));
                        m.setHP((float) (m.getHP()*2));
                        totalQuantity += m.getQuantity();
                    // Otherwise, double the quantity
                    } else {
                        m.setQuantity((int) (m.getQuantity()*2));
                        totalQuantity += m.getQuantity();
                    }
                }
                // Add the modified wave to a new wave
                Wave wave = new Wave(current_wave.getDuration(), current_wave.getPreWavePause(), new ArrayList<>(endlessMonsters), totalQuantity);
                waveList.add(wave);
            }
            // Reset all variables for the next wave
            gremlin_spawned = 0;
            beetle_spawned = 0;
            worm_spawned = 0;
            time_since_last_wave = 0;
            counter = 0;
        }
    }
    /**
     * Determines the appropriate path tile image based on the given directions.
     * <p>
     * This method selects a path tile image based on the combination of boolean values representing
     * the possible directions (left, right, up, down). The method also considers the need to rotate
     * certain images to fit the desired orientation.
     * </p>
     * <p>
     * For example, if the path should go left and right but not up or down, the method might return
     * a horizontal path image. If the path should go up and down but not left or right, it might return
     * a vertical path image.
     * </p>
     *
     * @param left  Indicates if there's a path to the left.
     * @param right Indicates if there's a path to the right.
     * @param up    Indicates if there's a path upwards.
     * @param down  Indicates if there's a path downwards.
     * @return The appropriate path tile image based on the given directions.
     */
    protected PImage pathTile(boolean left, boolean right, boolean up, boolean down) {
        // Determine the path type and orientation
        if (left && right && up && down) {
            return this.path3;
        }else if (left && right && up && !down) {
            return rotateImageByDegrees(this.path2, 180);
        } else if (!left && !right) {
            return rotateImageByDegrees(this.path0, 90);
        } else if (left && !right && up && down) {
            return rotateImageByDegrees(this.path2, 90);
        } else if (!left && right && up && down) {
            return rotateImageByDegrees(this.path2, 270);
        } else if (left && !right && !up && down) {
            return this.path1;
        } else if (!left && right && !up && down) {
            return rotateImageByDegrees(this.path1, 270);
        } else if (left && !right && up && !down) {
            return rotateImageByDegrees(this.path1, 90);
        } else if (left && right && !up && down) {
            return this.path2;
        } else if (!left && right && up  && !down) {
            return rotateImageByDegrees(this.path1, 180);
        } else{
            return this.path0;
        }
    }

    /**
     * Checks if the mouse is hovering over a tower.
     *
     * @param floatX   The X-coordinate of the tower.
     * @param floatY   The Y-coordinate of the tower.
     * @param mouseX   The current X-coordinate of the mouse cursor.
     * @param mouseY   The current Y-coordinate of the mouse cursor.
     * @return true if the mouse is over the tower, false otherwise.
     */
    protected boolean isMouseOverTower(float floatX, float floatY, float mouseX, float mouseY) {
        return mouseX >= floatX * 32 && mouseX <= floatX * 32 + 32 && mouseY >= floatY * 32 + 40 && mouseY <= floatY * 32 + 40 + 32;
    }

    /**
     * Draws the upgrade GUI for a given tower.
     *
     * @param t The tower for which the upgrade GUI should be drawn.
     */
    protected void drawUpgradeGUI(Tower t) {
        int numberOfSelected = getNumberOfSelected();
        drawBackground(numberOfSelected);
        drawTexts(t, numberOfSelected);
    }

    /**
     * Calculates the number of upgrades currently selected.
     *
     * @return The number of selected upgrades.
     */
    protected int getNumberOfSelected() {
        int count = 0;
        if (clickURange) count++;
        if (clickUSpeed) count++;
        if (clickUDamage) count++;
        return count;
    }

    /**
     * Draws the background of the upgrade GUI based on the number of selected upgrades.
     *
     * @param numberOfSelected The number of upgrades currently selected.
     */
    protected void drawBackground(int numberOfSelected) {
        fill(255, 255, 255);
        rect(650, 610 - (numberOfSelected * 11), 90, 45 + (numberOfSelected * 11));
        fill(255, 255, 255);
        rect(650, 610 - (numberOfSelected * 11) + 16, 90, 45 + (numberOfSelected * 11) - 31);
    }
    
    /**
     * Draws the upgrade texts over the GUI.
     *
     * @param t                The tower for which the upgrade texts should be drawn.
     * @param numberOfSelected The number of upgrades currently selected.
     */
    protected void drawTexts(Tower t, int numberOfSelected) {
        fill(0, 0, 0);
        textSize(13);
        text("Upgrade cost", 650 + 3, 610 - (numberOfSelected * 11) + 14);
        textSize(12);
    
        int yOffset = 0;
        if (clickURange) {
            drawText("range:", (int) t.getRangeUpgradeCost(), 650 + 3, 610 - (numberOfSelected * 11) + 16 + 14 + yOffset);
            yOffset += 14;
        }
        if (clickUSpeed) {
            drawText("speed:", (int) t.getSpeedUpgradeCost(), 650 + 3, 610 - (numberOfSelected * 11) + 16 + 14 + yOffset);
            yOffset += 14;
        }
        if (clickUDamage) {
            drawText("damage:", (int) t.getDamageUpgradeCost(), 650 + 3, 610 - (numberOfSelected * 11) + 16 + 14 + yOffset);
        }
    
        drawTotalCostText(t);
    }

    /**
     * Draws the text indicating the cost of a specific upgrade.
     *
     * @param label The label of the upgrade (e.g., "range:", "speed:").
     * @param cost  The cost of the upgrade.
     * @param x     The X-coordinate where the text should be drawn.
     * @param y     The Y-coordinate where the text should be drawn.
     */
    protected void drawText(String label, int cost, int x, int y) {
        text(label, x, y);
        text(cost, x + 65, y);
    }

    /**
     * Draws the text indicating the total cost of all selected upgrades for a given tower.
     *
     * @param t The tower for which the total upgrade cost should be calculated and drawn.
     */
    protected void drawTotalCostText(Tower t) {
        text("Total:", 650 + 3, 610 + 41);
        int totalCost = 0;
        if (clickURange) totalCost += t.getRangeUpgradeCost();
        if (clickUSpeed) totalCost += t.getSpeedUpgradeCost();
        if (clickUDamage) totalCost += t.getDamageUpgradeCost();
        text(totalCost, 650 + 3 + 60, 610 + 41);
    }
    /**
     * The main method to launch the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
/**
 * Represents a pair of integer coordinates (x, y).
 * This class is used to check if a tower exists on a specific coordinate.
 */
class Pair {
    /** The x-coordinate of the pair. */
    private int x;
    /** The y-coordinate of the pair. */
    private int y;

    /**
     * Constructs a new Pair with the specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compares this pair to the specified object. The result is {@code true} if and only if
     * the argument is not {@code null} and is a {@code Pair} object that represents the same
     * x and y coordinates as this object.
     *
     * @param o The object to compare this {@code Pair} against.
     * @return {@code true} if the given object represents a {@code Pair} with the same x and y coordinates,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x && y == pair.y;
    }

    /**
     * Returns a hash code for this pair. The hash code is computed based on the x and y coordinates.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }
}
