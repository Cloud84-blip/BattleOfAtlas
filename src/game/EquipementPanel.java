package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class EquipementPanel extends JPanel {
	/**
	 * Cette classe permet d'afficher les equipements.
	 */
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	public Color c;
	public BufferedImage img;
	public boolean estTest;

	public EquipementPanel(Color c) {
		this.setWidth(50);
		this.setHeight(50);
		this.c = c;
		this.estTest = true;
	}

	public EquipementPanel(BufferedImage img) {
		this.setWidth(50);
		this.setHeight(50);
		this.img = img;
		this.estTest = false;
	}

	public void paint(Graphics g, int x, int y){
		if (!estTest) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(this.img, x, y, null);
		}
		else {
			g.setColor(c);
			g.fillRect(x, y, this.width, this.height);
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
