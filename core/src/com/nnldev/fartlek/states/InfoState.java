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
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        output = getMusicCredits() + getMakerCredits() + getHowToPlay() + getHighScores();
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

    public String getHowToPlay() {
        String text, out = "\n\n";
        try {
            FileReader fr = new FileReader("Extras&Logo\\howtoplay.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while (!eof) {
                text = br.readLine();
                if (text.equals(null)) {
                    eof = true;
                } else {
                    out += "\n" + text;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load how to play.");
        }
        return out;
    }

    public String getMakerCredits() {
        String text, out = "\n\n";
        try {
            FileReader fr = new FileReader("Extras&Logo\\makercredits.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while (!eof) {
                text = br.readLine();
                if (text.equals(null)) {
                    eof = true;
                } else {
                    out += "\n" + text;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load maker credits.");
        }
        return out;
    }

    public String getMusicCredits() {
        String text, out = "";
        try {
            FileReader fr = new FileReader("Music\\musiccredits.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean eof = false;
            while (!eof) {
                text = br.readLine();
                if (text.equals(null)) {
                    eof = true;
                } else {
                    out += "\n" + text;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load music credits.");
        }
        return out;
    }

    public String getHighScores() {
        String out = "\n\nTop 10 High Scores";
        int scores = 0;
        try {
            FileReader fr = new FileReader("Extras&Logo\\scores.txt");
            BufferedReader br = new BufferedReader(fr);
            String text;
            boolean eof = false;
            while (!eof) {
                text = br.readLine();
                if (text == (null)) {
                    eof = true;
                } else {
                    scores++;
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] nums = new int[scores];
        if (scores > 0) {
            try {
                FileReader fr = new FileReader("Extras&Logo\\scores.txt");
                BufferedReader br = new BufferedReader(fr);
                for (int i = 0; i < scores; i++) {
                    nums[i] = Integer.parseInt(br.readLine());
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            quickSort(nums, 0, nums.length - 1);
            invertArray(nums);
            for (int i = 0; i < (Math.min(nums.length, 10)); i++) {
                out += "\n\t" + nums[i];
            }
        }
        return out;
    }

    private void invertArray(int arr[]) {
        int[] tmp = new int[arr.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = arr[(arr.length - 1) - i];
        }
        for (int i = 0; i < tmp.length; i++) {
            arr[i] = tmp[i];
        }
    }

    private void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }

    private int partition(int arr[], int left, int right) {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        ;

        return i;
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
