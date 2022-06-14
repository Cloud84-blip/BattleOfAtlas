package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ZoneReposPanel extends EcranPanel{
	/**
	 * Affiche et gere l'evenement Zone de Repos
	 */
	private static final long serialVersionUID = 1L;
	public int curseurX;
	public int curseurY;
	
	public final int xSoin = 50;
	public final int ySoin = 50;
	public final int widthSoin = 200;
	public final int heightSoin = 80;
	public int soin;
	public boolean soinUtilise;
	
	public final int xEquipement = 500;
	public final int yEquipement = 50;
	public boolean equipementTrouve;
	public Equipement equip;
	
	public final int xExit = 1100;
	public final int yExit = 650;
	public final int widthExit = 120;
	public final int heightExit = 50;
	public ArrayList<Equipement> listeEquipementsPossibles = new ArrayList<Equipement>();
	public Equipement e1 = new Equipement(1, "Orbe rouge", "Attaque", 2);
	public Equipement e2 = new Equipement(1, "Orbe bleue", "Attaque", 2);
	
	

	public ZoneReposPanel(String pNAME, FrameMain master) {
		super(pNAME, master);
		this.initBackground("/assets/fond/FOND-ZONEREPOS.png");
		this.initPanel();
		this.curseurX = 0;
		this.curseurY = 0;
		this.soin = 20;
		this.soinUtilise = false;
		this.listeEquipementsPossibles.add(e1);
		this.listeEquipementsPossibles.add(e2);
		this.initListeners();
	}
	public void initListeners(){
		this.activeMouseListener = new ZoneReposMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		if (!this.estVisible) return;
		g.drawImage(this.bg, 0, 0, null);
		
		if (equipementTrouve) {
			g.setColor(Color.white);
			g.drawString(this.equip.toString(), xEquipement, yEquipement-5);
			this.equip.getEquipementPanel().paint(g, xEquipement, yEquipement);
		}
		else {
			g.setColor(Color.white);
			g.drawString("Il ne reste plus rien.", xEquipement, yEquipement-5);
		}
		
		if (!this.soinUtilise){
			g.setColor(Color.gray);
			g.fillRect(xSoin, ySoin, widthSoin, heightSoin);
			g.setColor(Color.black);
			g.drawString("Soigner " + this.soin + " PV.", xSoin+(widthSoin/3), ySoin+(heightSoin/2));
		}
		
		g.setColor(Color.black);
		g.fillRect(xExit, yExit, widthExit, heightExit);
		g.setColor(Color.white);
		g.drawString("EXIT", xExit+10, yExit+25);
		
		g.dispose();
	}
	
	public void reset() {
		if(FrameMain.random.nextInt(2) == 0) {
			this.equipementTrouve = true;
			this.equip = listeEquipementsPossibles.get(FrameMain.random.nextInt(listeEquipementsPossibles.size()));
		}
		else {
			this.equipementTrouve = false;
		}
		this.soin = this.master_frame.topPanel.curHeros.getPvMax()/2;
		this.soinUtilise = false;
	}
}
