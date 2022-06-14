package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuKeyListener implements KeyListener {
	/**
	 * Utilisation du clavier lors du Menu principal
	 */
    private final FrameMain frame;
    private final MenuPanel mpanel;
    private static final int KEY_P = KeyEvent.VK_P;
    private static final int KEY_X = KeyEvent.VK_X;
    private static final int KEY_Y = KeyEvent.VK_Y;
    private static final int KEY_LEFT = KeyEvent.VK_LEFT;
    private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    private static final int KEY_ENTER = KeyEvent.VK_ENTER;

    private int key;

    public MenuKeyListener(FrameMain frame, MenuPanel mp){

        this.frame = frame;
        this.mpanel = mp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (this.key == KEY_LEFT){
            this.mpanel.moveSelectorLeft();
        }

        if (this.key == KEY_ENTER){
            this.mpanel.setCurHeros();
            this.mpanel.curHeros.panel.isAnimOn = false;
            this.frame.switchCurPanel(2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!mpanel.estVisible) return;;
        this.key = e.getKeyCode();

        if (this.key == KEY_X){ // Ces trois la nous on ete utile pour tester les changements de Panels
            this.frame.switchCurPanel(1); // Combat
        }

        if (this.key == KEY_Y){
            this.frame.switchCurPanel(2); // Carte du monde
        }

        if (this.key == KEY_P){
            this.frame.switchCurPanel(0); // Rester sur le Menu
        }

        if (this.key == KEY_RIGHT){
            this.mpanel.moveSelectorRight(); // Bouger a droite
        }

        if (this.key == KEY_LEFT){
            this.mpanel.moveSelectorLeft(); // Bouger a gauche
        }

        if (this.key == KEY_ENTER){ // Selectionner le heros
            this.mpanel.setCurHeros();
            this.mpanel.curHeros.panel.isAnimOn = false;
            this.frame.switchCurPanel(2);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
