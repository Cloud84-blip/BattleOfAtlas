package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CombatKeyListener implements KeyListener {
	
	public CombatPanel jpanel;
	private static final int KEY_P = KeyEvent.VK_P;
	private static final int KEY_D = KeyEvent.VK_D;
	private static final int KEY_R = KeyEvent.VK_R;
	private static final int KEY_M = KeyEvent.VK_M;
	private static final int KEY_L = KeyEvent.VK_L;
	private static final int KEY_J = KeyEvent.VK_J;
	private static final int KEY_X = KeyEvent.VK_X;
	private static final int KEY_BS = KeyEvent.VK_BACK_SPACE;
	private static final int KEY_O = KeyEvent.VK_O;

	private int key;
	
	public CombatKeyListener(CombatPanel p) {
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
		Deck tmp;
		switch (this.key){
			//Appuie sur p pour piocher
			case KEY_P :
				tmp = this.jpanel.h.deck.piocher(this.jpanel, 1);
				this.jpanel.main.ajouterEtViderDeck(tmp);
				break;

			// d pour defausser
			case KEY_D :
				tmp = this.jpanel.main.defausser(1);
				this.jpanel.defausse.ajouterEtViderDeck(tmp);
				break;


			//r pour remetrre la defausse dans le deck
			case KEY_R :
				tmp = this.jpanel.defausse.defausser_tout();
				if (tmp.getListeCarte().size() == 0) return;
				this.jpanel.h.deck.ajouterEtViderDeck(tmp);
				this.jpanel.h.deck.melanger();
				break;


			// l pour augmenter les pv Actuels
			case KEY_L:
				if (this.jpanel.h.getPvActuel() < this.jpanel.h.getPvMax())
					this.jpanel.h.setPvActuel(this.jpanel.h.getPvActuel() + 1);
				break;

			// m pour baisser les pv Actuels
			case KEY_M :
				if (this.jpanel.h.getPvActuel() > 0)
					this.jpanel.h.setPvActuel(this.jpanel.h.getPvActuel() - 1);
				break;

			// j pour jouer une carte et la défausser
			case KEY_J :
				this.jpanel.main.getListeCarte().get(this.jpanel.main.getListeCarte().size()-1).activer(this.jpanel);
				tmp = this.jpanel.main.defausser(this.jpanel.main.getListeCarte().get(this.jpanel.main.getListeCarte().size()-1));
				this.jpanel.defausse.ajouterEtViderDeck(tmp);
				break;

			// Back Space pour masquer/afficher la console
			case KEY_BS :
				this.jpanel.console.setIsShowing(!this.jpanel.console.getIsShowing());
				break;


			case KEY_X:
				if (!this.jpanel.tourHeros()) {
					return;
				}
				this.jpanel.effetsFinTour();
				this.jpanel.tour_fini();
				//this.jpanel.passer_tour_ennemi();
				tmp = this.jpanel.main.defausser_tout();
				this.jpanel.defausse.ajouterEtViderDeck(tmp);
				tmp = this.jpanel.h.deck.piocher(this.jpanel, 5);
				this.jpanel.h.mana = this.jpanel.h.manaMax;
				this.jpanel.main.ajouterEtViderDeck(tmp);
				break;
			case KEY_O :
				break;
			default:
				break;
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
