package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IRecyclable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;

public class BaseScene {
    private static ArrayList<BaseScene> sceneStack = new ArrayList<>();
    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();
    protected Player player;
    protected Camera camera;
    protected Handler hander = new Handler();

    public void update(float frameTime) {
        for (ArrayList<IGameObject> objs : layers) {
            for (IGameObject gobj : objs) {
                gobj.update(frameTime);
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<IGameObject> objs : layers) {
            for (IGameObject gobj : objs) {
                gobj.draw(canvas);
            }
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
        this.onStart();
        return sceneStack.size();
    }

    public void pauseScene() {
        onPause();
    }

    public void resumeScene() {
        onResume();
    }

    public void popScene() {
        this.onEnd();
        sceneStack.remove(this);
    }

    protected <E extends Enum<E>> void initLayers(E countEnum) {
        int layerCount = countEnum.ordinal();
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public <E extends Enum> void add(E layerEnum, IGameObject obj) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                getObjectsAt(layerEnum).add(obj);
            }
        });
    }

    public <E extends Enum> void remove(E layerEnum, IGameObject obj) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                boolean removed = getObjectsAt(layerEnum).remove(obj);
                if (removed && obj instanceof IRecyclable) {
                    RecycleBin.collect((IRecyclable) obj);
                }
            }
        });
    }

    public int getObjectCount() {
        int count = 0;
        for (ArrayList<IGameObject> objects : layers) {
            count += objects.size();
        }
        return count;
    }

    public <E extends Enum> ArrayList<IGameObject> getObjectsAt(E layerEnum) {
        return layers.get(layerEnum.ordinal());
    }

    protected void onStart() {
    }

    protected void onEnd() {
    }

    protected void onPause() {
    }

    protected void onResume() {
    }

    public Player getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }
}
