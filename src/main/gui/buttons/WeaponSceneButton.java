package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GameManager;
import gui.GameManager.GameState;

// Represents a button that takes the user to the weapon scene
public class WeaponSceneButton extends Button {


    // EFFECTS: constructs a weapon scene button for the given game manager
    public WeaponSceneButton(GameManager gameManager) {
        super("Weapon Inventory", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new WeaponClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class WeaponClickHandler implements ActionListener {

        // EFFECTS:  set current state to weapon scene
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameManager().setCurrentState(GameState.WEAPON);
        }
    }
}
