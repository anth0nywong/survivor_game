package gui.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.GameObject;

// Controls player movement based on keyboard input and listenes for key events
public class PlayerController extends GameController implements KeyListener {
    
    // EFFECTS: constructs a player controller for the given game object
    public PlayerController(GameObject object) {
        super(object);
    }
    
    // EFFECTS: handles key press events for player movement
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
        }
    }

    // EFFECTS: handles key release events for player movement
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
        }
    }

    // EFFECTS: handles key typed events for player movement
    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
