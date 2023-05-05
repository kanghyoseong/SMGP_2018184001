package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;

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
    public void killThis() {
        Log.d(TAG, "Game Over");
    }

    public Enemy findNearestEnemy() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return null;
        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        if (enemies.isEmpty()) return null;

        Enemy enemy = (Enemy) enemies.get(0);
        float distance = 999.f;
        for (IGameObject curEnemy : enemies) {
            float curDistance = Math.abs(posX - ((Enemy) curEnemy).getPosX())
                    + Math.abs(posY - ((Enemy) curEnemy).getPosY());
            if (distance > curDistance) {
                distance = curDistance;
                enemy = (Enemy) curEnemy;
            }
        }
        return enemy;
    }
}
