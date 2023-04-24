package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Character extends Object implements ICollidable {
    protected float dx = 0, dy = 0;
    protected int curHp = 20;
    protected int maxHp = 20;
    protected float movementSpeed;
    private RectF colliderRect = new RectF();
    float colliderSizeX = 0;
    float colliderSizeY = 0;

    public Character(float posX, float posY, float sizeX, float sizeY,
                     int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        aSprite.makeInvertedBitmap();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (BuildConfig.DEBUG) {
            RectF collider = new RectF(colliderRect);
            Camera camera = BaseScene.getTopScene().getCamera();
            if (camera != null) {
                collider.offset(-camera.getPosX(),
                        -camera.getPosY());
                canvas.drawRect(collider, GameView.colliderPaint);
            }
        }
    }

    public void move(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        aSprite.setIsDirLeft(dx < 0);
        float newX = posX + movementSpeed * dx * GameView.frameTime;
        float newY = posY + movementSpeed * dy * GameView.frameTime;
        //Log.d(TAG, "posX: " + posX + ", posY: " + posY);
        if (newX - sizeX / 2 > boundary.left &&
                newX + sizeX / 2 < boundary.right) {
            posX = newX;
        }
        if (newY - sizeY / 2 > boundary.top &&
                newY + sizeY / 2 < boundary.bottom) {
            posY = newY;
        }
        reconstructRect();
        reconstructColliderRect();
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        reconstructColliderRect();
    }

    @Override
    public void setcolliderSize(float sizeX, float sizeY) {
        this.colliderSizeX = sizeX;
        this.colliderSizeY = sizeY;
        reconstructColliderRect();
    }

    @Override
    public RectF getcolliderRect() {
        return colliderRect;
    }

    @Override
    public void reconstructColliderRect() {
        if (colliderRect != null) {
            float left = posX - colliderSizeX / 2;
            float top = posY - colliderSizeY / 2;
            float right = posX + colliderSizeX / 2;
            float bottom = posY + colliderSizeY / 2;
            colliderRect.set(left, top, right, bottom);
        }
    }
}
