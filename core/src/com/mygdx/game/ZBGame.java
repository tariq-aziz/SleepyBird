package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.helpers.AssetLoader;
import com.mygdx.screens.GameScreen;

//This class is launched by each of AndroidLauncher, DesktopLauncher, etc.
public class ZBGame extends Game{ //Game implements ApplicationListener

	@Override
	public void create() {
		System.out.println("ZBGame Created!");
		AssetLoader.load();
		setScreen(new GameScreen()); //from Game superclass
	}


	//call AssetLoader.dispose() when the dispose method of our ZBGame is called by our behind-the-scenes platform-dependent code. 
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();

	}

}
 	