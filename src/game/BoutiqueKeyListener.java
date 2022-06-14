package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoutiqueKeyListener implements KeyListener {
	/**
	 * Utilisation du clavier lors des achats, inutilise
	 */
	
	public BoutiquePanel jpanel;

	private int key;
	
	public BoutiqueKeyListener(FrameMain f, BoutiquePanel p) {
		this.jpanel = p;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		this.key = e.getKeyCode();
		switch (this.key){
			default:
				break;
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
