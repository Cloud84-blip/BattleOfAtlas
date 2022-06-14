package game;

import java.awt.*;


public class BoutiquePanel extends EcranPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Cette classe affiche la boutique.
	 */
	public int curseurX;
	public int curseurY;
	public final int xCartes = 100;
	public final int yCartes = 100;
	public final int xEquipements = 100;
	public final int yEquipements = 300;
	public final int xSoin = 100;
	public final int ySoin = 500;
	public final int widthSoin = 200;
	public final int heightSoin = 80;
	public final int xExit = 1100;
	public final int yExit = 650;
	public final int widthExit = 120;
	public final int heightExit = 50;
	
	
	
	public BoutiquePanel(String pNAME, FrameMain master) {
		super(pNAME, master);
		this.initBackground("/assets/fond/FOND-BOUTIQUE.png");
		this.curseurX = 0;
		this.curseurY = 0;
		this.initPanel();
		this.initListeners();
	}
	public void initListeners(){
		this.activeMouseListener = new BoutiqueMouseListener(this.master_frame, this);
	}
	
	public void paintComponent(Graphics g) {
		if (!this.estVisible) return;
		g.drawImage(this.bg, 0, 0, null);
		g.setColor(Color.RED);
		g.drawString("Or : " + this.master_frame.topPanel.curHeros.or, 10, 40);
		for (int i = 0; i<this.master_frame.boutique.cartesAVendre.getListeCarte().size(); i++) {
			if (!this.master_frame.boutique.cartesAchetees.get(i)) {
				this.master_frame.boutique.cartesAVendre.getListeCarte().get(i).getCartePanel().paint(g, xCartes + i*this.master_frame.boutique.cartesAVendre.getListeCarte().get(i).getCartePanel().getWidth(), yCartes);
			}
		}
		for (int i = 0; i<this.master_frame.boutique.equipementsAVendre.size(); i++) {
			if (!this.master_frame.boutique.equipementsAchetes.get(i)) {
				this.master_frame.boutique.equipementsAVendre.get(i).getEquipementPanel().paint(g, xEquipements + i*this.master_frame.boutique.equipementsAVendre.get(i).getEquipementPanel().getWidth()*7, yEquipements);
				g.setColor(Color.white);
				g.drawString(this.master_frame.boutique.equipementsAVendre.get(i).toString(), xEquipements + i*this.master_frame.boutique.equipementsAVendre.get(i).getEquipementPanel().getWidth()*7, yEquipements+60);
			}
		}
		if (!this.master_frame.boutique.soinUtilise){
			g.setColor(Color.gray);
			g.fillRect(xSoin, ySoin, widthSoin, heightSoin);
			g.setColor(Color.black);
			g.drawString("Soigner " + this.master_frame.boutique.soinPropose + " PV.", xSoin+(widthSoin/3), ySoin+(heightSoin/2));
		}
		
		g.setColor(Color.black);
		g.fillRect(xExit, yExit, widthExit, heightExit);
		g.setColor(Color.white);
		g.drawString("EXIT", xExit+10, yExit+25);
		
		
		g.dispose();
	}
}
