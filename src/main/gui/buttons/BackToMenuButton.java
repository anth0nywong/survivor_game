package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GameManager;
import gui.GameManager.GameState;

// Represents a button that takes the user back to the main menu
public class BackToMenuButton extends Button {

    // EFFECTS: constructs a back to menu button for the given game manager
    public BackToMenuButton(GameManager gameManager) {
        super("back", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new WeaponClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class WeaponClickHandler implements ActionListener {

        // EFFECTS: set current state to menu
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().setCurrentState(GameState.MENU);
        }
    }
}
