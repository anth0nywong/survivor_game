package model;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONObject;

// Represents an obtainable Item in the game world
public class Item  extends GameObject {

    Weapon weapon;
    boolean isCollected = false;
    
    // REQUIRES: posX >= 0, posY >= 0
    // EFFECTS: constructs an item at given position with speed 0, maxHealth 100, health 100
    public Item(int posX, int posY) {
        super(posX, posY, 0, 100, 100, 50, Color.YELLOW);
        int range = (int) (Math.random() * 3) + 1;
        int damage = (int) (Math.random() * 10) + 10;
        Color color = new Color((int)(Math.random() * 0x1000000));
        int frequency = ((int) (Math.random() * 5) + 5)  * 20;
        int bulletNumber = ((int) (Math.random() * 8) + 1) * 2;
        this.weapon = new Weapon(damage, range, color, frequency, bulletNumber);
        EventLog.getInstance().logEvent(new Event(" Item (x: " + this.getPosX() 
                + ", y: " + this.getPosY() +  ") is added to game canvas."));
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    public boolean isCollected() {
        return this.isCollected;
    }
    
    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }
    
    // EFFECTS: returns a JSON object representing this item,
    //          including weapon and collection status
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("weapon", weapon.toJson());
        json.put("isCollected", isCollected);
        return json;
    }
    
    @Override
    protected boolean checkCollision(ArrayList<GameObject> objs) {
        for (GameObject obj : objs) {
            if (this == obj || obj.getIsDeadth()) {
                continue;
            }
            if (isCollide(obj)) {
                resolveLocation(obj);
                return true;
            }
        }
        return false;
    }
}
