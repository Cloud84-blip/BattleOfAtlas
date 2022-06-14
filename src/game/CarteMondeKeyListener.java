package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import parser.DataParser;

public class CarteMondeKeyListener implements KeyListener {
	/**
	 * Le Key Listener pour la Carte du Monde.
	 */
	private final FrameMain frame;
    private final CarteMondePanel cmpanel;
    private static final int KEY_DOWN = KeyEvent.VK_DOWN;
    private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    private int key;

    public CarteMondeKeyListener(FrameMain master_frame, CarteMondePanel cmp) {
    	this.cmpanel = cmp;
        this.frame = master_frame;
	}


	@Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if(!cmpanel.estVisible) return;;
        this.key = e.getKeyCode();

        if (this.key == KEY_RIGHT){
            if (this.cmpanel.h.emplacementX < this.cmpanel.tableau[0].length -1) {
            	this.cmpanel.h.emplacementX += 1;
                this.cmpanel.repaint();
                FrameMain.sleep(1000);
                setCombatBoss();
                changeEnnemi(DataParser.choixEnnemi());
                this.frame.switchCurPanel(this.cmpanel.tableau[this.cmpanel.h.emplacementY][this.cmpanel.h.emplacementX]);
            }
        }

        if (this.key == KEY_DOWN){
            if (this.cmpanel.h.emplacementY < this.cmpanel.tableau.length -1) {
            	this.cmpanel.h.emplacementY += 1;
                this.cmpanel.repaint();
                FrameMain.sleep(1000);
                setCombatBoss();
                changeEnnemi(DataParser.choixEnnemi());
                this.frame.switchCurPanel(this.cmpanel.tableau[this.cmpanel.h.emplacementY][this.cmpanel.h.emplacementX]);
            }
        }


    }
    
    

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    public void setCombatBoss() {
    	if (this.cmpanel.h.emplacementX == this.cmpanel.tableau[0].length -1 && this.cmpanel.h.emplacementY == this.cmpanel.tableau.length -1) {
    		FrameMain.combatBoss = true;
    	}
    }
    
    public void changeEnnemi(Ennemi enn) {
    	this.frame.topPanel.curEnnemi.nom = enn.nom;
    	this.frame.topPanel.curEnnemi.deck = enn.deck;
    	this.frame.topPanel.curEnnemi.pvMax = enn.pvMax;
    }
}
