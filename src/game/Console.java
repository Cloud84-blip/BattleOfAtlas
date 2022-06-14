package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Console extends JPanel {
	/**
	 * Simple console qu'on peut afficher avec la touche Backspace afin de debogguer.
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<String> logs;
	private boolean isShowing;
	private final int x = 0;
	private final int y = 480;
	public Color lightblack = new Color(89, 89, 89);
	public Color lightgray = new Color(220, 200, 200);
	
	public Console() {
		this.setIsShowing(false);
		this.setBounds(this.x, this.y, 220, 220);
		this.logs = new ArrayList<String>();
	}

	public boolean getIsShowing() {
		return isShowing;
	}

	public void setIsShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}
	
	public void log(String msg) {
		if (this.logs.size() >= 14) {
			this.logs.remove(0);
		}
		this.logs.add(msg);
	}
	
	public void affiche_console(Graphics g) {
		int offset = 0;
		g.setColor(lightblack);
		g.fillRect(this.x, this.y, this.getWidth(), this.getHeight());
		g.setColor(lightgray);
		for (int i=this.logs.size()-1; i>=0; i--) {
			String log = this.logs.get(i);
			g.drawString(log, this.x , (this.y+this.getHeight()-10) - offset);
			offset += 20;
		}
	}

}
