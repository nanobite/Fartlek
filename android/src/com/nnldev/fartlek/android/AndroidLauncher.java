package com.nnldev.fartlek.android;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nnldev.fartlek.Fartlek;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Fartlek(), config);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdBuddiz.setPublisherKey("3`8e23cd4-a54c-4101-a470-eb9583d04395");
        AdBuddiz.cacheAds(this);
        showAd();
        ManageAds manageAds = new ManageAds();
        manageAds.start();
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
}
