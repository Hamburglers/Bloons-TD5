package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PImage;

public class AppDrawTest {
    private App app;

    @BeforeEach
    public void setUp() throws InterruptedException {
        // Create the board that would've been created during setup
        app = new App();
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
        app.mana = new Mana(1000f,1000f,2f,100f,100f,1.1f,1.1f);

        // Add one tower
        Tower tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.setFirstTrue();
        app.placedTowers.add(tower);

        // Initialise all variables
        app.monsterList = new ArrayList<>();
        Wave wave = new Wave(10f,10f,app.monsterList,10);
        app.waveList = new ArrayList<>();
        app.waveList.add(wave);
        app.counter = 0;
        app.wave_number = 0;
        app.game_start = true;
        app.mouseX = 0;
        app.mouseY = 32;
        app.mousePressed = true;
        app.clickUDamage = true;
        app.clickURange = true;
        app.clickUSpeed = true;
        app.startpos.put(0, new ArrayList<>());
        app.startpos.get(0).add(3);
        PImage image = new PImage();
        app.gremlinSprites = new PImage[1];
        app.gremlinSprites[0] = image;
        app.map = new ArrayList<>();
        app.map = Arrays.asList(
            "XXX     WX        WX",
            "XSS  WX  X A     SSX",
            "        SX    XX SSX",
            "XXXXX    X   SS   X ",
            "    X X  X      X SS",
            "W  XXXXXXXXXXXXXXXXX",
            "X     X     W   X  W",
            "XX    XS    W  XXS  ",
            "  XX   X  XXXXXXXW X",
            "XXX    XXWX       XX",
            "X    X    X    X  SS",
            "  X  XX   X X XX  XX",
            " XXX X    X    X X X",
            "X         XS    XXX ",
            "   WXXXXXXX  XX    X",
            "  X    X     X XX   ",
            " XX    X  X X   X   ",
            "W XXX  X XX XX X X  ",
            "X  X   X  X X XX XX ",
            "X     WXX        S X"
        );
    }

    @Test
    public void testBaseDraw() {
        // Test time is not passed prewave pause
        // Should just return
        app.draw();
        assertTrue(app.wave_number == 0);
        assertTrue(app.time_since_last_wave == 1);
        assertTrue(app.placedTowers.size() == 1);
        assertTrue(app.gremlin_array.isEmpty());
        assertTrue(app.beetle_array.isEmpty());
        assertTrue(app.worm_array.isEmpty());
        // Just because arrays are empty, game shouldn't be won due to wave number not the last
        assertTrue(app.game_win == false);
        // Tower is upgraded on all branches
        assertTrue(app.placedTowers.get(0).getRangeNumber() == 2);
        assertTrue(app.placedTowers.get(0).getSpeedNumber() == 2);
        assertTrue(app.placedTowers.get(0).getDamageNumber() == 2);
        // Check if it read map correctly
        assertTrue(app.map.size() == 20);
        // 7 start positions
        assertTrue(app.startpos.size() == 7);
        assertTrue(app.startpos.containsKey(0));
        assertTrue(app.startpos.containsKey(19));
        assertTrue(app.startpos.containsKey(9));
    }

    @Test
    public void testUpgradedTowerDraw() {
        // Test 1 upgraded tower
        Tower tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirstTrue();
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be level 3 now
        assertTrue(app.placedTowers.get(0).getRangeNumber() == 3);
        assertTrue(app.placedTowers.get(0).getSpeedNumber() == 3);
        assertTrue(app.placedTowers.get(0).getDamageNumber() == 3);
    }

    @Test
    public void testDifferentTowerList_Level1() {
        // Test tower upgraded max to level 1
        Tower tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirstTrue();
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirstTrue();
        app.placedTowers.add(tower);
        tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeRange();
        tower.setFirstTrue();
        app.placedTowers.add(tower);
        app.draw();
        // 3 towers of varying levels of upgrades
        assertTrue(app.placedTowers.size() == 3);
    }

    @Test
    public void testDifferentTowerList_Level2() {
        // Test tower upgraded max to level 2
        app.mousePressed = true;
        Tower tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirst();
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirstTrue();
        app.placedTowers.add(tower);
        tower = new Tower(0,0,null,100f,1f,100f,100f);
        tower.upgradeRange();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.setFirstTrue();
        app.placedTowers.add(tower);
        tower.setFirstTrue();
        app.draw();
        // 3 towers of varying levels of upgrades
        assertTrue(app.placedTowers.size() == 3);
    }

