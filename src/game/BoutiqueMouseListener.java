package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoutiqueMouseListener implements MouseListener{
	public BoutiquePanel bp;
	public FrameMain frame;
	private int posX;
	private int posY;
	
	public BoutiqueMouseListener(FrameMain f, BoutiquePanel p) {
		this.frame = f;
		this.bp = p;
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
			if(!bp.estVisible) return;
			this.posX = e.getX();
			this.posY = e.getY();
			int i = 0;
			for (Carte c : frame.boutique.cartesAVendre.getListeCarte()) {
				int width = c.getCartePanel().getWidth();
				int height = c.getCartePanel().getHeight();
				if (clicPanel(this.posX, this.posY, this.bp.xCartes+(i*width), this.bp.yCartes, width, height)) {
					if (frame.topPanel.curHeros.or >= 50 && !frame.boutique.cartesAchetees.get(i)){
						frame.topPanel.curHeros.or -= 50;
						frame.topPanel.curHeros.deck.ajouterCarte(c);
						frame.boutique.cartesAchetees.set(i, true);
					}
					break;
				}
				i+=1;
			}
			int j = 0;
			for (Equipement equip : frame.boutique.equipementsAVendre) {
				int width = equip.getEquipementPanel().getWidth();
				int height = equip.getEquipementPanel().getHeight();
				if (clicPanel(this.posX, this.posY, this.bp.xEquipements+(j*width*7), this.bp.yEquipements, width, height)) {
					if (frame.topPanel.curHeros.or >= 50 && !frame.boutique.equipementsAchetes.get(j)){
						frame.topPanel.curHeros.or -= 50;
						frame.topPanel.curHeros.listeEquipement.add(equip);
						frame.boutique.equipementsAchetes.set(j, true);
					}
					break;
				}
				j+=1;
			}
			
			if (clicPanel(this.posX, this.posY, this.bp.xSoin, this.bp.ySoin, this.bp.widthSoin, this.bp.heightSoin)) {
				if (frame.topPanel.curHeros.or >= 50 && !frame.boutique.soinUtilise){
					frame.topPanel.curHeros.or -= 50;
					frame.topPanel.curHeros.setPvActuel(frame.topPanel.curHeros.getPvActuel()+frame.boutique.soinPropose);
					frame.boutique.soinUtilise = true;
				}
			}
			
			if (clicPanel(this.posX, this.posY, this.bp.xExit, this.bp.yExit, this.bp.widthExit, this.bp.heightExit)) {
				frame.switchCurPanel(2);
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
