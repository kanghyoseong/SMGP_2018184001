package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Sprite;

public class Player {
    // Sprite Animation
    private Sprite sprite;
    private RectF dstRect = new RectF();
    float posX, posY;
    float sizeX, sizeY;

    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int curHp = 20;
    private int maxHp = 20;
    private int maxHp_increment = 2;
    private float movementSpeed = 0.5f;
    //private Item items[];

    public Player(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        sprite = new Sprite(resId, spriteCountX, spriteCountY, secToNextFrame);
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public void setPos(float x, float y) {
        posX = x;
        posY = y;
        reconstructRect();
    }

    public void move(float dx, float dy) {
        posX += movementSpeed * dx * GameView.frameTime;
        posY += movementSpeed * dy * GameView.frameTime;
        reconstructRect();
    }

    private void reconstructRect() {
        dstRect.set(posX - sizeX / 2, posY - sizeY / 2, posX + sizeX / 2, posY + sizeY / 2);
    }

    public void update(float eTime) {
        sprite.update(eTime);
    }
}
