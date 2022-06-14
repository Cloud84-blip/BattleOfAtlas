package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CarteMondePanel extends EcranPanel {
	/**
	 * Affiche la Carte du Monde.
	 */
	private static final long serialVersionUID = 1L;
	public Heros h;
	public int[][] tableau;
	public Random random = new Random(System.currentTimeMillis());
	public BufferedImage epee;
	public BufferedImage camp;
	public BufferedImage boutique;
	
	public TopPanel tp;

	public CarteMondePanel(String pNAME, FrameMain master) {
		super(pNAME, master);
		this.initBackground("/assets/fond/cartedumonde.png");
		this.initPanel();
		this.initListeners();
		imgLoader();
		this.tableau = creerTableau();
	}

	public void activate(Heros h){
		this.h = h;
		this.h.panel.isAnimOn = false;
	}


	public void initListeners(){
		this.activeKeyListeners = new CarteMondeKeyListener(this.master_frame, this);
	}
	
	public int[][] creerTableau(){
		int id = random.nextInt(FrameMain.MAX_ID_CARTE_MONDE);
		int[][] t;
		switch (id) {
			case 0 :
				t = new int[4][4];
				break;
			case 1 :
				t = new int[5][6];
				break;
			case 2 :
				t = new int[6][3];
				break;
			default :
				t = new int[3][3];
		}
		for (int i = 0; i< t.length; i++) {
			for (int j = 0; j< t[i].length; j++) {
				do {
					t[i][j] = random.nextInt(6);
				}
				while (t[i][j] != 1 && t[i][j] != 4 && t[i][j] != 5); // On autorise que les id 1 4 et 5 (2 etant la Carte du Monde et 3 les Recompenses de fin de combat).
			}
		}
		
		if (FrameMain.zoneActuelle == "Neant") {
			t = new int[4][1]; // Si derniere zone du jeu, couloir predefini
			t[0][0] = 2;
			t[1][0] = 4;
			t[2][0] = 5;
		}
		
		// La derniere case est toujours le Boss (id 1)
		t[t.length-1][t[0].length-1] = 1;
		return t;
	}

	public void imgLoader(){
		try {
			this.epee = ImageIO.read(new File(CombatPanel.getPath()+"/assets/autres/sword.png"));
			this.camp = ImageIO.read(new File(CombatPanel.getPath()+"/assets/autres/camp.png"));
			this.boutique = ImageIO.read(new File(CombatPanel.getPath()+"/assets/autres/boutique.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		if (!this.estVisible) return;
		g.drawImage(this.bg, 0, 0, null);
		g.setColor(Color.red);
		g.drawString("Or : " + this.master_frame.topPanel.curHeros.or, 1100, 20);
		int x = 50;
		int x0 = x;
		int y = 50;
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i< this.tableau.length; i++) {
			for (int j = 0; j< this.tableau[i].length; j++) {
				g.drawRect(x, y, 50, 50);
				if (!(i == 0 && j == 0)) { // la case en haut a gauche reste vide
					switch (this.tableau[i][j]) {
					case 1 :
						g2d.drawImage(epee, x, y, null); // dessiner une epee si c'est un combat
						break;
					case 4 :
						g2d.drawImage(boutique, x, y, null); // dessiner une piece pour la boutique
						break;
					case 5 :
						g2d.drawImage(camp, x, y, null); // dessiner un feu de camp pour la zone de repos
						break;
					}
				}
				x+=50;
			}
			x=x0;
			y+=50;
		}
		this.h.panel.paint(g, 45+50*this.h.emplacementX, 40+50*this.h.emplacementY);

		g.dispose();
	}
	
	public void reset() {
		this.tableau = creerTableau();
	}
}
