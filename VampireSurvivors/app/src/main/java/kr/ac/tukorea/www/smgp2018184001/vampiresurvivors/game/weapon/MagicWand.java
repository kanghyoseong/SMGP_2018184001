package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class MagicWand extends Weapon implements IAttackable {
    private static final String TAG = MagicWand.class.getSimpleName();

    public MagicWand(Player player) {
        super(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.magicwand, 1, 1, 0.01f, player);
        // attack 타이밍은 aSprite의 애니메이션이 끝나는것과 관련이 있다.
        // 리소스를 아끼기 위해 mipmap을 다른 weapon의 것에서 가져온다면
        // 동일한 bitmap은 서로 공유하므로 curFrame에서 outofIndex 오류가 날 수 있다.
        // 때문에 magicwand만의 이미지를 준다. 그 외 size값은 상관이 없다.
        atk = 10;
        elapsedCoolTime = maxCoolTime = 1.2f;
    }

    @Override
    public void draw(Canvas canvas) {
        // Magic Wand는 Wand Bullet을 소환하는 역할만 한다.
    }

    @Override
    protected void attack() {
        super.attack();
        Enemy target = player.findNearestEnemy();
        if (target == null) return;
        float dx = target.getPosX() - posX;
        float dy = target.getPosY() - posY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length > 0.0001f) {
            dx = dx / length;
            dy = dy / length;
        } else {
            dx = 1.0f;
            dy = 0.0f;
        }
        WandBullet bullet = WandBullet.get(posX, posY, R.mipmap.magicwandbullet, player.getBulletSpeedRatio());
        bullet.setAtk(atk);
        bullet.setDir(dx, dy);
        BaseScene.getTopScene().add(MainScene.Layer.weapon, bullet);
    }
}
