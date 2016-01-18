/**
 * @author Nano, Nick
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.*;
import com.nnldev.fartlek.sprites.*;

import java.util.ArrayList;

public class PlayState extends State {
    private Button exitBtn;
    private Button pauseBtn;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private Music music;
    private ArrayList<Scene> sceneTiles;
    private Box emptyBox = new Box("Items\\emptybox.png", 0, 0, 0, true);
    private ArrayList<Obstacle[]> obstacleSet;
    private boolean DONE;
    private int tiles = 3;
    private int OBSTACLE_AMT = 5, OBSTACLE_EMPTYS = 2;
    public static String[] songs = {"Music\\song1.mp3"};
    public static int currentSongNum;
    public static String[] OBSTACLE_TEXTURES = {"Items\\emptybox.png"};
    public static String tileTextureName = Fartlek.SCENE_BACKGROUND;

    public enum Phase {
        RUNNING, PAUSE
    }

    public Phase PLAYSTATE_PHASE;

    /**
     * Creates a new game state
     *
     * @param gsm The game state manager which is controlling this state
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        DONE = false;
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        pauseBtn = new Button("Buttons\\pausebtn.png", (float) (30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner(Fartlek.PLAYER_ANIMATION_NAME, Fartlek.PLAYER_ANIMATION_FRAMES,Fartlek.HIT_BOXES);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        obstacleSet = new ArrayList<Obstacle[]>();
        newObstacles();
        currentSongNum = 0;
        startMusic(songs[currentSongNum]);
        PLAYSTATE_PHASE = Phase.RUNNING;
        Fartlek.SCORE = 0;
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight() * (tiles - 1)) - 8);
    }

    /**
     * Creates a random array of obstacles
     *
     * @param len   The length of the array
     * @param nulls The number of empty slots in the array
     * @return Returns an array of randomly generated obstacles
     */
    public Obstacle[] randomObstacles(int len, int nulls) {
        Obstacle[] sendBack = new Obstacle[len];//creates array of obstacles
        for (int i = 0; i < len; i++) {//fills them up with 5 obstacles side by side, no picture but not truly "empty"
            sendBack[i] = new Box("empty.png", (float) ((((Fartlek.WIDTH) / 5) * i) + 23), (float) (Fartlek.HEIGHT), 100, false);
        }
        // Set value of empty spaces to be empty
        for (int i = 0; i < nulls; i++) {
            int zeroLoc = (int) (Math.random() * len);
            while (sendBack[zeroLoc].getEmpty()) {//if that random spot is already empty
                zeroLoc = (int) (Math.random() * len);
            }
            sendBack[zeroLoc].setEmpty(true);
        }
        // Fills up rest of indices with obstacles
        for (int i = 0; i < len; i++) {
            if (!sendBack[i].getEmpty()) {
                sendBack[i].setTexture(new Texture("obs.png"));
            }
        }
        System.out.println("Done With Obstacles");
        return sendBack;
    }

    /**
     * Makes new obstacles
     */
    public void newObstacles() {
        Fartlek.SCORE++;
        obstacleSet.add(new Obstacle[Obstacle.OBS_PER_ROW]);
        Obstacle[] obstacles = randomObstacles(OBSTACLE_AMT, OBSTACLE_EMPTYS);
        for (int i = 0; i < obstacleSet.get(obstacleSet.size() - 1).length; i++) {
            obstacleSet.get(obstacleSet.size() - 1)[i] = obstacles[i];
        }
    }

    /**
     * Starts playing a song
     *
     * @param song The name of the song to play
     */
    public void startMusic(String song) {
        try {
            if (music.isPlaying()) {
                music.stop();
            }
        } catch (Exception e) {
            System.out.println("Music Stop Error: \n" + e);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal(song));
        music.setLooping(false);
        music.setVolume(0.5f);
        if (Fartlek.soundEnabled)
            music.play();
    }

    public void pause() {
        if (PLAYSTATE_PHASE == Phase.RUNNING) {
            PLAYSTATE_PHASE = Phase.PAUSE;
            pauseBtn.setTexture("Buttons\\unpausebtn.png");
        } else if (PLAYSTATE_PHASE == Phase.PAUSE) {
            PLAYSTATE_PHASE = Phase.RUNNING;
            pauseBtn.setTexture("Buttons\\pausebtn.png");
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
            //If the player clicks the pause button
            if (pauseBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y) && Gdx.input.justTouched()) {
                pause();
            }
            if (PLAYSTATE_PHASE == Phase.RUNNING) {
                // If the x,y position of the click is in the bottom left
                if (bottomLeft.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                    runner.left();
                }
                // If the x,y position of the click is in the bottom right
                if (bottomRight.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                    runner.right();
                }// If the x,y position of the click is in the bottom middle
                if (bottomMiddle.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)
                        && Gdx.input.justTouched()) {
                    runner.shoot();
                }
            }


        }
    }

    /**
     * Updates the play state and all the information
     *
     * @param dt The game state manager which organizes which states will be
     *           shown
     */
    @Override
    public void update(float dt) {//dt is delta time
        handleInput();
        if (PLAYSTATE_PHASE == Phase.RUNNING) {
            if (!music.isPlaying()) {
                currentSongNum++;
                if (currentSongNum > songs.length - 1) {
                    currentSongNum = 0;
                }
                startMusic(songs[currentSongNum]);
            }

            if (!DONE) {
                runner.update(dt);
                // Loops through all the tiles and updates their positions
                for (int i = 0; i < sceneTiles.size(); i++) {
                    sceneTiles.get(i).update();
                }
                for (int i = 0; i < tiles; i++) {
                    if ((sceneTiles.get(i).getPosition().y + sceneTiles.get(i).getRectangle().height) < 0) {
                        resetSceneTile(i);
                    }
                }
                for (int i = 0; i < obstacleSet.size(); i++) {
                    for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                        obstacleSet.get(i)[j].update(dt);
                    }
                }
                for (int i = 0; i < obstacleSet.size(); i++) {
                    for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                        if ((obstacleSet.get(i)[j].getPosition().y + obstacleSet.get(i)[j].getRectangle().height) < 0) {
                            System.out.println("Collision");
                            obstacleSet.remove(i);
                            newObstacles();
                            break;
                        }
                    }
                }
                for (int i = 0; i < obstacleSet.size(); i++) {
                    for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                        boolean hit = false;
                        for(Rectangle rect: runner.getRectangle()){
                            if ((rect.overlaps(obstacleSet.get(i)[j].getRectangle())) &&
                                !obstacleSet.get(i)[j].getEmpty()) {
                                hit = true;
                            }
                        }
                        if (hit) {
                            gsm.push(new MenuState(gsm));
                            DONE = true;
                            dispose();
                        }
                    }
                }
            }
        }


    }

    /**
     * Renders the graphics to the screen
     *
     * @param sb The sprite batch which is all the stuff that's going to be
     *           drawn to the screen.
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(Fartlek.cam.combined);
        sb.begin();
        for (Scene tile : sceneTiles) {
            sb.draw(tile.getTexture(), tile.getPosition().x, tile.getPosition().y);
        }
        for (int i = 0; i < obstacleSet.size(); i++) {
            for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                sb.draw(obstacleSet.get(i)[j].getTexture(), obstacleSet.get(i)[j].getPosition().x,
                        obstacleSet.get(i)[j].getPosition().y, (Fartlek.WIDTH) / 5.5f, (Fartlek.WIDTH) / 5.5f);
            }
        }
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
        sb.draw(pauseBtn.getTexture(), pauseBtn.getPosition().x, pauseBtn.getPosition().y);
        //Draws a pause menu
        if (PLAYSTATE_PHASE == Phase.PAUSE) {
        }
        sb.end();
    }
    //TODO: Dispose of obstacles once they are fixed

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        Fartlek.SCORES.add(Fartlek.SCORE);
        if (Fartlek.SCORE > Fartlek.SCORE_HIGH) {
            Fartlek.SCORE_HIGH = Fartlek.SCORE;
        }
        System.out.println("Score: " + Fartlek.SCORE);
        exitBtn.dispose();
        pauseBtn.dispose();
        runner.dispose();
        music.stop();
        music.dispose();
        for (Scene scene : sceneTiles) {
            scene.dispose();
        }
        sceneTiles.clear();
    }
}
