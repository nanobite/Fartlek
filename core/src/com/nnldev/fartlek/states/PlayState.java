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
import com.nnldev.fartlek.essentials.*;
import com.nnldev.fartlek.sprites.*;

import java.util.ArrayList;

public class PlayState extends State {
    private Button exitBtn;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private Music music;
    private ArrayList<Scene> sceneTiles;
    private Box emptyBox;
    private String boxTextureName;
    private String tileTextureName;
    private boolean DONE;
    private int tileWidth;
    private int tileHeight;
    private int tiles = 3;
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
        emptyBox = new Box("Items\\emptybox.png", 0, 0, 0);
        boxTextureName = "Items\\box.png";
        tileTextureName = "Scene\\bckg1.png";
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner("Characters\\ship1Anim.png", 3);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        tileWidth = new Texture(tileTextureName).getWidth();
        tileHeight = new Texture(tileTextureName).getHeight();
        sceneTiles = new ArrayList<Scene>();
        sceneTiles.add(0, new Scene(tileTextureName, 0, 0));
        for (int i = 1; i < tiles; i++) {
            sceneTiles.add(i, new Scene(tileTextureName, 0, i * sceneTiles.get(0).getTexture().getHeight()));
        }
        currentSongNum = 0;
        startMusic(songs[currentSongNum]);
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(0).getTexture().getHeight()*(tiles-1)) - 10);
    }

    /**
     * Starts playing a song
     *
     * @param song The name of the song to play
     */
    public void startMusic(String song) {
        try{
            if(music.isPlaying()) {
                music.stop();
            }
        }catch (Exception e){
            System.out.println("Music Stop Error: \n"+e);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal(song));
        music.setLooping(false);
        music.setVolume(0.5f);
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
     * @param dt The game state manager which organizes which states will be
     *           shown
     */
    @Override
    public void update(float dt) {//dt is delta time
        if (!music.isPlaying()) {
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
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
        sb.end();
    }
    //TODO: Dispose of obstacles once they are fixed

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        runner.dispose();
        music.stop();
        music.dispose();
        for (Scene scene : sceneTiles) {
            scene.dispose();
        }
        sceneTiles.clear();
    }
}
