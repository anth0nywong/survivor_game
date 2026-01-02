package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GameManager;
import gui.GameManager.GameState;

// Represents a button that takes the user to the weapon scene
public class LogSceneButton extends Button {


    // EFFECTS:  constructs a weapon scene button for the given game manager
    public LogSceneButton(GameManager gameManager) {
        super("Game Log", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new LogSceneClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class LogSceneClickHandler implements ActionListener {

        // EFFECTS: set current state to weapon scene
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().setCurrentState(GameState.LOG);
        }
    }
}
