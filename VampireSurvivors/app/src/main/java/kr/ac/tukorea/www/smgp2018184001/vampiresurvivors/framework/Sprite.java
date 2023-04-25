package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Sprite {
    private Bitmap bitmap;
    protected RectF dstRect;
    protected static BitmapFactory.Options opts;

    static {
        opts = new BitmapFactory.Options();
        opts.inScaled = false;
    }

    public Sprite() {
    }

    public Sprite(int resId) {
        setBitmap(resId);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setBitmap(int resId) {
        bitmap = BitmapFactory.decodeResource(GameView.res, resId, opts);
    }

    public void setDstRect(RectF rect) {
        dstRect = rect;
    }
}
