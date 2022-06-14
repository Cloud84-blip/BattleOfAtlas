package game;

import java.util.ArrayList;
import java.util.Collections;

public class Boutique {
	/**
	 * Cette classe gere les evenements Boutique sur la carte du monde.
	 */
	public Deck cartesAVendre;
	public ArrayList<Boolean> cartesAchetees;
	public int soinPropose;
	public boolean soinUtilise;
	public ArrayList<Equipement> equipementsAVendre;
	public ArrayList<Boolean> equipementsAchetes;
	public BoutiquePanel panel;
	
	public Boutique (Deck d, ArrayList<Equipement> e, int soin, FrameMain master){
		this.cartesAVendre = d;
		this.equipementsAVendre = e;
		this.soinPropose = soin;
		this.soinUtilise = false;
		this.cartesAchetees = new ArrayList<Boolean>(d.getListeCarte().size());
		this.cartesAchetees.addAll(Collections.nCopies(d.getListeCarte().size(), Boolean.FALSE));
		this.equipementsAchetes = new ArrayList<Boolean>(e.size());
		this.equipementsAchetes.addAll(Collections.nCopies(e.size(), Boolean.FALSE));
		this.panel = new BoutiquePanel("boutique", master);
	}
	
}
