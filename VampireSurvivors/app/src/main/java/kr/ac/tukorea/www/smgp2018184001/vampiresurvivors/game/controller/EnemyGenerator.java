package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Bat;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Ghost;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.LizardPawn;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Mantichana;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Skeleton;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;

public class EnemyGenerator implements IGameObject {
    private int wave = 0;
    protected static final float TIME_TO_NEXT_WAVE = 30.0f;
    private float elapsedTime = TIME_TO_NEXT_WAVE * 0.9f;
    private final int INITIAL_NUM_OF_ENEMY = 15;
    private final int ENEMY_INCREMENT_PER_WAVE = 5;
    private int numofSpawnedEnemies = -1;

    public EnemyGenerator() {
        if (BuildConfig.DEBUG) {
            wave = DebugFlag.START_WAVE;
        }
    }

    @Override
    public void update(float eTime) {
        elapsedTime += eTime;
        if (elapsedTime > TIME_TO_NEXT_WAVE ||
                numofSpawnedEnemies == 0) {
            elapsedTime = 0;
            wave++;
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return;
        Player player = MainScene.player;
        if (player == null) return;
        Random random = new Random();
        if (numofSpawnedEnemies == -1) numofSpawnedEnemies = 0;
        for (int i = 0; i < EEnemyType.Count.ordinal(); i++) {
            EEnemyType curType = EEnemyType.values()[i];
            int spawnNum = (wave - curType.getWave()) * ENEMY_INCREMENT_PER_WAVE;
            spawnNum = spawnNum >= 0 ? spawnNum += INITIAL_NUM_OF_ENEMY : 0;
            //Log.d(null, "Spawn: " + curType + ", count: " + spawnNum);
            Enemy e = null;
            float posX, posY;
            for (int j = 0; j < spawnNum; j++) {
                posX = getRandomPos(random, true);
                posY = getRandomPos(random, false);
                switch (curType) {
                    case Bat:
                        e = Bat.get(posX, posY, player);
                        break;
                    case Skeleton:
                        e = Skeleton.get(posX, posY, player);
                        break;
                    case Ghost:
                        e = Ghost.get(posX, posY, player);
                        break;
                    case Mantichana:
                        e = Mantichana.get(posX, posY, player);
                        break;
                    case LizardPawn:
                        e = LizardPawn.get(posX, posY, player);
                        break;
                    default:
                        break;
                }
                if (e != null) {
                    numofSpawnedEnemies++;
                    scene.add(MainScene.Layer.enemy, e);
                }
            }
        }
    }

    private float getRandomPos(Random rand, boolean isPosX) {
        Player player = MainScene.player;
        float pos;
        if (isPosX) {
            pos = rand.nextFloat() * (Object.boundary.right - Object.boundary.left) + Object.boundary.left;
            float min = player.getPosX() - Metrics.game_width;
            float max = player.getPosX() + Metrics.game_width;
            if (pos > min && pos < max) {
                if (pos < player.getPosX()) pos = min;
                else pos = max;
            }
        } else {
            pos = rand.nextFloat() * (Object.boundary.bottom - Object.boundary.top) + Object.boundary.top;
            float min = player.getPosY() - Metrics.game_height;
            float max = player.getPosY() + Metrics.game_height;
            if (pos > min && pos < max) {
                if (pos < player.getPosY()) pos = min;
                else pos = max;
            }
        }
        return pos;
    }

    public int getEnemyNum() {
        return numofSpawnedEnemies;
    }

    public void enemyDestroyed() {
        numofSpawnedEnemies--;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    public int getWave() {
        return wave;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
