package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IGameObject;
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
                Log.d(null, "Key: " + entry.getKey() + ", value: " + entry.getValue());
            });
            Bat bat = new Bat(0, 0, player);
            scene.add(bat);
            addEnemy(bat);
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

    @Override
    public void draw(Canvas canvas) {
    }
}
