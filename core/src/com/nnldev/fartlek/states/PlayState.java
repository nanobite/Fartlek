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
	private int tileWidth;
	private int tileHeight;
	private float obstacleTime, maxObstacleTime = 0.75f;
	private String tileTextureName;
	private Box emptyBox;
	private Obstacle[] possibleObstacles = { new Box("Items\\box.png", 0, Fartlek.HEIGHT, 100) };
	private ArrayList<Obstacle[]> obstacles;
	private final int HORIZONTAL_OBSTACLE_BUFFER = 20;
	private boolean DONE;
	private int AMT_OBSTACLES = 8;

	/**
	 * Creates a new game state
	 *
	 * @param gsm
	 *            The game state manager which is controlling this state
	 */
	public PlayState(GameStateManager gsm) {
		super(gsm);
		DONE = false;
		obstacles = new ArrayList<Obstacle[]>();
		emptyBox = new Box("Items\\emptybox.png", 0, 0, 0);
		tileTextureName = "Scene\\bckg.png";
		exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
		runner = new Runner("Characters\\sphereAnim.png", 9);
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
		newObstacles();
		startMusic("music1.mp3");
	}

	/**
	 * Starts playing a song
	 *
	 * @param song
	 *            The name of the song to play
	 */
	public void startMusic(String song) {
		music = Gdx.audio.newMusic(Gdx.files.internal("Music\\song1.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		if (Fartlek.soundEnabled)
			music.play();
	}

	public void newObstacles() {
		Obstacle[] tmpObstacles = Arrays.copyOf(randomObstacles(AMT_OBSTACLES, possibleObstacles, 2), AMT_OBSTACLES);
		for(int i=0;i<tmpObstacles.length;i++){
			tmpObstacles[i].setPosition(new Vector3(i* tmpObstacles[i].getRectangle().getWidth() + HORIZONTAL_OBSTACLE_BUFFER,Fartlek.HEIGHT,0));
			System.out.println("Stuff: "+tmpObstacles[i].getXPosition());
		}
		String out = "New Pos's: ";
		for (int i=0;i<tmpObstacles.length;i++) {
			out += tmpObstacles[i].getPosition().x + ", ";
		}
		 System.out.println(out);
		obstacles.add(tmpObstacles);

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
		String checks = "";
		for (Obstacle obstacle : sendBack) {
			if (obstacle.getPath().equals(emptyBox.getPath())) {
				checks += "[O]";
			} else {
				checks += "[X]";
			}
		}
		checks+="\nX Position: ";
		for (int i = 0; i < sendBack.length; i++) {
			sendBack[i].setXPosition(i * sendBack[i].getRectangle().getWidth() + HORIZONTAL_OBSTACLE_BUFFER);
			System.out.println(sendBack[i].getXPosition());
			checks+=sendBack[i].getXPosition()+", ";
			sendBack[i].setYPosition(Fartlek.HEIGHT);
		}
		checks += "\tY: " + sendBack[0].getYPosition();
		System.out.println("\n" + checks);
		return sendBack;
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
	public void update(float dt) {
		// System.out.println("Update");
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
			for (int i = 0; i < obstacles.size(); i++) {
				String xcoords = "X Coordinates: ";
				for (int j = 0; j < obstacles.get(i).length; j++) {
					obstacles.get(i)[j].update(dt);
					xcoords += obstacles.get(i)[j].getXPosition() + ", ";
				}
				// System.out.println(xcoords);
			}
			if ((obstacles.size() > 0)
					&& ((obstacles.get(0)[0].getYPosition() + obstacles.get(0)[0].getTexture().getHeight()) < 0)) {
				obstacles.remove(0);
			}

			obstacleTime += dt;
			if (obstacleTime >= maxObstacleTime) {
				newObstacles();
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
		// System.out.println("Render");
		sb.setProjectionMatrix(Fartlek.cam.combined);
		sb.begin();
		for (Scene[] tileArray : sceneTiles) {
			for (Scene tile : tileArray) {
				sb.draw(tile.getTexture(), tile.getPosition().x, tile.getPosition().y);
			}
		}
		for (int i = 0; i < obstacles.size(); i++) {
			String xcoords = "X Coordinates: ";
			for (int j = 0; j < obstacles.get(i).length; j++) {
				sb.draw(obstacles.get(i)[j].getTexture(), obstacles.get(i)[j].getXPosition(),
						obstacles.get(i)[j].getYPosition());
				xcoords += obstacles.get(i)[j].getXPosition() + ", ";
			}
			// System.out.println(xcoords);
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
		for (Obstacle[] obstacleArray : obstacles) {
			for (Obstacle obstacle : obstacleArray) {
				obstacle.dispose();
			}
		}
		obstacles.clear();
	}
}
