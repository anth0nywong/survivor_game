package persistence;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Enemy;
import model.Item;
import model.Player;
import model.Weapon;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readPlayer();
            reader.readEnemies();
            reader.readItems();
            reader.readGame();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            Player player = reader.readPlayer();
            ArrayList<Enemy> enemies = reader.readEnemies();
            ArrayList<Item> items = reader.readItems();
            int round = reader.readGame();
            assertNewPlayer(player);
            assertEquals(round, 1);
            assertEquals(items.size(), 0);
            assertEquals(enemies.size(), 0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameEnemy() {
        try {
            Enemy e1 = new Enemy(5, 5, 1, 999, 999, 1, 1, "o", Color.RED);
            Enemy e2 = new Enemy(10, 10, 2, 10, 10, 2, 2, "o", Color.RED);
            JsonReader reader = new JsonReader("./data/testReaderGeneralGameEnemy.json");
            ArrayList<Enemy> enemies = reader.readEnemies();
            assertEquals(enemies.size(), 2);
            assertEnemy(enemies.get(0), e1);
            assertEnemy(enemies.get(1), e2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralGameItem() {
        try {
            Item i1 = new Item(1, 2);
            i1.setWeapon(new Weapon(3, 1, Color.GREEN, 5, 8));
            Item i2 = new Item(3, 3);
            i2.setWeapon(new Weapon(1, 2, Color.GREEN, 5, 8));
            JsonReader reader = new JsonReader("./data/testReaderGeneralGameItem.json");
            ArrayList<Item> items = reader.readItems();
            assertEquals(items.size(), 2);
            assertItem(items.get(0), i1);
            assertItem(items.get(1), i2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralGamePlayer() {
        try {
            Player p1 = new Player();
            p1.addWeapon(new Weapon(10, 5, Color.GREEN, 5, 8));
            p1.addWeapon(new Weapon(5, 3, Color.GREEN, 5, 8));
            p1.upgrade();
            p1.gainExperience(50);
            JsonReader reader = new JsonReader("./data/testReaderGeneralGamePlayer.json");
            Player player = reader.readPlayer();
            assertPlayer(p1, player);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}