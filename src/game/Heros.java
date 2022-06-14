package game;

import java.awt.*;
import java.util.ArrayList;

public class Heros extends Personnage {
	/**
	 * Cette classe cree un Personnage Heros
	 */
	public final String nomDepart;
	public final int pvMaxDepart;
	public final String classe;
	public int manaMax;
	public int mana;
	public int or;
	public static final String GUERRIER = "guerrier";
	public static final String MAGE = "mage";
	public static final String VOLEUR = "voleur";
	public ArrayList<Effet> listePouvoir;
	public int emplacementX;
	public int emplacementY;

	
	public Heros(String nom, Deck d, int pvM, int manaD, ArrayList<Effet> e, String classe) {
		super(nom, d, pvM, true);
		this.nomDepart = nom;
		this.pvMaxDepart = pvM;
		this.manaMax = manaD;
		this.or = 100;
		this.listePouvoir = e;
		this.mana = this.manaMax;
		this.emplacementX = 0;
		this.emplacementY = 0;
		this.classe = classe;
	}
	public void paintEquipement(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(18,8,204,34);
		g.setColor(new Color(176, 216, 230));
		g.fillRect(20,10,200,30);
		int nbEquip = 8;
		for (int i = 0; i<this.listeEquipement.size(); i++) {
			g.setColor(this.listeEquipement.get(i).couleur);
			g.fillRect(20+((200/nbEquip)*i),10,200/nbEquip,30);
			g.setColor(Color.black);
			g.drawString(this.listeEquipement.get(i).nom.substring(0,1), 20+(200*i/nbEquip)+(200/nbEquip/2)-5, 30);
		}
		g.setColor(Color.black);
		for (int i = 1; i<nbEquip; i++) {
			g.fillRect(20+200*i/nbEquip,10,2,30);
		}
	}
}
