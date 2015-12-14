package com.nnldev.fartlek.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.GameStateManager;

public class LoadState extends State {

    public LoadState(final GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(Fartlek.cam.combined);
        sb.begin();
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
