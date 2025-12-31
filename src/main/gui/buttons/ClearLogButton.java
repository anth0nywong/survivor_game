package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.RealTimeGameManager;
import model.EventLog;

// Represents a button that takes the user to the weapon scene
public class ClearLogButton extends Button {


    // EFFECTS:  constructs a weapon scene button for the given game manager
    public ClearLogButton(RealTimeGameManager gameManager) {
        super("Clear Log", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new ClearLogClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class ClearLogClickHandler implements ActionListener {

        // EFFECTS:  clear the event log
        @Override
        public void actionPerformed(ActionEvent e) {
            EventLog.getInstance().clear();
        }
    }
}
