package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;

public class Sprite implements IGameObject {
    protected Bitmap bitmap;
    protected RectF dstRect;

    public Sprite() {
    }

    public Sprite(int resId) {
        setBitmap(resId);
    }

    public Sprite(int resId, float x, float y, float width, float height) {
        setBitmap(resId);
        makeDstRect(x, y, width, height);
    }

    @Override
    public void update(float eTime) {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setBitmap(int resId) {
        bitmap = BitmapPool.get(resId, false);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setDstRect(RectF rect) {
        dstRect = rect;
    }

    public void makeDstRect(float x, float y, float width, float height) {
        float left = x - width / 2;
        float top = y - height / 2;
        float right = x + width / 2;
        float bottom = y + height / 2;
        dstRect = new RectF(left, top, right, bottom);
    }
}
