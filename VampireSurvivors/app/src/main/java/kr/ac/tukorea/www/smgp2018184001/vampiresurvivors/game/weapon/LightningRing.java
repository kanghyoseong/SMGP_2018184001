package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class LightningRing extends Weapon {
    private static final String TAG = LightningRing.class.getSimpleName();

    public LightningRing(Player player) {
        super(WeaponType.LightningRing, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.lightningring, 1, 1, 0.01f, player);
        atk = 15;
        elapsedCoolTime = maxCoolTime = 4.5f;
        projectileCount = 2;
    }

    @Override
    public void draw(Canvas canvas) {
        // LightningRing은 Lightning을 소환하는 역할만 한다.
    }

    @Override
    protected void attack(int callIndex) {
        Enemy target = player.findRandomEnemyInScreen();
        if (target == null) return;
        super.attack(callIndex);
        target.getDamage(atk * player.getAttackRatio());

        Lightning lightning = Lightning.get(target.getPosX(), target.getPosY());
        BaseScene.getTopScene().add(MainScene.Layer.weapon, lightning);
        Sound.playEffect(R.raw.lightning);
        //Log.d(TAG, "attack " + callIndex + ", Atk: " + atk*player.getAttackRatio());
        //Log.d(TAG, String.format("pCount: %d, atk: %.2f, CoolTime: %.2f", projectileCount, atk, maxCoolTime));
    }
}
