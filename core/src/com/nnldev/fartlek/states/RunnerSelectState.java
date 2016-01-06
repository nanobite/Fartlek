package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

/**
 * Created by Nano on 02/01/2016.
 */
public class RunnerSelectState extends State {
    private Button exitBtn;

    /**
     *
     * @param gsm
     */
    public RunnerSelectState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
    }

    /**
     *
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            if(exitBtn.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){
                gsm.push(new MenuState(gsm));
                dispose();
            }
        }
    }

    /**
     *
     * @param deltaTime The time since the previous update
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    /**
     *
     * @param sb All the graphics that will be drawn
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(exitBtn.getTexture(),exitBtn.getPosition().x,exitBtn.getPosition().y);
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
    }
}
