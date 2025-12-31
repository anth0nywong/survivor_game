package gui.controllers;

import model.GameObject;

// Controls bullet movement based on directional flags
public class BulletController extends GameController {

    double angle;

    // EFFECTS: construct a bullet controller that always moves up, and move up fucntion moves in the direction of angle
    public BulletController(GameObject object) {
        super(object);
        up = true;
        down = false;
        left = false;
        right = false;
    }
    
}
