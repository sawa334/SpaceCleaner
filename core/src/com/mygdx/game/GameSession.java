package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {

    long nextTrashSpawnTime;

    public GameState state;
    long nextEnemySpawnTime;
    long sessionStartTime;
    long pauseStartTime;

    public GameSession() {
    }

    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        nextEnemySpawnTime = sessionStartTime + (long) (GameSettings.STARTING_ENEMY_APPEARANCE_COOL_DOWN
                * getEnemyPeriodCoolDown());
    }

    private int getEnemyPeriodCoolDown() {
        return 0;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

        public boolean shouldSpawnTrash () {
            if (nextTrashSpawnTime <= TimeUtils.millis()) {
                nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                        * getTrashPeriodCoolDown());
                return true;
            }
            return false;
        }

        private float getTrashPeriodCoolDown () {
            return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime) / 1000);
        }

}
