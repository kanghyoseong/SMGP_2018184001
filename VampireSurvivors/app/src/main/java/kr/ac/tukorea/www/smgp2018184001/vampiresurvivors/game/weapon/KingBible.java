package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class KingBible extends Weapon {
    private static final String TAG = KingBible.class.getSimpleName();
    private final float PI = 3.14159f;
    public KingBible(Player player) {
        super(WeaponType.KingBible, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.kingbible, 1, 1, 0.01f, player);
        atk = 10;
        elapsedCoolTime = maxCoolTime = 3f;
        duration = 6f;
    }

    @Override
    public void update(float eTime) {
        if (!isAttacking && elapsedCoolTime > 0) {
            elapsedCoolTime -= eTime;
        } else if (!isAttacking) {
            attack(0);
        } else if (isAttacking) {
            duration_elapsedTime += GameView.frameTime;
            if (duration_elapsedTime > duration) {
                elapsedCoolTime = maxCoolTime * player.getCoolTimeRatio();
                isAttacking = false;
            }
        }
        //Log.d(null, "eTime: "+elapsedCoolTime+"duration: "+duration_elapsedTime);
    }

    @Override
    public void draw(Canvas canvas) {
        // KingBible은 KingBibleBullet을 소환하는 역할만 한다.
    }

    @Override
    protected void attack(int callIndex) {
        super.attack(callIndex);
        float div = 2 * PI / (float) projectileCount;
        for (int i = 0; i < projectileCount; i++) {
            //Log.d(null, "Spawn KingBible Bullet " + i);
            KingBibleBullet bullet = KingBibleBullet.get(player, duration);
            bullet.setAtk(atk);
            bullet.setOffset(div * (float) i);
            BaseScene.getTopScene().add(MainScene.Layer.weapon, bullet);
        }
        //Log.d(TAG, String.format("pCount: %d, atk: %.2f, CoolTime: %.2f", projectileCount, atk, maxCoolTime));
    }
}
