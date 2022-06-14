package game;

import javax.swing.*;

public class TopPanel extends JPanel implements Runnable {
    /**
	 * Le TopPanel est la fenetre qu'on affiche a l'ecran, il est remplace par les autres classes Panel lors des changements d'ecrans. Il contient le Heros et l'Ennemi courant
	 */
	private static final long serialVersionUID = 1L;
	private boolean running;
    private EcranPanel curPan;
    private Thread thread;
    public Heros curHeros;
    public Ennemi curEnnemi;

    public TopPanel(boolean b)
    {
        this.running = b;
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(running){
            this.getCurPan().validate();
            this.getCurPan().repaint();
            FrameMain.sleep(75);
        }
        
    }



    public EcranPanel getCurPan(){return curPan;}

    public void setCurPan(EcranPanel curPanel) {
        this.curPan = curPanel;
    }
}
