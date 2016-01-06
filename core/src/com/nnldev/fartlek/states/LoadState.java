package com.nnldev.fartlek.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.GameStateManager;

public class LoadState extends State {
    /**
     * A load screen state
     *
     * @param gsm
     */
    public LoadState(final GameStateManager gsm) {
        super(gsm);
    }

    /**
     *
     */
    @Override
    protected void handleInput() {

    }

    /**
     *
     * @param deltaTime The time since the previous update
     */
    @Override
    public void update(float deltaTime) {

    }

    /**
     *
     * @param sb All the graphics that will be drawn
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(Fartlek.cam.combined);
        sb.begin();
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
    }
}
