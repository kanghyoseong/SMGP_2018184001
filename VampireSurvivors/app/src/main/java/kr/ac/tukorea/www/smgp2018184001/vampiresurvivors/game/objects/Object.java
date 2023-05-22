package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IRecyclable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.AnimatedSprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Object implements IGameObject, IRecyclable {
    protected Sprite sprite;
    protected AnimatedSprite aSprite;
    protected RectF dstRect;
    protected RectF colliderRect;
    protected float sizeX, sizeY;
    protected float colliderSizeX, colliderSizeY;
    protected float posX, posY;
    public static RectF boundary;

    static {
        boundary = new RectF(-SpriteSize.BACKGROUND_SIZE / 2,
                -SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2);
    }

    public Object(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = 0;
        this.sizeY = 0;
    }

    public Object(float posX, float posY, float sizeX, float sizeY,
                  int resId) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        sprite = new Sprite(resId);
        dstRect = new RectF();
        colliderRect = new RectF();
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public Object(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        aSprite = AnimatedSprite.get(resId, spriteCountX, spriteCountY, secToNextFrame);
        dstRect = new RectF();
        colliderRect = new RectF();
        reconstructRect();
        aSprite.setDstRect(dstRect);
    }

    public void setPos(float x, float y) {
        posX = x;
        posY = y;
        reconstructRect();
    }

    public void draw(Canvas canvas) {
        reconstructRect();
        if (sprite != null) {
            sprite.draw(canvas);
        } else if (aSprite != null) {
            aSprite.draw(canvas);
        }
    }

    protected void reconstructRect() {
        if (dstRect != null) {
            float left = posX - sizeX / 2;
            float top = posY - sizeY / 2;
            float right = posX + sizeX / 2;
            float bottom = posY + sizeY / 2;
            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                Camera camera = scene.getCamera();
                if (camera != null) {
                    float x = camera.getPosX();
                    float y = camera.getPosY();
                    dstRect.set(left - x, top - y,
                            right - x, bottom - y);
                }
            }
        }
    }

    public void update(float eTime) {
        if (aSprite != null) {
            aSprite.update(eTime);
        }
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setcolliderSize(float sizeX, float sizeY) {
        this.colliderSizeX = sizeX;
        this.colliderSizeY = sizeY;
        reconstructColliderRect();
    }

    public RectF getcolliderRect() {
        return colliderRect;
    }

    public void reconstructColliderRect() {
        if (colliderRect != null) {
            float left = posX - colliderSizeX / 2;
            float top = posY - colliderSizeY / 2;
            float right = posX + colliderSizeX / 2;
            float bottom = posY + colliderSizeY / 2;
            colliderRect.set(left, top, right, bottom);
        }
    }

    public void setIsDirLeft(boolean isDirLeft) {
        aSprite.setIsDirLeft(isDirLeft);
    }

    public boolean getIsDirLeft() {
        return aSprite.getIsDirLeft();
    }

    @Override
    public void onRecycle() {
    }
}
