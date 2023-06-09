package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class FireWand extends Weapon {
    private static final String TAG = FireWand.class.getSimpleName();
    Random random = new Random();
    private static final float PI = 3.14159f;

    public FireWand(Player player) {
        super(WeaponType.FireWand, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.firewand, 1, 1, 0.01f, player);
        atk = 10;
        elapsedCoolTime = maxCoolTime = 3f;
        projectileCount = 3;
    }

    @Override
    public void draw(Canvas canvas) {
        // Fire Wand는 Wand Bullet을 소환하는 역할만 한다.
    }

    @Override
    protected void attack(int callIndex) {
        super.attack(callIndex);
        float radian = random.nextFloat() * 2 * PI;
        float dx = (float) Math.cos(radian);
        float dy = (float) Math.sin(radian);
        WandBullet bullet = WandBullet.get(posX, posY, R.mipmap.firewandbullet, player.getBulletSpeedRatio());
        bullet.setAtk(atk * player.getAttackRatio());
        bullet.setDir(dx, dy);
        BaseScene.getTopScene().add(MainScene.Layer.weapon, bullet);
        Sound.playEffect(R.raw.firewand);
        //Log.d(null, "attack "+callIndex);
        //Log.d(TAG, String.format("pCount: %d, atk: %.2f, CoolTime: %.2f", projectileCount, atk, maxCoolTime));
    }
}
