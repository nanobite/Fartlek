/**
 * @author Nano,Nick, Lazar
 * Main class for the Fartlek game
 */
package com.nnldev.fartlek;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input.Orientation;

import com.nnldev.fartlek.essentials.Animation;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.states.MenuState;

import java.util.ArrayList;

public class Fartlek extends ApplicationAdapter implements InputProcessor {
    public static final int WIDTH = 480, HEIGHT = 800;
    public static final String TITLE = "Fartlek";
    //Can use this in any class to see if it is inside a rectangle.
    public static Vector3 mousePos;
    public static OrthographicCamera cam;
    public static boolean soundEnabled;
    public static boolean soundFXEnabled;
    public static int scrnHeight;
    public static int scrnVertBezel;
    public static boolean ACCELEROMETER_AVAILABLE;
    public int ORIENTATION;
    public Orientation nativeOrientation;
    public Vector3 ACCEL;
    private SpriteBatch batch;
    private GameStateManager gsm;
    private float accDelta;
    public Texture border;
    public static int SCORE;
    public static int SCORE_HIGH;
    public static ArrayList<Integer> SCORES;
    public static String PLAYER_ANIMATION_NAME;
    public static int PLAYER_ANIMATION_FRAMES;
    public static String SCENE_BACKGROUND;
    private FPSLogger fpsLogger;
    public static String[] songs = {"Music\\gocart.mp3","Music\\exitthepremises.mp3","Music\\latinindustries.mp3"};
    public static String[] scenes = {"Scene\\dirtybackgrnd.png","Scene\\stoneback.png","Scene\\forestmap.png"};
    public static int currentSongNum;
    public static int currentSceneNum;
    /**
     * The method where everything is created
     */
    @Override
    public void create() {
        currentSongNum = 0;
        currentSceneNum = 0;
        fpsLogger = new FPSLogger();
        PLAYER_ANIMATION_NAME = "Characters\\sphereAnim.png";
        PLAYER_ANIMATION_FRAMES = 9;
        SCENE_BACKGROUND = "Scene\\bckg.png";
        SCORES = new ArrayList<Integer>();
        soundEnabled = true;
        soundFXEnabled = true;
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(0.f, 0.f, 0.f, 1);
        mousePos = new Vector3();
        cam = new OrthographicCamera();
        scrnHeight = Gdx.graphics.getHeight();
        gsm.push(new MenuState(gsm));
        ACCELEROMETER_AVAILABLE = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (ACCELEROMETER_AVAILABLE) {
            ORIENTATION = Gdx.input.getRotation();
            nativeOrientation = Gdx.input.getNativeOrientation();
            ACCEL = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
        }
        border = new Texture("Extras&Logo\\border.png");
    }

    /**
     * The method which loops continuously and where all the events are handled.
     */
    @Override
    public void render() {
        scrnHeight = Gdx.graphics.getHeight();
        if (scrnHeight <= HEIGHT) {
            cam.setToOrtho(false, WIDTH, HEIGHT);
        } else {
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                cam.setToOrtho(false, WIDTH, scrnHeight);
                cam.translate(0, -(scrnHeight - HEIGHT) / 2, 0);
            } else {
                cam.setToOrtho(false, WIDTH, HEIGHT + (HEIGHT / 16));
                cam.translate(0, -(((border.getHeight() - HEIGHT) / 32)), 0);
            }
        }
        cam.update();
        accDelta += Gdx.graphics.getDeltaTime();
        updateAccValues();
        printAccValues(accDelta);
        scrnVertBezel = (scrnHeight - WIDTH) / 2;
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        batch.begin();
        batch.draw(border, -(border.getWidth() - WIDTH) / 2, -(border.getHeight() / 4));
        batch.end();
    }

    /**
     * Prints the accelerometer values in regular intervals.
     *
     * @param accDelta
     */
    public void printAccValues(float accDelta) {
        if (accDelta >= 0.5f && ACCELEROMETER_AVAILABLE) {
            System.out.println("GYRO - (X: " + ACCEL.x + " Y: " + ACCEL.y + " Z: " + ACCEL.z + ")");
            accDelta = 0;
        }
    }

    /**
     * Updates the accelerometer values
     */
    public void updateAccValues() {
        if (ACCELEROMETER_AVAILABLE) {
            ACCEL.set(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
        }
    }

    /**
     * Disposes of random useless stuff.
     */
    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Sets the location of the mouse to wherever the mouse was pressed relative to the screen size.
     *
     * @param screenX The X on the screen
     * @param screenY The Y on the screen
     * @param pointer The mouse
     * @param button  idk
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mousePos.set(screenX, screenY, 0);
        cam.unproject(mousePos);
        return true;
    }

    /**
     * Sets the location of the mouse to wherever the mouse was released relative to the screen size.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mousePos.set(screenX, screenY, 0);
        cam.unproject(mousePos);
        return true;
    }

    /**
     * Sets the location of the mouse to wherever the mouse is being dragged relative to the screen size.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @return
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mousePos.set(screenX, screenY, 0);
        cam.unproject(mousePos);
        return true;
    }

    /**
     * Handles key down events
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Handles key up events
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Handles key typed events
     *
     * @param character
     * @return
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Handles mouse moved events
     *
     * @param screenX
     * @param screenY
     * @return
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Handles scrolled events
     *
     * @param amount
     * @return
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
