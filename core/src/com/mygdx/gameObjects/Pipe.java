package com.mygdx.gameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

//pipes and grass are only created in ScrollHandler, when GameWorld calls it.
public class Pipe extends Scrollable {

	private Random r;
	private Rectangle skullUp, skullDown, barUp, barDown;

	public static final int VERTICAL_GAP = 45;
	public static final int SKULL_WIDTH = 24;
	public static final int SKULL_HEIGHT = 11;

	private float groundY;

	private boolean isScored = false;

	// when created, creates new Srollable object along with additional
	// functionality (height)
	public Pipe(float x, float y, int width, int height, float scrollSpeed,
			float groundY) {
		super(x, y, width, height, scrollSpeed);

		r = new Random();

		skullUp = new Rectangle();
		skullDown = new Rectangle();
		barUp = new Rectangle();
		barDown = new Rectangle();

		this.groundY = groundY;
	}

	@Override
	// use this reset (subclass) rather than one in superclass (Scrollable).
	// When pipe.reset() called, subclass one is invoked
	public void reset(float newX) {
		// both reset methods are called.
		super.reset(newX);

		// .nextInt(max-min+1) + min
		height = r.nextInt(89) + 20; // height is inherited from prtected
										// subclass variable (scrollable)
		isScored = false; //when pipe is reset, if player passes, sound will play as isScored is true
	}

	@Override
	public void update(float delta) {
		// calls update (moves pipes/grass) method from superclass Scrollable
		super.update(delta);
		
		//reset pipes in ScrollHandler, where references to all 3 pipes are held (getTailX)

		barUp.set(position.x, position.y, width, height);
		barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));

		skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
				- SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);

		skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
				SKULL_WIDTH, SKULL_HEIGHT);
	}

	// First, check if bird passes pipes. then, check if hit pipes. If not,
	// continue and add point
	// Do this cheap (easy to perform) check before doing the more difficult
	// processing check
	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {

			// as long as one is true, will return TRUE
			return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), barDown)
					|| Intersector.overlaps(bird.getBoundingCircle(), skullUp) || Intersector
						.overlaps(bird.getBoundingCircle(), skullDown));

		}

		return false;

	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x); // calls superclass Scrollable's reset(), resets x position
	}

	public Rectangle getSkullUp() {
		return skullUp;
	}

	public Rectangle getSkullDown() {
		return skullDown;
	}

	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}

}
