package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GameManager;

// Represents a button that continues or resumes the game
public class ContinueButton extends Button {

    // EFFECTS: constructs a continue button for the given game manager
    public ContinueButton(GameManager gameManager) {
        super("Continue", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new ContinueClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class ContinueClickHandler implements ActionListener {

        // EFFECTS: games the game running or pauses it
        // called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().toggleRunning();
        }
    }
}
