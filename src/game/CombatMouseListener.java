package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CombatMouseListener implements MouseListener{
	/**
	 * Utilisation de la souris lors des combats
	 */
	public CombatPanel cp;
	public FrameMain frame;
	public int etat;

	public CombatMouseListener(FrameMain f, CombatPanel cp) {
		this.frame = f;
		this.cp = cp;
	}
	
	public boolean clicPanel(int xMouse, int yMouse, int xPanel, int yPanel, int width, int height) {
		return ((xMouse>=xPanel && xMouse<= xPanel + width) && (yMouse>=yPanel && yMouse<= yPanel + height));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			if(!cp.estVisible) return;
			cp.setMousPosX(e.getX());
			cp.setMousPosY(e.getY());
			int i = 0;
			for (Carte c : cp.main.getListeCarte()) { // Jouer une carte
				int width = c.getCartePanel().getWidth();
				int height = c.getCartePanel().getHeight();
				if (clicPanel(cp.getMousPosX(), cp.getMousPosY(), 220+(i*width), 540, width, height)) {
					if (c.getCout() <= cp.h.mana){
						c.activer(cp);
						cp.h.mana -= c.getCout();
						Deck tmp = cp.main.defausser(c);
						cp.defausse.ajouterEtViderDeck(tmp);
					}
					break;
				}
				i+=1;
			}
			
			if (clicPanel(cp.getMousPosX(), cp.getMousPosY(), this.cp.xExit, this.cp.yExit, this.cp.widthExit, this.cp.heightExit)) { // Termine le tour du joueur
				if (!this.cp.tourHeros()) {
					return;
				}
				Deck tmp;
				this.cp.effetsFinTour();
				this.cp.tour_fini();
				//this.jpanel.passer_tour_ennemi();
				tmp = this.cp.main.defausser_tout();
				this.cp.defausse.ajouterEtViderDeck(tmp);
				tmp = this.cp.h.deck.piocher(this.cp, 5);
				this.cp.h.mana = this.cp.h.manaMax;
				this.cp.main.ajouterEtViderDeck(tmp);
			}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
