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
    private Button soundBtn;

    /**
     * @param gsm
     */
    public SettingsState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        if (Fartlek.soundEnabled) {
            soundBtn = new Button("Buttons\\sound.png", Fartlek.WIDTH / 3, Fartlek.HEIGHT * 2 / 3, true);
        } else {
            soundBtn = new Button("Buttons\\nosound.png", Fartlek.WIDTH / 3, Fartlek.HEIGHT * 2 / 3, true);
        }
    }

    /**
     *
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
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
        sb.draw(soundBtn.getTexture(), soundBtn.getPosition().x, soundBtn.getPosition().y);
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        soundBtn.dispose();
    }
}
