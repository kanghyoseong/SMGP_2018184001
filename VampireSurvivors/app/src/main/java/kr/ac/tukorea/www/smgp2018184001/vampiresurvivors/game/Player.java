package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.util.Log;

public class Player extends Character {
    private static final String TAG = Player.class.getSimpleName();
    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int maxHp_increment = 2;
    private float elapsedInvincibleTime = 0;
    private float INVINCIBLETIME = 1f;
    public static float PLAYER_MOVEMENTSPEED = 0.5f;
    //private Item items[];

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

    public void getDamage(int damage) {
        if (isInvincible()) {
            aSprite.setIsInvincible(true);
            curHp -= damage;
            Log.d(TAG, "HP: " + curHp);
            elapsedInvincibleTime = INVINCIBLETIME;
            if (curHp <= 0) {
                // Game Over
                Log.d(TAG, "Game Over");
            }
        }
    }

    public boolean isInvincible() {
        return elapsedInvincibleTime <= 0;
    }
}
