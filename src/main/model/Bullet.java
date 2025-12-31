package model;

import java.awt.Color;
import java.util.ArrayList;

public class Bullet extends GameObject {
    private int damage;
    private double angle;
    private Player shooter;
    
    // REQUIRES: damage >= 0
    // EFFECTS: constructs a bullet with given damage
    public Bullet(int posX, int posY, int speed, int damage, Color color, double angle, Player shooter) {
        super(posX, posY, speed, 1, 1, 20, color);
        if (damage >= 0) {
            this.damage = damage;
        } else {
            this.damage = 0;
        }
        this.angle = angle;
        this.shooter = shooter;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    // MODIFIES: this
    // EFFECTS: moves the bullet up in the direction of its angle
    public void moveUp(ArrayList<GameObject> objs) {
        posY -= this.getSpeed() * Math.cos(angle / 360.0 * 2  * Math.PI);
        posX += this.getSpeed() * Math.sin(angle / 360.0 * 2  * Math.PI);
        checkCollision(objs);
    }

    Player getOwner() {
        return this.shooter;
    }

    @Override
    protected boolean checkCollision(ArrayList<GameObject> objs) {
        for (GameObject obj : objs) {
            if (this == obj || obj.getIsDeadth()) {
                continue;
            }
            if (isCollide(obj)) {
                if (obj instanceof Enemy) {
                    Bullet bullet = (Bullet) this;
                    this.setIsDeath();
                    if (obj.takeDamage(bullet.getDamage())) {
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
