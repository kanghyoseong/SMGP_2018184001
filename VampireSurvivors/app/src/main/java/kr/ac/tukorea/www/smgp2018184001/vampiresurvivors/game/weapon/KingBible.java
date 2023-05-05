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
    public KingBible(Player player) {
        super(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.kingbible, 1, 1, 0.01f, player);
        atk = 10;
        elapsedCoolTime = maxCoolTime = 3f;
        duration = 3f;
    }

    @Override
    public void update(float eTime) {
        if (!isAttacking && elapsedCoolTime > 0) {
            elapsedCoolTime -= eTime;
        } else if (!isAttacking) {
            attack();
        } else if (isAttacking) {
            duration_elapsedTime += GameView.frameTime;
            if (duration_elapsedTime > duration) {
                elapsedCoolTime = maxCoolTime;
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
    protected void attack() {
        Log.d(null, "attack");
        super.attack();
        KingBibleBullet bullet = KingBibleBullet.get(player, duration);
        bullet.setAtk(atk);
        BaseScene.getTopScene().add(MainScene.Layer.weapon, bullet);
    }
}
