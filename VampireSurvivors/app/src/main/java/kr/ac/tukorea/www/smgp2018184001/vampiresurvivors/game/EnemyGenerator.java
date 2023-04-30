package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bat;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Ghost;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.LizardPawn;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Mantichana;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Skeleton;

public class EnemyGenerator implements IGameObject {
    public static ArrayList<ICollidable> enemies = new ArrayList<>(); // 임시
    public static HashMap<EEnemyType, Integer> enemyWave = new HashMap<>();
    private static Handler hander = new Handler();
    public static int wave = 0;
    protected static final float TIME_TO_NEXT_WAVE = 30.0f;
    protected final int ENEMY_PER_WAVE_INCREMENT = 5;
    public static float elapsedTime = TIME_TO_NEXT_WAVE * 0.9f;
    private final int INITIAL_NUM_OF_ENEMY = 15;
    private final int ENEMY_INCREMENT_PER_WAVE = 5;

    public EnemyGenerator() {
        enemyWave.put(EEnemyType.Bat, 1);
        enemyWave.put(EEnemyType.Skeleton, 4);
        enemyWave.put(EEnemyType.Ghost, 7);
        enemyWave.put(EEnemyType.Mantichana, 10);
        enemyWave.put(EEnemyType.LizardPawn, 13);
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
        if (player != null) {
            enemyWave.entrySet().iterator().forEachRemaining((entry) -> {
                int spawnNum = (wave - entry.getValue()) * ENEMY_INCREMENT_PER_WAVE;
                spawnNum = spawnNum >= 0 ? spawnNum += INITIAL_NUM_OF_ENEMY : 0;
                Log.d(null, "Spawn: " + entry.getKey() + ", count: " + spawnNum);
                Enemy e = null;
                float posX, posY;
                for (int i = 0; i < spawnNum; i++) {
                    posX = getRandomPos(true);
                    posY = getRandomPos(false);
                    switch (entry.getKey()) {
                        case Bat:
                            e = new Bat(posX, posY, player);
                            break;
                        case Skeleton:
                            e = new Skeleton(posX, posY, player);
                            break;
                        case Ghost:
                            e = new Ghost(posX, posY, player);
                            break;
                        case Mantichana:
                            e = new Mantichana(posX, posY, player);
                            break;
                        case LizardPawn:
                            e = new LizardPawn(posX, posY, player);
                            break;
                        default:
                            break;
                    }
                    if (e != null) {
                        scene.add(e);
                        addEnemy(e);
                    }
                }
            });
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

    private float getRandomPos(boolean isPosX) {
        Player player = BaseScene.getTopScene().getPlayer();
        Random rand = new Random();
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
