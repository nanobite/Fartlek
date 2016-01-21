package com.nnldev.fartlek.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nnldev.fartlek.Fartlek;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AndroidLauncher extends AndroidApplication {

    FileOutputStream fos;
    public String SCORE_FILE = "SCORES";

    public AndroidLauncher() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Fartlek(), config);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdBuddiz.setPublisherKey("3`8e23cd4-a54c-4101-a470-eb9583d04395");
        AdBuddiz.cacheAds(this);
        AdBuddiz.setTestModeActive();
        System.out.println((AdBuddiz.isReadyToShowAd(this))?"Show":"Don't Show");
        AdBuddiz.showAd(this);
        ManageAchievements manageAchievements = new ManageAchievements();
        manageAchievements.start();
        ManageScores manageScores = new ManageScores();
        //manageScores.start();
        try {
            fos = openFileOutput(SCORE_FILE, Context.MODE_PRIVATE);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeScores(String out) {
        File f = new File(SCORE_FILE);
        try {
            fos = openFileOutput(SCORE_FILE, Context.MODE_PRIVATE);
            fos.write(out.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readScores() {
        String out = "", collected = null;
        FileInputStream fis = null;
        try {
            fis = openFileInput(SCORE_FILE);
            byte[] dataArray = new byte[fis.available()];
            while (fis.read(dataArray) != -1) {
                collected = new String(dataArray);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
    }

    /**
     * Shows an ad on every resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * I love libgdx but I hate that I have to make creative solutions to some problems.
     */
    public class ManageAchievements extends Thread {
        /**
         * Constructor for the thread.
         */
        ManageAchievements() {
        }

        /**
         * Runs the thread
         */
        public void run() {
            while (true) {
                if (Fartlek.showAchievements) {
                    Fartlek.showAchievements = false;
                    switch (Fartlek.achievement) {
                        case One:
                            break;
                        case Two:
                            break;
                        case Three:
                            break;
                        case Four:
                            break;
                        case Five:
                            break;
                    }
                }
            }
        }
    }

    public class ManageScores extends Thread {
        /**
         * Constructor for the thread.
         */
        ManageScores() {
        }

        /**
         * Runs the thread
         */
        public void run() {
            while (true) {
                if (Fartlek.writeScore) {
                    Fartlek.writeScore = false;
                    writeScores(Fartlek.getAndroidWriteScores);
                }
                if (Fartlek.readScore) {
                    Fartlek.readScore = false;
                    Fartlek.androidReadScores = readScores();
                }
            }
        }
    }
}
