package game;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;

public class FrameMain extends JFrame{
	/**
	 * Classe principale du programme, le main est present ici et permet de lancer les autres classes.
	 */
	private static final long serialVersionUID = 1L;
	public final int DEFAULT_WIDTH = 1280;
	public final int DEFAULT_HEIGHT = 720;
	public static int idCurPanel;
	public static Random random = new Random(System.currentTimeMillis());
	public static final int MAX_ID_CARTE_MONDE = 3;
	private final ArrayList<EcranPanel> panels;
	public Heros h = new Heros("Herve", new Deck(), 55, 4, new ArrayList<Effet>(), Heros.GUERRIER);
	public Heros z = new Heros("Zoe", new Deck(), 45, 6, new ArrayList<Effet>(), Heros.VOLEUR);
	public Heros m = new Heros("Mathilde", new Deck(), 35, 8, new ArrayList<Effet>(), Heros.MAGE);
	public Ennemi e = new Ennemi("Bateleur", new Deck(), 80);
	
	public static String zoneActuelle = "Egouts";
	public static boolean combatBoss = false;
	public static boolean initCombatPasse = false;
	
	public static HashMap<String, Deck> decksEnnemis = new HashMap<String, Deck>();
	
	public Boutique boutique;
	public String MENU = "menu";
	public String COMBAT = "combat";
	public String CARTE_DU_MONDE = "carte du monde";
	public String RECOMPENSE = "recompense";
	public String BOUTIQUE = "boutique";
	public String ZONE_DE_REPOS = "zone de repos";
	public String FIN = "fin";
	public TopPanel topPanel;
	public CardLayout cardLayout;
	public CombatPanel cp;
	public CarteMondePanel cmp;
	public RecompensePanel rp;
	public BoutiquePanel bp;
	public FinPanel fp;
	public ZoneReposPanel zrp;
	public MenuPanel mp;

	public FrameMain() {
		this.topPanel = new TopPanel(false);
		
		this.panels = new ArrayList<>();
		this.setidCurPanel(0);
		
		mp = new MenuPanel(MENU, h, z, m, this, this.topPanel);
		cmp = new CarteMondePanel(CARTE_DU_MONDE, this);
		rp = new RecompensePanel(RECOMPENSE, this, Deck.getDeckRecompenseBoutique(this.h, this.e, 3), 50);
		bp = new BoutiquePanel(BOUTIQUE, this);
		cp = new CombatPanel(COMBAT, this);
		zrp = new ZoneReposPanel(ZONE_DE_REPOS, this);
		fp = new FinPanel(FIN, this);

		this.panels.add(mp); // MENU : ID 0
		this.panels.add(cp); // COMBAT : ID 1
		this.panels.add(cmp);// CARTEMONDE : ID 2
		this.panels.add(rp); // RECOMPENSE : ID 3
		this.panels.add(bp); // BOUTIQUE : ID 4
		this.panels.add(zrp); // ZONE DE REPOS : ID 5
		this.panels.add(fp); // FIN : ID 6

		this.getCurPanel().setListener();
		this.getCurPanel().estVisible = true;
		this.initLayout();
		this.initBoutique();
		this.initWindow(new Dimension(getDefaultWidth(), DEFAULT_HEIGHT));
	}

	public void initLayout(){ // Ajoute tous les differents panels au TopPanel, afin d'afficher rapidement celui voulu
		this.cardLayout = new CardLayout();

		this.topPanel.add(MENU, mp);
		this.topPanel.add(COMBAT, cp);
		this.topPanel.add(CARTE_DU_MONDE, cmp);
		this.topPanel.add(RECOMPENSE, rp);
		this.topPanel.add(BOUTIQUE, bp);
		this.topPanel.add(ZONE_DE_REPOS, zrp);
		this.topPanel.add(FIN, fp);
		

		this.topPanel.setLayout(cardLayout);
		this.topPanel.setCurPan(this.getCurPanel());
		this.cardLayout.show(this.topPanel, MENU);

	}
	

