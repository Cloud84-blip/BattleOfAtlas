package game;

import java.awt.Graphics;

public class FinPanel extends EcranPanel{
	/**
	 * Ecran de fin
	 */
	private static final long serialVersionUID = 1L;

	public FinPanel(String pNAME, FrameMain master) {
		super(pNAME, master);
		this.initBackground("/assets/fond/FOND-FIN.png");
		this.initPanel();
	}
	
	public void paintComponent(Graphics g) {
		if (!this.estVisible) return;
		g.drawImage(this.bg, 0, 0, null);
		g.dispose();
	}
}
