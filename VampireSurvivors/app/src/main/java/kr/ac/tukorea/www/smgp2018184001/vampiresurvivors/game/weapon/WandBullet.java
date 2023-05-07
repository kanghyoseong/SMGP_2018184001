package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class WandBullet extends Bullet {
    public static WandBullet get(float posX, float posY, int resId, float speedRatio) {
        WandBullet bullet = (WandBullet) RecycleBin.get(WandBullet.class);
        if (bullet == null) {
            bullet = new WandBullet(posX, posY, resId, speedRatio);
        } else {
            bullet.posX = posX;
            bullet.posY = posY;
            bullet.setSprite(resId, 1, 2, 0.1f);
            bullet.movementSpeed = Player.PLAYER_MOVEMENTSPEED * 1.1f * speedRatio;
        }
        return bullet;
    }

    private WandBullet(float posX, float posY, int resId, float speedRatio) {
        super(posX, posY, resId, 1, 2, SpriteSize.BULLET_SIZE, SpriteSize.BULLET_SIZE * 0.5f);
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 1.1f * speedRatio;
        setcolliderSize(SpriteSize.BULLET_SIZE * 0.6f, SpriteSize.BULLET_SIZE * 0.6f);
    }

    @Override
    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.weapon, this);
    }
}
