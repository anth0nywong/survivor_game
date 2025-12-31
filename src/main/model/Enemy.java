package model;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONObject;

// Represents an Enemy that is a type of GameObject and can deal damage to the player
// and has a certain range within which it can attack
public class Enemy extends GameObject {
    int damage;
    int range;
    String icon;
    int experience;

    // EFFECTS: constructs an enemy with given position, speed, maxHealth, health, damage, range and experience
    //          damage and range must be non-negative, otherwise set to 0
    public Enemy(int posX, int posY, int speed, int maxHealth, int health,
            int damage, int range, String icon, Color color) {
        super(posX, posY, speed, maxHealth, health, 50, color);
        this.damage = Math.max(0, damage);
        this.range = Math.max(0, range);
        this.icon = icon;
        this.isDeath = false;
        this.experience = 10;
        EventLog.getInstance().logEvent(new Event(" Enemy (Hp: " + this.getHealth() 
                + ", Damage: " + this.getDamage() +  ") is added to game canvas."));
    }

    // REQUIRES: damage > 0, range > 0, player within range
    // MODIFIES: player
    // EFFECTS: attacks the player, reducing the player's health by damage amount
    public void attack(Player player) {
        player.takeDamage(damage);
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAttackRange() {
        return this.range;
    }
    
    public String getIcon() {
        return this.icon;
    }
    
    // EFFECT: calculate and return distance from object to another game object
    public int getDistantFrom(GameObject obj) {
        return (int) Math.ceil(Math.sqrt(
                Math.pow(this.getPosX() - obj.getPosX(), 2)
                + Math.pow(this.getPosY() - obj.getPosY(), 2)));
    }
    
    public int getExperience() {
        return this.experience;
    }
    
    public boolean getIsDeadth() {
        return this.isDeath;
    }
    
    public void setDeath(boolean death) {
        this.isDeath = death;
    }
    
    // EFFECTS: returns a JSON object representing this enemy,
    //          including damage, range, experience and icon
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("damage", damage);
        json.put("range", range);
        json.put("experience", experience);
        json.put("icon", icon);
        json.put("color", color.getRGB());
        return json;
    }

    @Override 
    protected boolean checkCollision(ArrayList<GameObject> objs) {
        for (GameObject obj : objs) {
            if (this == obj || obj.getIsDeadth()) {
                continue;
            }
            if (isCollide(obj)) {
                if (obj instanceof Player) {
                    Enemy enemy = (Enemy) this;
                    Player player = (Player) obj;
                    enemy.attack(player);
                }
                if (obj instanceof Bullet) {
                    Bullet bullet = (Bullet) obj;
                    obj.setIsDeath();
                    if (this.takeDamage(bullet.getDamage())) {
                        bullet.getOwner().gainExperience(10);
                    }
                }
                resolveLocation(obj);
                return true;
            }
        }
        return false;
    }
}
