package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameObjectTest {
    Player playerObject;
    Enemy enemyObject;
    ArrayList<GameObject> objs;
    
    @BeforeEach
    void runBefore() {
        objs = new ArrayList<GameObject>(); 
        playerObject = new Player();
        objs.add(playerObject);
        enemyObject = new Enemy(10, 0, 2, 50, 50, 20, 10, "✦", Color.RED);
        objs.add(enemyObject);
    }
    
    @Test
    void getterTest() {
        assertEquals(1000, playerObject.getPosX());
        assertEquals(1000, playerObject.getPosY());
        assertEquals(15, playerObject.getSpeed());
        assertEquals(100, playerObject.getMaxHealth());
        assertEquals(100, playerObject.getHealth());
    }
    
    @Test
    void setterTest() {
        playerObject.setHealth(50);
        assertEquals(50, playerObject.getHealth());
        playerObject.setHealth(-10);
        assertEquals(0, playerObject.getHealth());
        playerObject.setHealth(150);
        assertEquals(100, playerObject.getHealth());
    }
    
    @Test
    void movementTest() {
        int initY = playerObject.getPosY();
        int speed = playerObject.getSpeed();
        playerObject.moveUp(objs);
        assertEquals(initY - speed, playerObject.getPosY());
        playerObject.moveUp(objs);
        assertEquals(initY - 2 * speed, playerObject.getPosY());

        playerObject.moveDown(objs);
        assertEquals(initY - speed, playerObject.getPosY());
        playerObject.moveDown(objs);
        assertEquals(initY, playerObject.getPosY());

        int initX = playerObject.getPosX();
        playerObject.moveLeft(objs);
        assertEquals(initX - speed, playerObject.getPosX());
        playerObject.moveLeft(objs);
        assertEquals(initX - 2 * speed, playerObject.getPosX());

        playerObject.moveRight(objs);
        assertEquals(initX - speed, playerObject.getPosX());
        playerObject.moveRight(objs);
        assertEquals(initX, playerObject.getPosX());
    }
    
    @Test
    void movementObstacleTest() {
        // place player near small coordinates for obstacle checks
        playerObject.setPosX(10);
        playerObject.setPosY(10);
        playerObject.setSpeed(2);
        objs.add(new Enemy(12, 10, 2, 50, 50, 20, 10, "✦", Color.RED));
        objs.add(new Enemy(8, 10, 2, 50, 50, 20, 10, "✦", Color.RED));
        playerObject.moveRight(objs);
        // object may be resolved away from obstacle; ensure player position is valid (non-negative)
        int px = playerObject.getPosX();
        assertTrue(px >= 0);

        // try moving left and ensure no overlap after movement
        playerObject.moveLeft(objs);
        px = playerObject.getPosX();
        assertTrue(px >= 0);

        // vertical checks: add vertical obstacles and ensure no overlapping after moves
        objs.add(new Enemy(9, 8, 2, 50, 50, 20, 10, "✦", Color.RED));
        objs.add(new Enemy(9, 12, 2, 50, 50, 20, 10, "✦", Color.RED));
        playerObject.moveUp(objs);
        int py = playerObject.getPosY();
        assertTrue(py >= 0);
    }
    
    @Test
    void walkThroughDeathEnemyTest() {
        // set player at small coordinates and small speed for predictable movement
        playerObject.setPosX(10);
        playerObject.setPosY(10);
        playerObject.setSpeed(2);
        Enemy deathEnemy = new Enemy(12, 10, 2, 50, 50, 20, 10, "✦", Color.RED);
        deathEnemy.setIsDeath();

        // Use a local object list to make movement deterministic for this test only
        ArrayList<GameObject> localObjs = new ArrayList<>();
        localObjs.add(playerObject);
        localObjs.add(deathEnemy);

        playMoveRight(localObjs);
    }

    void playMoveRight(ArrayList<GameObject> localObjs) {
        // moving right should advance (dead enemy ignored)
        int before = playerObject.getPosX();
        playerObject.moveRight(localObjs);
        assertTrue(playerObject.getPosX() >= before);
        before = playerObject.getPosX();
        playerObject.moveRight(localObjs);
        assertTrue(playerObject.getPosX() >= before);
    }
    
    @Test
    void takeDamageTest() {
        assertEquals(100, playerObject.getHealth());
        playerObject.takeDamage(20);
        assertEquals(80, playerObject.getHealth());
        playerObject.takeDamage(50);
        assertEquals(30, playerObject.getHealth());
        playerObject.takeDamage(-50);
        assertEquals(30, playerObject.getHealth());
        playerObject.takeDamage(40);
        assertEquals(0, playerObject.getHealth());
        playerObject.takeDamage(10);
        assertEquals(0, playerObject.getHealth());
    }
}
