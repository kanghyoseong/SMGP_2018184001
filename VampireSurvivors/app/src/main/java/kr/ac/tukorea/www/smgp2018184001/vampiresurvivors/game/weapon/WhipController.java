package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class WhipController extends Weapon implements IAttackable {
    private static final String TAG = WhipController.class.getSimpleName();
    protected ArrayList<Whip> whips = new ArrayList<>();
    Player player;

    public WhipController(Player player, BaseScene scene) {
        super(WeaponType.Whip, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.whipcontroller, 1, 6, 0.01f, player);
        atk = 10;
        projectileCount = 1;
        elapsedCoolTime = maxCoolTime = 1.35f;
        this.player = player;
        init(scene);
    }

    private void init(BaseScene scene) {
        Whip whip = new Whip(player, atk);
        scene.add(MainScene.Layer.weapon, whip);
        whips.add(whip);
    }

    private void addWhip() {
        Whip whip = new Whip(player, atk);
        BaseScene.getTopScene().add(MainScene.Layer.weapon, whip);
        whips.add(whip);
    }

    @Override
    public void update(float eTime) {
        if (elapsedCoolTime > 0) {
            elapsedCoolTime -= eTime;
        } else if (!isAttacking) {
            attack(0);
            elapsedCoolTime = maxCoolTime;
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void addProjectileCount(int count) {
        super.addProjectileCount(count);
        addWhip();
    }

    @Override
    protected void attack(int callIndex) {
        for (int i = 0; i < whips.size(); i++) {
            Whip whip = whips.get(i);
            whip.setAtk(atk * player.getAttackRatio());
            whip.setCanAttack(true);
            whip.setCallIndex(i);
            whip.setCoolTime(0.1f * i);
            Sound.playEffect(R.raw.whip);
        }
        //Log.d(TAG, String.format("pCount: %d, atk: %.2f, CoolTime: %.2f", projectileCount, atk, maxCoolTime));
    }
}
