package com.nnldev.fartlek.android;

/**
 * Created by Nano on 1/20/2016.
 */
public interface ActionResolver {
    /**
     * Signs in
     * @return
     */
    public boolean getSignedInGPGS();

    public void loginGPGS();

    public void submitScoreGPGS(int score);

    public void unlockAchievementGPGS(String achievementId);

    public void getLeaderboardGPGS();

    public void getAchievementsGPGS();
}
