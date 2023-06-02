package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class EnemyRange extends Enemy {
    private float elapsedShootTime = 0;
    private float SHOOT_COOLTIME = 5f;
    private int bulletResId;
    private int bulletSpriteCountX, bulletSpriteCountY;

    public EnemyRange(float posX, float posY, float sizeX, float sizeY,
                      int resId, int spriteCountX, int spriteCountY, float secToNextFrame, Object target,
                      int bulletResId, int bulletSpriteCountX, int bulletSpriteCountY) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame, target);
        this.bulletResId = bulletResId;
        this.bulletSpriteCountX = bulletSpriteCountX;
        this.bulletSpriteCountY = bulletSpriteCountY;
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
        Bullet bullet = Bullet.get(posX, posY, bulletResId, bulletSpriteCountX, bulletSpriteCountY, SpriteSize.BULLET_SIZE, SpriteSize.BULLET_SIZE);
        bullet.setAtk(atk);
        bullet.setDir(dx, dy);
        BaseScene.getTopScene().add(MainScene.Layer.bullet, bullet);
    }
}
