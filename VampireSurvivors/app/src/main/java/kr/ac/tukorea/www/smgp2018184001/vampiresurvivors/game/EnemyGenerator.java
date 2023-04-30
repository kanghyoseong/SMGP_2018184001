package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

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
    private static Handler hander = new Handler();
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
            for (int i = 0; i < 1; i++) {
                Bat bat = new Bat(i * 0.2f, 0, player);
                Skeleton skeleton = new Skeleton(0.2f, 0, player);
                Ghost ghost = new Ghost(0.4f, 0, player);
                LizardPawn lp = new LizardPawn(0.6f, 0, player);
                Mantichana mc = new Mantichana(0.8f, 0, player);
                scene.add(bat);
                scene.add(skeleton);
                scene.add(ghost);
                scene.add(lp);
                scene.add(mc);
                addEnemy(bat);
                addEnemy(skeleton);
                addEnemy(ghost);
                addEnemy(lp);
                addEnemy(mc);
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

    @Override
    public void draw(Canvas canvas) {
    }
}
