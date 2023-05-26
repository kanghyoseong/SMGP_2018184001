package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class Joystick implements IGameObject {
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
        paint_joystickHandle.setColor(0xff666666);
    }

    @Override
    public void update(float eTime) {
        if (!(BaseScene.getTopScene() instanceof MainScene)) return;
        Player player = MainScene.player;
        if (player != null) {
            if (isTouchDown && length > 0.01f) {
                float moveX = dirX * speed_multiplier;
                float moveY = dirY * speed_multiplier;
                //Log.d(null, "moveX: " + moveX + ", moveY: " + moveY);
                player.move(moveX, moveY);
            }
        }
    }

    public void touchDown(float x, float y) {
        isTouchDown = true;
        posX = oldX = curX = Metrics.toGameX(x);
        posY = oldY = curY = Metrics.toGameY(y);
    }

    public void drag(float x, float y) {
        float offsetX = Metrics.toGameX(x) - posX;
        float offsetY = Metrics.toGameY(y) - posY;
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
        } else {
            curX = Metrics.toGameX(x);
            curY = Metrics.toGameY(y);
            speed_multiplier = length / dragLimit;
        }
    }

    public void touchUp() {
        isTouchDown = false;
        length = 0;
    }

    public void draw(Canvas canvas) {
        if (!(BaseScene.getTopScene() instanceof MainScene)) return;
        Player player = MainScene.player;
        if (player != null && isTouchDown) {
            canvas.drawCircle(posX, posY, SpriteSize.JOYSTICK_BACKGROUND_SIZE, paint_joystickBackground);
            canvas.drawCircle(curX, curY, SpriteSize.JOYSTICK_HANDLE_SIZE, paint_joystickHandle);
        }
    }
}
