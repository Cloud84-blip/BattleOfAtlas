package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class EcranPanel extends JPanel {
	/**
	 * Gere les autres Panels.
	 */

    private static final long serialVersionUID = 1L;
    public final String pNAME;
    public final FrameMain master_frame;
    public Console console = new Console();

    public Image bg;
    public KeyListener activeKeyListeners;
    public MouseListener activeMouseListener;
    public Boolean estVisible = false;

    public EcranPanel(String pNAME, FrameMain frame){
        this.pNAME = pNAME;
        this.master_frame = frame;
    }


    public void setListener(){
        this.addKeyListener(this.activeKeyListeners);
    }

    public void unsetListener(){
        this.removeKeyListener(this.activeKeyListeners);
    }
    
    public void setMouseListener(){
        this.addMouseListener(this.activeMouseListener);
    }

    public void unsetMouseListener(){
        this.removeMouseListener(this.activeMouseListener);
    }

    public void initBackground(String bgPath){
        this.bg = Toolkit.getDefaultToolkit().createImage(CombatPanel.getPath() + bgPath);
    }

    public void initPanel(){
        this.setOpaque(true);
        this.setFocusable(true);
    }

}