	public void initWindow(Dimension d) { // initialise la fenetre graphique
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		this.setContentPane(this.topPanel);
		this.setVisible(true);
		this.pack();
	}
	
	public void initBoutique() { // cree une boutique
		Deck tmp = Deck.getDeckRecompenseBoutique(h, e, 5);
		
		ArrayList<Equipement> listeE = new ArrayList<Equipement>();
		Equipement e1 = new Equipement(5, "Orbe rouge", "Attaque", 5);
		Equipement e2 = new Equipement(5, "Orbe bleue", "Defense", 5);
		Equipement e3 = new Equipement(5, "Orbe rouge", "Attaque", 4);
		listeE.add(e1);
		listeE.add(e2);
		listeE.add(e3);

		this.boutique = new Boutique(tmp, listeE, 10, this);
	}
	private void close() {
		this.setVisible(false);
		System.exit(0);
	}

	public void setidCurPanel(int i){
		idCurPanel = i;
	}

	public EcranPanel getCurPanel(){
		return panels.get(idCurPanel);
	}
	
	public void initRecompenses() { // Utiliser FrameMain.combatBoss pour savoir si grosse rencompense ou non
		if (!FrameMain.combatBoss) this.rp.cartesRecompense =  Deck.getDeckRecompenseBoutique(this.h, this.e, 3); // Si le combat est normal
		else this.rp.cartesRecompense = Deck.getDeckRecompenseBoss(this.h, this.e, 3); // Si le combat est un boss
	}

	public void switchCurPanel(int id){
		//on desactive le panel courant
		this.topPanel.getCurPan().estVisible = false;
		this.topPanel.getCurPan().unsetListener();
		this.topPanel.getCurPan().unsetMouseListener();
		this.topPanel.curHeros.panel.isAnimOn = false;
		this.topPanel.curEnnemi.panel.isAnimOn = false;
		this.topPanel.curHeros.panel.currentSpritePos = this.e.panel.initSpritePos;
		this.topPanel.curEnnemi.panel.currentSpritePos = this.e.panel.initSpritePos;

		//le panel courant est remplace par le panel suivant
		this.setidCurPanel(id);
		this.topPanel.setCurPan(this.getCurPanel());

		//il faut initialiser les decks et les Personnages en cas de combat
		if (Objects.equals(this.topPanel.getCurPan().pNAME, COMBAT)) {
			CombatPanel tmp = (CombatPanel) this.topPanel.getCurPan();
			tmp.activate(this.topPanel.curHeros, this.topPanel.curEnnemi);
			//tmp.resetCombat();
		}

		if (Objects.equals(this.topPanel.getCurPan().pNAME, CARTE_DU_MONDE)) {
			CarteMondePanel tmp = (CarteMondePanel) this.topPanel.getCurPan();
			tmp.activate(this.topPanel.curHeros);
		}
		
		//il faut initialiser les recompenses en cas de fin de combat
		if (Objects.equals(this.topPanel.getCurPan().pNAME, RECOMPENSE)) {
			initRecompenses();
		}
		
		//il faut reinitialiser les boutiques
		if (Objects.equals(this.topPanel.getCurPan().pNAME, BOUTIQUE)) {
			this.initBoutique();
		}
		
		//il faut reinitialiser la zone de repos
		if (Objects.equals(this.topPanel.getCurPan().pNAME, ZONE_DE_REPOS)) {
			this.zrp.reset();
		}

		//puis activer le panel demande
		this.topPanel.getCurPan().setListener();
		this.topPanel.getCurPan().setMouseListener();
		this.topPanel.getCurPan().estVisible = true;

		this.cardLayout.show(this.topPanel, this.topPanel.getCurPan().pNAME); //affiche le panel demande
		this.topPanel.getCurPan().requestFocusInWindow();
	}

	public int getDefaultWidth() {
		return DEFAULT_WIDTH;
	}


	public static void main(String[] args) {
		FrameMain f = new FrameMain();
		f.launch();
	}

	private void launch() {
		this.topPanel.start();
		this.topPanel.curEnnemi = this.e;
	}
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
