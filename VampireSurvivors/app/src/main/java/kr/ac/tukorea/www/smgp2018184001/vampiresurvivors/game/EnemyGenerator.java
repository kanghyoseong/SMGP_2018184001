package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.os.Handler;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bat;

public class EnemyGenerator implements IGameObject {
    public static ArrayList<Enemy> enemies = new ArrayList<>(); // 임시
    private Handler hander = new Handler();

    private static final float GEN_INTERVAL = 5.0f;
    private float elapsedTime = 0;

    @Override
    public void update(float eTime) {
        elapsedTime -= eTime;
        if (elapsedTime <= 0) {
            elapsedTime += GEN_INTERVAL;
            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                Bat bat = new Bat(0, 0, SpriteSize.BAT_SIZE, SpriteSize.BAT_SIZE, R.mipmap.bat, 2, 2, 0.1f);
                Player player = scene.getPlayer();
                if (player != null) {
                    bat.setTarget(player);
                }
                bat.setcolliderSize(SpriteSize.BAT_SIZE * 0.6f, SpriteSize.BAT_SIZE * 0.6f);
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
