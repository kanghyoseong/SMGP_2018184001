package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Bat;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Ghost;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.LizardPawn;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Mantichana;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Skeleton;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;

public class EnemyGenerator implements IGameObject {
    public static ArrayList<ICollidable> enemies = new ArrayList<>(); // 임시
    private static Handler hander = new Handler();
    public static int wave = 1;
    protected static final float TIME_TO_NEXT_WAVE = 30.0f;
    public static float elapsedTime = TIME_TO_NEXT_WAVE * 0.9f;
    private final int INITIAL_NUM_OF_ENEMY = 15;
    private final int ENEMY_INCREMENT_PER_WAVE = 5;

    public EnemyGenerator() {
        if (BuildConfig.DEBUG) {
            wave = DebugFlag.START_WAVE;
        }
    }

    @Override
    public void update(float eTime) {
        elapsedTime += eTime;
        if (elapsedTime > TIME_TO_NEXT_WAVE) {
            elapsedTime -= TIME_TO_NEXT_WAVE;
            wave++;
            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                spawnEnemy(scene);
            }
        }
    }

    private void spawnEnemy(BaseScene scene) {
        Player player = scene.getPlayer();
        if (player == null) return;
        Random random = new Random();
        for (int i = 0; i < EEnemyType.Count.ordinal(); i++) {
            EEnemyType curType = EEnemyType.values()[i];
            int spawnNum = (wave - curType.getWave()) * ENEMY_INCREMENT_PER_WAVE;
            spawnNum = spawnNum >= 0 ? spawnNum += INITIAL_NUM_OF_ENEMY : 0;
            Log.d(null, "Spawn: " + curType + ", count: " + spawnNum);
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
                    scene.add(MainScene.Layer.enemy, e);
                    addEnemy(e);
                }
            }
        }
    }

    public static void addEnemy(ICollidable enemy) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                enemies.add(enemy);
            }
        });
    }

    public static void removeEnemy(ICollidable enemy) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                enemies.remove(enemy);
            }
        });
    }

    private float getRandomPos(Random rand, boolean isPosX) {
        Player player = BaseScene.getTopScene().getPlayer();
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

    @Override
    public void draw(Canvas canvas) {
    }
}
