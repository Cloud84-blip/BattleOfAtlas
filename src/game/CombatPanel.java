package game;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CombatPanel extends EcranPanel {
	/**
	 * Affiche et Gere les combats. (Notre premiere grosse classe du projet, donc c'est pas tres bien range desole...)
	 */
	private static final long serialVersionUID = 1L;
	private int mousPosX = 0;
	private int mousPosY = 0;
	public Deck main;
	public Deck defausse = new Deck();
	public Graphics2D g2d;
	public Heros h;
	public Ennemi e;
	public Personnage[] ordreTours;
	public int numTour = 0;
	
	public final int xExit = 1100;
	public final int yExit = 650;
	public final int widthExit = 120;
	public final int heightExit = 50;

	public CombatPanel(String pNAME, FrameMain master) {
		super(pNAME, master);
		this.initBackground("/assets/fond/FOND-" + FrameMain.zoneActuelle + ".png");
		this.initPanel();
	}

	public void activate(Heros h, Ennemi e){
		this.h = h;
		this.e = e;
		ordreTours = creer_ordre_tours(this.h, this.e);
		this.initCombat();
		this.initListeners();
	}

	public void initCombat(){ // reset le combat
		this.e.setPvActuel(this.e.getPvMax());
		this.h.listeBuffs = new ArrayList<Equipement>();
		this.e.listeBuffs = new ArrayList<Equipement>();
		this.h.panel.changeSpritePos(390, 190, 15, 5);
		
		if (!FrameMain.initCombatPasse) {
			this.h.deck.DeckDeDepart(this.h, this.master_frame.cp.e);
			this.h.setPvActuel(this.h.getPvMax());
			FrameMain.initCombatPasse = true;
		}
		else {
			this.h.deck.ajouterEtViderDeck(this.main);
			this.h.deck.ajouterEtViderDeck(this.defausse);
			this.h.mana = this.h.manaMax;
		}
		
		this.h.deck.melanger();
		this.main = piocheDebutCombat(5);
		this.e.panel.initPanelSprite(getPath()+"/assets/perso/" + e.getNom() + ".png", 850, 190, 1, 5);
	}

	public void initListeners(){
		this.activeMouseListener = new CombatMouseListener(this.master_frame, this);
		this.activeKeyListeners = new CombatKeyListener(this);
	}


	public static String getPath(){
		String currentPath = null;
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return currentPath;
	}
	
	void setMousPosX(int x) {
		// TODO Auto-generated method stub
		this.mousPosX = x;
	}
	
	void setMousPosY(int y) {
		// TODO Auto-generated method stub
		this.mousPosY = y;
	}
	
	int getMousPosX() {
		// TODO Auto-generated method stub
		return this.mousPosX;
	}
	
	int getMousPosY() {
		// TODO Auto-generated method stub
		return this.mousPosY;
	}
	
	
	public boolean tourHeros() {
		return this.ordreTours[this.numTour] == this.h;
	}

	public void paint(Graphics g) { // Affichage de tous les elements
		if (!this.estVisible) return;
		this.g2d = (Graphics2D) g;
		g.drawImage(this.bg, 0, 0, null);
		paintOrdreTours(g);
		int offsetX = 0;
		int offsetY = 0;
		for (Carte c : this.h.deck.getListeCarte()) { // Peindre le deck
			int width = c.getCartePanel().getWidth();
			int height = c.getCartePanel().getHeight();
			this.paintCard(g, c, 1100 - offsetX, 400 - offsetY);
			offsetX += width / 100;
			offsetY += height / 100;
		}
		
		int newoffsetX = 0; // Peindre la main
		for (Carte c : this.main.getListeCarte()) {
			int width = c.getCartePanel().getWidth();
			switch (c.getListeEffets().get(0).id){
				case 0:
				case 1:
				case 4:
					c.getCartePanel().paint(g, 220 + newoffsetX, 540);
					break;
				default:
					this.paintCard(g, c, 220+newoffsetX, 540);
					break;
			}
			newoffsetX += width;
		}
		
		int newnewoffsetX = 0;
		int newnewoffsetY = 0; // Peindre la defausse
		for (Carte c : this.defausse.getListeCarte()) {
			int width = c.getCartePanel().getWidth();
			int height = c.getCartePanel().getHeight();
			this.paintCard(g, c, 1150 - newnewoffsetX, 180 - newnewoffsetY);
			newnewoffsetX += width / 100;
			newnewoffsetY += height / 100;
		}
		
		paintBarrePV(g, this.h, 325, 100); // barre de vie Heros
		this.h.panel.paint(g); // peindre le Heros
		this.h.paintEquipement(g); // Equipement Heros

		paintBarrePV(g, this.e, 775, 100);// peindre Ennemi
		paintNomEnnemi(g);
		this.e.panel.paint(g);

		paintBuffs(g, this.h, 325, 80); // Peindre les bufs
		paintBuffs(g, this.e, 775, 80);

		paintMana(g, 250, 90, this.h.mana); // Peindre la mana

		if (this.console.getIsShowing()) {
			this.console.affiche_console(g);
		}
		
		paintTerminerTour(g); // Peindre le bouton pour terminer le tour
		
		this.passer_tour_ennemi();
		this.checkPv(); // check tout le temps si des PV sont tombes a 0
		g.dispose();
	}

	public void paintMana(Graphics g, int x ,int y, int mana){
		g.setColor(Color.black);
		g.fillOval(x, y, 50, 50);
		g.setColor(new Color(31, 168, 242));
		g.fillOval(x+1, y+1, 48, 48);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(Integer.toString(mana), x+21, y+30);
	}
	
	public void paintBarrePV(Graphics g, Personnage c, int x, int y) {
		g.setColor(Color.black);
		g.fillRect(x-2,y-2,204,34);
		g.setColor(Color.red);
		g.fillRect(x,y,200,30);
		if (c.getPvMax() != 0) {
			g.setColor(Color.green);
			g.fillRect(x,y,(200*c.getPvActuel()) / c.getPvMax(), 30);
		}
		g.setColor(Color.BLACK);
		g.drawString("PV:" + c.getPvActuel() + "/" + c.getPvMax(), x+75, y+15);
	}
	
	public void paintCard(Graphics g, Carte c, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(c.dosCarte, x, y, null);
	}
	
	public void paintPerspectiveCard(Graphics g, Carte c, int x, int y) {
		int width = c.getCartePanel().getWidth();
		int height = c.getCartePanel().getHeight();
		int[] coinBasGauche = new int[] {x, y + height};
		int[] coinBasDroit = new int[] {x + width, y + height};
		int[] coinHautDroit = new int[] {(x + (width + (width / 2))), y};
		int[] coinHautGauche = new int[] {(x + width / 2), y};
		int[] xPoints = new int[] {coinBasGauche[0], coinBasDroit[0], coinHautDroit[0], coinHautGauche[0]};
		int[] yPoints = new int[] {coinBasGauche[1], coinBasDroit[1], coinHautDroit[1], coinHautGauche[1]};
		g.fillPolygon(xPoints, yPoints, xPoints.length);	
	}
	
	public void paintOrdreTours(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(998,8,204,34);
		g.setColor(Color.red);
		g.fillRect(1000,10,200,30);
		int nbCases = this.ordreTours.length;
		g.setColor(Color.green);
		g.fillRect(1000+((200/nbCases)*this.numTour),10,200/nbCases,30);
		g.setColor(Color.black);
		for (int i = 1; i<nbCases; i++) {
			g.fillRect(1000+200*i/nbCases,10,2,30);
		}
		for (int i = 0; i<nbCases; i++) {
			int i1 = 1000 + (200 * i / nbCases) + (200 / nbCases / 2) -5;
			if (this.ordreTours[i].estJoueur)
				g.drawString("H", i1, 30);
			else
				g.drawString("E", i1, 30);
		}
	}

	public void paintBuffs(Graphics g, Personnage p, int x, int y) {
		ArrayList<Equipement> listeB = p.getListeBuffs();

		for (int i = 0; i< listeB.size(); i++) {
			g.setColor(listeB.get(i).couleur);
			g.fillRect(x+20*i, y, 20, 20);
			g.setColor(Color.white);
			g.drawString(listeB.get(i).classe.charAt(0) + " " + listeB.get(i).valeur, x+ 20*i, y);
		}
	}
	
	public void paintNomEnnemi(Graphics g) {
		String nom = this.master_frame.topPanel.curEnnemi.nom;
		g.setColor(Color.white);
		g.drawString(nom,775,145);
	}
	
	public void paintTerminerTour(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(xExit, yExit, widthExit, heightExit);
		g.setColor(Color.white);
		g.drawString("Terminer", xExit+10, yExit+25);
	}
	
	public Personnage[] creer_ordre_tours(Personnage a, Personnage b) { // Nous voulions implementer un systeme de vitesse permettant aux personnages d'agir potentiellement plusieurs fois d'affille, mais par manque de temps nous avons du l'enlever
		Personnage[] res = new Personnage[4];

		for (int i = 0; i<4; i++) { // Renvoie simplement un tableau a 4 cases qui alterne entre les deux personnages
			if (i % 2 == 0)
				res[i] = h;
			else
				res[i] = e;
		}
		return res;
	}
	
	public Deck piocheDebutCombat(int i) { // permet de piocher i cartes
		return this.h.deck.piocher(this, i);
	}
	
	public void passer_tour_ennemi() {
		Personnage e = this.ordreTours[this.numTour];
		if (e == this.h) return;
		this.tourEnnemi(e);
	}

	public void tour_fini() { // incremente le tour
		if (this.numTour < this.ordreTours.length - 1) {
			this.numTour += 1;
		} else {
			this.numTour = 0;
		}
	}

	public void tourEnnemi(Personnage p) { // L'ennemi joue
		this.console.log("Tour de :" + p.getNom());
		FrameMain.sleep(500);
		this.repaint();
		Carte c = p.tirerCarteAleatoire();
		c.activer(this);
		this.effetsFinTour();
		this.tour_fini();
	}

	public void effetsFinTour() { // Active les effets de fin de tour, tels que le poison
		Personnage p = this.ordreTours[this.numTour];
		ArrayList<Equipement> buffs = p.getListeBuffs();
		int degatPoison;
		for (Equipement buff : buffs) {
			switch (buff.classe) {
				case "Poison" :
					degatPoison = p.getPvMax()*buff.valeur/100;
					if (degatPoison == 0){
						degatPoison = 1;
					}
					p.setPvActuel(p.getPvActuel() - degatPoison);
				default :
					return;
			}
		}
	}

	public void checkPv(){
		if (this.h.getPvActuel() == 0){
			AnimationPos.DeathAnimation();
			this.h.panel.isAnimOn = true;
			this.h.panel.currentSpritePos = this.h.panel.lastSpritePos;
			// si le heros meurt on retourne au menu
			this.h.setPvActuel(this.h.getPvMax());
			FrameMain.combatBoss = false;
			FrameMain.zoneActuelle = "Egouts";
			FrameMain.initCombatPasse = false;
			this.master_frame.switchCurPanel(0);

		} else if (this.e.getPvActuel() == 0){
			AnimationPos.DeathAnimation();
			this.e.panel.isAnimOn = true;
			this.e.panel.currentSpritePos = this.e.panel.lastSpritePos;
			// si l'ennemi est mort on retourne sur l'ecran des recompenses
			if (FrameMain.zoneActuelle == "Neant") this.master_frame.switchCurPanel(6); // Si c'etait le Boss final, ecran de fin
			else this.master_frame.switchCurPanel(3);
			if (FrameMain.combatBoss) { // Si c'etait un combat de boss
				this.master_frame.rp.orGagne = 150;

				this.h.emplacementX = 0;
				this.h.emplacementY = 0;
				switch(FrameMain.zoneActuelle) { // On passe a la zone suivante
					case "Egouts" :
						FrameMain.zoneActuelle = "Labyrinthe";
						break;
					case "Labyrinthe" :
						FrameMain.zoneActuelle = "Enfers";
						break;
					case "Enfers" :
						FrameMain.zoneActuelle = "Paradis";
						break;
					case "Paradis" :
						FrameMain.zoneActuelle = "Neant";
						break;
					case "Neant" :
						this.master_frame.switchCurPanel(6);
						break;
					default :
						break;
				}
				this.initBackground("/assets/fond/FOND-" + FrameMain.zoneActuelle + ".png"); // on load le bg suivant
				FrameMain.combatBoss = false;
				this.master_frame.cmp.reset(); // Le boss etant vaincu on reinitialise la carte
			}
			else {
				this.master_frame.rp.orGagne = 50;
			}
			
		}
	}
}
