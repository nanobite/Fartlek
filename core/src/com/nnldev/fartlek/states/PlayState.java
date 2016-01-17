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
    private Rectangle pauseRect;
    private Rectangle playRect;
    private String pauseExitPath;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private Music music;
    private float musicPos;
    private ArrayList<Scene> sceneTiles;
    private ArrayList<Obstacle[]> obstacleSet;
    private String emptyBoxTextureName;
    private String realBoxTextureName;
    private String boxTextureName;
    private String tileTextureName;
    private int score;
    private boolean DONE;
    private int tiles;
    private int prevY;
    private BitmapFont scoreFont;
    private BitmapFont deadFont;
    private FreeTypeFontGenerator generator;
    private boolean dead;
    private boolean pause;
    private boolean justUnpaused;
    private boolean musicRepeat;
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
        emptyBoxTextureName = "Items\\emptybox.png";
        boxTextureName = "Items\\box.png";
        tileTextureName = "Scene\\tile1.png";
        pauseExitPath = "Buttons\\pause.png";
        pauseBtn = new Button(pauseExitPath, (float) (Fartlek.WIDTH * 0.86), (float) (Fartlek.HEIGHT * 0.92), false);
        pauseRect = new Rectangle((float) (Fartlek.WIDTH * 0.86), (float) (Fartlek.HEIGHT * 0.92),
                (Fartlek.WIDTH / 7), (Fartlek.WIDTH / 7));
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
        musicRepeat = true;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/vp.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sParameter.size = 38;
        sParameter.color = Color.BLACK;
        FreeTypeFontGenerator.FreeTypeFontParameter dParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        dParameter.size = 50;
        dParameter.color = Color.BLACK;
        scoreFont = generator.generateFont(sParameter);
        deadFont = generator.generateFont(dParameter);
        tiles = 3;
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        obstacleSet = new ArrayList<Obstacle[]>();
        obstacleSet.add(new Obstacle[1]);
        obstacleSet.get(0)[0] = new Box(boxTextureName, (float) (Math.random() * Fartlek.WIDTH), Fartlek.HEIGHT * 2, 100);
        prevY = 0;
        for (int i = 0; i < 6; i++) {
            newObstacles();
        }
        currentSongNum = 0;
        startMusic(songs[currentSongNum]);
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight() * (tiles - 1)) - 8);
    }

    public void newObstacles() {
            //TODO: Weighted chance for 1 per row versus 2 per row
            obstacleSet.add(new Obstacle[1]);
            float[] xPos = generateObXPos(1);
            for (int j = 0; j < obstacleSet.get(obstacleSet.size() - 1).length; j++) {
                obstacleSet.get(obstacleSet.size() - 1)[j] = new Box(boxTextureName, xPos[j], generateObYPos(prevY), 100);
            }
            prevY = obstacleSet.size() - 1;
    }


    public float[] generateObXPos(int num) {
        float[] xPos = new float[num];
        xPos[0] = -Obstacle.OBSTACLE_WIDTH;
        float x = xPos[0];
        for (int i = 0; i < num; i++) {
            while (((xPos[i] + Obstacle.OBSTACLE_WIDTH) >= x) && ((x + Obstacle.OBSTACLE_WIDTH) >= xPos[i])) {
                xPos[i] = (float) (Math.random() * Fartlek.WIDTH);
            }
            x = xPos[i];
        }
        return xPos;
    }

    public float generateObYPos(int prevY) {
        float yPos = obstacleSet.get(prevY)[0].getYPosition();
        yPos += ((float) (Math.random() * Fartlek.HEIGHT / 3)) + Obstacle.OBSTACLE_WIDTH;
        return yPos;
    }


    /**
     * Starts playing a song
     *
     * @param song The name of the song to play
     */
    public void startMusic(String song) {
        music = Gdx.audio.newMusic(Gdx.files.internal(song));
        music.setLooping(false);
        music.setVolume(0.5f);
        if (Fartlek.soundEnabled)
            music.play();
    }

    public void gameOver() {
        music.stop();
        musicRepeat = false;
        restartBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2, true);
    }


    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        justUnpaused = false;
        if (Gdx.input.justTouched()) {
            if (!dead) {
                if (!pause) {
                    // If the x,y position of the click is in the pause button
                    if (pauseBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        pause = true;
                        musicPos = music.getPosition();
                        music.pause();
                        musicRepeat = false;
                        DONE = true;
                        pauseExitPath = "Buttons\\exitbtn.png";
                    }
                } else {
                    if (pauseBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        gsm.push(new MenuState(gsm));
                        DONE = true;
                        dispose();
                    }
                    // If the x,y position of the click is in the play button
                    if (playBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                        pause = false;
                        justUnpaused = true;
                        music.play();
                        music.setPosition(musicPos);
                        musicRepeat = true;
                        DONE = false;
                        pauseExitPath = "Buttons\\pause.png";
                    }
                }
            } else {
                if (restartBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                    dispose();
                    MenuState.startGameSound.play(0.75f);
                    gsm.push(new PlayState(gsm));
                }
            }
        }
        pauseBtn.setTexture(pauseExitPath);
        if (!justUnpaused) {
            if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
                if (!DONE) {
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
    }

    /**
     * Updates the play state and all the information
     *
     * @param dt The game state manager which organizes which states will be
     *           shown
     */
    @Override
    public void update(float dt) {//dt is delta time
        if (!music.isPlaying() && musicRepeat) {
            currentSongNum++;
            if (currentSongNum > songs.length - 1) {
                currentSongNum = 0;
            }
            startMusic(songs[currentSongNum]);
        }
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
                for (int j = 0; j < 1; j++) {
                    obstacleSet.get(i)[j].update(dt);
                }
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < 1; j++) {
                    if ((obstacleSet.get(i)[j].getPosition().y + obstacleSet.get(i)[j].getRectangle().height) < 0) {
                        obstacleSet.remove(0);
                        prevY -= 1;
                        newObstacles();
                        score++;
                        break;
                    }
                }
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < 1; j++) {
                    if (runner.getRectangle().overlaps(obstacleSet.get(i)[j].getRectangle())) {
                        DONE = true;
                        dead = true;
                        gameOver();
                        j = 1;
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
            for (int j = 0; j < 1; j++) {
                sb.draw(obstacleSet.get(i)[j].getTexture(), obstacleSet.get(i)[j].getPosition().x,
                        obstacleSet.get(i)[j].getPosition().y, (Fartlek.WIDTH) / 5.5f, (Fartlek.WIDTH) / 5.5f);
            }
        }
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        scoreFont.draw(sb, "Score: " + score, Fartlek.WIDTH / 35, (Fartlek.HEIGHT - (Fartlek.HEIGHT / 70)));
        if (dead) {
            deadFont.draw(sb, "GAME OVER", Fartlek.WIDTH / 5, (Fartlek.HEIGHT / 3) * 2);
            sb.draw(restartBtn.getTexture(), restartBtn.getPosition().x, restartBtn.getPosition().y, restartBtn.getRectangle().width,
                    restartBtn.getRectangle().height);
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
        runner.dispose();
        music.stop();
        music.dispose();
        generator.dispose();
        for (Scene scene : sceneTiles) {
            scene.dispose();
        }
        for (int i = 0; i < obstacleSet.size(); i++) {
            for (int j = 0; j < 1; j++) {
                obstacleSet.get(i)[j].dispose();
            }
        }
        sceneTiles.clear();
        obstacleSet.clear();
    }
}
