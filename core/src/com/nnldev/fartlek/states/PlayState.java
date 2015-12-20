/**
 * @author Nano
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
import com.nnldev.fartlek.sprites.FloorTile;
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
    private ArrayList<Obstacle> obstacles;
    private ArrayList<FloorTile[]> floorTiles;
    private int tileWidth;
    private int tileHeight;
    private String tileTextureName;

    /**
     * Creates a new game state
     *
     * @param gsm The game state manager which is controlling this state
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        tileTextureName = "Scene\\bckg.png";
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner("Characters\\ship1Anim.png", 3);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        obstacles = new ArrayList<Obstacle>();
        tileWidth = new Texture(tileTextureName).getWidth();
        tileHeight = new Texture(tileTextureName).getHeight();
        floorTiles = new ArrayList<FloorTile[]>();
        newTileRow();
        startMusic("music1.mp3");
    }

    /**
     * Starts playing a song
     *
     * @param song The name of the song to play
     */
    public void startMusic(String song) {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music\\song1.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        if (Fartlek.soundEnabled) music.play();
    }

    public void newObstacles() {

    }

    /**
     * Makes a new row of tiles
     */
    public void newTileRow() {
        floorTiles.add(new FloorTile[FloorTile.TILES_PER_ROW]);
        for (int i = 0; i < floorTiles.get(0).length; i++) {
            floorTiles.get(floorTiles.size() - 1)[i] = new FloorTile(tileTextureName, i * tileWidth, Fartlek.HEIGHT);
        }
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        //If oyu thouched the screen
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            //If the x,y position of the click is in the exit button
            if (exitBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
            //If the x,y position of the click is in the bottom left
            if (bottomLeft.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                runner.left();
            }
            //If the x,y position of the click is in the bottom right
            if (bottomRight.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                runner.right();
            }
            if (bottomMiddle.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
            }

        }
    }

    /**
     * Updates the play state and all the information
     *
     * @param deltaTime The game state manager which organizes which states will be shown
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
        runner.update(deltaTime);
        //Loops through all the tiles and updates their positions
        for (FloorTile[] tileArray : floorTiles) {
            for (FloorTile tile : tileArray) {
                tile.update();
            }
        }
        //If the array at the top's height + its y position are equal to the height of the screen, it will add another array on top of it
        if ((floorTiles.get(floorTiles.size() - 1)[0].getPosition().y + floorTiles.get(floorTiles.size() - 1)[0].getRectangle().height) == Fartlek.HEIGHT) {
            newTileRow();
        }
        //If the array of tiles goes below 0 then it will dispose of it to avoid memory leaks and save space
        if ((floorTiles.get(0)[0].getPosition().y + floorTiles.get(0)[0].getRectangle().height) < 0) {
            System.out.println("Dispose of tile.");
            //Removes the oldest one
            for (FloorTile tile : floorTiles.get(0)) {
                tile.dispose();
            }
            floorTiles.remove(0);
        }

    }

    /**
     * Renders the graphics to the screen
     *
     * @param sb The sprite batch which is all the stuff that's going to be drawn to the screen.
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(Fartlek.cam.combined);
        sb.begin();
        for (Obstacle obstacle : obstacles)
            sb.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y);
        for (FloorTile[] tileArray : floorTiles) {
            for (FloorTile tile : tileArray) {
                sb.draw(tile.getTexture(), tile.getPosition().x, tile.getPosition().y);
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
        for (Obstacle obstacle : obstacles)
            obstacle.dispose();
    }
}
