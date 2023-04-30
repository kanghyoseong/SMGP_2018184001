package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class EnemyRange extends Enemy {
    private float elapsedShootTime = 0;
    private float SHOOT_COOLTIME = 2f;

    public EnemyRange(float posX, float posY, float sizeX, float sizeY,
                      int resId, int spriteCountX, int spriteCountY, float secToNextFrame, Object target) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame, target);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedShootTime > 0) {
            elapsedShootTime -= eTime;
        } else {
            float distanceX = target.getPosX() - posX;
            float distanceY = target.getPosY() - posY;
            float distance_square = distanceX * distanceX + distanceY + distanceY;
            if (distance_square < 1f) {
                attack();
                elapsedShootTime = SHOOT_COOLTIME;
            }
        }
    }

    public void attack() {
        float dx = target.getPosX() - posX;
        float dy = target.getPosY() - posY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length > 0.001f) {
            dx = dx / length;
            dy = dy / length;
        } else {
            return;
        }
        Bullet bullet = new Bullet(posX, posY);
        bullet.setAtk(atk);
        bullet.setDir(dx, dy);
        BaseScene.getTopScene().add(bullet);
        EnemyGenerator.addEnemy(bullet);
    }
}
