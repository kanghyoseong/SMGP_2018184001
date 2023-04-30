package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;

public class BaseScene {
    private static ArrayList<BaseScene> sceneStack = new ArrayList<>();
    protected ArrayList<IGameObject> objs = new ArrayList<>();
    protected Player player;
    protected Camera camera;
    protected Handler hander = new Handler();

    public void update(float frameTime) {
        for (IGameObject obj : objs) {
            obj.update(frameTime);
        }
    }

    public void draw(Canvas canvas) {
        for (IGameObject obj : objs) {
            obj.draw(canvas);
        }
    }

    public static BaseScene getTopScene() {
        int top = sceneStack.size() - 1;
        if (top < 0) {
            return null;
        }
        return sceneStack.get(top);
    }

    public static void popAll() {
        while (!sceneStack.isEmpty()) {
            BaseScene scene = getTopScene();
            sceneStack.remove(scene);
        }
    }

    public int pushScene() {
        sceneStack.add(this);
        return sceneStack.size();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public int add(IGameObject obj) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                objs.add(obj);
            }
        });
        return objs.size();
    }

    public int remove(IGameObject obj) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                objs.remove(obj);
            }
        });
        return objs.size();
    }

    public Player getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }
}
