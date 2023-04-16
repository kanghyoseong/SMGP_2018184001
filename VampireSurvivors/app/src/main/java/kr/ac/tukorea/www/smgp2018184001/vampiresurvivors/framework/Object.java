package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class Object {
    protected Sprite sprite;
    protected AnimatedSprite aSprite;
    protected RectF dstRect;
    protected float sizeX, sizeY;
    protected float posX, posY;
    protected RectF boundary;

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
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public Object(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        aSprite = new AnimatedSprite(resId, spriteCountX, spriteCountY, secToNextFrame);
        dstRect = new RectF();
        reconstructRect();
        aSprite.setDstRect(dstRect);
        setBoundary(); // 움직이는 물체에만 필요하다.
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
            if (GameView.camera != null) {
                float x = GameView.camera.getPosX();
                float y = GameView.camera.getPosY();
                dstRect.set(left - x, top - y,
                        right - x, bottom - y);
            } else {
                dstRect.set(left, top,
                        right, bottom);
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

    protected void setBoundary() {
        boundary = new RectF(-SpriteSize.BACKGROUND_SIZE / 2,
                -SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2);
    }
}
