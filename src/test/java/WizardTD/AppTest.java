package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import processing.event.MouseEvent;
import java.util.*;
import java.util.function.Supplier;

public class AppTest {

    private App app;
    private MouseEvent mockEvent;

    @BeforeEach
    public void setUp() {
        // Instantiate variables on startup
        app = new App();
        mockEvent = mock(MouseEvent.class);
        app.grid = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        app.goal = new int[2];
        app.goal[1] = 3;
        app.goal[0] = 14;
    }

    @Test
    public void testFindTile() {
        // Testing functionality of findTile() function
        String result = app.findTile(10, 10);
        assertEquals("", result); // Outside the map

        result = app.findTile(-1, 10);
        assertEquals("", result); // Outside the map

        result = app.findTile(680, 10);
        assertEquals("", result); // Outside the map

        result = app.findTile(720, 10);
        assertEquals("", result); // Outside the map

        result = app.findTile(10, -1);
        assertEquals("", result); // Outside the map

        result = app.findTile(10, 680);
        assertEquals("", result); // Outside the map

        result = app.findTile(10, 720);
        assertEquals("", result); // Outside the map

        result = app.findTile(10, 681);
        assertEquals("", result); // Outside the map

        result = app.findTile(10, 39);
        assertEquals("", result); // Outside the map

        result = app.findTile(100, -39);
        assertEquals("", result); // Outside the map

        result = app.findTile(200,230);
        assertEquals("path", result); // On a path

        result = app.findTile(0,40);
        assertEquals("shrub", result); // On shrub

        result = app.findTile(639,40);
        assertEquals("grass", result); // On grass
    }

    @Test
    public void testGetRandomEntry() {
        // Testing randomEntry() function
        // Create a sample map
        Map<Integer, List<Integer>> sampleMap = new HashMap<>();
        sampleMap.put(1, new ArrayList<>());
        sampleMap.get(1).add(10);
        sampleMap.put(2, new ArrayList<>());
        sampleMap.get(2).add(20);
        sampleMap.put(3, new ArrayList<>());
        sampleMap.get(3).add(30);

        Map.Entry<Integer, Integer> entry = App.getRandomEntry(sampleMap);
        assertNotNull(entry);
        assertTrue(sampleMap.containsKey(entry.getKey()), "Map doesn't contain random key");
        assertTrue(sampleMap.get(entry.getKey()).contains(entry.getValue()), "List for the key doesn't contain random value");
    }

    @Test
    public void testInitiateMonster() {
        // Test intiateMonster() function
        Gremlin gremlin = new Gremlin(0,0,null,100,2,0.8f,32,10);
        // Case when entry point is on left border
        app.initiateMonster(gremlin, 0, 3, 2);
        assertEquals(0 * 32 - 32, gremlin.getX(), "LeftBorder: Gremlin X position incorrect");
        assertEquals(3 * 32 + 40 + 5, gremlin.getY(), "LeftBorder: Gremlin Y position incorrect");
        assertEquals(2, gremlin.getSpeedX(), "LeftBorder: Gremlin X speed incorrect");
        assertEquals(0, gremlin.getSpeedY(), "LeftBorder: Gremlin Y speed incorrect");
        // Case when entry point is on top border
        app.initiateMonster(gremlin, 9, 0, 2);
        assertEquals(9 * 32 + 5, gremlin.getX(), "TopBorder: Gremlin X position incorrect");
        assertEquals(0 * 32 + 40 - 5, gremlin.getY(), "TopBorderGremlin Y position incorrect");
        assertEquals(0, gremlin.getSpeedX(), "TopBorderGremlin X speed incorrect");
        assertEquals(2, gremlin.getSpeedY(), "TopBorderGremlin Y speed incorrect");
        // Case when entry point is on right border
        app.initiateMonster(gremlin, 19, 5, 2);
        assertEquals(19 * 32 + 32, gremlin.getX(), "RightBorder: Gremlin X position incorrect");
        assertEquals(5 * 32 + 40 + 6, gremlin.getY(), "RightBorder: Gremlin Y position incorrect");
        assertEquals(-2, gremlin.getSpeedX(), "RightBorder: Gremlin X speed incorrect");
        assertEquals(0, gremlin.getSpeedY(), "RightBorder: Gremlin Y speed incorrect");
        // Case when entry point is on bottom border
        app.initiateMonster(gremlin, 7, 19, 2);
        assertEquals(7 * 32, gremlin.getX(), "BottomBorder: Gremlin X position incorrect");
        assertEquals(19 * 32 + 40 +5, gremlin.getY(), "BottomBorder: Gremlin Y position incorrect");
        assertEquals(0, gremlin.getSpeedX(), "BottomBorder: Gremlin X speed incorrect");
        assertEquals(-2, gremlin.getSpeedY(), "BottomBorder: Gremlin Y speed incorrect");
    }
    
