package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.gameWorld.GameRenderer;
import com.mygdx.gameWorld.GameWorld;
import com.mygdx.helpers.InputHandler;

//GameScreen should only have 1 job (OOP) - displaying UI. Updating/rendering images by helper classes (gameworld/renderer)
public class GameScreen implements Screen{
	
	private GameWorld world; //updates objects
	private GameRenderer renderer; //draws objects (generating images)
	private InputHandler handler;
	private float runTime = 0;
	
	public GameScreen(){
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		int midPointY = (int)(gameHeight/2);
		
		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world, (int)gameHeight, midPointY); //renderer must have reference to the world it is drawing (positions determined through GameWorld updates)
		
		Gdx.input.setInputProcessor(new InputHandler(world)); //pass InputHandler class with world
		
		//Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
	}
	@Override // these methods called by Game class - Render can be treated as game loop
	public void render(float delta) {
		world.update(delta); //pass in delta to update() for frame-rate independent movement
		
		runTime +=delta;
		renderer.render(runTime); //runTime  = how long game has been running

	}

	@Override
	public void resize(int width, int height) {
		//Gdx.app.log("GameScreen", "resizing");		
	}

	@Override
	public void show() {
		//Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
		//Gdx.app.log("GameScreen", "hide called");
		
	}

	@Override
	public void pause() {
		//Gdx.app.log("GameScreen", "pause called");
		
	}

	@Override
	public void resume() {
		//Gdx.app.log("GameScreen", "resume called");
		
	}

	@Override
	public void dispose() {
		
	}

}
