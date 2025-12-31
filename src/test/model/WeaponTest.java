package model;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeaponTest {
    Weapon weapon;
    Weapon failWeapon;
    ArrayList<Enemy> enemyList;
    Player playerObject;
    
    @BeforeEach
    void runBefore() {
        weapon = new Weapon(10, 5, Color.GREEN, 5, 8);
        enemyList = new ArrayList<>();
        playerObject = new Player();
    }

    @Test
    void constructorTest() {
        assertEquals(10, weapon.getDamage());
        assertEquals(5, weapon.getRange());
        failWeapon = new Weapon(-5, -5, Color.GREEN, 5, 8);
        assertEquals(failWeapon.getDamage(), 0);
        assertEquals(failWeapon.getRange(), 0);
        failWeapon = new Weapon(-5, 1, Color.GREEN, 5, 8);
        assertEquals(failWeapon.getDamage(), 0);
        assertEquals(failWeapon.getRange(), 0);
        failWeapon = new Weapon(5, -1, Color.GREEN, 5, 8);
        assertEquals(failWeapon.getDamage(), 0);
        assertEquals(failWeapon.getRange(), 0);
    }

    @Test
    void fireTest() {
        enemyList.add(new Enemy(13, 10, 2, 50, 50, 20, 10, "✦", Color.RED));
        enemyList.add(new Enemy(15, 10, 2, 50, 50, 20, 10, "✦", Color.RED));
        enemyList.add(new Enemy(18, 10, 2, 50, 50, 20, 10, "✦", Color.RED));
        // Weapon has frequency; until frequency is reached it returns no bullets
        for (int i = 0; i < weapon.getFrequency() - 1; i++) {
            assertEquals(0, weapon.fire(enemyList, playerObject).size());
        }
        // On the frequency-th call, bullets should be produced
        java.util.ArrayList<Bullet> bullets = weapon.fire(enemyList, playerObject);
        assertEquals(weapon.getBulletNumber(), bullets.size());

        // apply a couple of bullets to the first two enemies to simulate hits
        if (bullets.size() >= 2) {
            enemyList.get(0).takeDamage(bullets.get(0).getDamage());
            enemyList.get(1).takeDamage(bullets.get(1).getDamage());
            assertEquals(50 - bullets.get(0).getDamage(), enemyList.get(0).getHealth());
            assertEquals(50 - bullets.get(1).getDamage(), enemyList.get(1).getHealth());
        }
    }
    
    @Test
    void fireNoTargetsTest() {
        enemyList = new ArrayList<>();
        assertEquals(0, weapon.fire(enemyList, playerObject).size());
    }
}
