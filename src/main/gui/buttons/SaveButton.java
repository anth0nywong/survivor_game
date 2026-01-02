package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GameManager;

// Represents a button that saves the current game
public class SaveButton extends Button {

    // EFFECTS: constructs a save button for the given game manager
    public SaveButton(GameManager gameManager) {
        super("Save Game", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new SaveClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class SaveClickHandler implements ActionListener {

        // EFFECTS: saves the current game
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().saveGame();
        }
    }
}
