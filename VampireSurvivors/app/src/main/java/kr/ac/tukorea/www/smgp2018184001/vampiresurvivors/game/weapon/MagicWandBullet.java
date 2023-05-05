package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class MagicWandBullet extends Bullet {
    public static MagicWandBullet get(float posX, float posY) {
        MagicWandBullet bullet = (MagicWandBullet) RecycleBin.get(MagicWandBullet.class);
        if (bullet == null) {
            bullet = new MagicWandBullet(posX, posY);
        } else {
            bullet.posX = posX;
            bullet.posY = posY;
        }
        return bullet;
    }

    private MagicWandBullet(float posX, float posY) {
        super(posX, posY, R.mipmap.magicwandbullet, 1, 2);
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 1.1f;
        setcolliderSize(SpriteSize.BULLET_SIZE * 0.6f, SpriteSize.BULLET_SIZE * 0.6f);
    }

    @Override
    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.weapon, this);
    }
}
