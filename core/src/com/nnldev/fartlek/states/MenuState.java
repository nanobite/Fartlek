/**
 * @author Nano
 * Menu state for the Fartlek game
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

public class MenuState extends State {
    private Button playBtn;
    private Button soundBtn;

    /**
     * Makes a new menu state
     *
     * @param gsm The game state manager which organizes which states will be shown
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2, true);
        if (Fartlek.soundEnabled) {
            soundBtn = new Button("Buttons\\sound.png", 30, Fartlek.HEIGHT - 30, true);
        } else {
            soundBtn = new Button("Buttons\\nosound.png", 30, Fartlek.HEIGHT - 30, true);
        }

    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (playBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new PlayState(gsm));
                dispose();
            }
            if (soundBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                if (soundBtn.getPath().equals("Buttons\\sound.png")) {
                    Fartlek.soundEnabled = false;
                    soundBtn.setTexture("Buttons\\nosound.png");
                } else {
                    Fartlek.soundEnabled = true;
                    soundBtn.setTexture("Buttons\\sound.png");
                }
            }
        }

    }

    /**
     * Updates the menu state and all the information
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        handleInput();

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
        sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y);
        sb.draw(soundBtn.getTexture(), soundBtn.getPosition().x, soundBtn.getPosition().y);
        sb.end();
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        playBtn.dispose();
        soundBtn.dispose();
    }
}
