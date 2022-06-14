package game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Personnage {
	/**
	 * Cette classe cree un Personnage, qui peut soit etre un Heros ou un Ennemi.
	 */
	public String nom;
	public Deck deck;
	public int pvMax;
	private int pvActuel;
	public final boolean estJoueur;
	public PersonnagePanel panel;
	public ArrayList<Equipement> listeEquipement;
	public ArrayList<Equipement> listeBuffs;
	public static final String persoPath = CombatPanel.getPath() + "/assets/perso/";
	public String path;
	
	public Personnage(String nom, Deck deck, int pvMax, boolean estJoueur) {
		this.nom = nom;
		this.deck = deck;
		this.pvMax = pvMax;
		this.setPvActuel(pvMax);
		this.panel = new PersonnagePanel();
		this.estJoueur = estJoueur;
		this.listeEquipement = new ArrayList<>();
		this.listeBuffs = new ArrayList<>();
		this.path = persoPath + this.nom + ".png";

	}

	public int getPvMax() {
		return pvMax;
	}
	
	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public String getNom() {
		return nom;
	}

	public Deck getDeck() {
		return deck;
	}

	public int getPvActuel() {
		return pvActuel;
	}
	
	public ArrayList<Equipement> getListeEquipement(){
		return listeEquipement;
	}
	
	public ArrayList<Equipement> getListeBuffs(){
		return listeBuffs;
	}

	public void setPvActuel(int pvA) {
		if (pvA > this.pvMax) {
			this.pvActuel = pvMax;
		}
		else this.pvActuel = Math.max(pvA, 0);

	}
	
	public void changePvActuel(int i) {
		if (this.pvActuel + i > this.pvMax)
			this.pvActuel = this.pvMax;
		else if(this.pvActuel + i < 0)
			this.pvActuel = 0;
		else
			this.pvActuel += i;
	}
	
	public void changePvMax(int i) {
		if (this.pvMax + i < 0) {
			this.pvMax = 0;
			this.pvActuel = 0;
		}
		else if (this.pvMax + i < this.pvActuel) {
			this.pvMax += i;
			this.pvActuel = this.pvMax;
		}
		else
			this.pvMax += i;
	}
	
	public void calculDegat(int i, Personnage heros, Personnage cible) { // utilise pour infliger des degats, en prenant en compte les equipements
		int degats = i;
		for (Equipement equi : heros.listeEquipement) {
			if (Objects.equals(equi.classe, "Attaque") && !cible.estJoueur) {
				degats += equi.valeur;
			}
			else if (Objects.equals(equi.classe, "Defense") && cible.estJoueur) {
				degats -= equi.valeur;
			}
			if (degats < 0) degats = 0;
		}
		
		if (cible.pvActuel - degats > cible.pvMax)
			cible.pvActuel = cible.pvMax;
		else if(cible.pvActuel - degats < 0)
			cible.pvActuel = 0;
		else
			cible.pvActuel -= degats;
	}
	
	public void ajouteDebuffPoison(int i) { // Ajouter du poison a la cible
		for (Equipement buff : this.listeBuffs) {
			if (Objects.equals(buff.classe, "Poison")) {
				buff.valeur +=  i;
				if (buff.valeur == 0) this.listeBuffs.remove(buff);
				return;
			}
		}
		Equipement debuffPoison = new Equipement (4, "Subit des degats chaque tour", "Poison", i);
		this.listeBuffs.add(debuffPoison);
	}
	
	public Carte tirerCarteAleatoire() { // Tire une carte aleatoire dans le deck du personnage
		Random random = new Random();
		return this.deck.getListeCarte().get(random.nextInt(this.deck.getListeCarte().size()));
	}
}