    @Test
    public void testMoveEntities() {
        // Test moveEntities function, called once every frame
        // Create a sketch so processing images are not skipped
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.grid = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        app.goal = new int[2];
        app.goal[1] = 3;
        app.goal[0] = 14;
        
        // Mana
        app.mana = new Mana(200,1000,2,100,150,1.5f,1.1f);

        List<Gremlin> gremlin_array = new ArrayList<>();
        List<Beetle> beetle_array = new ArrayList<>();
        List<Worm> worm_array = new ArrayList<>();

        // Test empty array
        gremlin_array.add(null);
        app.moveEntities(gremlin_array);
        assertNull(gremlin_array.get(0));
        gremlin_array.remove(null);

        // Tower list
        Tower tower = new Tower(0,0,null,100,2f,1,100);
        Tower towerTooFar = new Tower(1000,1000,null,100,2f,1000,100);
        Tower instaKill = new Tower(0,0,null,100,2f,1000,100);
        app.placedTowers.add(tower);
        app.placedTowers.add(towerTooFar);
        app.placedTowers.add(instaKill);

        // Create path
        Path pathFinder = new Path();
        Path.Node start = pathFinder.new Node(0, 3);
        Path.Node goal = pathFinder.new Node(3, 14);
        List<Path.Node> path = pathFinder.findPath(app.grid, start, goal);

        // Create classes
        Beetle beetle = new Beetle(0,0,null,100,2,0.8f,32,10);
        Worm worm = new Worm(0,0,null,100,2,0.8f,32,10);
        Gremlin gremlin = new Gremlin(0,0,null,100,2,0.8f,32,10);

        beetle.setPath(path);
        worm.setPath(path);
        gremlin.setPath(path);

        beetle_array.add(beetle);
        worm_array.add(worm);
        gremlin_array.add(gremlin);

        app.moveEntities(beetle_array);
        app.moveEntities(worm_array);
        app.moveEntities(gremlin_array);
        assertEquals(Math.round(2/60), Math.round(gremlin.getSpeedX()), "Gremlin should've moved 2/60 spaces due to its path");
        assertEquals(Math.round(2/60), Math.round(worm.getSpeedX()), "worm should've moved 2/60 spaces due to its path");
        assertEquals(Math.round(2/60), Math.round(beetle.getSpeedX()), "beetle should've moved 2/60 spaces due to its path");

        // Tick the tower timer, so now can shoot
        for (int i = 0; i < 60; i++) {
            tower.tick();
            towerTooFar.tick();
        }

        // Gremlin is shot with 1 damage
        for (int i = 0; i < 10; i++) {
            app.moveEntities(gremlin_array);
        }
        app.moveEntities(gremlin_array);
        assertEquals((float) (100-(1*0.8)), gremlin.getCurrentHealth(), "Gremlin takes 1 damage from tower hit");
        assertEquals(0,tower.getTimeSince(), "Time should be 0 after shot");
        assertNotEquals(0, towerTooFar.getTimeSince(), "Tower should not have shot since its too far");
        app.placedTowers.remove(tower);
        app.placedTowers.remove(towerTooFar);

        // Tick the instakill tower
        for (int i = 0; i < 60; i++) {
            instaKill.tick();
        }
        // Move gremlin so it gets hit with 1000 damage
        gremlin.set(0,0);
        for (int i = 0; i < 60; i++) {
            app.moveEntities(gremlin_array);
        }
        assertEquals(0,instaKill.getTimeSince(), "Time should be 0 after shot");
        assertEquals(200+32,app.mana.getCurrent(), "Mana is not equal after killing enemy");

        // Gremlin finished death animation
        for (int i = 0; i < 20; i++) {
            gremlin.isDying();
        }

        app.moveEntities(gremlin_array);
        assertTrue(gremlin_array.isEmpty(), "Gremlin is dead, array should be empty");
        
        // Tick tower
        for (int i = 0; i < 60; i++) 
            instaKill.tick();
        app.moveEntities(beetle_array);
        assertEquals(200+32,app.mana.getCurrent(), "Mana is not equal after killing enemy");
        // Beetle is shot with 1000 damage
        for (int i = 0; i < 19; i++) {
            app.moveEntities(beetle_array);
        }
        assertEquals(200+32+32,app.mana.getCurrent(), "Mana is not equal after killing enemy");
        beetle_array.remove(beetle);

        // Tick tower
        for (int i = 0; i < 60; i++)
            instaKill.tick();
        app.moveEntities(worm_array);
        assertEquals(200+32+32,app.mana.getCurrent(), "Mana is not equal after killing enemy");
        // Worm is shot with 1000 damage
        for (int i = 0; i < 60; i++)
            app.moveEntities(worm_array);
        assertEquals(200+32+32+32,app.mana.getCurrent(), "Mana is not equal after killing enemy");

        // Test manage high speed feature
        gremlin = new Gremlin(0,0,null,100,150,0.8f,32,10);
        gremlin.setPath(path);
        int original_length = gremlin.getPath().size();
        gremlin_array.add(gremlin);
        app.moveEntities(gremlin_array);
        gremlin_array.remove(gremlin);
        assertEquals(original_length - 1,gremlin.getPath().size(), "Gremlin should've removed one instruction");

        // Gremlin reaches wizard house
        app.wizard_house_right1 = 3*32;
        app.wizard_house_row_number1 = 14*32+40;
        gremlin = new Gremlin(3*32,14*32+40,null,3,2000,0.8f,32,10);
        gremlin.setPath(path);
        gremlin.savePath(path);
        gremlin_array.add(gremlin);
        app.moveEntities(gremlin_array);

        // Test mana
        assertEquals(200+32+32+32-3,app.mana.getCurrent(), "Mana should be subtracted when enemy banished");
        // Test losing
        gremlin = new Gremlin(3*32,14*32+40,null,300,2000,0.8f,32,10);
        gremlin.setPath(path);
        gremlin.savePath(path);
        gremlin_array.add(gremlin);
        app.moveEntities(gremlin_array);
        assertTrue(app.game_over, "Game should be over when gremlin is banished with more hp than mana");
    }

