package com.mygdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, logoTexture, bgTexture;
	public static TextureRegion bg, grass, bird, birdUp, birdDown, skullUp,
			skullDown, bar, playButtonUp, playButtonDown, logo, zbLogo;

	public static Animation birdAnimation;
	public static Sound smack, flap, scored;
	public static BitmapFont font, shadow;
	public static Preferences prefs;

	public static void load() {
		
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logo = new TextureRegion(logoTexture, 0,0,512, 114);

		bgTexture = new Texture(Gdx.files.internal("data/gameBack2.png"));
		bgTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = new TextureRegion(bgTexture, 0,0,136,56);

		
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		// sets texture shrinkage (first parameter) and expansion filters
		// (prevents bluriness when expanded)
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		zbLogo = new TextureRegion(texture, 0,55,135,24);
		zbLogo.flip(false, true);
		
		playButtonUp = new TextureRegion(texture, 0,83,29,16);
		playButtonDown = new TextureRegion(texture, 29,83,29,16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);
		
		//bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);

		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);

		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);

		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);

		// array of TextureRegion objects which is passed to new Animation
		// object
		TextureRegion[] birds = { birdDown, bird, birdUp };

		birdAnimation = new Animation(0.06f, birds);
		// each frame (each version of bird i.e up/down) is 0.06 seconds - used 
		//in runTime to determine frame to display
		
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); 
		// Ping-pong after last frame, order is reversed (wings go up then down)											

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);

		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true);

		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		smack = Gdx.audio.newSound(Gdx.files.internal("data/smack.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		scored = Gdx.audio.newSound(Gdx.files.internal("data/score.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.25f, -.25f);

		prefs = Gdx.app.getPreferences("FlappyGame");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}

	public static void dispose() {
		texture.dispose();
		smack.dispose();
		flap.dispose();
		scored.dispose();
		font.dispose();
		shadow.dispose();
	}
	
	public static void setHighScore(int val){
		prefs.putInteger("highScore", val);
		prefs.flush(); //saves highscore
	}
	
	public static int getHighScore(){
		return prefs.getInteger("highScore");
	}

}
