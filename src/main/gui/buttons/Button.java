// Citation: This class is based on the sample ui code provided in the
// CPSC 121 (University of British Columbia) Draw Editor Demo.
// Modifications have been made to fit the structure and requirements
// of my own application.

package gui.buttons;

import javax.swing.JButton;

import gui.GameManager;

// Represents an abstract button in the GUI
public abstract class Button {
    GameManager gameManager;
    JButton button;
    
    // EFFECTS: constructs a button with the given label
    public Button(String label, GameManager gameManager) {
        button = new JButton(label);
        button = customizeButton(button);
        this.gameManager = gameManager;
        addListener();
    }
    
    // EFFECTS: gets the label of the button
    public String getLabel() {
        return button.getText();
    }
    
    // EFFECTS: gets the button component
    public JButton getButton() {
        return button;
    }
    
    // MODIFIES: this
    // EFFECTS: customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }
    
    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();
    
    // EFFECTS: gets the game manager associated with this button
    public GameManager getGameManager() {
        return this.gameManager;
    }
    
}
