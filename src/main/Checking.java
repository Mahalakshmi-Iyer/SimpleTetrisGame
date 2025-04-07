package main;

import javax.swing.JFrame;

public class Checking {

    public static void main(String[] args) {
        // Create a window
        JFrame window = new JFrame(" Tetris Game");

        // Safely exit on close
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Add the game panel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Pack and display
        window.pack();
        window.setLocationRelativeTo(null); // Center on screen
        window.setVisible(true);

        // Start the game loop
        gamePanel.launchGame();
    }
}
