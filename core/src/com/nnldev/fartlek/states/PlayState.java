/**
 * @author Nano
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchSector;
import com.nnldev.fartlek.sprites.Scene;
import com.nnldev.fartlek.sprites.Box;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class PlayState extends State {
    private Button pauseBtn;
    private Button playBtn;
    private Button restartBtn;
    private Button quitBtn;
    private Rectangle pauseRect;
    private Rectangle playRect;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private Music music;
    private float musicPos;
    private ArrayList<Scene> sceneTiles;
    private ArrayList<Obstacle> obstacleSet;
    private String boxTextureName;
    private int score;
    private boolean DONE;
    private int tiles;
    private int prevY;
    private BitmapFont scoreFont;
    private BitmapFont deadFont;
    private float scoreFontX;
    private float scoreFontY;
    private FreeTypeFontGenerator generator;
    private boolean dead;
    private boolean pause;
    private boolean justUnpaused;
    public static String[] songs = {"Music\\exitthepremises" +
            ".mp3"};
    public static int currentSongNum;

    public static String[] OBSTACLE_TEXTURES = {"Items\\emptybox.png"};
    public static String tileTextureName = Fartlek.SCENE_BACKGROUND;

    public enum Phase {
        RUNNING, PAUSE, DEAD
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
        boxTextureName = "Items\\woodbox.png";
        tileTextureName = "Scene\\forestmap.png";
        pauseBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH * 0.874), (float) (Fartlek.HEIGHT * 0.924), false);
        pauseRect = new Rectangle((float) (Fartlek.WIDTH * 0.874), (float) (Fartlek.HEIGHT * 0.924),
                (float) (pauseBtn.getTexture().getWidth() * 1.01), (float) (pauseBtn.getTexture().getHeight() * 1.01));
        pauseBtn.setTexture("Buttons\\pause.png");
        playBtn = new Button("Buttons\\play.png", (Fartlek.WIDTH / 2 - Fartlek.WIDTH / 6),
                (Fartlek.HEIGHT / 2 - Fartlek.HEIGHT / 8), false);
        playRect = new Rectangle(playBtn.getPosition().x, playBtn.getPosition().y,
                (Fartlek.WIDTH / 3), (Fartlek.HEIGHT / 4));
        pauseBtn.setRectangle(pauseRect);
        playBtn.setRectangle(playRect);
        runner = new Runner("Characters\\stephen.png", 8);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        score = 0;
        dead = false;
        pause = false;
        musicPos = 0f;
        justUnpaused = false;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/vp.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sParameter.size = 38;
        sParameter.color = Color.BLACK;
        FreeTypeFontGenerator.FreeTypeFontParameter dParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        dParameter.size = 50;
        dParameter.color = Color.BLACK;
        scoreFont = generator.generateFont(sParameter);
        deadFont = generator.generateFont(dParameter);
        scoreFontX = Fartlek.WIDTH / 35;
        scoreFontY = (Fartlek.HEIGHT - (Fartlek.HEIGHT / 60));
        tiles = 3;
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        obstacleSet = new ArrayList<Obstacle>();
        obstacleSet.add(new Box(boxTextureName, generateObXPos(), Fartlek.HEIGHT * 2, 100));
        prevY = 0;
        newObstacles(4);
        currentSongNum = 0;
        startMusic(songs[currentSongNum]);
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight() * (tiles - 1)) - 8);
    }

    public void newObstacles(int amt) {
        for (int i = 0; i < amt; i++) {
            obstacleSet.add(new Box(boxTextureName, generateObXPos(), generateObYPos(prevY), 100));
            prevY++;
        }
    }

    public float generateObXPos() {
        float xPos = -1;
        while ((xPos < 0) || (xPos > (Fartlek.WIDTH - Box.BOX_WIDTH))) {
            xPos = (float) (Math.random() * Fartlek.WIDTH);
        }
        return xPos;
    }

    public float generateObYPos(int prevY) {
        float yPos = obstacleSet.get(prevY).getYPosition() + Box.BOX_WIDTH;
        yPos += ((float) (Math.random() * Fartlek.HEIGHT / 3)) + (runner.getRectangle().height / 2);
        return yPos;
    }

    /**
     * Starts playing a song
     *
     * @param song The name of the song to play
     */
    public void startMusic(String song) {
        music = Gdx.audio.newMusic(Gdx.files.internal(song));
        music.setLooping(true);
        music.setVolume(0.5f);
        if (Fartlek.soundEnabled) {
            music.play();
        }
    }

    public void gameOver() {
        music.stop();
        restartBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 4, Fartlek.HEIGHT / 5 * 2, false);
        quitBtn = new Button("Buttons\\exitbtn.png", Fartlek.WIDTH - ((Fartlek.WIDTH / 4) + (Fartlek.WIDTH / 6)),
                Fartlek.HEIGHT / 5 * 2, false);
        scoreFontX = (float) (Fartlek.WIDTH * 0.32);
        scoreFontY = (Fartlek.HEIGHT / 5) * 3;
    }


    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            if (Gdx.input.justTouched()) {
                if (PLAYSTATE_PHASE == Phase.PAUSE) {
                    if (pauseBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        if (pauseBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                            gsm.push(new MenuState(gsm));
                            DONE = true;
                            dispose();
                        }
                    }
                    // If the x,y position of the click is in the play button
                    if (playBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        pauseBtn.setTexture("Buttons\\pausebtn.png");
                        PLAYSTATE_PHASE = Phase.RUNNING;
                        music.play();
                        music.setPosition(musicPos);
                        DONE = false;
                    }
                } else {
                    // If the x,y position of the click is in the exit button
                    if (pauseBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y) && !(PLAYSTATE_PHASE == Phase.PAUSE) && Gdx.input.justTouched()) {
                        PLAYSTATE_PHASE = Phase.PAUSE;
                        musicPos = music.getPosition();
                        music.pause();
                        DONE = true;
                        pauseBtn.setTexture("Buttons\\exitbtn.png");
                    }
                }
                if (PLAYSTATE_PHASE == Phase.DEAD) {
                    if (restartBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        dispose();
                        gsm.push(new PlayState(gsm));
                    }
                }

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
                obstacleSet.get(i).update(dt);
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                if ((obstacleSet.get(i).getPosition().y + obstacleSet.get(i).getRectangle().height) < 0) {
                    obstacleSet.remove(0);
                    prevY -= 1;
                    newObstacles(1);
                    score++;
                }
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                if (runner.getRectangle().overlaps(obstacleSet.get(i).getRectangle())) {
                    DONE = true;
                    dead = true;
                    gameOver();
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
            sb.draw(obstacleSet.get(i).getTexture(), obstacleSet.get(i).getPosition().x,
                    obstacleSet.get(i).getPosition().y,  obstacleSet.get(i).getRectangle().width,  
                    obstacleSet.get(i).getRectangle().height);
        }
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        scoreFont.draw(sb, "Score: " + score, scoreFontX, scoreFontY);
        if (dead) {
            deadFont.draw(sb, "GAME OVER", (float) (Fartlek.WIDTH / 5.7), (Fartlek.HEIGHT / 4) * 3);
            sb.draw(restartBtn.getTexture(), restartBtn.getPosition().x, restartBtn.getPosition().y, Fartlek.WIDTH / 6,
                    Fartlek.WIDTH / 6);
            sb.draw(quitBtn.getTexture(), quitBtn.getPosition().x, quitBtn.getPosition().y, Fartlek.WIDTH / 6,
                    Fartlek.WIDTH / 6);
        } else {
            sb.draw(pauseBtn.getTexture(), pauseBtn.getPosition().x, pauseBtn.getPosition().y, pauseBtn.getRectangle().width,
                    pauseBtn.getRectangle().height);
            if (pause) {
                sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y, playBtn.getRectangle().width,
                        playBtn.getRectangle().height);
            }
        }
        if (runner.shoot) {
            for (int i = 0; i < runner.bullets.size(); i++) {
                runner.bullets.get(i).render(sb);
            }
        }
        sb.end();
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        pauseBtn.dispose();
        playBtn.dispose();
        if (dead) {
            restartBtn.dispose();
            quitBtn.dispose();
        }
        runner.dispose();
        music.stop();
        music.dispose();
        generator.dispose();
        for (Scene scene : sceneTiles) {
            scene.dispose();
        }
        for (int i = 0; i < obstacleSet.size(); i++) {
            obstacleSet.get(i).dispose();
        }
        sceneTiles.clear();
        obstacleSet.clear();
    }
}
