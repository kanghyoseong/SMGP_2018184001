package kr.ac.tukorea.www.smgp2018184001.a08_samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter implements IGameObject {
    private static final float RADIUS = 1.25f;
    private static Bitmap bitmap;
    private RectF dstRect = new RectF();
    private float x, y; // 현재 위치
    private float tx, ty; // 목표 위치, touch event로 받는다.
    private float dx, dy; // 초당 속도
    private static float SPEED = 10.0f;
    private float angle;

    public Fighter() {
        x = tx = 5.0f;
        y = ty = 13.25f;
        dx = dy = 0;
        dstRect.set(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        if (bitmap == null) {// static이라 1번만 실행됨
            bitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.plane_240);
        }
    }

    public static void setBitmap(Bitmap bitmap) {
        Fighter.bitmap = bitmap;
    }

    public void update() {
        float time = BaseScene.frameTime;
        x += dx * time;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx;
            dx = 0;
        }
        y += dy * time;
        if ((dy > 0 && y > ty) || (dy < 0 && y < ty)) {
            y = ty;
            dy = 0;
        }
        dstRect.set(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        float dx = tx - this.x;
        float dy = ty - this.y;
        double radian = Math.atan2(dy, dx);
        this.dx = (float) (SPEED * Math.cos(radian));
        this.dy = (float) (SPEED * Math.sin(radian));
        angle = (float) Math.toDegrees(radian) + 90;
        //dstRect.set(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        //this.x = x;
        //this.y = y;
    }
}
