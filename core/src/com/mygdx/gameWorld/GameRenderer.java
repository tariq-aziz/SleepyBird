package com.mygdx.gameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.gameObjects.Bird;
import com.mygdx.gameObjects.Grass;
import com.mygdx.gameObjects.Pipe;
import com.mygdx.gameObjects.ScrollHandler;
import com.mygdx.helpers.AssetLoader;

//don't need to import GameWorld since it is in same package
public class GameRenderer {

	private final int COLUMN_GAP = 45;

	private GameWorld myWorld; // able to call non-static getRect() with
								// instance of this class
	private OrthographicCamera cam; // represents 3D object in 2D
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private int groundY;
	private int gameHeight;
	private int bgStart;

	private TextureRegion bg, grass, skullUp, skullDown, bird, birdUp,
			birdDown, bar;
	private Animation birdAnimation;

	private Bird myBird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world; // pass it world created in GameScreen (worlds in all 3
							// classes are the same)

		groundY = midPointY + 77;
		//bgStart = midPointY + 22;
		bgStart = midPointY-40;

		this.gameHeight = gameHeight;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		shapeRenderer = new ShapeRenderer();
		// Matrix : environment/material in which something (shapes) develop.
		// sets origin to be same as defined for cam (yDown)
		shapeRenderer.setProjectionMatrix(cam.combined);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

	}

	// Creaete helper methods to obtain all objects used when class is created,
	// not each time rendered (60 fps).
	// Helper methods keep constructor tidy
	private void initGameObjects() {
		myBird = myWorld.getBird();
		scroller = myWorld.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();

	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bird = AssetLoader.bird;
		birdDown = AssetLoader.birdDown;
		birdUp = AssetLoader.birdUp;
		bar = AssetLoader.bar;
		birdAnimation = AssetLoader.birdAnimation;

	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
				frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(),
				backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {

		batcher.draw(skullUp, pipe1.getX() - 1, // skullUp = top pipe
				pipe1.getY() + pipe1.getHeight() - 14, 24, 14); // go up a bit
																// on pipe,
																// bring height
																// back down
		batcher.draw(skullDown, pipe1.getX() - 1,
				pipe1.getY() + pipe1.getHeight() + 45, 24, 14); // start at top
																// of bottom
																// pipe, go down

		batcher.draw(skullUp, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {
		// .getY() = 0 (top of screen). Height of top pipe varies, which causes
		// bottom height to vary to maintain gap
		batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
				pipe1.getHeight());
		batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight()
				+ COLUMN_GAP, pipe1.getWidth(), groundY
				- (pipe1.getHeight() + COLUMN_GAP));

		batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
				pipe2.getHeight());
		batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight()
				+ COLUMN_GAP, pipe2.getWidth(), groundY
				- (pipe2.getHeight() + COLUMN_GAP));

		batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
				pipe3.getHeight());
		batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight()
				+ COLUMN_GAP, pipe3.getWidth(), groundY
				- (pipe3.getHeight() + COLUMN_GAP));
	}

	// runTime determines which bird to display (using frame duration_
	public void render(float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1); // fill screen w/ black bg to prevent
											// flickering
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw background color
		//shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.setColor(111/255.0f, 209/255.0f, 227/255.0f, 1);
		shapeRenderer.rect(0, 0, 136, groundY);

		// Draw grass
		// shapeRenderer.setColor(111 / 255.0f, 186 / 2	55.0f, 45 / 255.0f, 1);
		// shapeRenderer.rect(0, groundY, 136, 11);

		// Draw dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, groundY, 136, 60);

		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(bg, 0, bgStart, 136, 117); // ends at top of grass

		drawGrass();
		drawPipes();

		batcher.enableBlending();
		drawSkulls();

		if (myBird.shouldntFlap()) {
			// originX/Y = offsets (origin at which object should rotated and
			// scaled - center)
			batcher.draw(bird, myBird.getX(), myBird.getY(),
					myBird.getWidth() / 2, myBird.getHeight() / 2,
					myBird.getWidth(), myBird.getHeight(), 1, 1,
					myBird.getRotation());
		}

		else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), myBird.getX(),
					myBird.getY(), myBird.getWidth() / 2,
					myBird.getHeight() / 2, myBird.getWidth(),
					myBird.getHeight(), 1, 1, myBird.getRotation());
		}

		if (myWorld.isReady()) {
			// Draw shadow first
			AssetLoader.shadow.draw(batcher, "Flap on!", (136 / 2) - (35), 76);
			// Draw text
			AssetLoader.font
					.draw(batcher, "Flap on!", (136 / 2) - (35 - 1), 75);
		} 
		
		else { //running, gameover, or highscore

			if (myWorld.isGameOver() || myWorld.isHighScore()) {
				
				if (myWorld.isGameOver()) { //only gameover
					AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
					AssetLoader.font.draw(batcher, "Game Over", 24, 55);
					
					AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score:", 22, 105);
					
					String highScore = AssetLoader.getHighScore() + "";

                    // Draw shadow first
                    AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);
                    // Draw text
                    AssetLoader.font.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);
				}
				
				else{ //only if highScore
					AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score!", 18, 55);
				}
				
				//displays try again whether or not highscore was obtained
				AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try again?", 24, 75);
            
			}

			// displays score if state is running, gameover or highscore
			String score = myWorld.getScore() + "";
			AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2)
					- (3 * score.length()), 12);
			// Draw text
			AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
					- (3 * score.length() - 1), 11);
		}

		batcher.end();

	}

}
