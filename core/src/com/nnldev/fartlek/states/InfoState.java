package com.nnldev.fartlek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Button;
import com.nnldev.fartlek.essentials.GameStateManager;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Nano on 02/01/2016.
 */
public class InfoState extends State {
    private Button exitBtn;
    private FreeTypeFontGenerator generator;
    private BitmapFont infoFont;
    String output;

    /**
     * New information screen state
     *
     * @param gsm
     */
    public InfoState(GameStateManager gsm) {
        super(gsm);
        exitBtn = new Button("Buttons\\exitbtn.png", (float) (Fartlek.WIDTH - 30), (float) (Fartlek.HEIGHT - 30), true);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/vp.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sParameter.size = 15;
        sParameter.color = Color.WHITE;
        FreeTypeFontGenerator.FreeTypeFontParameter dParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        dParameter.size = 30;
        dParameter.color = Color.WHITE;
        infoFont = generator.generateFont(sParameter);
        output = getMusicCredits() + getMakerCredits() + getHowToPlay();
    }

    /**
     * Handles user input
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtn.contains(Fartlek.mousePos.x, Fartlek.mousePos.y)) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
        }
    }
    public String getHowToPlay(){
        String text,out ="\n\n";
        try{
            FileReader fr = new FileReader("Extras&Logo\\howtoplay.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while(!eof){
                text = br.readLine();
                if(text.equals(null)){
                    eof = true;
                }else{
                    out+="\n"+text;
                }
            }
        }catch(Exception e){
            System.out.println("Could not load how to play.");
        }
        return out;
    }
    public String getMakerCredits(){
        String text,out ="\n\n";
        try{
            FileReader fr = new FileReader("Extras&Logo\\makercredits.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while(!eof){
                text = br.readLine();
                if(text.equals(null)){
                    eof = true;
                }else{
                    out+="\n"+text;
                }
            }
        }catch(Exception e){
            System.out.println("Could not load maker credits.");
        }
        return out;
    }
    public String getMusicCredits(){
        String text, out="";
        try{
            FileReader fr = new FileReader("Music\\musiccredits.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while(!eof){
                text = br.readLine();
                if(text.equals(null)){
                    eof = true;
                }else{
                    out+="\n"+text;
                }
            }
        }catch(Exception e){
            System.out.println("Could not load music credits.");
        }
        return out;
    }

    /**
     * handles numerical updates and calculations
     *
     * @param deltaTime The time since the previous update
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    /**
     * Handles rendering to the screen
     *
     * @param sb All the graphics that will be drawn
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(exitBtn.getTexture(), exitBtn.getPosition().x, exitBtn.getPosition().y);
        infoFont.draw(sb, output, 0, Fartlek.HEIGHT - 50);
        sb.end();
    }

    /**
     * Handles disposing of unneeded objects
     */
    @Override
    public void dispose() {
        exitBtn.dispose();
        infoFont.dispose();
    }
}
