package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.RealTimeGameManager;

// Represents a button that loads a saved game
public class LoadButton extends Button {

    // EFFECTS: constructs a load button for the given game manager
    public LoadButton(RealTimeGameManager gameManager) {
        super("Load Game", gameManager);
    }
    
    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new LoadClickHandler());
    }

    // EFFECTS: gets the game manager associated with this button
    private class LoadClickHandler implements ActionListener {

        // EFFECTS: trigger load game
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().loadGame();
        }
    }
}