    @Test
    public void testRotateBy90Degrees() {
        // Test rotated function doesn't change any other attributes
        PImage testImage = new PImage(100, 100); // Assume a 100x100 image for simplicity

        PImage result = app.rotateImageByDegrees(testImage, 90);

        // Assert the dimensions after a 90 degree rotation.
        assertEquals(100, result.width);
        assertEquals(100, result.height);
    }

    @Test
    public void testRotateBy45Degrees() {
        // Test rotated function doesn't change any other attributes
        PImage testImage = new PImage(100, 100);

        PImage result = app.rotateImageByDegrees(testImage, 45);
        assertTrue(result.width > 100 && result.width < 142); // Pythagoras
        assertTrue(result.height > 100 && result.height < 142);
    }

    //
    // Testing the isMouseOverTower function
    //

    @Test
    public void testIsMouseOverTower_insideBounds() {
        // Test if mouse is over tower
        assertTrue(app.isMouseOverTower(1, 1, 34, 74), "Mouse is over tower");
    }

    @Test
    public void testIsMouseOverTower_leftOfBounds() {
        // Test if mouse is not over tower
        assertFalse(app.isMouseOverTower(1, 1, 31, 74), "Mouse is to the left out of bounds");
    }

    @Test
    public void testIsMouseOverTower_rightOfBounds() {
        // Test if mouse is not over tower
        assertFalse(app.isMouseOverTower(1, 1, 65, 74), "Mouse is to the right out of bounds");
    }

