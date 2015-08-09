package com.mygdx.gameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.helpers.AssetLoader;

public class Bird {

	private Circle boundingCircle;

	private Vector2 position; // 2D vector (direction + magnitude)
	private Vector2 velocity;
	private Vector2 acceleration;

	private float rotation;
	private int width;
	private int height;

	private boolean isAlive;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);

		boundingCircle = new Circle();
		isAlive = true;
	}

	public void update(float delta) {
		velocity.y += acceleration.y * delta;

		if (velocity.y > 200) // set velocity downwards cap
			velocity.y = 200;

		position.y += velocity.y * delta; // update position

		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

		if (velocity.y < 0) {
			rotation -= 600 * delta;

			if (rotation < -20)
				rotation = -20;
		}

		//rotate if hit object or just lowering
		if (isFalling() || !isAlive) {
			rotation += 480 * delta;

			if (rotation >= 90)
				rotation = 90;
		}
	}

	public boolean isFalling() { // when bird should rotate clockwise
		return velocity.y > 110;
	}

	public boolean shouldntFlap() { // when bird should stop animating
		return velocity.y > 70 || !isAlive;
	}

	public void onClick() {
		if (isAlive && position.y >0) {
			velocity.y = -140; // everytime clicks, bird goes up by same amount.
								// NOT
								// -= since the veloc. would be diff. depending
								// on
								// starting veloc.
			AssetLoader.flap.play();
		}
	}
	
	public void die(){
		isAlive = false;
		velocity.y = 0;
	}
	
	public void decelerate(){
		acceleration.y = 0;
	}
	
	public void onRestart(int y){ 
		rotation = 0;
		position.y = y; //s position stays same (doesnt move horizontally)
		velocity.set(0, 0);
		acceleration.set(0, 460);
		isAlive = true;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getRotation() {
		return rotation;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}

}
