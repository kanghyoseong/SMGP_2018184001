package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.util.Log;

public class Player extends Character {
    private static final String TAG = Player.class.getSimpleName();
    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int maxHp_increment = 2;
    public static float PLAYER_MOVEMENTSPEED = 0.5f;

    public Player(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        movementSpeed = PLAYER_MOVEMENTSPEED;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedInvincibleTime > 0) {
            elapsedInvincibleTime -= eTime;
        } else {
            aSprite.setIsInvincible(false);
        }
    }

    @Override
    public void killThis() {
        Log.d(TAG, "Game Over");
    }
}
