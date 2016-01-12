package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

/**
 * Nano, Nick
 */
public class RunnerSelectState extends State {
    private Button exitBtn;
    private Button stephenBtn;//character stephen
    private Button tr8rBtn; //spinning guy

    /**
     *
     * @param gsm
     */
    public RunnerSelectState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        stephenBtn = new Button("Buttons\\stephenIcon.png", (float) (Fartlek.WIDTH/4), (float) (Fartlek.HEIGHT*0.7), true);
        tr8rBtn = new Button("Buttons\\c2.png", (float) (Fartlek.WIDTH/4), (float) (Fartlek.HEIGHT*0.5), true);
    }

    /**
     *
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            if(exitBtn.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//exit button clicked
                gsm.push(new MenuState(gsm));
                dispose();
            }
            if(stephenBtn.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//stephen character selected button clicked
                //add code to change button texture to make it "pressed looking"
                PLAYER_ANIMATION_NAME = "Characters\\stephen.png";
                PLAYER_ANIMATION_FRAMES = 8;
            }else if(tr8rBtn.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//spinning guy character selected button clicked
                //add code to change button texture to make it "pressed looking"
                PLAYER_ANIMATION_NAME = "Characters\\sphereAnim.png";
                PLAYER_ANIMATION_FRAMES = 9;
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
        sb.draw(stephenBtn.getTexture(),stephenBtn.getPosition().x,stephenBtn.getPosition().y);
        sb.draw(tr8rBtn.getTexture(),tr8r.getPosition().x,tr8r.getPosition().y);
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        stephenBtn.dispose();
        tr8rBtn.dispose();
    }
}
