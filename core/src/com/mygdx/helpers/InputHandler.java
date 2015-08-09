package com.mygdx.helpers;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.gameObjects.Bird;
import com.mygdx.gameWorld.GameWorld;

public class InputHandler implements InputProcessor{

	private Bird myBird;
	private GameWorld myWorld;
	
	public InputHandler(GameWorld myWorld){
		myBird = myWorld.getBird();
		this.myWorld = myWorld;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//when gameOver is clicked, restart makes currentState ready
		if(myWorld.isReady()){ 
			myWorld.start(); //sets currentState to RUNNING when clicked
		}
	
		myBird.onClick();
		
		if(myWorld.isGameOver() || myWorld.isHighScore()){
			// Reset all variables, go to GameState.READY
			myWorld.restart();
		}
		return true; //return true to say we handled the touch
	}

	
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
