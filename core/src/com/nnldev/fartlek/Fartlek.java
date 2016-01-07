/**
 * @author Nano
 * Main class for the Fartlek game
 */
package com.nnldev.fartlek;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input.Orientation;

import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.states.MenuState;

public class Fartlek extends ApplicationAdapter implements InputProcessor {
    public static final int WIDTH = 480, HEIGHT = 800;
    public static final String TITLE = "Fartlek";
    //Can use this in any class to see if it is inside a rectangle.
    public static Vector3 mousePos;
    public static OrthographicCamera cam;
    public static boolean soundEnabled;
    public static int scrnHeight;
    public static int scrnVertBezel;
    public boolean GYROAVAILABLE;
    public int ORIENTATION;
    public Orientation nativeOrientation;
    public Vector3 ACCEL;

    private SpriteBatch batch;
    private GameStateManager gsm;
    private float accDelta;
    //private FPSLogger fpsLogger;

    /**
     * The method where everything is created
     */
    @Override
    public void create() {
        //fpsLogger = new FPSLogger();
        soundEnabled = true;
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        //r,g,b,alpha
        Gdx.gl.glClearColor(1, 1, 1, 1);
        mousePos = new Vector3();
        cam = new OrthographicCamera();
        scrnHeight = Gdx.graphics.getHeight();
        gsm.push(new MenuState(gsm));
        GYROAVAILABLE = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (GYROAVAILABLE) {
            ORIENTATION = Gdx.input.getRotation();
            nativeOrientation = Gdx.input.getNativeOrientation();
            ACCEL = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
        }
    }

    /**
     * The method which loops continuously and where all the events are handled.
     */
    @Override
    public void render() {
        accDelta += Gdx.graphics.getRawDeltaTime();
        updateAccValues();
        if(accDelta>=0.5f&&GYROAVAILABLE){
            System.out.println("GYRO - (X: " + ACCEL.x + " Y: " + ACCEL.y + " Z: " + ACCEL.z + ")");
            accDelta = 0;
        }
        //So I was planning on making it so the screen was fulyl adjustable and stuff and would scale some stuff easier for larger screens but nah.
        //scrnHeight = Gdx.graphics.getHeight();
        scrnHeight = HEIGHT;
        Fartlek.cam.setToOrtho(false, Fartlek.WIDTH, Fartlek.scrnHeight);
        //fpsLogger.log();
        scrnVertBezel = (scrnHeight - WIDTH) / 2;
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    /**
     * Updates the accelerometer values
     */
    public void updateAccValues() {
        if (GYROAVAILABLE) {
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
