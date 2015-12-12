/**
 * @author Nano
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;
import com.nnldev.fartlek.essentials.TouchQuadrant;
import com.nnldev.fartlek.sprites.Runner;

public class PlayState extends State {
    private Button exitBtn;
    private Runner runner;
    private TouchQuadrant bottomLeft;
    private TouchQuadrant bottomRight;
    private Music music;

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
        if (Fartlek.soundEnabled) {
            music.play();
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
        sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
        sb.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
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
    }
}
