package model;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONObject;

import persistence.Writable;

// Represents a Weapon that can deal damage to GameObjects within a certain range
public class Weapon implements Writable {
    int damage;
    int range;
    int frequency;
    int currentFrequency;
    int bulletNumber;
    Color color;
    
    // REQUIRES: damage >= 0, range >= 0
    // EFFECTS: constructs a weapon with given damage and range
    public Weapon(int damage, int range, Color color, int frequency, int bulletNumber) {
        if (damage >= 0 && range >= 0) {
            this.damage = damage;
            this.range = range;
            this.color = color;
        } else {
            this.damage = 0;
            this.range = 0;
        }
        this.color = color;
        this.frequency = frequency;
        this.bulletNumber = bulletNumber;
        this.currentFrequency = 0;
    }
    
    // REQUIRES: targets.size() > 0
    // EFFECTS: fires at all enemies within range, return exp gain by this attack
    public ArrayList<Bullet> fire(ArrayList<Enemy> targets, Player player) {
        ArrayList<Bullet> objs = new ArrayList<Bullet>();
        currentFrequency++;
        if (currentFrequency < frequency) {
            return objs;
        }
        for (int i = 0; i < bulletNumber; i++) {
            double angle = 360 * i / bulletNumber;
            Bullet b = new Bullet(player.getPosX(), player.getPosY(), 10, damage, color, angle, player);
            currentFrequency = 0;
            objs.add(b);
        }
        return objs;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public int getRange() {
        return this.range;
    }

    public Color getColor() {
        return color;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getBulletNumber() {
        return bulletNumber;
    }

    // EFFECTS: returns this Weapon as a JSON object containing damage, range
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("damage", damage);
        json.put("range", range);
        json.put("color", color.getRGB());
        json.put("frequency", frequency);
        json.put("bulletNumber", bulletNumber);
        return json;
    }
} 
