package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Joystick {
    private float posX, posY, oldX, oldY, curX, curY;
    private float dirX, dirY;
    private float length;
    private float speed_multiplier = 0;
    private float dragLimit = 0.11f;
    private boolean isTouchDown = false;
    Paint paint_joystickBackground;
    Paint paint_joystickHandle;

    public Joystick() {
        paint_joystickBackground = new Paint();
        paint_joystickBackground.setColor(0xbbaaaaaa);
        paint_joystickHandle = new Paint();
        paint_joystickHandle.setColor(0xff888888);
    }

    public void update(Player player) {
        if (isTouchDown && length > 0.01f) {
            float moveX = dirX * speed_multiplier;
            float moveY = dirY * speed_multiplier;
            Log.d(null, "moveX: " + moveX + ", moveY: " + moveY);
            player.move(moveX, moveY);
        }
    }

    public void touchDown(float x, float y) {
        isTouchDown = true;
        posX = oldX = curX = x / GameView.scale;
        posY = oldY = curY = y / GameView.scale;
    }

    public void drag(float x, float y) {
        float offsetX = x / GameView.scale - posX;
        float offsetY = y / GameView.scale - posY;
        length = (float) Math.sqrt((double) ((offsetX * offsetX) + (offsetY * offsetY)));
        //Log.d(null, "length: "+length);
        dirX = offsetX / length;
        dirY = offsetY / length;
        //Log.d(null, "dirX: "+dirX);
        oldX = curX;
        oldY = curY;
        if (length > dragLimit) {
            curX = posX + dirX * dragLimit;
            curY = posY + dirY * dragLimit;
            speed_multiplier = 1.0f;
            Log.d(null, "curX: " + curX);
        } else {
            curX = x / GameView.scale;
            curY = y / GameView.scale;
            speed_multiplier = length / dragLimit;
        }
    }

    public void touchUp() {
        isTouchDown = false;
        length = 0;
    }

    public void draw(Canvas canvas) {
        if (isTouchDown) {
            canvas.drawCircle(posX, posY, 0.1f, paint_joystickBackground);
            canvas.drawCircle(curX, curY, 0.05f, paint_joystickHandle);
        }
    }
}
