/**
 * @author Nano
 * Menu state for the Fartlek game
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

public class MenuState extends State {
    private Button playBtn;

    private Button infoBtn;
    private Button settingsBtn;
    private Button runnerBtn;
    public static Sound startGameSound;

    /**
     * Makes a new menu state
     *
     * @param gsm The game state manager which organizes which states will be shown
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Button("Buttons\\playbtn.png", Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2, true);
        infoBtn = new Button("Buttons\\infobtn.png", Fartlek.WIDTH / 2 - 120, Fartlek.HEIGHT / 2 - 100, true);
        settingsBtn = new Button("Buttons\\settings.png", Fartlek.WIDTH / 2, Fartlek.HEIGHT / 2 - 150, true);
        runnerBtn = new Button("Buttons\\runnericon.png", Fartlek.WIDTH / 2 + 120, Fartlek.HEIGHT / 2 - 100, true);
        startGameSound = Gdx.audio.newSound(Gdx.files.internal("Sounds\\startgamenoise.ogg"));
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (runnerBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new RunnerSelectState(gsm));
                dispose();
            }
            if (settingsBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new SettingsState(gsm));
                dispose();
            }
            if (infoBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new InfoState(gsm));
                dispose();
            }
            if (playBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                startGameSound.play(0.75f);
                gsm.push(new PlayState(gsm));
                dispose();
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
        sb.draw(settingsBtn.getTexture(), settingsBtn.getPosition().x, settingsBtn.getPosition().y);
        sb.draw(infoBtn.getTexture(), infoBtn.getPosition().x, infoBtn.getPosition().y);
        sb.draw(runnerBtn.getTexture(), runnerBtn.getPosition().x, runnerBtn.getPosition().y);
        sb.end();
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        playBtn.dispose();

    }
}
