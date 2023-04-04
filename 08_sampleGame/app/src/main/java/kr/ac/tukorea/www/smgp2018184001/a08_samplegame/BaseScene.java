package kr.ac.tukorea.www.smgp2018184001.a08_samplegame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    private ArrayList<IGameObject> objects = new ArrayList<>();
    public static float frameTime;

    public static BaseScene getTopScene() {
        return stack.get(stack.size() - 1); // Array List 이니까 맨 끝에 있는 Scene을 가져온다.
    }

    public int pushScene() {
        stack.add(this);
        return stack.size();
    }

    public int add(IGameObject gobj) {
        objects.add(gobj);
        return objects.size();
    }

    public void update(long elapsedNanos) {
        frameTime = elapsedNanos / 1_000_000_000f;
        //Log.d(null, "FrameTime: " + String.valueOf(frameTime) + 's');
        for (IGameObject gobj : objects) {
            gobj.update();
        }
    }

    public void draw(Canvas canvas) {
        for (IGameObject gobj : objects) {
            gobj.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
