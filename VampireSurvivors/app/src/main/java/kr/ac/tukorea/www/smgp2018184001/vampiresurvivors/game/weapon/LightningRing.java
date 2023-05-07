package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class LightningRing extends Weapon {
    private static final String TAG = LightningRing.class.getSimpleName();

    public LightningRing(Player player) {
        super(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
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
    protected void attack() {
        Enemy target = player.findRandomEnemyInScreen();
        if (target == null) return;
        super.attack();
        target.getDamage(atk);

        Lightning lightning = Lightning.get(target.getPosX(), target.getPosY());
        BaseScene.getTopScene().add(MainScene.Layer.weapon, lightning);
        //Log.d(TAG, "Spawn Lightning");
    }
}