package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.FilePrinter;
import gui.GameManager;
import model.EventLog;

// Represents a button that takes the user to the weapon scene
public class SaveLogButton extends Button {


    // EFFECTS: constructs a weapon scene button for the given game manager
    public SaveLogButton(GameManager gameManager) {
        super("Save Log to File", gameManager);
    }

    // EFFECTS: adds action listener to the button
    @Override
    protected void addListener() {
        button.addActionListener(new SaveLogClickHandler());
    }
    
    // EFFECTS: gets the game manager associated with this button
    private class SaveLogClickHandler implements ActionListener {

        // EFFECTS:  save the event log to file
        @Override
        public void actionPerformed(ActionEvent e) {
            FilePrinter fp = new FilePrinter();
            fp.printLog(EventLog.getInstance());
        }
    }
}