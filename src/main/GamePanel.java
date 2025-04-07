package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Font;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	PlayManager pm;
	public static Sound music = new Sound();
	public static Sound se = new Sound(); 
	public static PlayManager playManager = new PlayManager(); // or initialize it elsewhere

	
	public GamePanel() {
		// Panel settings
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		
		// Implement KeyListener
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
	     pm = new PlayManager();
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		music.play(0, true);
		music.loop();
	}


	@Override
	public void run() {
		// game loop
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
		
	}
	
	private void update() {
		if(KeyHandler.pausePressed == false && pm.gameOver == false) {
			pm.update();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Graphics2D g2 = (Graphics2D) g;

	    int height = getHeight();

	    // Top to middle: darker lavender to light pink
	    GradientPaint topGradient = new GradientPaint(
	        0, 0, new Color(181, 126, 220),     // Darker lavender
	        0, height / 2, new Color(255, 182, 193)  // Light pink
	    );
	    g2.setPaint(topGradient);
	    g2.fillRect(0, 0, getWidth(), height / 2);

	    // Middle to bottom: light pink to deep hot pink
	    GradientPaint bottomGradient = new GradientPaint(
	        0, height / 2, new Color(255, 182, 193),   // Light pink
	        0, height, new Color(199, 21, 133)         // Deep hot pink
	    );
	    g2.setPaint(bottomGradient);
	    g2.fillRect(0, height / 2, getWidth(), height / 2);

	    // Draw the game
	    pm.draw(g2);

	    if (pm.gameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent overlay
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "GAME OVER";
            int msgWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String restartMsg = "Press R to Restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartMsg);
            g.drawString(restartMsg, (WIDTH - restartWidth) / 2, HEIGHT / 2 + 40);
        }
	    g2.dispose();
	}

	}

