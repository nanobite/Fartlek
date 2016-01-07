/**
 * @author Nano, Nick
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchSector;
import com.nnldev.fartlek.sprites.Scene;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;

import java.util.ArrayList;

public class PlayState extends State {
	private Button exitBtn;
	private Runner runner;
	private TouchSector bottomLeft;
	private TouchSector bottomRight;
	private TouchSector bottomMiddle;
	private Music music;
	private ArrayList<Scene[]> sceneTiles;
	private Obstacle[] obstacleLine;
	private boolean obstacleExists;
	private float obstacleTime, maxObstacleTime = 2.0f;
	private String tileTextureName;
	private final int HORIZONTAL_OBSTACLE_BUFFER = 20;
	private boolean DONE;
	private int AMT_OBSTACLES = 9, AMT_HOLES = 4;
	private int tileWidth;
	private int tileHeight;
	private float obstacleTimer, MAX_OBSTACLE_TIMER=4;//timer for obstacles

	/**
	 * Creates a new game state
	 *
	 * @param gsm
	 *            The game state manager which is controlling this state
	 */
	public PlayState(GameStateManager gsm) {
		super(gsm);
		DONE = false;
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
		System.out.println("New Scene Tile");
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
	public Obstacle[] randomObstacles(int len, int nulls) {
		System.out.println("Random Obstacles");
		Obstacle[] sendBack = new Obstacle[len];//creates array of obstacles
		for (int i = 0; i < len; i++) {//fills them up with 5 obstacles side by side, no picture but not truly "empty"
			sendBack[i] = new Obstacle("empty.png", (float)(((Fartlek.WIDTH)/5)*i), (float)(Fartlek.HEIGHT),false);
		}
		// Set value of empty spaces to be empty
		for (int i = 0; i < nulls; i++) {
			int zeroLoc = (int) (Math.random() * len);
			while (!sendBack[zeroLoc].emptyStatus()) {//if that random spot is already empty
				zeroLoc = (int) (Math.random() * len);
			}
			sendBack[zeroLoc].setEmpty(true);
		}
		// Fills up rest of indices with obstacles
		for (int i = 0; i < len; i++) {
			if (!sendBack[i].emptyStatus()){
				sendBack[i].setTexture("box.png");
			}
		}
		System.out.println("Done With Obstacles");
		return sendBack;
	}
	/**
	 * Handles user input
	 */
	@Override
	protected void handleInput() {
		// If you touched the screen
		if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
			// If the x,y position of the click is in the exit button
			if (exitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
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
			obstacleTimer+=dt;
			if(obstacleTimer>=MAX_OBSTACLE_TIMER){//timer has hit for a new obstacle line
				obstacleTimer=0;
				obstacleExists = true;//says that an obstacle line is moving
				//code goes here for obstacle
				obstacleLine = randomObstacles(5, 3);
			}
			System.out.println("Obstacle Exist?");
			if(obstacleExists){//moves the obstacle
				System.out.println("Obstacle Exists");
				for(int i = 0;i<5;i++){//moves the obstacles down
					obstacleLine[i].setY((float)((obstacleLine[i].getY())+4));
				}
				//if obstacleLine is below screen
				if ((obstacleLine[0].getY()-40)<0){
					obstacleExists = false;
					/*for(int i = 0;i<5;i++){//deletes the line
						obstacleLine[i].dispose();
					}*/
				}	
			}
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
				for(int i=0;i<sceneTiles.get(0).length;i++){
					sceneTiles.get(0)[i].dispose();
				}
				sceneTiles.remove(0);
				System.out.println("Removed Tile, now there are: " + sceneTiles.size() + " tile Arrays.");
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
		for(int i=0;i<sceneTiles.size();i++){
			for(int j=9;j<sceneTiles.get(i).length;i++){
				sb.draw(sceneTiles.get(i)[j].getTexture(),sceneTiles.get(i)[j].getPosition().x, sceneTiles.get(i)[j].getPosition().y);
			}
		}
		//draw obstacles here
		if(obstacleExists) {
			for (int i = 0; i < 5; i++) {
				sb.draw(obstacleLine[i].getTexture(), obstacleLine[i].getX(), obstacleLine[i].getY());
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
}
