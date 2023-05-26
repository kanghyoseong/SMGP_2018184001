package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IRecyclable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ITouchable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;

public class BaseScene {
    private static ArrayList<BaseScene> sceneStack = new ArrayList<>();
    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();
    protected Player player;
    protected Camera camera;
    protected Handler hander = new Handler();
    public static Paint textPaint = new Paint();
    public static Paint levelTextPaint = new Paint();

    static {
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Metrics.screenWidth * 0.1f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        levelTextPaint.setColor(Color.WHITE);
        levelTextPaint.setTextSize(Metrics.screenWidth * 0.1f);
        levelTextPaint.setTextAlign(Paint.Align.CENTER);
    }


    public void update(float frameTime) {
        for (ArrayList<IGameObject> objs : layers) {
            for (IGameObject gobj : objs) {
                gobj.update(frameTime);
            }
        }
    }

    public void draw(Canvas canvas) {
        draw(canvas, sceneStack.size() - 1);
    }

    protected void draw(Canvas canvas, int index) {
        canvas.save();
        BaseScene scene = sceneStack.get(index);
        if (scene.isTransparent() && index > 0) {
            draw(canvas, index - 1);
        }
        ArrayList<ArrayList<IGameObject>> layers = scene.layers;
        for (ArrayList<IGameObject> objs : layers) {
            for (IGameObject gobj : objs) {
                gobj.draw(canvas);
            }
        }
        canvas.restore();
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
        int touchLayer = getTouchLayerIndex();
        if (touchLayer < 0) return false;
        ArrayList<IGameObject> gameObjects = layers.get(touchLayer);
        for (IGameObject gobj : gameObjects) {
            if (!(gobj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) gobj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    protected int getTouchLayerIndex() {
        return -1;
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

    public boolean isTransparent() {
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }
}
