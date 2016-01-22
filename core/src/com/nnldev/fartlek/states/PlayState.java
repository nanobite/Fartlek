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
import java.io.FileReader;
import java.io.FileWriter;
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
        Rectangle rect1 = new Rectangle(240 - (texture.getWidth() / 16), 160 + ((float) texture.getHeight() * (float) (1 / 3)), texture.getWidth() / Fartlek.PLAYER_ANIMATION_FRAMES, texture.getHeight() * (1 / 3));
        Rectangle rect2 = new Rectangle(240 - (texture.getWidth() / 48), 160, (texture.getWidth() / Fartlek.PLAYER_ANIMATION_FRAMES) * (float)(1 / 3), texture.getHeight());
        Rectangle[] rectangles = {rect1, rect2};
        boxTextureName = Fartlek.BOX_TEXTURE;
        tileTextureName = Fartlek.SCENE_BACKGROUND;
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

    public float generateObXPos() {
        float xPos = -1;
        while ((xPos < 0) || (xPos > (Fartlek.WIDTH - Box.BOX_WIDTH))) {
            xPos = (float) (Math.random() * Fartlek.WIDTH);
        }
        return xPos;
    }

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
        music.setVolume(0.5f);
        if (Fartlek.soundEnabled) {
            music.play();
        }
    }

    /**
     * Handles events that occur when th player dies
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
        Fartlek.SHOW_AD = ((int) (Math.random() * 5) == 1);//I love these
    }


    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        //If th screen is touched
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            //if the screen is touchd once
            if (Gdx.input.justTouched()) {
                //Switches between actionsfo the playr while running, while dead and while pausing
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
            //If the runner is running
            if (PLAYSTATE_PHASE == Phase.RUNNING) {
                //Allows for the player to move using gyro

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
        //Makes for prtty stuff
        if (Fartlek.GYRO_ON&&PLAYSTATE_PHASE==Phase.RUNNING) {
            if (Math.abs(yRotationDiff) > 1.5f) {
                if (yRotationDiff < 0) {
                    runner.move(Math.max(-10f, yRotationDiff));
                } else {
                    runner.move(Math.min(10f, yRotationDiff));
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
        yRotationDiff = (Fartlek.rotations.y - startYRotation) / 5;
        //Moves the player based on the gyro
        if (PLAYSTATE_PHASE == Phase.RUNNING) {

            runner.update(dt);
            // Loops through all the tiles and updates their positions
            for (int i = 0; i < sceneTiles.size(); i++) {
                sceneTiles.get(i).update();
            }
            //Resets scene the scene tile which is below the screen
            for (int i = 0; i < tiles; i++) {
                if ((sceneTiles.get(i).getPosition().y + sceneTiles.get(i).getRectangle().height) < 0) {
                    resetSceneTile(i);
                }
            }
            //Updates all the obstacles
            for (int i = 0; i < obstacleSet.size(); i++) {
                obstacleSet.get(i).update(dt);
            }
            //Removes any un-needed obstacles
            for (int i = 0; i < obstacleSet.size(); i++) {
                if ((obstacleSet.get(i).getPosition().y + obstacleSet.get(i).getRectangle().height) < 0) {
                    obstacleSet.remove(0);
                    prevY -= 1;
                    newObstacles(1);
                    score++;
                }
            }
            //Checks for obstacle - player collision
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < runner.getRectangle().length; j++) {
                    if (runner.getRectangle()[j].overlaps(obstacleSet.get(i).getRectangle())) {
                        DONE = true;
                        dead = true;
                        gameOver();
                    }
                }

            }
            //Checks for obstacle bullet collision
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < runner.bullets.size(); j++) {

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
            //Checks for collateral
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
        //Loops through all the scene tiles and renders them
        for (Scene tile : sceneTiles) {
            sb.draw(tile.getTexture(), tile.getPosition().x, tile.getPosition().y);
        }
        //Loops through all obstacles and draws them
        for (int i = 0; i < obstacleSet.size(); i++) {
            sb.draw(obstacleSet.get(i).getTexture(), obstacleSet.get(i).getPosition().x,
                    obstacleSet.get(i).getPosition().y, obstacleSet.get(i).getRectangle().width,
                    obstacleSet.get(i).getRectangle().height);
        }

        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        scoreFont.draw(sb, "Score: " + score, scoreFontX, scoreFontY);
        //Draws play button
        if (PLAYSTATE_PHASE == Phase.PAUSE) {
            sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y, 200, 200);
        }
        //Draws dead guy stuff
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
        //Draws bullets
        if (runner.shoot) {
            //Loops through bullets
            for (int i = 0; i < runner.bullets.size(); i++) {
                runner.bullets.get(i).render(sb);
            }
        }
        scoreFont.draw(sb, "Score: " + score, scoreFontX, scoreFontY);
        if (drawCollat) {
            //Draws collateral txt
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
     * Adds score to the txt file on desktop
     *
     * @param score The scor to be appended to the text file.
     */
    public void addScoreToFile(int score) {
        String out = "";
        try {
            FileReader fr = new FileReader("Extras&Logo\\scores.txt");
            BufferedReader br = new BufferedReader(fr);
            String txt;
            boolean eof = false;
            while (!eof) {
                txt = br.readLine();
                if (txt == null) {
                    eof = true;
                    out += score;
                } else {
                    out += txt + "\n";
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
        try {
            FileWriter fw = new FileWriter("Extras&Logo\\scores.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(out);
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        addScoreToFile(score);
        Fartlek.SCORE = score;
        Fartlek.androidScores = "";
        Fartlek.writeScore = true;
        pauseBtn.dispose();
        playBtn.dispose();
        if (PLAYSTATE_PHASE == Phase.DEAD) {
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
