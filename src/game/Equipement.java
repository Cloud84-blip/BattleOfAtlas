package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Equipement {
	/**
	 * Cette classe gere a la fois les equipements detenus par le Heros, et les buffs et debuffs infliges au Heros et a l'Ennemi.
	 */
	public int id;
	public String nom;
	public String classe;
	public int valeur;
	public Color couleur;
	private EquipementPanel panel;
	public BufferedImage img;

	public Equipement(int i, String n, String c, int v) {
		this.id = i;
		this.nom = n;
		this.classe = c;
		this.valeur = v;
		switch (this.classe) {
			case "Attaque":
				this.couleur = Color.red;
				this.panel = new EquipementPanel(Color.red);
				break;
			case "Defense":
				this.couleur = Color.blue;
				this.panel = new EquipementPanel(Color.blue);
				break;
			case "Poison":
				this.couleur = Color.magenta;
				this.panel = new EquipementPanel(Color.magenta);
				break;
			default:
				this.couleur = Color.black;
				this.panel = new EquipementPanel(Color.black);
				break;
		}
	}
	
	public EquipementPanel getEquipementPanel() {
		return this.panel;
	}
	
	public void imgLoader(){
		try {
			switch(this.nom) {
				case "Orbe Rouge" :
					this.img = ImageIO.read(new File(CombatPanel.getPath()+"/assets/autres/orbeRouge.png"));
					break;
				case "Orbe Bleue" :
					this.img = ImageIO.read(new File(CombatPanel.getPath()+"/assets/autres/orbeBleue.png"));
					break;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		switch (this.classe) {
			case "Attaque":
				return "Augmente vos degats physiques de " + this.valeur + ".";
			case "Defense":
				return "Reduit vos degats physiques subits de " + this.valeur + ".";
			case "Poison":
				return "Subissez l'equivalent de " + this.valeur + "% de vos PV Max.";
			default:
				return "Not found";
		}
	}

}
