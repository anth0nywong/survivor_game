package gui;

import javax.swing.JFrame;

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        JFrame window = new GameFrame("Java Game Starter");
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                for (Event log : EventLog.getInstance()) {
                    System.out.println(log.toString());
                }
                
                // do whatever you need here
            }
        });
        GameManager gamePanel = new GameManager(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.start();
    }
}
