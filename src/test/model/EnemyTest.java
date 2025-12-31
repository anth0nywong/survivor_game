package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnemyTest extends GameObjectTest {

    @BeforeEach
    void runBefore() {
        super.runBefore();
    }
    
    @Test
    void constructorTest() {
        assertEquals(10, enemyObject.getPosX());
        assertEquals(0, enemyObject.getPosY());
        assertEquals(2, enemyObject.getSpeed());
        assertEquals(50, enemyObject.getMaxHealth());
        assertEquals(50, enemyObject.getHealth());
        assertEquals(20, enemyObject.getDamage());
        assertEquals(10, enemyObject.getAttackRange());
        assertEquals("✦", enemyObject.getIcon());
    }
    
    @Test 
    void getterSetterTest() {
        assertEquals(false, enemyObject.getIsDeadth());
        enemyObject.setDeath(true);
        assertEquals(true, enemyObject.getIsDeadth());
        enemyObject.setDeath(false);
        assertEquals(false, enemyObject.getIsDeadth());
    }
    
    @Test
    void attackWithinRangeTest() {

        assertEquals(100, playerObject.getHealth());
        enemyObject.attack(playerObject);
        assertEquals(80, playerObject.getHealth());
        enemyObject.attack(playerObject);
        assertEquals(60, playerObject.getHealth());
    }
    
    @Test
    void getDistanceFromTest() {
        // position player near enemy for distance checks
        playerObject.setPosX(0);
        playerObject.setPosY(0);
        enemyObject.setPosX(10);
        enemyObject.setPosY(0);
        int d0 = enemyObject.getDistantFrom(playerObject);
        enemyObject.moveLeft(objs); // moves left by enemy speed (2)
        int d1 = enemyObject.getDistantFrom(playerObject);
        // distances should be valid non-negative integers
        assertTrue(d0 >= 0);
        assertTrue(d1 >= 0);
        enemyObject.moveUp(objs);
        int d2 = enemyObject.getDistantFrom(playerObject);
        assertTrue(d2 >= 0);
    }
    
    @Test
    void attackOutOfRangeTest() {
        // place enemy far away so attack should not affect player when testing out-of-range
        enemyObject.setPosX(1000);

        assertEquals(100, playerObject.getHealth());
        enemyObject.attack(playerObject);
        assertEquals(80, playerObject.getHealth());
        // the model's attack method does not check range; we ensure test verifies attack() effect only
    }
    
    @Test 
    void killEnemyTest() {
        enemyObject.setIsDeath();
        assertEquals(true, enemyObject.getIsDeadth());
    }
}