    @Test
    public void testIsMouseOverTower_aboveBounds() {
        // Test if mouse is not over tower
        assertFalse(app.isMouseOverTower(1, 1, 34, 69), "Mouse is on top out of bounds");
    }

    @Test
    public void testIsMouseOverTower_belowBounds() {
        // Test if mouse is not over tower
        assertFalse(app.isMouseOverTower(1, 1, 34, 105), "Mouse is bottom out of bounds");
    }

    //
    // Testing GUI function
    //

    @Test
    public void testGetNumberOfSelected_allFalse() {
        // Test function on none that are true
        app.clickURange = false;
        app.clickUSpeed = false;
        app.clickUDamage = false;

        assertEquals(0, app.getNumberOfSelected(), "All false");
    }

    @Test
    public void testGetNumberOfSelected_onlyRangeTrue() {
        // Test function on one true
        app.clickURange = true;
        app.clickUSpeed = false;
        app.clickUDamage = false;

        assertEquals(1, app.getNumberOfSelected(), "Range true");
    }

    @Test
    public void testGetNumberOfSelected_onlySpeedTrue() {
        // Test function on one true
        app.clickURange = false;
        app.clickUSpeed = true;
        app.clickUDamage = false;

        assertEquals(1, app.getNumberOfSelected(), "Speed true");
    }

    @Test
    public void testGetNumberOfSelected_onlyDamageTrue() {
        // Test function on one true
        app.clickURange = false;
        app.clickUSpeed = false;
        app.clickUDamage = true;

        assertEquals(1, app.getNumberOfSelected(), "Damage true");
    }

    @Test
    public void testGetNumberOfSelected_allTrue() {
        // Test function on all true
        app.clickURange = true;
        app.clickUSpeed = true;
        app.clickUDamage = true;

        assertEquals(3, app.getNumberOfSelected(), "All true");
    }

    //
    // Testing mouse moved with Mockito
    // All testcases call testHoverCondition, except main menu buttons as their sizes are different
    //

    @Test
    public void testMouseMoved() {
        // Different tests of each button, calling the functions below
        testHoverCondition(app, 655, 55, () -> app.hoverSpeed);
        testHoverCondition(app, 655, 110, () -> app.hoverPause);
        testHoverCondition(app, 655, 165, () -> app.hoverTower);
        testHoverCondition(app, 655, 220, () -> app.hoverURange);
        testHoverCondition(app, 655, 275, () -> app.hoverUSpeed);
        testHoverCondition(app, 655, 330, () -> app.hoverUDamage);
        testHoverCondition(app, 655, 385, () -> app.hoverPool);
        testMainMenuButtons(app, 380-110, 450-25+5, () -> app.hoverNormalStart);
        testMainMenuButtons(app, 380-110, 550-25+5, () -> app.hoverEndlessStart);
    }

