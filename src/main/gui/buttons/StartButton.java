package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.RealTimeGameManager;

// Represents a button that starts a new game
public class StartButton extends Button {
    // EFFECTS: constructs a start button for the given game manager

    public StartButton(RealTimeGameManager gameManager) {
        super("New Game", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new StartClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class StartClickHandler implements ActionListener {

        // EFFECTS:  starts a new game
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().startGame(true);
        }
    }
}
