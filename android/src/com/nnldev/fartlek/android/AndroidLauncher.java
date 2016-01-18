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
        AdBuddiz.setPublisherKey("38e23cd4-a54c-4101-a470-eb9583d04395");
        AdBuddiz.cacheAds(this);
        AdBuddiz.showAd(this);
        ManageAds manageAds = new ManageAds();
        manageAds.start();
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
     * Shows an ad
     */
    public void showAd() {
        AdBuddiz.showAd(this);
        Fartlek.SHOW_AD = false;
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
        public void run(){
            while(true){
                if (Fartlek.SHOW_AD) {
                    AdBuddiz.showAd(AndroidLauncher.this);
                }
            }
        }
    }
    public class ManageAchievements extends Thread{
        ManageAchievements(){
            
        }
        public void run(){
            while(true){
                if(Fartlek.Achievement!=Fartlek.Achievements.NONE){
                    switch(Fartlek.achievement){
                        case 5Run:
                            break;
                        case 10Run:
                            break;
                        case 25Run:
                            break;
                        case 50Run:
                            break;
                        case 100Run:
                            break;
                        case 10Death
                    }
                }
            }
        }
    }
}
