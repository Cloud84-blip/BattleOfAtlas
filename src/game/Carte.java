package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Carte {
	/**
	 * Cette classe permet de creer les cartes utilisees pour attaquer ou se defendre
	 */
	private final int id;
	private final String nom;
	public  Color c;
	public String CardPath = CombatPanel.getPath()+"/assets/cartes/";
	private int cout;
	private ArrayList<Effet> listeEffets;
	private CartePanel cartePanel;
	private Boolean estPioche;
	public int idEffet;
	public BufferedImage dosCarte = imgLoader(CardPath+"Dos-Carte-Perspective.png");

	public BufferedImage img;
	
	public Carte(int id, String nom, int cout, Color c, Boolean b, ArrayList<Effet> e) {
		//Carte Sans Png
		this.id = id;
		this.nom = nom;
		this.setCout(cout);
		this.setCartePanel(new CartePanel(c));
		this.listeEffets = e;
		this.setEstPioche(false);
	}
	
	public Carte(int id, String nom, int cout, int idEffet, String path, ArrayList<Effet> e) {
		//Avec Png
		this.id = id;
		this.nom = nom;
		this.setCout(cout);
		this.listeEffets = e;
		this.idEffet = idEffet;
		this.setCartePanel(new CartePanel(imgLoader(path)));
		this.setEstPioche(false);
	}

	public BufferedImage imgLoader(String path){
		try {
			this.img = ImageIO.read(new File(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this.img;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public ArrayList<Effet> getListeEffets() {
		return listeEffets;
	}

	public void setListeEffets(ArrayList<Effet> listeEffets) {
		this.listeEffets = listeEffets;
	}

	public CartePanel getCartePanel() {
		return cartePanel;
	}

	public void setCartePanel(CartePanel cartePanel) {
		this.cartePanel = cartePanel;
	}

	public Boolean getEstPioche() {
		return estPioche;
	}

	public void setEstPioche(Boolean estPioche) {
		this.estPioche = estPioche;
	}
	
	public void activer(CombatPanel j) { // Jouer la carte
		Personnage p = j.ordreTours[j.numTour];
		for (Effet e : this.listeEffets) { // Pour chaque effet dans la carte
			j.console.log("Id : " + e.id + " Effet : " + e.effet_toString()); // On l'affiche dans la console integree
			j.console.log("Source : " + e.source);
			j.console.log("Cible : " + e.cible);
			j.repaint();
			switch(e.id) {
			case 0 : // soigne pv du lanceur
				AnimationPos.CastAnimation();
				if (!e.source.estJoueur){
					AnimationPos.anim_pos -= 1;
				}
				e.source.panel.isAnimOn = true;
				e.source.setPvActuel(e.source.getPvActuel()+e.nb);
				break;
			case 1 : // Inflige x degats actuels
				AnimationPos.SlashAnimation();
				if (!e.source.estJoueur){
					AnimationPos.anim_pos -= 2;
				}
				e.source.panel.isAnimOn = true;
				p.calculDegat(e.nb, j.h, e.cible);
				break;
			case 2 : // Pioche x cartes
				Deck tmp = j.h.deck.piocher(j, Math.abs(e.nb));
				j.main.ajouterEtViderDeck(tmp);
				break;
			case 3 : // Passer son tour
				j.console.log(j.ordreTours[j.numTour].getNom() + "passe son tour.");
				break;
			case 4 : // Debuff : x poison
				AnimationPos.CastAnimation();
				if (!e.source.estJoueur){
					AnimationPos.anim_pos -= 1;
				}
				e.source.panel.isAnimOn = true;
				e.cible.ajouteDebuffPoison(e.nb);
			}
		}
	}
}
