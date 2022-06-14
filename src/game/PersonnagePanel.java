package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PersonnagePanel extends JPanel{
	/**
	 * Affiche le personnage
	 */
	private static final long serialVersionUID = 1L;
	GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	public Image sprite;
	public int height;
	public int width;
	private int x;
	private int y;
	public boolean isAnimOn = false;
	public int initSpritePos = 0;
	public int currentSpritePos;
	BufferedImage[][] sprites;
	public int currentSpriteLine;
	public int initSpriteLine;
	public int lastSpritePos;

	public void initPanelSprite(String spritePath, int x, int y, int currentSpriteLine, int lastSpritePos){
		initSprite(spritePath);
		this.changeSpritePos(x, y, currentSpriteLine, lastSpritePos);

	}

	public void changeSpritePos(int x, int y, int currentSpritePos, int lastSpritePos){
		this.x = x;
		this.y = y;
		this.currentSpritePos = currentSpritePos;
		this.initSpriteLine = currentSpritePos;
		this.lastSpritePos = lastSpritePos;
	}

	private void initSprite(String path){
		imgLoader(path);
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
		sprites = slice((BufferedImage) this.sprite, 13, 21);
	}

	private BufferedImage[][] slice(BufferedImage source, int cols, int rows){ // Coupe l'image du personnage en un tableau de tous ses sprites differents
		int w=source.getWidth();
		int h=source.getHeight();
		int spritew=w/cols;
		int spriteh=h/rows;
		BufferedImage [][]slices=new BufferedImage[cols][rows];
		for(int x=0;x<cols;x++){
			for(int y=0;y<rows;y++){
				slices[x][y]=gc.createCompatibleImage(spritew,spriteh,source.getTransparency());
				Graphics2D g=(Graphics2D)slices[x][y].getGraphics();
				g.setComposite(AlphaComposite.Src);
				g.drawImage(
						source,
						0,
						0,
						spritew,
						spriteh,
						w/cols*x,
						h/rows*y,
						w/cols*x+spritew,
						h/rows*y+spriteh,
						null
				);
				g.dispose();
			}
		}
		return slices;
	}

	public void imgLoader(String path){
		try {
			this.sprite = ImageIO.read(new File(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g){
		if (isAnimOn){
			this.doAnimation(g);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(this.sprites[initSpritePos][currentSpriteLine], x, y, null);
		}
	}
	
	public void paint(Graphics g, int x, int y){
		if (isAnimOn){
			this.doAnimation(g);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(this.sprites[initSpritePos][currentSpriteLine], x, y, null);
		}
	}

	public void paintLoopAnimation(Graphics g){
		if (this.isAnimOn){
			this.loopOnAnimation(g);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(this.sprites[initSpritePos][currentSpriteLine], x, y, null);
		}
	}

	public void doAnimation(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		if (currentSpritePos >= AnimationPos.anim_len){
			currentSpritePos = initSpritePos;
			currentSpriteLine = initSpriteLine;
			g2d.drawImage(sprites[currentSpritePos][currentSpriteLine], x, y, null);
			isAnimOn = false;
			return;
		}
		currentSpriteLine = AnimationPos.anim_pos;
		g2d.drawImage(sprites[currentSpritePos][currentSpriteLine], x, y, null);
		currentSpritePos += 1;
	}

	public void loopOnAnimation(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		if (currentSpritePos >= AnimationPos.anim_len){
			currentSpritePos = initSpritePos;
			currentSpriteLine = initSpriteLine;
			g2d.drawImage(sprites[currentSpritePos][currentSpriteLine], x, y, null);
			return;
		}
		currentSpriteLine = AnimationPos.anim_pos;
		g2d.drawImage(sprites[currentSpritePos][currentSpriteLine], x, y, null);
		currentSpritePos += 1;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.dispose();
	}

	public int getY(){return this.y;}
	public void setY(int y){this.y = y;}
	public int getX(){return this.x;}
	public void setX(int x){this.x = x;}
}
