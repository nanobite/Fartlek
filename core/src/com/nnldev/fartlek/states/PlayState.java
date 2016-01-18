/**
 * @author Nano, Nick
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Application;
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
    private String pauseExitPath;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private Music music;
    private float musicPos;
    private ArrayList<Scene> sceneTiles;
    private Box emptyBox = new Box("Items\\emptybox.png", 0, 0, 0, true);
    private ArrayList<Obstacle[]> obstacleSet;
    private String emptyBoxTextureName = "Items\\emptybox.png";
    private String realBoxTextureName;
    private String boxTextureName;
    private int score;
    private boolean DONE;
    private int tiles = 3;
    private BitmapFont scoreFont;
    private BitmapFont deadFont;
    private FreeTypeFontGenerator generator;

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
        realBoxTextureName = "Items\\woodbox.png";
        tileTextureName = Fartlek.scenes[Fartlek.currentSceneNum];
        pauseExitPath = "Buttons\\pausebtn.png";
        pauseBtn = new Button(pauseExitPath, (float) (Fartlek.WIDTH * 0.86), (float) (Fartlek.HEIGHT * 0.92), true);
        playBtn = new Button("Buttons\\play.png", (Fartlek.WIDTH / 2), (Fartlek.HEIGHT / 2), true);
        runner = new Runner(Fartlek.PLAYER_ANIMATION_NAME, Fartlek.PLAYER_ANIMATION_FRAMES,Fartlek.PLAYER_RECT_BUFFER);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        score = 0;
        musicPos = 0f;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/vp.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sParameter.size = 38;
        sParameter.color = Color.BLACK;
        FreeTypeFontGenerator.FreeTypeFontParameter dParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        dParameter.size = 50;
        dParameter.color = Color.BLACK;
        scoreFont = generator.generateFont(sParameter);
        deadFont = generator.generateFont(dParameter);
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        obstacleSet = new ArrayList<Obstacle[]>();
        newObstacles();
        startMusic(Fartlek.songs[Fartlek.currentSongNum]);
        PLAYSTATE_PHASE = Phase.RUNNING;
        Fartlek.SCORE = 0;
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight() * (tiles - 1)) - 8);
    }

    public void newObstacles() {
        obstacleSet.add(new Obstacle[Obstacle.OBS_PER_ROW]);
        int[] obLine = generateObLine((int) ((Math.random() * 4) + 1),5);
        for (int i = 0; i < obstacleSet.get(obstacleSet.size() - 1).length; i++) {
            if (obLine[i] == 0) {
                boxTextureName = emptyBoxTextureName;
            } else {
                boxTextureName = realBoxTextureName;
            }
            obstacleSet.get(obstacleSet.size() - 1)[i] = new Box(boxTextureName, 20 + (i * 90), Fartlek.HEIGHT, 100);
        }
    }

    public int[] generateObLine(int zeros, int len) {
        int[] line =new int[len];
        for(int i=0;i<len;i++){
            line[i] = 1;
        }
        for (int i = 0; i < zeros; i++) {
            int spot = (int) (Math.random() * 5);
            while (line[spot] == 0) {
                spot = (int) (Math.random() * 5);
            }
            line[spot] = 0;
        }
        return line;
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
        if (Fartlek.soundEnabled)
            music.play();
    }

    public void gameOver() {
        int showAds =(int)((Math.random()*3)+1);
        if(showAds == 1){
            Fartlek.SHOW_AD = true;
        }
        music.stop();
        restartBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2, true);
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
                for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                    obstacleSet.get(i)[j].update(dt);
                }
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                    if ((obstacleSet.get(i)[j].getPosition().y + obstacleSet.get(i)[j].getRectangle().height) < 0) {
                        obstacleSet.remove(i);
                        newObstacles();
                        score++;
                        break;
                    }
                }
            }
            for (int i = 0; i < obstacleSet.size(); i++) {
                for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                    if ((runner.getRectangle().overlaps(obstacleSet.get(i)[j].getRectangle())) &&
                            obstacleSet.get(i)[j].getPath().equals(realBoxTextureName)) {
                        DONE = true;
                        PLAYSTATE_PHASE = Phase.DEAD;
                        gameOver();
                        j = Obstacle.OBS_PER_ROW;
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
        scoreFont.draw(sb, "Score: " + score, Fartlek.WIDTH / 35, (Fartlek.HEIGHT - (Fartlek.HEIGHT / 70)));
        if (PLAYSTATE_PHASE == Phase.DEAD) {
            deadFont.draw(sb, "GAME OVER", Fartlek.WIDTH / 5, (Fartlek.HEIGHT / 3) * 2);
            sb.draw(restartBtn.getTexture(), restartBtn.getPosition().x, restartBtn.getPosition().y, restartBtn.getRectangle().width,
                    restartBtn.getRectangle().height);
        } else {
            sb.draw(pauseBtn.getTexture(), pauseBtn.getPosition().x, pauseBtn.getPosition().y);
            if (PLAYSTATE_PHASE == Phase.PAUSE) {
                sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y);
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
        Fartlek.SCORES.add(Fartlek.SCORE);
        if (Fartlek.SCORE > Fartlek.SCORE_HIGH) {
            Fartlek.SCORE_HIGH = Fartlek.SCORE;
        }
        System.out.println("Score: " + Fartlek.SCORE);
        pauseBtn.dispose();
        runner.dispose();
        music.stop();
        if (PLAYSTATE_PHASE == Phase.DEAD) {
            restartBtn.dispose();
        }
        music.dispose();
        generator.dispose();
        for (Scene scene : sceneTiles) {
            scene.dispose();
        }
        for (int i = 0; i < obstacleSet.size(); i++) {
            for (int j = 0; j < Obstacle.OBS_PER_ROW; j++) {
                obstacleSet.get(i)[j].dispose();
            }
        }
        sceneTiles.clear();
        obstacleSet.clear();
    }
}
