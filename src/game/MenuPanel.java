package game;
import java.awt.*;
import java.util.ArrayList;



public class MenuPanel extends EcranPanel {

    /**
	 * Menu principal du jeu
	 */
	private static final long serialVersionUID = 1L;
	public int fontSize = 20;
    public Font font = new Font("Comic Sans MS", Font.BOLD, fontSize);
    private final ArrayList<Heros> heros = new ArrayList<>();
    public Heros curHeros;
    public Graphics2D g2d;
    public TopPanel tp;
    private int selectorX = 300;
    private int selectorY = 155;

    public MenuPanel(String pNAME, Heros h, Heros m, Heros z, FrameMain frame, TopPanel tp){
        super(pNAME, frame);
        this.initPanel();
        this.initListeners();
        this.initBackground("/assets/fond/FOND-MENU.png");
        this.tp = tp;
        heros.add(h);
        heros.add(z);
        heros.add(m);
        this.curHeros = heros.get(0);
        this.initChoix();
    }

    public void initChoix(){ // Affiche les personnages
        int i = 1;
        AnimationPos.WalkAnimation();
        for (Heros he : this.heros){
            he.panel.initPanelSprite(he.path, i*300, 150, 10, 9);
            he.panel.isAnimOn = true;
            i+=1;
        }
    }

    public void initListeners(){
        this.activeKeyListeners = new MenuKeyListener(this.master_frame, this);
    }

    public void paint(Graphics g){
        if (!this.estVisible) return;
        this.g2d = (Graphics2D) g;
		g.drawImage(this.bg, 0, 0, null);
        for(Heros he: this.heros){
            he.panel.paintLoopAnimation(g);
        }
        g.setColor(Color.RED);
        g.drawRect(selectorX, selectorY, 65, 60);
        g.setFont(font);

    }

    public void moveSelectorRight(){ // bouger a droite
        if (this.selectorX>=900){
            selectorX = 300;
        } else {
            selectorX += 300;
        }
    }

    public void moveSelectorLeft(){ // bouger a gauche
        if (this.selectorX<=300){
            selectorX = 900;
        } else {
            selectorX -= 300;
        }
    }

    public void setCurHeros(){ // Fixe le heros choisi et cree son deck
        this.curHeros = this.heros.get(selectorX/300-1);
		FrameMain.decksEnnemis = Deck.dictDeckEnnemis(this.master_frame.topPanel.curEnnemi, this.curHeros);
		this.master_frame.e.deck = FrameMain.decksEnnemis.get(this.master_frame.e.getNom());
		this.curHeros.deck.DeckDeDepart(this.curHeros, this.master_frame.e);
		this.curHeros.setPvActuel(this.curHeros.getPvMax());
        this.tp.curHeros = curHeros;
    }
}
