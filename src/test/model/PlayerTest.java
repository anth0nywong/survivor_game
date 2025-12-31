package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest extends GameObjectTest {
    
    @BeforeEach
    void runBefore() {
        super.runBefore();
    }
    
    @Test
    void constructorTest() {
        assertEquals(1000, playerObject.getPosX());
        assertEquals(1000, playerObject.getPosY());
        assertEquals(15, playerObject.getSpeed());
        assertEquals(100, playerObject.getMaxHealth());
        assertEquals(100, playerObject.getHealth());
        assertEquals(0, playerObject.getWeapons().size());
        assertEquals(1, playerObject.getLevel());
        assertEquals(0, playerObject.getExperience());
    }

    @Test
    void addWeaponTest() {
        assertEquals(0, playerObject.getWeapons().size());
        playerObject.addWeapon(new Weapon(10, 5, Color.GREEN, 5, 8));
        assertEquals(1, playerObject.getWeapons().size());
        playerObject.addWeapon(new Weapon(5, 3, Color.GREEN, 5, 8));
        assertEquals(2, playerObject.getWeapons().size());
    }
    
    @Test
    void addDuplicateWeaponTest() {
        assertEquals(0, playerObject.getWeapons().size());
        Weapon weapon1 = new Weapon(10, 5, Color.GREEN, 5, 8);
        playerObject.addWeapon(weapon1);
        assertEquals(1, playerObject.getWeapons().size());
        playerObject.addWeapon(weapon1);
        assertEquals(1, playerObject.getWeapons().size());
    }
    
    @Test
    void upgradeTest() {
        playerObject.upgrade();
        assertEquals(2, playerObject.getLevel());
        playerObject.gainExperience(50);
        assertEquals(50, playerObject.getExperience());
        playerObject.setHealth(10);
        assertEquals(10, playerObject.getHealth());
        playerObject.gainExperience(100);
        assertEquals(3, playerObject.getLevel());
        assertEquals(100, playerObject.getHealth());
    }
}
