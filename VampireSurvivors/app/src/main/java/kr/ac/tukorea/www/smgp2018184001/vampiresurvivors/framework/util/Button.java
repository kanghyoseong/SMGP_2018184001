package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ITouchable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;

public class Button extends Sprite implements ITouchable, IGameObject {
    private static final String TAG = Button.class.getSimpleName();
    private final Callback callback;
    protected float posX, posY;
    protected float width, height;

    public enum Action {
        pressed, released,
    }

    public interface Callback {
        public boolean onTouch(Action action);
    }

    public Button(int bitmapResId, float posX, float posY, float width, float height, Callback callback) {
        super(bitmapResId);
        dstRect = new RectF();
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.callback = callback;
        reconstructRect();
    }

    @Override
    public void update(float eTime) {
    }

    public void draw(Canvas canvas) {
        reconstructRect();
        super.draw(canvas);
    }

    protected void reconstructRect() {
        if (dstRect != null) {
            float left = posX - width / 2;
            float top = posY - height / 2;
            float right = posX + width / 2;
            float bottom = posY + height / 2;
            dstRect.set(left, top,
                    right, bottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        if (!dstRect.contains(x, y)) return false;
        //Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", Action: " + e.getAction() + ", X: " + e.getX() + ", Y: " + e.getY() + ")");
        int action = e.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            callback.onTouch(Action.pressed);
        } else if (action == MotionEvent.ACTION_UP) {
            callback.onTouch(Action.released);
        }
        return true;
    }
}
