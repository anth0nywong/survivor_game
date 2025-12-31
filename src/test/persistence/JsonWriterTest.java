package persistence;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.RealTimeGameManager;
import model.Enemy;
import model.Item;
import model.Player;
import model.Weapon;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
    
    @Test
    void testWriterEmptyGame() {
        try {
            RealTimeGameManager gm = new RealTimeGameManager(600, 800);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(gm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            Player player = reader.readPlayer();
            ArrayList<Enemy> enemies = reader.readEnemies();
            ArrayList<Item> items = reader.readItems();
            assertNewPlayer(player);
            assertEquals(items.size(), 0);
            assertEquals(enemies.size(), 0);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    void testWriterGeneralGameEnemy() {
        try {
            RealTimeGameManager gm = new RealTimeGameManager(600, 800);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameEnemy.json");
            Enemy e1 = new Enemy(5, 5, 1, 999, 999, 1, 1, "o", Color.RED);
            gm.addEnemy(e1);
            Enemy e2 = new Enemy(10, 10, 2, 10, 10, 2, 2, "o", Color.RED);
            gm.addEnemy(e2);
            writer.open();
            writer.write(gm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameEnemy.json");
            
            ArrayList<Enemy> enemies = reader.readEnemies();
            assertEquals(enemies.size(), 2);
            assertEnemy(enemies.get(0), e1);
            assertEnemy(enemies.get(1), e2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    void testWriterGeneralGameItem() {
        try {
            RealTimeGameManager gm = new RealTimeGameManager(600, 800);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameItem.json");
            Item i1 = new Item(1, 2);
            gm.addItem(i1);
            Item i2 = new Item(3, 3);
            gm.addItem(i2);
            writer.open();
            writer.write(gm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameItem.json");

            ArrayList<Item> items = reader.readItems();
            assertEquals(items.size(), 2);
            assertItem(items.get(0), i1);
            assertItem(items.get(1), i2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    void testWriterGeneralGamePlayer() {
        try {
            RealTimeGameManager gm = new RealTimeGameManager(600, 800);
            
            Player p1 = new Player();
            gm.setPlayer(p1);
            p1.addWeapon(new Weapon(10, 5, Color.GREEN, 5, 8));
            p1.addWeapon(new Weapon(5, 3, Color.GREEN, 5, 8));
            p1.upgrade();
            p1.gainExperience(50);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGamePlayer.json");
            
            writer.open();
            writer.write(gm);
            writer.close();
            
            JsonReader reader = new JsonReader("./data/testWriterGeneralGamePlayer.json");
            Player player = reader.readPlayer();
            assertPlayer(p1, player);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
