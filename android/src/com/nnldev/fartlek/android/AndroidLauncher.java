package com.nnldev.fartlek.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.nnldev.fartlek.Fartlek;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, GameHelper.GameHelperListener {
    private GameHelper gameHelper;

    public AndroidLauncher() {
        gameHelper = new GameHelper(this, 0);
        gameHelper.enableDebugLog(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameHelper.setup(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Fartlek(), config);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdBuddiz.setPublisherKey("3`8e23cd4-a54c-4101-a470-eb9583d04395");
        AdBuddiz.cacheAds(this);
        showAd();
        ManageAds manageAds = new ManageAds();
        manageAds.start();
        ManageAchievements manageAchievements = new ManageAchievements();
        manageAchievements.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        gameHelper.onActivityResult(request, response, data);
    }


    public void showAd() {
        AdBuddiz.showAd(this);
        System.out.println("Ad Shown");
        Fartlek.SHOW_AD = false;
    }

    /**
     * Shows an ad on every resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        showAd();
    }

    @Override
    public boolean getSignedInGPGS() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void loginGPGS() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ex) {
        }
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {

    }

    @Override
    public void getLeaderboardGPGS() {
    }

    @Override
    public void getAchievementsGPGS() {
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
    }

    @Override
    public void submitScoreGPGS(int score) {
    }


    /**
     * I love libgdx but I hate that I have to make creative solutions to some problems.
     */
    public class ManageAds extends Thread {
        /**
         * Constructor for the thread.
         */
        ManageAds() {
        }

        /**
         * Runs the thread
         */
        public void run() {
            while (true) {
                if (Fartlek.SHOW_AD) {
                    showAd();
                }
            }

        }
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
}
