package com.mygdx.gameObjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	//protected is similar to private, but allows inheritance by subclasses - would otherwise need getters
	protected Vector2 velocity;
	protected Vector2 position;
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;
	
	public Scrollable(float x, float y, int width, int height, float scrollSpeed){
		position = new Vector2(x,y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		
		isScrolledLeft = false;
		
	}
	
	public void update (float delta){
		
		position.x += velocity.x * delta;
		
		if(position.x + width<0) //when all of object is off-screen, reset
			isScrolledLeft = true;
	}
	
	public void reset (float newX){
		//called in ScrollHandler, where references to all 3 pipes are held (x = getTailX)
		position.x = newX;
		isScrolledLeft = false;
	}
	
	public void stop(){
		velocity.x = 0;
	}
	
	
	public boolean isScrolledLeft(){
		return isScrolledLeft;
	}
	
	public float getTailX(){
		return position.x + width;
	}
	
	public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
