package com.nnldev.fartlek.states;

import com.badlogic.gdx.Application;
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

    /**
     * Returns how to play th game
     *
     * @return a string value holdign th info on how to play the game
     */
    public String getHowToPlay() {
        String text, out = "\n\n";
        //Reads the how to play file
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
        //If it is on android it won't read data files
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            out += "Use the middle left and right parts of the screen\n" +
                    "to move left and right and the bottom fifth to shoot.\n" +
                    "You an go into the settings menu and toggle gyro movement if you wish.";
        }
        return out;
    }

    /**
     * Returns the credits on who made the game and stuff
     *
     * @return The info on the publishers
     */
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
        //If on android, reading files that way wont work
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            out += "Game made by Nano,  Lazar and Nick";
        }
        return out;
    }

    /**
     * Gets the music credits
     *
     * @return
     */
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
        //If on android reading fils that way wont work
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            out = "Exit the Premises :\n" +
                    "\"Exit the Premises\" Kevin MacLeod (incompetech.com) \n" +
                    "Licensed under Creative Commons: By Attribution 3.0[1]\n" +
                    "\n" +
                    "Go Cart:\n" +
                    "\"Go Cart - Loop Mix\" Kevin MacLeod (incompetech.com) \n" +
                    "Licensed under Creative Commons: By Attribution 3.0[1]\n" +
                    "\n" +
                    "Latin Industries:\n" +
                    "\"Latin Industries\" Kevin MacLeod (incompetech.com) \n" +
                    "Licensed under Creative Commons: By Attribution 3.0[1]\n" +
                    "\n" +
                    "[1]http://creativecommons.org/licenses/by/3.0/";
        }
        return out;
    }

    /**
     * Returns the high scores in the data files if possible
     *
     * @return the string representation of the list of high scores
     */
    public String getHighScores() {
        String out = "\n\nTop 10 High Scores";
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
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
        } else {
            out += "\n" + Fartlek.androidReadScores;
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

    /**
     * Quik sorts an array
     *
     * @param arr   The integer array
     * @param left  The left point
     * @param right The right point
     */
    private void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }

    /**
     * partitions an array for quik sorting
     *
     * @param arr   The int array
     * @param left  The left point
     * @param right The right point
     * @return
     */
    private int partition(int arr[], int left, int right) {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];
        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            //Swaps the values
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
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
