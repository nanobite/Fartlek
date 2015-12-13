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
import com.nnldev.fartlek.essentials.TouchQuadrant;
import com.nnldev.fartlek.sprites.FloorTile;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;

import java.util.ArrayList;

public class PlayState extends State {
    private Button exitBtn;
    private Runner runner;
    private TouchQuadrant bottomLeft;
    private TouchQuadrant bottomRight;
    private Music music;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<FloorTile[]> floorTiles;
    private int tileWidth;
    private int tileHeight;

    /**
     * Creates a new game state
     *
     * @param gsm
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner("rabbit.png");
        bottomLeft = new TouchQuadrant(0, 0, Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2);
        bottomRight = new TouchQuadrant(Fartlek.WIDTH / 2, 0, Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2);
        music = Gdx.audio.newMusic(Gdx.files.internal("music1.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        obstacles = new ArrayList<Obstacle>();
        if (Fartlek.soundEnabled) {
            music.play();
        }
        tileWidth = new Texture("floortile.png").getWidth();
        tileHeight = new Texture("floortile.png").getHeight();
        floorTiles = new ArrayList<FloorTile[]>();
        newTileRow(0);
    }

    public void newTileRow(int index) {
        floorTiles.add(new FloorTile[FloorTile.TILES_PER_ROW]);
        for (int i = 0; i < floorTiles.get(0).length; i++) {
            floorTiles.get(index)[i] = new FloorTile("floortile.png", i * tileWidth, Fartlek.HEIGHT + tileHeight);
        }
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            if (exitBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
            if (bottomLeft.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                runner.left();
            }
            if (bottomRight.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                runner.right();
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
        runner.update();
        for (FloorTile[] tileArray : floorTiles) {
            for (FloorTile tile : tileArray) {
                tile.update();
            }
        }
        if ((floorTiles.get(floorTiles.size() - 1)[0].getPosition().y + floorTiles.get(floorTiles.size() - 1)[0].getRectangle().height) == Fartlek.HEIGHT) {
            System.out.println("Top Y: " + floorTiles.get(floorTiles.size() - 1)[0].getPosition().y);
            floorTiles.add(new FloorTile[FloorTile.TILES_PER_ROW]);
            for (int i = 0; i < floorTiles.get(0).length; i++) {
                floorTiles.get(floorTiles.size() - 1)[i] = new FloorTile("floortile.png", i * tileWidth, Fartlek.HEIGHT);
            }
        }


        if ((floorTiles.get(0)[0].getPosition().y + floorTiles.get(0)[0].getRectangle().height) < 0) {
            floorTiles.remove(0);
        }

    }

    /**
     * Renders the graphics to the screen
     *
     * @param sb
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
