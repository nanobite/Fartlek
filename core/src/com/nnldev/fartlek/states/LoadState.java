package com.nnldev.fartlek.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.GameStateManager;

public class LoadState extends State {
    float time;

    /**
     * A load screen state
     *
     * @param gsm
     */
    public LoadState(final GameStateManager gsm) {
        super(gsm);
    }

    /**
     * Handles usr input
     */
    @Override
    protected void handleInput() {

    }

    /**
     * The update method where values ar updated
     *
     * @param deltaTime The time since the previous update
     */
    @Override
    public void update(float deltaTime) {
        time += deltaTime;
        if (time > 3) {
            gsm.push(new MenuState(gsm));
            dispose();
        }
    }

    /**
     * @param sb All the graphics that will be drawn
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(Fartlek.cam.combined);
        sb.begin();
        sb.draw(new Texture("Extras&Logo\\featuregraphic.png"), 0, 600, 480, 200);
        sb.draw(new Texture("Extras&Logo\\smalllogo.png"), Fartlek.WIDTH / 2 - new Texture("Extras&Logo\\smalllogo.png").getWidth() / 2, 200);
        sb.end();
    }

    /**
     * Disposs of unncessary resources
     */
    @Override
    public void dispose() {

    }
}
