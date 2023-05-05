package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class Whip extends Weapon implements IAttackable {
    public Whip(Player player) {
        super(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.whip, 1, 6, 0.03f, player);
        setcolliderSize(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y);
        atk = 10;
        elapsedCoolTime = maxCoolTime = 1.35f;
    }

    @Override
    protected void attack() {
        aSprite.setCurFrame(1);
        isAttacking = true;
        float offset;
        aSprite.setIsDirLeft(player.getIsDirLeft());
        if (aSprite.getIsDirLeft()) {
            offset = -SpriteSize.WHIP_SIZE_X / 2.f - player.getSizeX() / 2;
        } else {
            offset = SpriteSize.WHIP_SIZE_X / 2.f + player.getSizeX() / 2;
        }
        setPos(player.getPosX() + offset, player.getPosY());
    }
}
