package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Object {
    protected Sprite sprite;
    protected RectF dstRect;
    protected float sizeX, sizeY;
    protected float posX, posY;

    public Object(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = 0;
        this.sizeY = 0;
        sprite = null;
        dstRect = null;
    }

    public Object(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        sprite = new Sprite(resId, spriteCountX, spriteCountY, secToNextFrame);
        dstRect = new RectF();
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public void setPos(float x, float y) {
        posX = x;
        posY = y;
        reconstructRect();
    }

    public void draw(Canvas canvas) {
        if (sprite != null) {
            sprite.draw(canvas);
        }
    }

    protected void reconstructRect() {
        if (dstRect != null) {
            dstRect.set(posX - sizeX / 2, posY - sizeY / 2, posX + sizeX / 2, posY + sizeY / 2);
        }
    }

    public void update(float eTime) {
        if (sprite != null) {
            sprite.update(eTime);
        }
    }
}
