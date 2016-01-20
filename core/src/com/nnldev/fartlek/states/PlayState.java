/**
 * @author Nano, Nick
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchSector;
import com.nnldev.fartlek.sprites.Enemy;
import com.nnldev.fartlek.sprites.Scene;
import com.nnldev.fartlek.sprites.Box;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;
import com.badlogic.gdx.math.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayState extends State {
    private Button exitBtn;
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
    private BitmapFont collatFont;
    private float collatFontY;
    private int obTypeChoose;

    private int collatCount;
    private boolean drawCollat;
    public static int killerID;
    public static String[] songs = {"Music\\exitthepremises" +
            ".mp3"};
    public static int currentSongNum;

    public static String[] OBSTACLE_TEXTURES = {"Items\\emptybox.png"};
    public static String tileTextureName = Fartlek.SCENE_BACKGROUND;
    public static float startYRotation, yRotationDiff;
    /**
     * All the phases of the game
     * 
     */
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
        PLAYSTATE_PHASE = Phase.RUNNING;
        obTypeChoose = 0;
        DONE = false;
        Texture texture = new Texture(Fartlek.PLAYER_ANIMATION_NAME);
        //rect one is the horizontal one
        Rectangle rect1 = new Rectangle(240 - (texture.getWidth() / 16), 160 + texture.getHeight() * (1 / 3), texture.getWidth() / Fartlek.PLAYER_ANIMATION_FRAMES, texture.getHeight() * (1 / 3));
        Rectangle rect2 = new Rectangle(240 - (texture.getWidth() / 48), 160, (texture.getWidth() / Fartlek.PLAYER_ANIMATION_FRAMES) * (1 / 3), texture.getHeight());
        Rectangle[] rectangles = {rect1, rect2};
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
        runner = new Runner("Characters\\stephen.png", 8, rectangles);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner(Fartlek.PLAYER_ANIMATION_NAME, Fartlek.PLAYER_ANIMATION_FRAMES, rectangles);

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
        //Adds a bunch of scene tiles
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }

        prevY = 0;
        currentSongNum = 0;
        startMusic(songs[currentSongNum]);
        PLAYSTATE_PHASE = Phase.RUNNING;
        Fartlek.SCORE = 0;

        collatCount = 0;
        drawCollat = false;
        killerID = -1;
        FreeTypeFontGenerator.FreeTypeFontParameter cParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        cParameter.size = 28;
        cParameter.color = Color.RED;
        collatFont = generator.generateFont(cParameter);
        collatFontY = 700;
        obTypeChoose = 0;
        obstacleSet = new ArrayList<Obstacle>();
        obstacleSet.add(new Box(boxTextureName, generateObXPos(), Fartlek.HEIGHT * 2, 100));
        newObstacles(4);
        bottomLeft = new TouchSector(0, 150, Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((Fartlek.WIDTH) / 2, 150, Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(0, 0, Fartlek.WIDTH, 149);
        startYRotation = Fartlek.rotations.y;
        yRotationDiff = 0;
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight() * (tiles - 1)) - 8);
    }
    /**
     * Makes new obstacles
     * @param amt The amount of obstacles to make
     */
    public void newObstacles(int amt) {
        for (int i = 0; i < amt; i++) {
            obTypeChoose = (int) (Math.random() * 2);
            if (obTypeChoose == 1) {
                obstacleSet.add(new Box(Fartlek.BOX_TEXTURE, generateObXPos(), generateObYPos(prevY), 100));
            } else {
                obstacleSet.add(new Enemy(Fartlek.ENEMY_TEXTURE, generateObXPos(), generateObYPos(prevY), 100));
            }
            prevY++;
        }
    }
    /**
     * Generates a random x position for the obstacle
     * @return returns a random float value for the x position of an obstacle
     */
    public float generateObXPos() {
        float xPos = -1;
        while ((xPos < 0) || (xPos > (Fartlek.WIDTH - Box.BOX_WIDTH))) {
            xPos = (float) (Math.random() * Fartlek.WIDTH);
        }
        return xPos;
    }
    /**
     * Generates a random y position for the obstacle
     * @return returns a random float value for the y position of an obstacle
     */
    public float generateObYPos(int prevY) {
        float yPos = obstacleSet.get(prevY).getYPosition() + Box.BOX_WIDTH;
        yPos += ((float) (Math.random() * Fartlek.HEIGHT / 3)) + (runner.getRectangle()[0].getWidth() / 2);
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
        music.setVolume(0.6f);
        if (Fartlek.soundEnabled) {
            music.play();
        }
    }
    /**
     * A game over method which handles what occurs when the player loses
     */
    public void gameOver() {
        music.stop();
        restartBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 4, Fartlek.HEIGHT / 5 * 2, false);
        quitBtn = new Button("Buttons\\exitbtn.png", Fartlek.WIDTH - ((Fartlek.WIDTH / 4) + (Fartlek.WIDTH / 6)),
                Fartlek.HEIGHT / 5 * 2, false);
        scoreFontX = (float) (Fartlek.WIDTH * 0.32);
        scoreFontY = (Fartlek.HEIGHT / 5) * 3;
        PLAYSTATE_PHASE = Phase.DEAD;
        Fartlek.SCORES.add(score);
    }


    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        //Checks if the screen was touched
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            //If it was pressed only once, not pressed and held
            if (Gdx.input.justTouched()) {
                //If the game is paused it will handle more functionality
                if (PLAYSTATE_PHASE == Phase.PAUSE) {
                    //If the mouse position is on the pause button
                    if (pauseBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        if (pauseBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                            gsm.push(new MenuState(gsm));
                            DONE = true;
                            dispose();
                        }
                    }
                    // If the x,y position of the click is in the play button
                    if (playBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        pauseBtn.setTexture("Buttons\\pause.png");
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
                //If the player is dead
                if (PLAYSTATE_PHASE == Phase.DEAD) {
                    if (restartBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        dispose();
                        gsm.push(new PlayState(gsm));
                    }
                    if (quitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        dispose();
                        gsm.push(new MenuState(gsm));
                    }
                }
            }
            //IF the player is dead
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
        yRotationDiff = Fartlek.rotations.y - startYRotation;
        //IF the player is runnning
        if (PLAYSTATE_PHASE == Phase.RUNNING) {
            if (Fartlek.GYRO_ON) {
                //runner.move((float) ((int) (yRotationDiff / 5)));
                if(Math.abs(float) ((int) (yRotationDiff / 5))>5){
                    if((float) ((int) (yRotationDiff / 5))>0){
                    runner.left();
                }else{
                    runner.right();
                }
                }
            }
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
                for (int j = 0; j < runner.getRectangle().length; j++) {
                    if (runner.getRectangle()[j].overlaps(obstacleSet.get(i).getRectangle())) {
                        DONE = true;
                        dead = true;
                        gameOver();
                    }
                }

            }
            //For every obstacle
            for (int i = 0; i < obstacleSet.size(); i++) {
                //For every buller
                for (int j = 0; j < runner.bullets.size(); j++) {
                    //If there is a coliision the obstacle disapears and the player gets a kill
                    if (runner.bullets.get(j).getRectangle().overlaps(obstacleSet.get(i).getRectangle())) {
                        if (obstacleSet.get(i).getPath().equals(Fartlek.ENEMY_TEXTURE)) {
                            obstacleSet.get(i).dispose();
                            obstacleSet.get(i).setRectangle(new Rectangle(-420, -69, 1, 1));
                            score += 5;
                            runner.bullets.get(j).kills++;
                            if (killerID == -1) {
                                killerID = j;
                            }
                        } else {
                            runner.bullets.remove(j);
                            killerID = -1;
                        }
                    }
                }
            }
            for (int i = 0; i < runner.bullets.size(); i++) {
                if (i == killerID) {
                    collatCount = runner.bullets.get(i).kills;
                    drawCollat = true;
                }
            }
            if (collatCount > 1) {

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
                    obstacleSet.get(i).getPosition().y, obstacleSet.get(i).getRectangle().width,
                    obstacleSet.get(i).getRectangle().height);
        }
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        scoreFont.draw(sb, "Score: " + score, scoreFontX, scoreFontY);
        if (PLAYSTATE_PHASE == Phase.DEAD) {
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
        scoreFont.draw(sb, "Score: " + score, scoreFontX, scoreFontY);
        if (drawCollat) {
            collatFont.draw(sb, "" + collatCount, Fartlek.WIDTH / 2, collatFontY);
            collatFontY++;
            if (collatFontY > Fartlek.HEIGHT) {
                drawCollat = false;
                collatFontY = 700;
            }
        }
        sb.end();
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        String scores;

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

    int partition(ArrayList<Integer> arr, int left, int right) {
        int i = left, j = right;
        int tmp;
        int pivot = arr.get((left + right) / 2);
        while (i <= j) {
            while (arr.get(i) < pivot)
                i++;
            while (arr.get(j) > pivot)
                j--;
            if (i <= j) {
                tmp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }


    private void quickSort(ArrayList<Integer> arr, int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }

}
