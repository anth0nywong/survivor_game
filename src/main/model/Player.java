package model;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a Player that is a type of GameObject and has a list of weapons
public class Player extends GameObject {

    private ArrayList<Weapon> weapons;
    private int level;
    private int experience;
    
    // EFFECTS: constructs a player with default position (0,0), speed 5, maxHealth 100, health 100, 
    //          and a weapon list with no Playerweapons, set default level to 1 and experience to 0
    public Player() {
        super(1000, 1000, 15, 100, 100, 200, Color.BLUE);
        weapons = new ArrayList<Weapon>();
        this.level = 1;
        this.experience = 0;
    }
    
    // REQUIRES: weapon is not already in the player's weapon list
    // MODIFIES: this
    // EFFECTS: adds a weapon to the player's weapon list
    public void addWeapon(Weapon weapon) {
        if (!this.weapons.contains(weapon)) {
            this.weapons.add(weapon);
            EventLog.getInstance().logEvent(new Event(" Player picked up weapon: (Bullets: "
                    + weapon.getBulletNumber() + ", Damage: " + weapon.getDamage() 
                    + ", Fire Rate: " + weapon.getFrequency() + ")"));
        }
    }
    
    // EFFECTS: returns the list of weapons the player has
    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    // MODIFIES: this
    // EFFECTS: upgrade level by 1
    public void upgrade() {
        this.level++;
        EventLog.getInstance().logEvent(new Event(" Player leveled up to level " + this.level));
        this.setHealth(maxHealth);
    }
    
    // MODIFIES: this
    // EFFECTS: add experience to player, if exceed 100, return true and upgrade the player and comsume 100 exp
    //          else return false if not upgraded
    public boolean gainExperience(int exp) {
        this.experience += exp;
        if (this.experience >= 100) {
            upgrade();
            this.experience -= 100;
            return true;
        }
        return false;
    }
    
    public int getExperience() {
        return this.experience;
    }
    
    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    // EFFECTS: returns a JSON object representing this player, including weapons,
    //          level, and experience
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        JSONArray weaponsArray = new JSONArray();
        
        for (Weapon weapon : weapons) {
            weaponsArray.put(weapon.toJson());
        }
        json.put("weapons", weaponsArray);
        json.put("level", level);
        json.put("experience", experience);
        return json;
    }

    @Override
    protected boolean checkCollision(ArrayList<GameObject> objs) {
        for (GameObject obj : objs) {
            if (this == obj || obj.getIsDeadth()) {
                continue;
            }
            if (isCollide(obj)) {
                if (obj instanceof Item) {
                    Item item = (Item) obj;
                    if (item.isCollected()) {
                        continue;
                    }
                    Player player = (Player) this;
                    player.addWeapon(item.getWeapon());
                    item.setCollected(true);
                    continue;
                }
                if (obj instanceof Bullet) {
                    continue;
                }
                resolveLocation(obj);
                return true;
            }
        }
        return false;
    }
}
