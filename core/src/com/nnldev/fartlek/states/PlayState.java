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
import com.nnldev.fartlek.sprites.Bullet;
import com.nnldev.fartlek.sprites.Enemy;
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
    private int obTypeChoose;
    private String tileTextureName;
    private int score;
    private boolean DONE;
    private int tiles;
    private int prevY;
    private BitmapFont scoreFont;
    private BitmapFont deadFont;
    private BitmapFont collatFont;
    private float scoreFontX;
    private float scoreFontY;
    private float collatFontY;
    private FreeTypeFontGenerator generator;
    private boolean dead;
    private boolean pause;
    private int collatCount;
    private boolean drawCollat;
    public static int killerID;
    private boolean justUnpaused;
    public static String[] songs = {"Music\\song1.mp3"};
    public static int currentSongNum;

    /**
     * Creates a new game state
     *
     * @param gsm The game state manager which is controlling this state
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        DONE = false;
        tileTextureName = "Scene\\tile1.png";
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
        FreeTypeFontGenerator.FreeTypeFontParameter cParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        cParameter.size = 22;
        cParameter.color = Color.RED;
        scoreFont = generator.generateFont(sParameter);
        deadFont = generator.generateFont(dParameter);
        collatFont = generator.generateFont(cParameter);
        scoreFontX = Fartlek.WIDTH / 35;
        scoreFontY = (Fartlek.HEIGHT - (Fartlek.HEIGHT / 60));
        collatFontY = 700;
        tiles = 3;
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        obstacleSet = new ArrayList<Obstacle>();
        obstacleSet.add(new Box(boxTextureName, generateObXPos(), Fartlek.HEIGHT * 2, 100));
        obTypeChoose = 0;
        prevY = 0;
        newObstacles(4);
        collatCount = 0;
        drawCollat = false;
        killerID = -1;
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
        Rectangle restartRect = new Rectangle(restartBtn.getPosition().x, restartBtn.getPosition().y, Fartlek.WIDTH / 6,
                Fartlek.WIDTH / 6);
        restartBtn.setRectangle(restartRect);
        quitBtn = new Button("Buttons\\exitbtn.png", Fartlek.WIDTH - ((Fartlek.WIDTH / 4) + (Fartlek.WIDTH / 6)),
                Fartlek.HEIGHT / 5 * 2, false);
        Rectangle quitRect = new Rectangle(quitBtn.getPosition().x, quitBtn.getPosition().y, Fartlek.WIDTH / 6, Fartlek.WIDTH / 6);
        quitBtn.setRectangle((quitRect));
        scoreFontX = (float) (Fartlek.WIDTH * 0.32);
        StringBuilder sb = new StringBuilder();
        sb.append(score);
        String sScore = sb.toString();
        if (sScore.length() > 1) {
            scoreFontX -= 9 * (sScore.length() - 1);
        }
        scoreFontY = (Fartlek.HEIGHT / 5) * 3;
    }


    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        justUnpaused = false;
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            if (Gdx.input.justTouched()) {
                if (!dead) {
                    if (!pause) {
                        // If the x,y position of the click is in the pause button
                        if (pauseBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                            pause = true;
                            musicPos = music.getPosition();
                            music.stop();
                            DONE = true;
                            pauseBtn.setTexture("Buttons\\exitbtn.png");
                        }
                    } else {
                        if (pauseBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                            DONE = true;
                            dispose();
                            gsm.push(new MenuState(gsm));
                        }
                        // If the x,y position of the click is in the play button
                        if (playBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                            pause = false;
                            justUnpaused = true;
                            music.play();
                            music.setPosition(musicPos);
                            DONE = false;
                            pauseBtn.setTexture("Buttons\\pause.png");
                        }
                    }
                } else {
                    if (restartBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        dispose();
                        MenuState.startGameSound.play(0.75f);
                        gsm.push(new PlayState(gsm));
                    }
                    if (quitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        dispose();
                        gsm.push(new MenuState(gsm));
                    }
                }
            }
            if (!DONE && !justUnpaused) {
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
                obstacleSet.get(i).update(dt);
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                if ((obstacleSet.get(i).getPosition().y + obstacleSet.get(i).getRectangle().height) < 0) {
                    obstacleSet.remove(0);
                    prevY--;
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
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < runner.bullets.size(); j++) {
                    if (runner.bullets.get(j).getRectangle().overlaps(obstacleSet.get(i).getRectangle())) {
                        if (obstacleSet.get(i).getPath().equals(enemyTextureName)) {
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
        if (runner.bullets.size() > 0) {
            for (int i = 0; i < runner.bullets.size(); i++) {
                runner.bullets.get(i).dispose();
            }
            runner.bullets.clear();
        }
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
