package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bat;

public class EnemyGenerator implements IGameObject {
    public static ArrayList<Enemy> enemies = new ArrayList<>(); // 임시
    private Handler hander = new Handler();
    public static int wave = 0;
    protected static final float TIME_TO_NEXT_WAVE = 30.0f;
    protected final int ENEMY_PER_WAVE_INCREMENT = 5;
    public static float elapsedTime = TIME_TO_NEXT_WAVE * 0.9f;

    @Override
    public void update(float eTime) {
        elapsedTime += eTime;
        if (elapsedTime > TIME_TO_NEXT_WAVE) {
            elapsedTime -= TIME_TO_NEXT_WAVE;
            wave++;
            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                spawnEnemy(scene);
                if (Bat.spawnWave > wave) {
                    Bat.maxNum += ENEMY_PER_WAVE_INCREMENT;
                }
            }
        }
    }

    private void spawnEnemy(BaseScene scene) {
        Player player = scene.getPlayer();
        if (player != null) {
            for (int i = 0; i < Bat.maxNum; i++) {
                Bat bat = new Bat(i * 0.2f, 0, player);
                scene.add(bat);
                addEnemy(bat);
            }
        }
    }

    private void addEnemy(Enemy enemy) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                enemies.add(enemy);
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
