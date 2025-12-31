package model;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONObject;

import persistence.Writable;

// Represents a generic game object with position, speed, health, and max health
public abstract class GameObject implements Writable {
    protected int posX;
    protected int posY;
    private int speed;
    private int health;
    private int size;
    protected int maxHealth;
    protected boolean isDeath;
    Color color;
    
    //  Effects: constructs a game object with given position, speed, maxHealth, and health
    //           all parameters must be non-negative, otherwise set to 0
    public GameObject(int posX, int posY, int speed, int maxHealth, int health, int size, Color color) {
        
        posX = Math.max(0, posX);
        posY = Math.max(0, posY);
        speed = Math.max(0, speed);
        maxHealth = Math.max(0, maxHealth);
        health = Math.max(0, health);
        
        this.maxHealth = maxHealth;
        setHealth(health);
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.isDeath = false;
    }

    public int getPosX() {
        return this.posX;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getHealth() {
        return this.health;
    }
    
    public int getSize() {
        return this.size;
    }

    public Color getColor() {
        return color;
    }

    // EFFECT: set game object health to specific health if larger than or equal 0.
    //         If health is smaller than 0, set health to 0 and game object to die.
    public void setHealth(int health) {
        if (health > 0) {
            this.health = Math.min(health, maxHealth);
        } else {
            setIsDeath();
            this.health = 0;
        }
    }
    
    // MODIFIES: this, objs
    // EFFECTS:  checks for collision with other game objects in the list
    //           and handles the collision effects accordingly
    //           returns true if a collision occurred, false otherwise
    //           special interactions for Player picking up Items and Enemy attacking Player
    protected abstract boolean checkCollision(ArrayList<GameObject> objs);

    // MODIFIES: this
    // EFFECTS: resolve the location of this object after collision with another object
    void resolveLocation(GameObject obj) {
        double dx = this.getPosX() - obj.getPosX();
        double dy = this.getPosY() - obj.getPosY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double nonTouchDistance = this.getSize() + obj.getSize();

        double x = nonTouchDistance / distance * dx;
        double y = nonTouchDistance / distance * dy;
        
        this.setPosX((int) (obj.getPosX() + x));
        this.setPosY((int) (obj.getPosY() + y));
    }

    // EFFECTS: returns true if this object collides with another object
    protected boolean isCollide(GameObject obj) {
        double dx = this.getPosX() - obj.getPosX();
        double dy = this.getPosY() - obj.getPosY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.getSize() + obj.getSize());
    }

    // MODIFIES: this
    // EFFECTS: moves the object to up by speed
    public void moveUp(ArrayList<GameObject> objs) {
        posY -= this.speed;
        checkCollision(objs);
    }

    // MODIFIES: this
    // EFFECTS: moves the object to down by speed
    public void moveDown(ArrayList<GameObject> objs) {
        
        posY += this.speed;
        checkCollision(objs);
        
    }

    // MODIFIES: this
    // EFFECTS: moves the object to left by speed
    public void moveLeft(ArrayList<GameObject> objs) {
        posX -= this.speed;
        checkCollision(objs);
    }
    
    // MODIFIES: this
    // EFFECTS: moves the object to right by speed
    public void moveRight(ArrayList<GameObject> objs) {
        posX += this.speed;
        checkCollision(objs);
    }
    
    // REQUIRES: damage > 0
    // MODIFIES: this
    // EFFECTS: reduces health by damage amount
    public boolean takeDamage(int damage) {
        if (damage > 0) {
            health -= damage;
            if (health < 0) {
                health = 0;
                setIsDeath();
                return true;
            }
        }
        return false;
    }
    
    public void setIsDeath() {
        this.isDeath = true;
    }

    public boolean getIsDeadth() {
        return this.isDeath;
    }
    
    // EFFECTS: returns a JSON object representing this game object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("posX", posX);
        json.put("posY", posY);
        json.put("speed", speed);
        json.put("health", health);
        json.put("maxHealth", maxHealth);
        json.put("isDeath", isDeath);
        return json;
    }
}
