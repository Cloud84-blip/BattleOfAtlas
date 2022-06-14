package game;

public class AnimationPos {
	/**
	 * Petite classe pour garder en memoire le num des animations des Personnages
	 */
    public static int anim_len;
    public static int anim_pos;

    public static void SlashAnimation(){
        anim_len = 6;
        anim_pos = 15;
    }

    public static void CastAnimation(){
        anim_len = 7;
        anim_pos = 3;
    }

    public static void DeathAnimation(){
        anim_len = 6;
        anim_pos = 20;
    }

    public static void WalkAnimation(){
        anim_len = 9;
        anim_pos = 10;

    }

}
