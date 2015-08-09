package com.mygdx.gameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameObjects.Bird;
import com.mygdx.gameObjects.ScrollHandler;
import com.mygdx.helpers.AssetLoader;

public class GameWorld {

	public enum GameState { // create variable and define small set of values
							// (no specified type i.e int)
		READY, RUNNING, GAMEOVER, HIGHSCORE // each of these will trigger
											// certain drawing in GameRenderer,
											// including if highScore acheived
	}

	private GameState currentState;
	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private int fellCount;
	private int midPointY;

	public GameWorld(int midPointY) {
		fellCount = 0;
		currentState = GameState.READY;

		this.midPointY = midPointY;

		bird = new Bird(33, midPointY - 5, 17, 12);
		// grass should begin 66 pixels below the midPointY
		scroller = new ScrollHandler(this,(midPointY + 77));

		// create non-moving rectangle to check if hits ground, less costly
		// check (rather than updating rect. as grass moves)
		ground = new Rectangle(0, midPointY + 77, 136, 11);
	}

	public void update(float delta) {
		switch (currentState) {
		case READY:
			updateReady(delta);
			break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;

		}
	}

	public void updateReady(float delta) {
		scroller.update(delta);
	}

	public void updateRunning(float delta) {

		// Add a delta cap so that if our game takes too long
		// to update, we will not break our collision detection.
		if (delta >= .15f)
			delta = .15f;

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) {
			scroller.stop();
			bird.die(); // sets isAlive to false, sound wont play next time this
						// update() is called
			AssetLoader.smack.play();
			fellCount++;
		}

		// dont decelerate until bird touches ground (even after death)
		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			scroller.stop(); // may be 2nd time this is called (if already dead)
			bird.die();
			bird.decelerate();

			if (fellCount == 0)
				AssetLoader.smack.play();
				fellCount++;

			if (bird.getRotation() == 90) { //ensure bird has rotated before continuing to next screen
				currentState = GameState.GAMEOVER;

				if (score > AssetLoader.getHighScore()) {
					AssetLoader.setHighScore(score);
					currentState = GameState.HIGHSCORE;
				}
			}
		}
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore() {
		score++;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		fellCount = 0;
		scroller.onRestart();
		bird.onRestart(midPointY - 5);
	}
}
