package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ZoneReposMouseListener implements MouseListener{
	/**
	 * Utilisation de la souris dans la zone de repos
	 */
	public ZoneReposPanel zrp;
	private int posX;
	private int posY;
	
	public ZoneReposMouseListener(ZoneReposPanel p) {
		this.zrp = p;
	}
	
	public boolean clicPanel(int xMouse, int yMouse, int xPanel, int yPanel, int width, int height) {
		return ((xMouse>=xPanel && xMouse<= xPanel + width) && (yMouse>=yPanel && yMouse<= yPanel + height));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			if(!zrp.estVisible) return;
			this.posX = e.getX();
			this.posY = e.getY();
			if (this.zrp.equipementTrouve) { // Ajoute l'equipement a l'inventaire
				if (clicPanel(this.posX, this.posY, this.zrp.xEquipement, this.zrp.yEquipement, this.zrp.equip.getEquipementPanel().getWidth(), this.zrp.equip.getEquipementPanel().getHeight())) {
					this.zrp.master_frame.topPanel.curHeros.listeEquipement.add(this.zrp.equip);
					this.zrp.equipementTrouve = false;
				}
			}
			
			if (!this.zrp.master_frame.boutique.soinUtilise){ // Pour se soigner
				if (clicPanel(this.posX, this.posY, this.zrp.xSoin, this.zrp.ySoin, this.zrp.widthSoin, this.zrp.heightSoin)) {
					this.zrp.master_frame.topPanel.curHeros.setPvActuel(this.zrp.master_frame.topPanel.curHeros.getPvActuel()+this.zrp.soin);
					this.zrp.soinUtilise = true;
				}
			}
			
			if (clicPanel(this.posX, this.posY, this.zrp.xExit, this.zrp.yExit, this.zrp.widthExit, this.zrp.heightExit)) { // Si on clic sur exit, on revient a la carte
				this.zrp.master_frame.switchCurPanel(2);
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