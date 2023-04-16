package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Player extends Object {
    float dx = 0, dy = 0;

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
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
    }

    public void move(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        posX += movementSpeed * dx * GameView.frameTime;
        posY += movementSpeed * dy * GameView.frameTime;
        reconstructRect();
    }
}
