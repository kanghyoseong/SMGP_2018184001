package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;

public class Player extends Character {
    private static final String TAG = Player.class.getSimpleName();
    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int maxHp_increment = 2;
    public static float PLAYER_MOVEMENTSPEED = 0.5f;
    ArrayList<IGameObject> enemiesInScreen = new ArrayList<>();
    HashMap<Passive.PassiveType, Integer> passiveItemNum = new HashMap<>();

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

    public Enemy findRandomEnemyInScreen() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return null;
        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        if (enemies.isEmpty()) return null;

        float range = (Metrics.game_width + Metrics.game_height) / 2.f;
        for (IGameObject e : enemies) {
            float distance = Math.abs(((Enemy) e).getPosX() - posX) +
                    Math.abs(((Enemy) e).getPosY() - posY);
            if (distance < range) {
                enemiesInScreen.add(e);
            }
        }
        if (enemiesInScreen.isEmpty()) return null;
        //Log.d(TAG, "enemiesinscreen: " + enemiesInScreen.size());
        Random random = new Random();
        Enemy enemy = (Enemy) enemiesInScreen.get(random.nextInt(enemiesInScreen.size()));
        enemiesInScreen.clear();
        return enemy;
    }

    public void addPassiveItem(Passive.PassiveType type) {
        if (passiveItemNum.get(type) == null) {
            passiveItemNum.put(type, 1);
            //Log.d(TAG, "first added " + type);
            return;
        }
        int num = passiveItemNum.get(type);
        if (num >= 5) {
            // -------------------- Increase Exp --------------------
            return;
        }
        passiveItemNum.put(type, num + 1);
        num = passiveItemNum.get(type);
        //Log.d(TAG, type + "added, num: " + num);
    }
}
