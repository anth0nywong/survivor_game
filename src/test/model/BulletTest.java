package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BulletTest {
    Player owner;

    @BeforeEach
    void setup() {
        owner = new Player();
    }

    @Test
    void constructorAndGettersTest() {
        Bullet b = new Bullet(10, 20, 5, 12, Color.BLACK, 90.0, owner);
        assertEquals(12, b.getDamage());
        assertEquals(10, b.getPosX());
        assertEquals(20, b.getPosY());
        assertEquals(owner, b.getOwner());
    }

    @Test
    void moveUpDirectionTest() {
        // angle 90 should move primarily in +x direction (sin = 1)
        Bullet b = new Bullet(50, 50, 4, 5, Color.BLACK, 90.0, owner);
        int beforeX = b.getPosX();
        int beforeY = b.getPosY();
        b.moveUp(new ArrayList<GameObject>()); // no collisions
        assertTrue(b.getPosX() > beforeX, "bullet should move right for 90deg angle");
        // for 90deg cos ~ 0 so Y should not increase; allow equality or a decrease due to rounding
        assertTrue(b.getPosY() <= beforeY, "bullet Y should be non-increasing for 90deg angle");
    }

    @Test
    void collisionKillsEnemyAndGivesExpToOwner() {
        // create an enemy at same position as bullet; use speed 0 so position doesn't change
        Enemy e = new Enemy(10, 10, 2, 50, 50, 20, 10, "✦", Color.RED);
        // choose damage > enemy health so it will die and owner gains experience
        Bullet b = new Bullet(10, 10, 0, 60, Color.BLACK, 0.0, owner);
        ArrayList<GameObject> objs = new ArrayList<>();
        objs.add(b);
        objs.add(e);

        assertEquals(50, e.getHealth());
        assertEquals(0, owner.getExperience());

        b.moveUp(objs);

        assertTrue(b.getIsDeadth(), "bullet should be marked dead after colliding with enemy");
        assertEquals(0, e.getHealth(), "enemy health should be reduced to 0 or below on lethal hit");
        assertEquals(10, owner.getExperience(), "owner should gain 10 exp for the kill");
    }
}
