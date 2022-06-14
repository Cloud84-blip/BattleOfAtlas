package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RecompensePanel extends EcranPanel {
	
	/**
	 * Affiche les recompenses a la fin d'un combat
	 */
	private static final long serialVersionUID = 1L;
	
	public int orGagne;
	public int emplacementCurseur;
	public Deck cartesRecompense;
	public static BufferedImage fleche;
	
	public RecompensePanel(String pNAME, FrameMain master, Deck c, int or) {
		super(pNAME, master);
		this.initBackground("/assets/fond/FOND-RECOMPENSE.png");
		this.orGagne = or;
		this.emplacementCurseur = 0;
		this.cartesRecompense = c;
		flecheLoader(CombatPanel.getPath()+"/assets/autres/fleche.png");
		this.initPanel();
		this.initListeners();
	}
	
	public void initListeners(){
		this.activeKeyListeners = new RecompenseKeyListener(this.master_frame, this);
	}
	
	public static void flecheLoader(String path){
		try {
			RecompensePanel.fleche = ImageIO.read(new File(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		if (!this.estVisible) return;
		g.drawImage(this.bg, 0, 0, null);
		g.setColor(Color.RED);
		g.drawString("Or gagne : " + orGagne, 10, 40);
		int x = 1280/(cartesRecompense.getListeCarte().size()+1);
		int yCartes = 720/3;
		for (int i = 0; i<cartesRecompense.getListeCarte().size(); i++) { //paint les cartes
			cartesRecompense.getListeCarte().get(i).getCartePanel().paint(g, x+(i*x) - cartesRecompense.getListeCarte().get(i).getCartePanel().getWidth()/2, yCartes);
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fleche, x*(emplacementCurseur+1), 720/2, null);
		g.dispose();
	}
} 
