package WizardTD;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;

public class AppSetupTest {

    @Test
    public void generalTest() throws InterruptedException {
        // Test general coverage of different visuals
        // If theres no error, then everything is working as intended
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(3000);
        // Test menu bars
        app.loadMenu();
        // Test upgrade GUIs
        app.drawText("", 0, 0, 0);
        Tower tower = new Tower(0f,0f,new PImage(),0f,0f,0f,0f);
        app.delay(500);
        app.drawTexts(tower, 0);
        app.drawBackground(0);
        app.drawTotalCostText(tower);
        app.drawUpgradeGUI(tower);
        app.draw();

        // Test menu bar when mouse hovers over buttons
        app.hoverSpeed = true;
        app.hoverPause = true;
        app.hoverURange = true;
        app.hoverUSpeed = true;
        app.hoverUDamage = true;
        app.hoverTower = true;
        app.loadMenu();

        // Test menu bar when buttons are clicked
        app.delay(2000);
        app.clickURange = true;
        app.clickUDamage = true;
        app.clickUSpeed = true;
        app.fastforward = true;
        app.hoverPause = true;
        app.clickTower = true;
        app.hoverTower = true;
        app.hoverPool = true;
        app.loadMenu();

        app.delay(2000);
        app.drawTotalCostText(tower);
        app.drawTexts(tower, 0);

        // Test when the main menu button sare being hovered over
        app.hoverNormalStart = true;
        app.hoverEndlessStart = true;
        app.draw();

        // Test the tower is drawn correctly with upgrades
        app.delay(2000);
        tower.setFirstTrue();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        tower.upgradeDamage();
        tower.upgradeRange();
        tower.upgradeSpeed();
        app.placedTowers = new ArrayList<>();
        app.placedTowers.add(tower);
        app.mouseX = 0;
        app.mouseY = 40;
        app.game_start = true;
        app.wave_number = app.waveList.size();
        app.draw();

        // Test the text for the time for next wave is correctly done
        app.wave_number = 0;
        app.time_since_last_wave = 1000;
        app.draw();

        app.delay(2000);
        app.wave_number = 1;
        app.time_since_last_wave = 0;
        app.draw();
        
        // Test pause screen
        app.isPaused = true;
        app.game_over = false;
        app.game_win = false;
        app.draw();
    }
}
