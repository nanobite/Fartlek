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
    //3 character buttons
    private Button stephenBtn;//character stephen
    private Button character2; //ship
    private Button character3;//character carl
    //3 background buttons
    private Button scene1;//stone
    private Button scene2;//tech
    private Button scene3;//forest
    /**
     *
     * @param gsm
     */
    public RunnerSelectState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        stephenBtn = new Button("Buttons\\stephenbtn.png", (float) (Fartlek.WIDTH/4), (float) (Fartlek.HEIGHT*0.7), true);
        character2 = new Button("Buttons\\shipbtn.png", (float) (Fartlek.WIDTH/4), (float) (Fartlek.HEIGHT*0.5), true);
        character3 = new Button("Buttons\\carlbtn.png", (float) (Fartlek.WIDTH/4), (float) (Fartlek.HEIGHT*0.3), true);
        scene1 = new Button("Buttons\\stonemapbtn.png", (float) (Fartlek.WIDTH*3/4), (float) (Fartlek.HEIGHT*0.7), true);
        scene2 = new Button("Buttons\\techbtn.png", (float) (Fartlek.WIDTH*3/4), (float) (Fartlek.HEIGHT*0.5), true);
        scene3 = new Button("Buttons\\forestmapbtn.png", (float) (Fartlek.WIDTH*3/4), (float) (Fartlek.HEIGHT*0.3), true);
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
            Fartlek.HorizontalPlayerBuffer = 0;
            if(stephenBtn.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//stephen character selected button clicked
                //add code to change button texture to make it "pressed looking"
                Fartlek.PLAYER_ANIMATION_NAME = "Characters\\stephen.png";
                Fartlek.PLAYER_ANIMATION_FRAMES = 8;
            }else if(character2.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)){//ship
                //add code to change button texture to make it "pressed looking"
                Fartlek.PLAYER_ANIMATION_NAME = "Characters\\shipAnim.png";
                Fartlek.PLAYER_ANIMATION_FRAMES = 3;
            }else if(character3.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//carl
                //add code to change button texture to make it "pressed looking"
                Fartlek.PLAYER_ANIMATION_NAME = "Characters\\carlAnim.png";
                Fartlek.PLAYER_ANIMATION_FRAMES = 14;
            }else if(scene1.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//stone map
                //add code to change button texture to make it "pressed looking"
                Fartlek.SCENE_BACKGROUND = "Scene\\stoneback.png";
                Fartlek.BOX_TEXTURE="Items\\woodbox.png";
                Fartlek.ENEMY_TEXTURE="Enemies\\zombie.png";
            }else if(scene2.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//tech map
                //add code to change button texture to make it "pressed looking"
                Fartlek.SCENE_BACKGROUND = "Scene\\techback.png";
                Fartlek.BOX_TEXTURE="Items\\techbox.png";
                Fartlek.ENEMY_TEXTURE="Enemies\\ufo.png";
            }else if(scene3.contains(Fartlek.mousePos.x,Fartlek.mousePos.y)){//forest map
                //add code to change button texture to make it "pressed looking"
                Fartlek.SCENE_BACKGROUND = "Scene\\forstmap.png";
                Fartlek.BOX_TEXTURE="Items\\woodbox.png";
                Fartlek.ENEMY_TEXTURE="Enemies\\snake.png";
                Fartlek.HorizontalPlayerBuffer = 30;
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
        sb.draw(character2.getTexture(),character2.getPosition().x,character2.getPosition().y);
        sb.draw(character3.getTexture(),character3.getPosition().x,character3.getPosition().y);
        sb.draw(scene1.getTexture(),scene1.getPosition().x,scene1.getPosition().y);
        sb.draw(scene2.getTexture(), scene2.getPosition().x, scene2.getPosition().y);
        sb.draw(scene3.getTexture(),scene3.getPosition().x,scene3.getPosition().y);
        sb.end();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        stephenBtn.dispose();
        character2.dispose();
        character3.dispose();
        scene1.dispose();
        scene2.dispose();
        scene3.dispose();
    }
}
