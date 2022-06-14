package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RecompenseKeyListener implements KeyListener {
	
	public RecompensePanel jpanel;
	private final FrameMain frame;
	private static final int KEY_ENTER = KeyEvent.VK_ENTER;
    private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    private static final int KEY_LEFT = KeyEvent.VK_LEFT;

	private int key;
	
	public RecompenseKeyListener(FrameMain f, RecompensePanel p) {
		this.frame = f;
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
			//Appuie sur p pour piocher
			case KEY_RIGHT :
				//bouger vers la droite
				if (jpanel.emplacementCurseur < jpanel.cartesRecompense.getListeCarte().size()-1) jpanel.emplacementCurseur+=1;
				else jpanel.emplacementCurseur = 0;
				break;
			case KEY_LEFT :
				//bouger vers la gauche
				if (jpanel.emplacementCurseur == 0) jpanel.emplacementCurseur = jpanel.cartesRecompense.getListeCarte().size()-1;
				else jpanel.emplacementCurseur -= 1;
				break;
			case KEY_ENTER :
				//on ajoute la carte au deck du heros
				frame.topPanel.curHeros.deck.ajouterCarte(jpanel.cartesRecompense.getListeCarte().get(jpanel.emplacementCurseur));
				//on ajoute l'or gagne au heros
				frame.topPanel.curHeros.or += jpanel.orGagne;
				//on retourne a la carte du monde
				this.frame.switchCurPanel(2);
			default:
				break;
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
