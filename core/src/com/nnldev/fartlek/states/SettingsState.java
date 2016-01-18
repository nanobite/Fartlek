package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

/**
 * Created by Nano on 02/01/2016.
 */
public class SettingsState extends State {
    private Button exitBtn;
    private Button musicBtn;
    private Button soundFXBtn;

    /**
     * @param gsm
     */
    public SettingsState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH * 0.874), (float) (Fartlek.HEIGHT * 0.924), false);
        if (Fartlek.soundEnabled) {
            musicBtn = new Button("Buttons\\sound.png", Fartlek.WIDTH / 3, Fartlek.HEIGHT * 2 / 3, true);
        } else {
            musicBtn = new Button("Buttons\\nosound.png", Fartlek.WIDTH / 3, Fartlek.HEIGHT * 2 / 3, true);
        }
        if (Fartlek.soundFXEnabled) {
            soundFXBtn = new Button("Buttons\\soundfx.png", Fartlek.WIDTH / 3 * 2, Fartlek.HEIGHT * 2 / 3, true);
        } else {
            soundFXBtn = new Button("Buttons\\nosoundfx.png", Fartlek.WIDTH / 3 * 2, Fartlek.HEIGHT * 2 / 3, true);
        }
    }

    /**
     *
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
            if (musicBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                if (musicBtn.getPath().equals("Buttons\\sound.png")) {
                    Fartlek.soundEnabled = false;
                    musicBtn.setTexture("Buttons\\nosound.png");
                } else {
                    Fartlek.soundEnabled = true;
                    musicBtn.setTexture("Buttons\\sound.png");
                }
            }
            if (soundFXBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                if (soundFXBtn.getPath().equals("Buttons\\soundfx.png")) {
                    Fartlek.soundFXEnabled = false;
                    soundFXBtn.setTexture("Buttons\\nosoundfx.png");
                } else {
                    Fartlek.soundFXEnabled = true;
                    soundFXBtn.setTexture("Buttons\\soundfx.png");
                }
            }
        }
    }

    /**
     * @param deltaTime The time since the previous update
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    /**
     * @param sb All the graphics that will be drawn
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
        sb.draw(musicBtn.getTexture(), musicBtn.getPosition().x, musicBtn.getPosition().y);
        sb.draw(soundFXBtn.getTexture(), soundFXBtn.getPosition().x, soundFXBtn.getPosition().y);
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        musicBtn.dispose();
        soundFXBtn.dispose();
    }
}
