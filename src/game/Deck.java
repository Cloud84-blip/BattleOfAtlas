package game;


import java.awt.Color;
import java.util.*;
import parser.*;


public class Deck {
	/**
	 * Gere les paquets de Cartes
	 */
	private ArrayList<Carte> listeCarte;
	public String CardPath = CombatPanel.getPath()+"/assets/cartes/";
	String psnCarte = CardPath+4+".png";
	String atkCarte = CardPath+1+".png";
	String soinCarte = CardPath+0+".png";
	private final Color[] ColorList = new Color[] {
			Color.red,
			Color.white,
			Color.green,
			Color.yellow,
			Color.darkGray,
			Color.pink,
			Color.cyan
			};
	public Random random = new Random();
	
	
	public Deck() {
		this.creerVide();
	}

	public void DeckDeDepart(Personnage source, Personnage cible){
		if (this.getListeCarte().size() == 0){
			String nom;
			if (source.estJoueur) {
				nom = ((Heros) source).classe;
			}
			else {
				nom = source.getNom();
			}
				String[] names = DataParser.getCardNameList(nom);
				assert names != null;
				int nombre;
				for (String name : names) {
					int asciiDernierCaractere = (int) name.charAt(name.length()-1); 
					if (asciiDernierCaractere <= 57 && asciiDernierCaractere >= 48) { // On check si le dernier caractere est un chiffre (0-9)
						nombre = asciiDernierCaractere-48;
						Carte c = DataParser.getCarte(source, cible, name.substring(0,name.length()-1).trim()); // On enleve le chiffre pour pouvoir chercher la carte
						for (int i=0;i<nombre;i++) { // Si oui, la carte est ajoutee n fois
							this.ajouterCarte(c);
						}
					}
					else { // Sinon, une seule fois
						Carte c = DataParser.getCarte(source, cible, name.trim());
						this.ajouterCarte(c);
					}
				}
		}
	}
	
	
	public static Deck getDeckRecompenseBoutique (Personnage source, Personnage cible, int nbDeCartes) {
		Deck d = new Deck();
		String lieu = FrameMain.zoneActuelle;
		String[] names = DataParser.getCardNameLieu(lieu);
		assert names != null;
		for (int i = 0; i< nbDeCartes; i++) {
			String name = names[FrameMain.random.nextInt(names.length)];
			Carte c = DataParser.getCarte(source, cible, name.trim());
			d.ajouterCarte(c);
		}
		return d;
	}
	
	public static Deck getDeckRecompenseBoss (Personnage source, Personnage cible, int nbDeCartes) {
		Deck d = new Deck();
		String lieu = FrameMain.zoneActuelle;
		String[] names = DataParser.getCardNameBoss(lieu);
		assert names != null;
		for (int i = 0; i< nbDeCartes; i++) {
			String name = names[FrameMain.random.nextInt(names.length)];
			Carte c = DataParser.getCarte(source, cible, name.trim());
			d.ajouterCarte(c);
		}
		return d;
	}
	
	public static HashMap<String, Deck> dictDeckEnnemis(Personnage source, Personnage cible) {
		HashMap<String, Deck> res = new HashMap<String, Deck>();
		ArrayList<String> nomsEnnemis = DataParser.getEnnemiList();
		for (String nom : nomsEnnemis) {
			String[] names = DataParser.getCardNameListEnnemi(nom);
			assert names != null;
			int nombre;
			Deck d = new Deck();
			for (String name : names) {
				int asciiDernierCaractere = (int) name.charAt(name.length()-1); 
				if (asciiDernierCaractere <= 57 && asciiDernierCaractere >= 48) { // On check si le dernier caractere est un chiffre (0-9)
					nombre = asciiDernierCaractere-48;
					Carte c = DataParser.getCarte(source, cible, name.substring(0,name.length()-1).trim()); // On enleve le chiffre pour pouvoir chercher la carte
					for (int i=0;i<nombre;i++) { // Si oui, la carte est ajoutee n fois
						d.ajouterCarte(c);
					}
				}
				else { // Sinon, une seule fois
					Carte c = DataParser.getCarte(source, cible, name.trim());
					d.ajouterCarte(c);
				}
			}
			res.put(nom, d);
		}
		return res;
	}

