package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Enemy extends Character {
    protected int atk;
    protected AtkType atkType;
    protected int dropExp;
    protected int spawnWave;

    protected Object target = null;

    public Enemy(float posX, float posY, float sizeX, float sizeY,
                 int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (target != null) {
            float dx = target.getPosX() - posX;
            float dy = target.getPosY() - posY;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            if (length > 0.01f) {
                dx = dx / length;
                dy = dy / length;
                move(dx, dy);
            }
        }
    }
}
