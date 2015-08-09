package com.mygdx.gameObjects;

public class Grass extends Scrollable{

	public Grass(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		// TODO Auto-generated constructor stub
	}
	
	public void onRestart(float x, float scrollSpeed){
		position.x = x;
		velocity.x = scrollSpeed; //before restarting, would be 0 due to stop()
		//y pos stays the same
	}

}