	public void melanger() {
		ArrayList<Carte> tmp = this.getListeCarte();
		Collections.shuffle(tmp);
		this.setListeCarte(tmp);
	}

	private void creerVide() {
		this.setListeCarte(new ArrayList<Carte>());
	}
	
	void ajouterCarte(Carte c) {
		ArrayList<Carte> tmp = this.getListeCarte();
		tmp.add(c);
		this.setListeCarte(tmp);
	}
	
	void ajouterEtViderDeck(Deck d) {
		Carte c;
		while ((c = d.popCarte()) != null) {
			this.ajouterCarte(c);
		}
	}
	
	private Carte pop() throws Deck.DeckException {
		ArrayList<Carte> tmp = this.getListeCarte();
		if (tmp.size() == 0) throw new DeckException();
		Carte c = tmp.get(tmp.size() -1);
		tmp.remove(tmp.size() - 1);
		this.setListeCarte(tmp);
		return c;
	}
	
	public Carte popCarte() {
		Carte c;
		try {
			c = this.pop();
		} catch (Deck.DeckException e) {
			c = null;
		}
		return c;
	}

	public ArrayList<Carte> getListeCarte() {
		return listeCarte;
	}

	public void setListeCarte(ArrayList<Carte> listeCarte) {
		this.listeCarte = listeCarte;
	}
	
	public Deck piocher(CombatPanel p, int nombre) {
		Deck pioche = new Deck();
		for (int i=0; i<nombre; i++) {
			Carte c = this.popCarte();
			if (c != null) {
				pioche.ajouterCarte(c);
				c.setEstPioche(true);
			}
			else {
				if (p.defausse.listeCarte.size() != 0) {
					Deck tmp = p.defausse.defausser_tout();
					p.h.deck.ajouterEtViderDeck(tmp);
					p.h.deck.melanger();
					i--;
				}
			}
		}
		return pioche;
	}
	
	public Deck piocher_tout(CombatPanel p) {
		return this.piocher(p, this.getListeCarte().size());
	}
	
	public Deck defausser(int nombre) {
		Deck defausse = new Deck();
		for (int i=0; i<nombre; i++) {
			Carte c = this.popCarte();
			if (c != null) {
				defausse.ajouterCarte(c);
				c.setEstPioche(false);
			}
		}
		return defausse;
	}
	
	public Deck defausserPos(int pos) {
		Deck defausse = new Deck();
		try {
			defausse.ajouterCarte(this.listeCarte.remove(pos));
		} catch (IndexOutOfBoundsException e) {
			Deck.DeckException d = new DeckException();
			d.printNotInDeckException();
		}
		return defausse;
	}
	
	public Deck defausser(Carte c) {
		Deck defausse = new Deck();
		for (Carte carte : this.listeCarte) {
			if (Objects.equals(carte, c)) {
				c.setEstPioche(false);
				defausse.ajouterCarte(carte);
				this.listeCarte.remove(carte);
				return defausse;
			}
		}
		return defausse;
	}
	
	public Deck defausser_tout() {
		return this.defausser(this.getListeCarte().size());
	}

	public void genereDeckDeTest(int nbDeCartes, Personnage p, Personnage cible) {
		Effet e;
		ArrayList<Effet> ae;
		
		for (int i=0; i<nbDeCartes; i++) {
			e = new Effet (p, cible);
			ae = new ArrayList<>();
			ae.add(e);
			switch (e.id){
				case 0:
					this.ajouterCarte(new Carte(i, "TEST"+(i+1), 1, 0, soinCarte, ae));
					break;
				case 1:
					this.ajouterCarte(new Carte(i, "TEST"+(i+1), 1, 1, atkCarte, ae));
					break;
				case 4:
					this.ajouterCarte(new Carte(i, "TEST"+(i+1), 1, 4, psnCarte, ae));
					break;
				default:
					this.ajouterCarte(new Carte(i, "TEST"+(i+1), 1, this.ColorList[this.random.nextInt(this.ColorList.length)], false, ae));
					break;
			}
		}
	}
	
	public static class DeckException extends Exception{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		
		void printEmptyDeckException() {
			System.out.println("Le Deck est vide");
		}
		
		void printNotInDeckException() {
			System.err.println("Item not in Deck");
		}
	}
	
}
