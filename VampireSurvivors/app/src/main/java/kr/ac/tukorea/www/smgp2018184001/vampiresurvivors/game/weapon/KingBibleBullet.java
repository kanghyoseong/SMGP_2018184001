package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class KingBibleBullet extends Bullet {
    private float duration;
    private float elapsedTime = 0;
    private float radius = 0.25f;
    private static final float KINGBIBLEBULLET_SPEED = 3.0f;

    private Player player;

    public static KingBibleBullet get(Player player, float duration) {
        KingBibleBullet bullet = (KingBibleBullet) RecycleBin.get(KingBibleBullet.class);
        if (bullet == null) {
            bullet = new KingBibleBullet(player, duration);
        } else {
            bullet.duration = duration;
            bullet.elapsedTime = 0;
            bullet.movementSpeed = KINGBIBLEBULLET_SPEED * player.getBulletSpeedRatio();
        }
        return bullet;
    }

    private KingBibleBullet(Player player, float duration) {
        super(player.getPosX(), player.getPosY(), R.mipmap.kingbible, 1, 1, SpriteSize.BULLET_SIZE, SpriteSize.BULLET_SIZE * 0.5f);
        this.player = player;
        this.duration = duration;
        movementSpeed = KINGBIBLEBULLET_SPEED * player.getBulletSpeedRatio();
        setcolliderSize(SpriteSize.BULLET_SIZE * 0.6f, SpriteSize.BULLET_SIZE * 0.6f);
    }

    @Override
    public void move() {
        elapsedTime += GameView.frameTime;
        posX = player.getPosX();
        posY = player.getPosY();
        float ratio = elapsedTime / duration;
        posX += Math.cos(elapsedTime * movementSpeed) * radius;
        posY += Math.sin(elapsedTime * movementSpeed) * radius;
        sizeX = sizeY = SpriteSize.BULLET_SIZE * (4 * ratio * (1 - ratio));
        //Log.d(null, "duraton: " + duration + ", ratio: " + (4 * ratio * (1 - ratio)));
        reconstructRect();
        reconstructColliderRect();
        if (elapsedTime > duration) {
            remove();
        }
    }

    @Override
    public float getAtk() {
        return atk * player.getAttackRatio();
    }

    @Override
    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.weapon, this);
    }
}
