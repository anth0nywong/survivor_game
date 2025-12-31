package gui.controllers;

import gui.RealTimeGameManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Controls interface actions based on keyboard input and listens for key events
public class InterfaceController implements KeyListener {
    
    private RealTimeGameManager manager;

    // EFFECTS: constructs an interface controller for the given game manager
    public InterfaceController(RealTimeGameManager manager) {
        this.manager = manager;
    }

    // EFFECTS: handles key press events for interface controls
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_P -> manager.toggleRunning();
        }
    }

    // EFFECTS: handles key release events for interface controls
    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

    // EFFECTS: handles key typed events for interface controls
    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }
}
