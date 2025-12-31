package gui.controllers;

import java.util.ArrayList;

import model.GameObject;
import model.Player;

// Controls game object movement based on directional flags
public class EnemyController extends GameController {
    public EnemyController(GameObject object) {
        super(object);
    }
    
    // MODIFIES: this
    // EFFECTS: makes movement decisions based on the player's position
    public void movementDecision(Player player) {
        Player closest = null;
        int x = getControlledObject().getPosX();
        int y = getControlledObject().getPosY();
        double minDistance = Double.MAX_VALUE;
        int targetX = player.getPosX();
        int targetY = player.getPosY();
        double distance = Math.hypot(targetX - x, targetY - y);
        if (distance < minDistance) {
            closest = player;
            minDistance = distance;
        }
        this.makeXDecision(x, closest.getPosX());
        this.makeYDecision(y, closest.getPosY());
    }
    
    // MODIFIES: this
    // EFFECTS: makes horizontal movement decision towards targetX
    void makeXDecision(int currentX, int targetX) {
        if (currentX < targetX) {
            right = true;
            left = false;
        } else if (currentX > targetX) {
            right = false;
            left = true;
        } else {
            right = false;
            left = false;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: makes vertical movement decision towards targetY
    void makeYDecision(int currentY, int targetY) {

        if (currentY < targetY) {
            up = false;
            down = true;
        } else if (currentY > targetY) {
            up = true;
            down = false;
        } else {
            up = false;
            down = false;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: updates the controlled object's position and returns the updated list of game objects
    @Override
    public ArrayList<GameObject> update(ArrayList<GameObject> gameObjects, Player player) {
        this.movementDecision(player);
        return super.update(gameObjects, player);
    }
}
