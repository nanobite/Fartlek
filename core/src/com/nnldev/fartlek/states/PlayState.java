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
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchSector;
import com.nnldev.fartlek.sprites.Scene;
import com.nnldev.fartlek.sprites.Box;
import com.nnldev.fartlek.sprites.Obstacle;
import com.nnldev.fartlek.sprites.Runner;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayState extends State {
    private Obstacle[] obstacleLine;
    private boolean obstacleExists = false;
    private final int HORIZONTAL_OBSTACLE_BUFFER = 20;
    private int AMT_OBSTACLES = 9, AMT_HOLES = 4;
    private float obstacleTimer, MAX_OBSTACLE_TIMER = 4;//timer for obstacles
    private Button exitBtn;
    private Runner runner;
    private TouchSector bottomLeft;
    private TouchSector bottomRight;
    private TouchSector bottomMiddle;
    private ArrayList<Scene> sceneTiles;
    private String tileTextureName;
    private boolean DONE;
    private int tiles = 2;
    private int STARTBUFFER = 2;
    private float startTimer;
    private int obstacles = 6, emptyobstacles = 2;

    /**
     * Creates a new game state
     *
     * @param gsm The game state manager which is controlling this state
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        DONE = false;
        tileTextureName = "Scene\\bckg1.png";
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        runner = new Runner("Characters\\char1Anim.png", 9);
        bottomLeft = new TouchSector(0, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomRight = new TouchSector((2 * Fartlek.WIDTH) / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        bottomMiddle = new TouchSector(Fartlek.WIDTH / 3, 0, Fartlek.WIDTH / 3, Fartlek.HEIGHT / 2);
        sceneTiles = new ArrayList<Scene>();
        for (int i = 0; i < tiles; i++) {
            sceneTiles.add(new Scene(tileTextureName, 0, i * (new Texture(tileTextureName)).getHeight()));
        }
    }

    /**
     * Makes a new row of tiles
     */
    public void resetSceneTile(int index) {
        sceneTiles.get(index).setY((sceneTiles.get(index).getTexture().getHeight() * (tiles - 1)) - 8);
        System.out.println("Y Position: " + ((sceneTiles.get(index).getTexture().getHeight() * (tiles - 1)) - 8) + "\t\tIndex: " + index);
    }

    public Obstacle[] randomObstacles(int len, int nulls) {
        Obstacle[] sendBack = new Obstacle[len];//creates array of obstacles
        for (int i = 0; i < len; i++) {//fills them up with 5 obstacles side by side, no picture but not truly "empty"
            sendBack[i] = new Box("empty.png", (float) ((((Fartlek.WIDTH) / 5) * i) + 23), (float) (Fartlek.HEIGHT), false);
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

    public void die() {
        gsm.push(new MenuState(gsm));
        DONE = true;
        dispose();
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        // If you touched the screen
        if (Gdx.input.justTouched() || Gdx.input.isTouched()) {
            // If the x,y position of the click is in the exit button
            if (exitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
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
        startTimer += dt;
        handleInput();
        if (!DONE) {
            if (startTimer >= STARTBUFFER) {
                startTimer = STARTBUFFER;
                try {
                    System.out.println("Top Y Position: " + (obstacleLine[0].getPosition().y + obstacleLine[0].getTexture().getHeight()));
                    if ((obstacleLine[0].getPosition().y + obstacleLine[0].getTexture().getHeight()) < 0) {//timer has hit for a new obstacle line
                        obstacleExists = true;//says that an obstacle line is moving
                        obstacleLine = randomObstacles(obstacles, emptyobstacles); //second parameter is number of empty spots, 5 total spots for obstacle

                    }
                } catch (Exception e) {
                    obstacleExists = true;//says that an obstacle line is moving
                    //code goes here for obstacle
                    obstacleLine = randomObstacles(obstacles, emptyobstacles); //second parameter is number of empty spots, 5 total spots for obstacle
                }
            }

            if (obstacleExists) {//moves the obstacle
                for (int i = 0; i < obstacles; i++) {//moves the obstacles down
                    //System.out.println("" + obstacleLine[i].getPosition().y);
                    obstacleLine[i].setYPosition(((obstacleLine[i].getPosition().y) - 4));//moves by 9 down each tick
                    if (obstacleLine[i].getRectangle().overlaps(runner.getRectangle()) && !obstacleLine[i].getEmpty()) {
                        die();
                    }
                }
                //if obstacleLine is below screen
                if ((obstacleLine[0].getPosition().y + obstacleLine[0].getTexture().getHeight()) < 0) {
                    obstacleExists = false;
                }
            }
            for (int i = 0; i < sceneTiles.size(); i++) {
                if ((sceneTiles.get(i).getPosition().y + sceneTiles.get(i).getRectangle().height) < 0) {
                    resetSceneTile(i);
                }
            }
            runner.update(dt);
            // Loops through all the tiles and updates their positions
            for (Scene tile : sceneTiles) {
                tile.update();
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
        //draw obstacles here
        if (obstacleExists) {
            for (Obstacle toDraw : obstacleLine) {
                sb.draw(toDraw.getTexture(), toDraw.getPosition().x, toDraw.getPosition().y);
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
        for (Scene scene : sceneTiles)
            scene.dispose();
        sceneTiles.clear();
    }
}
