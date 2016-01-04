/**
 * @author Nano
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchSector;
import com.nnldev.fartlek.sprites.Scene;
import com.nnldev.fartlek.sprites.Box;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayState extends State {
	private Button exitBtn;
	private Runner runner;
	private TouchSector bottomLeft;
	private TouchSector bottomRight;
	private TouchSector bottomMiddle;
	private Music music;
	private ArrayList<Scene[]> sceneTiles;
	private float obstacleTime, maxObstacleTime = 2.0f;
	private String tileTextureName;
	private Box emptyBox;
	private final int HORIZONTAL_OBSTACLE_BUFFER = 20;
	private boolean DONE;
	private int AMT_OBSTACLES = 9, AMT_HOLES = 4;
	private int tileWidth;
	private int tileHeight;

	/**
	 * Creates a new game state
	 *
	 * @param gsm
	 *            The game state manager which is controlling this state
	 */
	public PlayState(GameStateManager gsm) {
		super(gsm);
		DONE = false;
		emptyBox = new Box("Items\\emptybox.png", 0, 0, 0);
		tileTextureName = "Scene\\bckg1.png";
		exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
		runner = new Runner("Characters\\ship1Anim.png", 3);
		bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
		bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
		bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
		tileWidth = new Texture(tileTextureName).getWidth();
		tileHeight = new Texture(tileTextureName).getHeight();
		sceneTiles = new ArrayList<Scene[]>();
		sceneTiles.add(new Scene[Scene.TILES_PER_ROW]);
		for (int i = 0; i < sceneTiles.get(0).length; i++) {
			sceneTiles.get(sceneTiles.size() - 1)[i] = new Scene(tileTextureName, i * tileWidth, 0);
		}
		newSceneTile();
		startMusic("music1.mp3");
	}

	/**
	 * Makes a new row of tiles
	 */
	public void newSceneTile() {
		sceneTiles.add(new Scene[Scene.TILES_PER_ROW]);
		for (int i = 0; i < sceneTiles.get(0).length; i++) {
			sceneTiles.get(sceneTiles.size() - 1)[i] = new Scene(tileTextureName, i * tileWidth, Fartlek.HEIGHT);
		}
	}

	/**
	 * Starts playing a song
	 *
	 * @param song
	 *            The name of the song to play
	 */
	public void startMusic(String song) {
		music = Gdx.audio.newMusic(Gdx.files.internal("Music\\song1.mp3"));
		music.setLooping(false);
		music.setVolume(0.1f);
		if (Fartlek.soundEnabled)
			music.play();
	}

	/**
	 * Handles user input
	 */
	@Override
	protected void handleInput() {
		// If you touched the screen
		if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
			// If the x,y position of the click is in the exit button
			if (exitBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
				gsm.push(new MenuState(gsm));
				DONE = true;
				dispose();
			}
			// If the x,y position of the click is in the bottom left
			if (bottomLeft.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
				runner.left();
			}
			// If the x,y position of the click is in the bottom right
			if (bottomRight.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
				runner.right();
			}
			// If the x,y position of the click is in the bottom middle
			if (bottomMiddle.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)
					&& Gdx.input.justTouched()) {
				runner.shoot();
			}
		}
	}

	/**
	 * Updates the play state and all the information
	 *
	 * @param dt
	 *            The game state manager which organizes which states will be
	 *            shown
	 */
	@Override
	public void update(float dt) {//dt is delta time
		handleInput();
		if (!DONE) {
			runner.update(dt);
			// Loops through all the tiles and updates their positions
			for (Scene[] tileArray : sceneTiles) {
				for (Scene tile : tileArray) {
					tile.update();
				}
			}
			// If the array at the top's height + its y position are equal to
			// the
			// height of the screen, it will add another array on top of it
			if ((sceneTiles.get(sceneTiles.size() - 1)[0].getPosition().y
					+ sceneTiles.get(sceneTiles.size() - 1)[0].getRectangle().height) == Fartlek.HEIGHT) {
				newSceneTile();
			}
			// If the array of tiles goes below 0 then it will dispose of it to
			// avoid memory leaks and save space
			if ((sceneTiles.get(0)[0].getPosition().y + sceneTiles.get(0)[0].getRectangle().height) < 0) {
				// Removes the oldest one
				for (Scene tile : sceneTiles.get(0)) {
					tile.dispose();
				}
				sceneTiles.remove(0);
			}
			obstacleTime += dt;
			if (obstacleTime >= maxObstacleTime) {
				obstacleTime = 0;
			}
		}
	}

	/**
	 * Renders the graphics to the screen
	 *
	 * @param sb
	 *            The sprite batch which is all the stuff that's going to be
	 *            drawn to the screen.
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(Fartlek.cam.combined);
		sb.begin();
		for (Scene[] tileArray : sceneTiles) {
			for (Scene tile : tileArray) {
				sb.draw(tile.getTexture(), tile.getPosition().x, tile.getPosition().y);
			}
		}
		sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
		sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
		sb.end();
	}

	/**
	 * Disposes of objects to avoid memory leaks
	 */
	@Override
	public void dispose() {
		exitBtn.dispose();
		runner.dispose();
		music.dispose();
		for (Scene[] sceneArray : sceneTiles) {
			for (Scene scene : sceneArray)
				scene.dispose();
		}
		sceneTiles.clear();
	}

	private class ObstacleManager {
		private Obstacle[] possibleObstacles = { new Box("Items\\box.png", 0, Fartlek.HEIGHT, 100) };
		private ArrayList<Obstacle[]> obstacles;

		/**
		 * Makes it a lot easier to control the obstacles and find what is wrong
		 * with them.
		 */
		public ObstacleManager() {
			obstacles = new ArrayList<Obstacle[]>();
		}

		/**
		 * Creates an array of obstacles with a given length, a given amount of
		 * empty indices, and an array of permitted obstacles
		 * 
		 * @param len
		 *            Length of the returned array
		 * @param obstacles
		 *            The list of possible obstacles
		 * @param nulls
		 *            The amount of empty indices
		 * @return
		 */
		public Obstacle[] randomObstacles(int len, Obstacle[] obstacles, int nulls) {
			Obstacle[] sendBack = new Obstacle[len];
			for (int i = 0; i < len; i++) {
				sendBack[i] = new Box("empty.png", 0, 0, 0);
			}
			// First place nulls in random indices
			for (int i = 0; i < nulls; i++) {
				int zeroLoc = (int) (Math.random() * len);
				while (sendBack[zeroLoc].equals(emptyBox)) {
					zeroLoc = (int) (Math.random() * len);
				}
				sendBack[zeroLoc] = emptyBox;
			}
			// Fills up rest of indices with obstacles
			for (int i = 0; i < len; i++) {
				int random = (int) (Math.random() * obstacles.length);
				if (!sendBack[i].equals(emptyBox))
					sendBack[i] = obstacles[random];
			}
			// Sets the coordinates
			for (int i = 0; i < sendBack.length; i++) {
				sendBack[i].setPosition(new Vector3(
						i * sendBack[i].getTexture().getWidth() + HORIZONTAL_OBSTACLE_BUFFER, Fartlek.HEIGHT, 0));
			}
			return sendBack;
		}

		/**
		 * Adds a new osbtacle
		 */
		public void newObstacles() {
			Obstacle[] tmpObstacles = randomObstacles(AMT_OBSTACLES, possibleObstacles, AMT_HOLES);
			obstacles.add(tmpObstacles);
			for (int i = 0; i < obstacles.get(obstacles.size() - 1).length; i++) {
			}
		}

		public void update(float dt) {
			// Updates all of the obstacles
			for (int i = 0; i < obstacles.size(); i++) {
				for (int j = 0; j < obstacles.get(i).length; j++) {
					obstacles.get(i)[j].update(dt);
				}
			}
			for (int i = 0; i < obstacles.size(); i++) {
				if ((obstacles.get(i)[0].getYPosition() + obstacles.get(i)[0].getTexture().getHeight()) < 0) {
					for (int j = 0; j < obstacles.get(i).length; j++) {
						obstacles.get(i)[j].dispose();
					}
					obstacles.remove(i);
				}
			}
		}

		public void render(SpriteBatch sb) {
			for (int i = 0; i < obstacles.size(); i++) {
				for (int j = 0; j < obstacles.get(i).length; j++) {
					sb.draw(obstacles.get(i)[j].getTexture(),
							j * obstacles.get(i)[j].getTexture().getWidth() + HORIZONTAL_OBSTACLE_BUFFER,
							obstacles.get(i)[j].getYPosition());
				}
			}
		}

		public void dispose() {
			for (Obstacle[] obstacleArray : obstacles) {
				for (Obstacle obstacle : obstacleArray) {
					obstacle.dispose();
				}
			}
			obstacles.clear();
		}
	}
}
