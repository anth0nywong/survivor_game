package gui.controllers;

import java.util.ArrayList;

import model.GameObject;
import model.Player;

// Controls game object movement based on directional flags
public abstract class GameController {
    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;
    private GameObject object;

    // EFFECTS: constructs a game controller for the given game object
    public GameController(GameObject object) {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.object = object;
    }

    // MODIFIES: this
    // EFFECTS: updates the controlled object's position and returns the updated list of game objects
    public ArrayList<GameObject> update(ArrayList<GameObject> gameObjects, Player player) {
        ArrayList<GameObject> collidedObjects = new ArrayList<>();
        GameObject currentObj = getControlledObject();
        if (currentObj == null || currentObj.getIsDeadth()) {
            return collidedObjects;
        }
        if (isUp()) {
            currentObj.moveUp(collidedObjects);
        }
        if (isDown()) {
            currentObj.moveDown(gameObjects);
        }
        if (isLeft()) {
            currentObj.moveLeft(gameObjects);
        }
        if (isRight()) {
            currentObj.moveRight(gameObjects);
        }
        return collidedObjects;
    }
    
    public GameObject getControlledObject() {
        return object;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

}