    @Test
    public void testUpdateSpriteBranch1() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(2);
        when(tower.getSpeedNumber()).thenReturn(2);
        when(tower.getDamageNumber()).thenReturn(2);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be called once as all level 2
        verify(tower, times(1)).updateSprite(null);
    }

    @Test
    public void testUpdateSpriteBranch1_1() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(2);
        when(tower.getSpeedNumber()).thenReturn(1);
        when(tower.getDamageNumber()).thenReturn(2);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should not be called as not all is level 2
        verify(tower, times(0)).updateSprite(null);
    }

    @Test
    public void testUpdateSpriteBranch1_2() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        tower.setFirstTrue();
        when(tower.getRangeNumber()).thenReturn(2);
        when(tower.getSpeedNumber()).thenReturn(2);
        when(tower.getDamageNumber()).thenReturn(1);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        verify(tower, times(0)).updateSprite(null);
    }
    
    @Test
    public void testUpdateSpriteBranch1_3() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(1);
        when(tower.getSpeedNumber()).thenReturn(2);
        when(tower.getDamageNumber()).thenReturn(2);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should not be called as not all is level 2
        verify(tower, times(0)).updateSprite(null);
    }

    @Test
    public void testUpdateSpriteBranch2() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(3);
        when(tower.getSpeedNumber()).thenReturn(3);
        when(tower.getDamageNumber()).thenReturn(3);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be called twice, once for all level 2, once for level 3
        verify(tower, times(2)).updateSprite(null);
    }
    

    @Test
    public void testUpdateSpriteBranch2_1() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(3);
        when(tower.getSpeedNumber()).thenReturn(2);
        when(tower.getDamageNumber()).thenReturn(3);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be called once for all level 2
        verify(tower, times(1)).updateSprite(null);
    }
    
    @Test
    public void testUpdateSpriteBranch2_2() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(3);
        when(tower.getSpeedNumber()).thenReturn(3);
        when(tower.getDamageNumber()).thenReturn(2);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be called once for all level 2
        verify(tower, times(1)).updateSprite(null);
    }

    @Test
    public void testUpdateSpriteBranch2_3() {
        // Test sprite has been updated correctly
        app.mousePressed = true;
        app.clickUDamage = false;
        app.clickURange = false;
        app.clickUSpeed = false;
        Tower tower = mock(Tower.class);
        tower.setFirstTrue();
        when(tower.getX()).thenReturn(0f);
        when(tower.getY()).thenReturn(0f);
        when(tower.getFirst()).thenReturn(true);
        when(tower.getRangeNumber()).thenReturn(2);
        when(tower.getSpeedNumber()).thenReturn(3);
        when(tower.getDamageNumber()).thenReturn(3);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Should be called once for all level 2
        verify(tower, times(1)).updateSprite(null);
    }

    @Test
    public void testUpgradedTowerDraw_ButNoMana() {
        // Test 1 upgraded tower
        Tower tower = new Tower(0,0,null,100f,1f,100f,100f);
        app.mana = new Mana(10f,100f,10f,10f,10f,10f,10f);
        tower.setFirstTrue();
        app.mouseX = 0;
        app.mouseY = 32;
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.draw();
        // Tower shouldn't be upgraded as no mana
        assertEquals(10f, (int) app.mana.getCurrent());
        assertTrue(app.placedTowers.get(0).getRangeNumber() == 1);
        assertTrue(app.placedTowers.get(0).getSpeedNumber() == 1);
        assertTrue(app.placedTowers.get(0).getDamageNumber() == 1);
    }

    @Test
    public void testGremlinTimePassedPreWavePause() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(gremlin);
        Fireball<Moveable> ball = new Fireball<>(null, 5, gremlin, 100f);
        app.fireballs.add(ball);
        app.draw();
        // 1 fireball in arraylist
        assertTrue(app.fireballs.size() == 1);
        // Only gremlin spawned 1
        assertTrue(app.gremlin_spawned == 1);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testFireballGetsRemoved() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(gremlin);
        @SuppressWarnings("unchecked")
        Fireball<Moveable> ball = mock(Fireball.class);
        app.fireballs.add(ball);
        when(ball.getMagnitude()).thenReturn(5f);
        app.draw();
        // Fireball is removed as it is within the threshold of an enemy to be removed
        assertTrue(app.fireballs.isEmpty());
    }

    @Test
    public void testGremlinSpawnReached() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,0);
        app.monsterList.add(gremlin);
        app.draw();
        // No gremlins should be spawned as cap is reached
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testBeetle() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Beetle beetle = new Beetle(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(beetle);
        app.draw();
        // Only beetle is spawned
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 1);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testBeetleSpawnReached() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Beetle beetle = new Beetle(0,3,null,100f,1f,0.9f,100,0);
        app.monsterList.add(beetle);
        app.draw();
        // Nothing is spawned
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testBeetle_timeSinceSpawnNotReached() {
        // Test time is passed prewave pause
        app.time_since_spawned = 0;
        app.time_since_last_wave = 1000;
        Beetle beetle = new Beetle(0,3,null,100f,1f,0.9f,100,1);
        app.monsterList.add(beetle);
        app.draw();
        // Nothing is spawned as time is not reached
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testWorm() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Worm worm = new Worm(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(worm);
        app.draw();
        // Only worm is spawned
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 1);
    }

    @Test
    public void testWormSpawnReached() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Worm worm = new Worm(0,3,null,100f,1f,0.9f,100,0);
        app.monsterList.add(worm);
        app.draw();
        // Nothing is spawned as cap is reached
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }
    
    @Test
    public void testWorm_timeSinceSpawnNotReached() {
        // Test time is passed prewave pause
        app.time_since_spawned = 0;
        app.time_since_last_wave = 1000;
        Worm worm = new Worm(0,3,null,100f,1f,0.9f,100,1);
        app.monsterList.add(worm);
        app.draw();
        // Nothing is spawned as time is not reached
        assertTrue(app.gremlin_spawned == 0);
        assertTrue(app.beetle_spawned == 0);
        assertTrue(app.worm_spawned == 0);
    }

    @Test
    public void testEmptyMonsterList() {
        // Test time is passed prewave pause
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,1);
        app.monsterList.add(gremlin);
        app.draw();
        // List is empty as gremlin spawn quota is reached so its removed
        assertTrue(app.monsterList.isEmpty());
    }

    @Test
    public void testEmptyMonsterList_EndlessMode() {
        // Test time is passed prewave pause
        app.endless_start = true;
        app.intialise = true;
        app.game_start = false;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,1);
        app.endlessMonsters = new ArrayList<>();
        app.endlessMonsters.add(gremlin);
        app.waveList = new ArrayList<>();
        app.waveList.add(new Wave(0,0,app.endlessMonsters,1));
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        app.draw();
        // List is empty as gremlin spawn quota is reached so its removed
        // Endless mode
        assertTrue(app.monsterList.isEmpty());
    }

    @Test
    public void testBugStart() {
        // Test time is passed prewave pause
        app.endless_start = true;
        app.intialise = false;
        app.game_start = false;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,1);
        app.monsterList.add(gremlin);
        app.endlessMonsters = new ArrayList<>();
        app.endlessMonsters.add(gremlin);
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        app.draw();
        // List is unchanged
        assertTrue(app.waveList.size() == 2);
        // Endless monster list is unchanged
        assertTrue(app.endlessMonsters.size() == 1);
    }

    @Test
    public void testBugStart1() {
        // Test time is passed prewave pause
        app.width = 100;
        app.height = 100;
        app.endless_start = false;
        app.game_start = false;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,1);
        app.monsterList.add(gremlin);
        app.endlessMonsters = new ArrayList<>();
        app.endlessMonsters.add(gremlin);
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        app.draw();
        // Game is not intialised (false if initialised so condition is not met next time)
        assertTrue(app.intialise = true);
    }

    @Test
    public void testPaused() {
        // Test pause button on different scenarios
        app.game_start = true;
        app.isPaused = true;
        app.draw();
        assertTrue(app.isPaused);
        app.game_over = true;
        app.draw();
        assertTrue(app.isPaused);
        app.game_win = true;
        app.draw();
        assertTrue(app.isPaused);
        app.game_over = false;
        app.draw();
        assertTrue(app.isPaused == true);
    }

    @Test
    public void testClickTower() {
        // Test tower has been clicked
        app.tower = mock(Tower.class);
        app.game_start = true;
        app.clickTower = true;
        app.mouseX = 0;
        app.mouseY = 0;
        app.draw();
        assertTrue(app.clickTower);
    }

    @Test
    public void testIsMouseOnTower() {
        // Test it knows when mouse is on tower or not
        Tower tower = mock(Tower.class);
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.game_start = true;
        app.mousePressed = false;
        // Mouse IS on tower
        app.mouseX = 32;
        app.mouseY = 40;
        app.draw();
        // Check if condition inside condition of when mouse is clicked IS called
        verify(tower, times(0)).getFirst();
        // Mouse is not on tower
        app.mouseX = 33;
        app.mouseY = 100;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
        app.mouseX = 31;
        app.mouseY = 100;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
        app.mouseX = 31;
        app.mouseY = 72;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
        app.mouseX = -1;
        app.mouseY = 50;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
        app.mouseX = 15;
        app.mouseY = 30;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
        app.mouseX = 15;
        app.mouseY = 110;
        app.draw();
        // Check if condition inside condition of when mouse is clicked is not called
        verify(tower, times(0)).getFirst();
    }

    @Test
    public void testFinalWave() {
        // Test functionality when game is won
        app.wave_number = 1;
        app.game_start = true;
        app.draw();
        // Game is won, there are not entities left and it is the last wave
        assertTrue(app.game_win);
    }

    @Test
    public void testWave_EndlessMode() {
        // Test wave during endless mode
        app.wave_number = 1;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,10);
        app.endlessMonsters = new ArrayList<>();
        app.endlessMonsters.add(gremlin);
        app.waveList.add(new Wave(1,0,app.endlessMonsters,1));
        app.endless_start = true;
        app.gremlin_array = new ArrayList<>();
        app.gremlin_array.add(null);
        app.beetle_array = new ArrayList<>();
        app.beetle_array.add(null);
        app.worm_array = new ArrayList<>();
        app.endless_start = true;
        app.game_start = false;
        app.draw();
        // Wave length of 2 is truncated to length of 1 due to endless start
        assertTrue(app.waveList.size() == 1);
    }

    @Test
    public void testEndlessMode_tooManyMonsters() {
        // Test when if too many monsters are doubled, their attributes are stronger instead
        app.wave_number = 0;
        app.time_since_spawned = 1000;
        app.time_since_last_wave = 1000;
        app.monsterList = new ArrayList<>();
        Wave wave = new Wave(10f,10f,app.monsterList,10);
        app.waveList = new ArrayList<>();
        app.waveList.add(wave);
        Gremlin gremlin = new Gremlin(0,3,null,1000f,1f,0.9f,100,1);
        app.monsterList.add(gremlin);
        app.endless_start = true;
        app.intialise = false;
        app.placedTowers = new ArrayList<>();
        app.endlessMonsters = new ArrayList<>();
        app.endlessMonsters.add(new Gremlin(0,3,null,1000f,1f,0.9f,100,64));
        app.draw();
        // Number of enemies is not doubled as its passed the quota of 32
        assertTrue(app.endlessMonsters.get(0).getQuantity() == 64);
    }

    @Test
    public void testWave_NormalModeLastWave() {
        // Test last wave on normal mode
        app.wave_number = 2;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(gremlin);
        app.waveList.add(new Wave(2,0,app.monsterList,1));
        app.waveList.add(new Wave(2,0,app.monsterList,1));
        app.game_start = true;
        app.draw();
        // Game is not won yet, still 10 gremlins left on last wave
        assertTrue(app.game_win == false);
    }

    @Test
    public void testWave_NormalModeLastWave_stillMonsters() {
        // Test when there are leftover monsters in the last wave
        app.wave_number = 1;
        app.gremlin_array.add(mock(Gremlin.class));
        app.beetle_array.add(mock(Beetle.class));
        app.worm_array.add(mock(Worm.class));
        app.game_start = true;
        app.draw();
        // Game is not won yet, still gremlins, beetles and worms left on last wave
        assertTrue(app.game_win == false);

        app.gremlin_array = new ArrayList<>();
        app.beetle_array.add(mock(Beetle.class));
        app.worm_array.add(mock(Worm.class));
        app.draw();
        // Game is not won yet, still beetles, worms left on last wave
        assertTrue(app.game_win == false);

        app.beetle_array = new ArrayList<>();
        app.worm_array.add(mock(Worm.class));
        app.draw();
        // Game is not won yet, still worms left on last wave
        assertTrue(app.game_win == false);

        app.worm_array = new ArrayList<>();
        app.draw();
        // Game IS won, no entities left
        assertTrue(app.game_win);
    }

    @Test
    public void testWave_NormalMode_SecondLastWave() {
        // Test wave progression is correct
        app.wave_number = 1;
        Gremlin gremlin = new Gremlin(0,3,null,100f,1f,0.9f,100,10);
        app.monsterList.add(gremlin);
        app.waveList.add(new Wave(2,0,app.monsterList,1));
        app.waveList.add(new Wave(2,0,app.monsterList,1));
        app.game_start = true;
        app.draw();
        // Test there are 3 waves
        assertTrue(app.waveList.size() == 3);
    }

    @Test
    public void testUpgradeGUI_allFalse() {
        // Test how many is selected when drawing upgrade gui
        app.game_start = true;
        app.clickUDamage = false;
        app.clickUSpeed = false;
        app.clickURange = false;
        app.draw();
        // Should be 0, nothing is selected
        assertTrue(app.getNumberOfSelected() == 0);
    }

    @Test
    public void testUpgradeGUI_oneFalse() {
        // Test how many is selected when drawing upgrade gui
        app.game_start = true;
        app.clickUDamage = true;
        app.clickUSpeed = true;
        app.clickURange = false;
        app.draw();
        // Should be 2, nothing is selected
        assertTrue(app.getNumberOfSelected() == 2);
    }

    @Test
    public void testUpgradeGUI_twoFalse() {
        // Test how many is selected when drawing upgrade gui
        app.game_start = true;
        app.clickUDamage = true;
        app.clickUSpeed = false;
        app.clickURange = false;
        app.draw();
        // Should be 1, nothing is selected
        assertTrue(app.getNumberOfSelected() == 1);
    }
}
