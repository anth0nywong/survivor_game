package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest extends GameObjectTest {

    Item itemObject;
    
    @BeforeEach
    void runBefore() {
        super.runBefore();
        itemObject = new Item(12, 10);
        objs.add(itemObject);
    }
    
    @Test
    void constructorTest() {
        assertEquals(12, itemObject.getPosX());
        assertEquals(10, itemObject.getPosY());
        assertEquals(0, itemObject.getSpeed());
        assertEquals(100, itemObject.getMaxHealth());
        assertEquals(100, itemObject.getHealth());
    }
    
    @Test
    void pickUpTest() {
        assertEquals(0, playerObject.getWeapons().size());
        // put player near the item and use small speed for predictable pickup
        playerObject.setPosX(10);
        playerObject.setPosY(10);
        playerObject.setSpeed(2);
        // move up to a few times until the item is collected
        int attempts = 0;
        while (!itemObject.isCollected() && attempts < 10) {
            playerObject.moveRight(objs);
            attempts++;
        }
        assertTrue(itemObject.isCollected() || playerObject.getWeapons().size() > 0);
    }
    
    @Test
    void failPickUpTest() {
        enemyObject = new Enemy(10, 10, 2, 100, 100, 0, 0, "✦", Color.RED);
        enemyObject.moveRight(objs);
        assertEquals(false, itemObject.isCollected());
    }
}
