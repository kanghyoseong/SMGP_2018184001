package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class Whip extends Weapon implements IAttackable {
    private static final String TAG = Whip.class.getSimpleName();
    private int callIndex;
    private boolean canAttack = false;

    public Whip(Player player, float atk) {
        super(WeaponType.Whip, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.whip, 1, 6, 0.03f, player);
        setcolliderSize(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y);
        this.atk = atk;
    }

    @Override
    public void update(float eTime) {
        if (aSprite != null) {
            aSprite.update(eTime);
        }
        if (canAttack) {
            if (!isAttacking) {
                if (elapsedCoolTime > 0) {
                    elapsedCoolTime -= eTime;
                } else {
                    attack(this.callIndex);
                }
                //Log.d(TAG, "id "+callIndex+", coolTime: "+elapsedCoolTime);
            } else if (aSprite.getCurFrame() == aSprite.getFrameCount()) {
                isAttacking = false;
                this.canAttack = false;
            }
        }
        //Log.d(TAG, "ca: "+canAttack+", ia: "+isAttacking+", id "+callIndex+", coolTime: "+elapsedCoolTime);
    }

    protected void attack(int callIndex) {
        aSprite.setCurFrame(1);
        isAttacking = true;
        float offset;
        float dir = player.getIsDirLeft() ? 1 : -1;
        boolean isDirLeft = callIndex % 2 == 0;
        if (!player.getIsDirLeft()) isDirLeft = !isDirLeft;
        if (callIndex % 2 == 0) {
            offset = dir * -SpriteSize.WHIP_SIZE_X / 2.f;
        } else {
            offset = dir * SpriteSize.WHIP_SIZE_X / 2.f;
        }
        aSprite.setIsDirLeft(isDirLeft);
        setPos(player.getPosX() + offset, player.getPosY() - 0.1f * callIndex);
        //Log.d(TAG, "attack " + callIndex + ", offset: " + offset);
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setCallIndex(int callIndex) {
        this.callIndex = callIndex;
    }

    public void setCoolTime(float coolTime) {
        this.elapsedCoolTime = coolTime;
    }
}
