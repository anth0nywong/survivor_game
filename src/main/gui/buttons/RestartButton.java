package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.RealTimeGameManager;

// Represents a button that restarts the game
public class RestartButton extends Button {

    // EFFECTS: constructs a restart button for the given game manager
    public RestartButton(RealTimeGameManager gameManager) {
        super("Restart Game", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new RestartClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class RestartClickHandler implements ActionListener {

        // EFFECTS:  restarts the game
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().startGame(true);
        }
    }
}
