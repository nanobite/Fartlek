/**
 * @author Nano
 * In game Play State for the Fartlek game.
 */
package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

public class PlayState extends State {
    private Button exitBtn;

    /**
     * Creates a new game state
     *
     * @param gsm
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtn.getRectangle().contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
                dispose();
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
        sb.end();
    }

    /**
     * Disposes of objects to avoid memory leaks
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
    }
}