    private void testHoverCondition(App app, int x, int y, Supplier<Boolean> hoverConditionGetter) {
        // Assortment of tests related to the button
        triggerMouseMoved(app, x, y);
        assertTrue(hoverConditionGetter.get());
        triggerMouseMoved(app, x-15, y);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x+45, y);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x, y-15);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x, y+45);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x-15, y-15);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x+45, y+45);
        assertFalse(hoverConditionGetter.get());
    }

    private void testMainMenuButtons(App app, int x, int y, Supplier<Boolean> hoverConditionGetter) {
        // Assortment of tests related to the button
        triggerMouseMoved(app, x, y);
        assertTrue(hoverConditionGetter.get());
        triggerMouseMoved(app, x-15, y);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x+230, y);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x, y-50);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x, y+50);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x-15, y-15);
        assertFalse(hoverConditionGetter.get());
        triggerMouseMoved(app, x+45, y+50);
        assertFalse(hoverConditionGetter.get());
    }

    // Function to move the mouse
    private void triggerMouseMoved(App app, int x, int y) {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getX()).thenReturn(x);
        when(event.getY()).thenReturn(y);
        app.mouseMoved(event);
    }

    //
    // Test keypressed function
    //

    @Test
    public void testKeyPressR_GameOver() {
        // Test game is reset when r is pressed on lose screen
        app.game_over = true;
        mockKeyCode(82); // R
        app.keyPressed();
        assertFalse(app.game_over);
        assertTrue(app.placedTowers.isEmpty());
    }

    @Test
    public void testKeyPressR_GameWin() {
        // Test game is reset when r is pressed on win screen
        app.game_win = true;
        mockKeyCode(82); // R
        app.keyPressed();
        assertFalse(app.game_win);
        assertTrue(app.placedTowers.isEmpty());
    }

    @Test
    public void testKeyPressRandom_GameWin() {
        // Test random key press on game win screen does nothing
        app.game_win = true;
        // Save initial states
        boolean initialIsPaused = app.isPaused;
        boolean initialFastforward = app.fastforward;
        boolean initialClickTower = app.clickTower;
        boolean initialClickURange = app.clickURange;
        boolean initialClickUSpeed = app.clickUSpeed;
        boolean initialClickUDamage = app.clickUDamage;
    
        mockKeyCode(1); // Random key
        app.keyPressed();
    
        // Assert that no changes occurred
        assertEquals(initialIsPaused, app.isPaused);
        assertEquals(initialFastforward, app.fastforward);
        assertEquals(initialClickTower, app.clickTower);
        assertEquals(initialClickURange, app.clickURange);
        assertEquals(initialClickUSpeed, app.clickUSpeed);
        assertEquals(initialClickUDamage, app.clickUDamage);
    }

    @Test
    public void testKeyPressP() {
        // Test when p button is pressed on keyboard
        app.isPaused = false;
        mockKeyCode(80); // P
        app.keyPressed();
        assertTrue(app.isPaused);
        // Further test
        mockKeyCode(70); // F
        app.keyPressed();
        assertTrue(app.fastforward);
        // Toggle again
        mockKeyCode(80); // P
        app.keyPressed();
        assertFalse(app.isPaused);
    }

    @Test
    public void testKeyPressF_Fastforward() {
        // Test when f is pressed on keyboard
        app.fastforward = false;
        mockKeyCode(70); // F
        app.keyPressed();
        assertTrue(app.fastforward);

        app.fastforward = true;
        mockKeyCode(70); // F
        app.keyPressed();
        assertFalse(app.fastforward);
    }

    @Test
    public void testKeyPressF_NormalSpeed() {
        // Test the button is toggled on and off
        app.fastforward = true;
        mockKeyCode(70); // F
        app.gremlin_array = new ArrayList<>();
        app.gremlin_array.add(new Gremlin(0,0,null,0,0,0,0,0));
        app.beetle_array = new ArrayList<>();
        app.beetle_array.add(new Beetle(0,0,null,0,0,0,0,0));
        app.worm_array = new ArrayList<>();
        app.worm_array.add(new Worm(0,0,null,0,0,0,0,0));
        app.fireballs = new ArrayList<>();
        app.fireballs.add(new Fireball<>(null,0,null,0));
        app.keyPressed();
        assertFalse(app.fastforward);

        mockKeyCode(70); // F
        app.keyPressed();
        assertTrue(app.fastforward);
    }

    @Test
    public void testKeyPressT() {
        // Test tower is selected when t is pressed
        app.clickTower = false;
        mockKeyCode(84); // T
        app.keyPressed();
        assertTrue(app.clickTower);

        mockKeyCode(84); // T
        app.keyPressed();
        assertFalse(app.clickTower);
    }

    @Test
    public void testKeyPress1() {
        // Test when 1 is pressed, upgrade range is toggled
        app.clickURange = false;
        mockKeyCode(49); // 1
        app.keyPressed();
        assertTrue(app.clickURange);

        mockKeyCode(49); // 1
        app.keyPressed();
        assertFalse(app.clickURange);
    }

    @Test
    public void testKeyPress2() {
        // Test when 2 is pressed, upgrade speed is toggled
        app.clickUSpeed = false;
        mockKeyCode(50); // 2
        app.keyPressed();
        assertTrue(app.clickUSpeed);

        mockKeyCode(50); // 2
        app.keyPressed();
        assertFalse(app.clickUSpeed);
    }

    @Test
    public void testKeyPress3() {
        // Test when 3 is pressed, upgrade damage is toggled
        app.clickUDamage = false;
        mockKeyCode(51); // 3
        app.keyPressed();
        assertTrue(app.clickUDamage);

        mockKeyCode(51); // 3
        app.keyPressed();
        assertFalse(app.clickUDamage);
    }

    @Test
    public void testKeyPressM_CastSpell() {
        // Test mana pool is casted
        app.mana = mock(Mana.class);
        when(app.mana.getCurrent()).thenReturn(100f);
        when(app.mana.getPool()).thenReturn(50f);
        mockKeyCode(77); // M
        app.keyPressed();
        verify(app.mana).castSpell();
    }

    @Test
    public void testKeyPressM_NotEnoughMana() {
        // Test trying to click mana pool without enough mana
        app.mana = mock(Mana.class);
        when(app.mana.getCurrent()).thenReturn(40f);
        when(app.mana.getPool()).thenReturn(50f);
        mockKeyCode(77); // M
        app.keyPressed();
        verify(app.mana, times(0)).castSpell();
    }

    @Test
    public void testRandomKey() {
        // Test nothing happens when random key is pressed not in the specified keybinds
        app.mana = mock(Mana.class);
        
        // Save initial states
        boolean initialIsPaused = app.isPaused;
        boolean initialFastforward = app.fastforward;
        boolean initialClickTower = app.clickTower;
        boolean initialClickURange = app.clickURange;
        boolean initialClickUSpeed = app.clickUSpeed;
        boolean initialClickUDamage = app.clickUDamage;
    
        mockKeyCode(1); // Random key
        app.keyPressed();
    
        // Assert that no changes occurred
        assertEquals(initialIsPaused, app.isPaused);
        assertEquals(initialFastforward, app.fastforward);
        assertEquals(initialClickTower, app.clickTower);
        assertEquals(initialClickURange, app.clickURange);
        assertEquals(initialClickUSpeed, app.clickUSpeed);
        assertEquals(initialClickUDamage, app.clickUDamage);
        verify(app.mana, times(0)).castSpell();
    }

    public void mockKeyCode(int code) {
        app.keyCode = code;
    }


    //
    // Testing mousePressed
    //

    @Test
    public void testHoverNormalStart() {
        // Test normal start button click
        app.hoverNormalStart = true;
        app.mousePressed(mockEvent);
        assertTrue(app.game_start);
    }

    @Test
    public void testHoverEndlessStart() {
        // Test endless start button click
        app.hoverEndlessStart = true;
        app.mousePressed(mockEvent);
        assertTrue(app.endless_start);
    }

    @Test
    public void testDisableKeybindsIfGameNotStarted() {
        // Test keybinds are disabled before game starts
        app.game_start = false;
        app.endless_start = false;
        app.hoverPause = true;
        
        app.mousePressed(mockEvent);
        
        assertFalse(app.isPaused);
    }

    @Test
    public void testGameOverOrGameWin() {
        app.game_start = true;

        // Testing returns without changing anything
        // Test if pause button is still active even when clicking pause
        app.game_over = true;
        app.game_win = true;
        app.hoverPause = true;
        app.mousePressed(mockEvent);
        assertFalse(app.isPaused);

        app.game_over = false;
        app.game_win = true;
        app.hoverPause = true;
        app.mousePressed(mockEvent);
        assertFalse(app.isPaused);

        app.game_over = true;
        app.game_win = false;
        app.hoverPause = true;
        app.mousePressed(mockEvent);
        assertFalse(app.isPaused);

        // Since both are false, pause button should work
        app.game_over = false;
        app.game_win = false;
        app.hoverPause = true;
        app.mousePressed(mockEvent);
        assertTrue(app.isPaused);
    }

    @Test
    public void testPause() {
        // Test when pause is enabled
        app.game_start = true; 
        app.hoverPause = true;
        
        app.mousePressed(mockEvent);
        assertTrue(app.isPaused);

        app.mousePressed(mockEvent);
        assertFalse(app.isPaused);
    }

    @Test
    public void testFastforward() {
        // Test when fastforward is enabled
        app.game_start = true;
        app.hoverSpeed = true;
        
        app.gremlin_array = new ArrayList<>();
        app.gremlin_array.add(new Gremlin(0,0,null,0,0,0,0,0));
        app.beetle_array = new ArrayList<>();
        app.beetle_array.add(new Beetle(0,0,null,0,0,0,0,0));
        app.worm_array = new ArrayList<>();
        app.worm_array.add(new Worm(0,0,null,0,0,0,0,0));
        app.fireballs = new ArrayList<>();
        app.fireballs.add(new Fireball<>(null,0,null,0));

        app.mousePressed(mockEvent);
        assertTrue(app.fastforward);

        app.mousePressed(mockEvent);
        assertFalse(app.fastforward);
    }

    @Test
    public void testTowerPlacement() {
        // Test different scenarios when tower is trying to be placed
        app = spy(new App());
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        // Check when tower button is pressed
        app.game_start = true;
        app.hoverTower = true;
        app.clickTower = false;
        app.mousePressed(mockEvent);
        assertTrue(app.clickTower);
        // Check when tower is already selected, then pressed again
        app.game_start = true;
        app.hoverTower = true;
        app.clickTower = true;
        app.mousePressed(mockEvent);
        assertFalse(app.clickTower);
        // Check placing tower with no upgrades
        app.hoverTower = true;
        app.clickTower = false;
        app.tower.setFirstTrue();
        app.mana = mock(Mana.class);
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(100f);
        when(app.findTile(639, 40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 1);
        // Check placing tower on same place
        app.hoverTower = true;
        app.clickTower = false;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(100f);
        when(app.findTile(639, 40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 1);
        // Check placing tower on non grass object
        app.hoverTower = true;
        app.clickTower = false;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(1000f);
        when(app.findTile(639, 40)).thenReturn("shrub");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 1);
        // Check placing tower with not enough mana
        app.hoverTower = true;
        app.clickTower = false;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(80f);
        when(app.findTile(639, 40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 1);

        // Check placing tower with all upgrades 
        app.game_start = true;
        app.hoverTower = true;
        app.clickTower = false;
        app.clickUDamage = true;
        app.clickURange = true;
        app.clickUSpeed = true;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        app.mana = mock(Mana.class);
        // Try when mana is not enough
        when(mockEvent.getX()).thenReturn(0);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(100f);
        when(app.findTile(0,40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 2);
        // Then try with lots of mana
        app.game_start = true;
        app.hoverTower = true;
        app.clickTower = false;
        app.clickUDamage = true;
        app.clickURange = true;
        app.clickUSpeed = true;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        when(mockEvent.getX()).thenReturn(0);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(1000f);
        when(app.findTile(630,30)).thenReturn("grass");
        app.pair = new ArrayList<>();
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 3);
        // Try when user clicks a tower that is already placed
        app.game_start = true;
        app.hoverTower = true;
        app.clickTower = false;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirst();
        when(mockEvent.getX()).thenReturn(0);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(1000f);
        when(app.findTile(630,30)).thenReturn("grass");
        app.pair = new ArrayList<>();
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 3);
        // Test place tower with 1 upgrades
        app.hoverTower = true;
        app.clickTower = false;
        app.clickUDamage = false;
        app.clickUSpeed = false;
        app.clickURange = true;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        app.pair = new ArrayList<>();
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(1000f);
        when(app.findTile(639, 40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 4);
        // Test place tower with 2 upgrades
        app.hoverTower = true;
        app.clickTower = false;
        app.clickUDamage = false;
        app.clickUSpeed = true;
        app.clickURange = true;
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirstTrue();
        app.pair = new ArrayList<>();
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        when(app.mana.getCurrent()).thenReturn(1000f);
        when(app.findTile(639, 40)).thenReturn("grass");
        app.mousePressed(mockEvent);
        assertTrue(app.placedTowers.size() == 5);
    }

    @Test
    public void testManaPoolWithEnoughMana() {
        // Test when mana pool is clicked with enough mana
        app.game_start = true;
        app.hoverPool = true;
        app.mana = mock(Mana.class);
        
        when(app.mana.getCurrent()).thenReturn(100f);
        when(app.mana.getPool()).thenReturn(50f);
        
        app.mousePressed(mockEvent);
        
        verify(app.mana).castSpell();
    }

    @Test
    public void testManaPoolWithInsufficientMana() {
        // Test when mana pool is clicked with not enough mana
        app.game_start = true;
        app.hoverPool = true;
        app.mana = mock(Mana.class);
        
        when(app.mana.getCurrent()).thenReturn(30f);
        when(app.mana.getPool()).thenReturn(50f);
        
        app.mousePressed(mockEvent);
        
        verify(app.mana, times(0)).castSpell();
    }

    @Test
    public void testHoverURange() {
        // Test when mouse is hovering over upgrade range button
        app = spy(new App());
        app.game_start = true;
        app.hoverURange = true;
        app.clickURange = false;
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirst();
        app.mousePressed(mockEvent);
        assertTrue(app.clickURange);

        app.mousePressed(mockEvent);
        assertFalse(app.clickURange);
    }
    
    @Test
    public void testHoverUSpeed() {
        // Test when mouse is hovering over upgrade speed button
        app = spy(new App());
        app.game_start = true;
        app.hoverUSpeed = true;
        app.clickUSpeed = false;
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirst();
        app.mousePressed(mockEvent);
        assertTrue(app.clickUSpeed);

        app.mousePressed(mockEvent);
        assertFalse(app.clickUSpeed);
    }
    
    @Test
    public void testHoverUDamage() {
        // Test when mouse is hovering over upgrade damage button
        app = spy(new App());
        app.game_start = true;
        app.hoverUDamage = true;
        app.clickUDamage = false;
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(639);
        when(mockEvent.getY()).thenReturn(40);
        app.tower = new Tower(0, 40, null, 100, 2f, 1, 100);
        app.tower.setFirst();
        app.mousePressed(mockEvent);
        assertTrue(app.clickUDamage);

        app.mousePressed(mockEvent);
        assertFalse(app.clickUDamage);
    }
}
