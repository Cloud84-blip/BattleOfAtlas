package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class CartePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	public Color testColor;
	public BufferedImage img;
	public boolean estTest;

	public CartePanel(Color c) {
		this.setWidth(110);
		this.setHeight(180);
		this.testColor = c;
		this.estTest = true;
	}

	public CartePanel(BufferedImage img) {
		this.setWidth(110);
		this.setHeight(180);
		this.img = img;
		this.estTest = false;
	}

	public void paint(Graphics g, int x, int y){
		if (!estTest) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(this.img, x, y, null);
		}
		else {
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawString("Test", x + 20, y + 50);
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
