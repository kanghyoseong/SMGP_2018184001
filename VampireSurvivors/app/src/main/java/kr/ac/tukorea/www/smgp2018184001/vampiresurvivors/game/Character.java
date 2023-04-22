package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Character extends Object {
    protected float dx = 0, dy = 0;
    protected int curHp = 20;
    protected int maxHp = 20;
    protected float movementSpeed;

    public Character(float posX, float posY, float sizeX, float sizeY,
                     int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
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
    }
}
