package game;

import java.util.*;

public class Effet {
	/**
	 * Cette classe cree les effets utilises par les cartes, tels que les degats, l'empoisonnement ou le soin.
	 */
	public Random rdm = new Random();
	public int id;
	public int nb;
	public String s;
	public Personnage cible;
	public Personnage source;

	public Effet(Personnage p, Personnage cible) {
		int idMax = 4;
		ArrayList<Integer> idReserveJoueur = new ArrayList<Integer>();
		idReserveJoueur.add(2);
		ArrayList<Integer> idReserveEnnemi = new ArrayList<Integer>();
		idReserveEnnemi.add(3);
		this.source = p;
		if (!p.estJoueur) {
			do {
				this.id = rdm.nextInt(idMax+1);
			} while (idReserveJoueur.contains(this.id));
		}
		else {
			do {
				this.id = rdm.nextInt(idMax+1);
			} while (idReserveEnnemi.contains(this.id));
		}
		this.nb = rdm.nextInt(10)+1;
		switch (this.id) {
			case 2 :
				this.cible = p;
				this.nb = Math.abs(this.nb);
				break;
			case 4 :
				this.cible = cible;
				this.nb = Math.abs(this.nb);
				break;
			default :
				this.cible = cible;
				break;
		}
	}

	public Effet(Personnage p, Personnage cible, int val, int id) {
		this.source = p;
		this.cible = cible;
		this.nb = val;
		this.id = id;
	}

	
	public void jeter_carte(Deck main, Deck defausse, int nombre) {
		Deck tmp = main.defausser(nombre);
		defausse.ajouterEtViderDeck(tmp);
	}
	
	public void jeter_carte_random(Deck main, Deck defausse) {
		Deck tmp = main.defausserPos(this.rdm.nextInt());
		defausse.ajouterEtViderDeck(tmp);
	}
	
	public String effet_toString() {
		switch (id) {
			case 0 :
				return nb + " PV Max";
			case 1 :
				return nb + " PV";
			case 2 :
				return "Piocher " + nb + " cartes";
			case 3 :
				return "Passe son tour";
			case 4 :
				return "Inflige " + nb + "poison";
			default :
				return "error";
		}
	}
}
