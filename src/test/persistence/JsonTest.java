package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Enemy;
import model.Item;
import model.Player;
import model.Weapon;

public class JsonTest {
    void assertWeapon(Weapon w1, Weapon w2) {
        assertEquals(w1.getDamage(), w2.getDamage());
        assertEquals(w1.getRange(), w2.getRange());
    }
    
    void assertItem(Item i1, Item i2) {
        assertEquals(i1.getPosY(), i2.getPosY());
        assertEquals(i1.getPosX(), i2.getPosX());
        assertWeapon(i1.getWeapon(), i2.getWeapon());
        assertEquals(i1.isCollected(), i2.isCollected());
    }
    
    void assertEnemy(Enemy e1, Enemy e2) {
        assertEquals(e1.getPosY(), e2.getPosY());
        assertEquals(e1.getPosX(), e2.getPosX());
        assertEquals(e1.getSpeed(), e2.getSpeed());
        assertEquals(e1.getHealth(), e2.getHealth());
        assertEquals(e1.getMaxHealth(), e2.getMaxHealth());
        assertEquals(e1.getAttackRange(), e2.getAttackRange());
        assertEquals(e1.getDamage(), e2.getDamage());
        assertEquals(e1.getIcon(), e2.getIcon());
        assertEquals(e1.getIsDeadth(), e2.getIsDeadth());
    }
    
    void assertNewPlayer(Player player) {
        assertPlayer(player, new Player());
    }
    
    void assertPlayer(Player p1, Player p2) {
        assertEquals(p1.getPosX(), p2.getPosX());
        assertEquals(p1.getPosY(), p2.getPosY());
        assertEquals(p1.getSpeed(), p2.getSpeed());
        assertEquals(p1.getMaxHealth(), p2.getMaxHealth());
        assertEquals(p1.getHealth(), p2.getHealth());
        for (int i = 0; i < p1.getWeapons().size(); i++) {
            assertWeapon(p1.getWeapons().get(i), p2.getWeapons().get(i));
        }
        assertEquals(p1.getLevel(), p2.getLevel());
        assertEquals(p1.getExperience(), p2.getExperience());
    }
}
