package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class Whip extends Weapon implements IAttackable {
    public Whip(float posX, float posY) {
        super(posX, posY, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y,
                R.mipmap.whip, 1, 6, 0.03f);
        setcolliderSize(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y);
        atk = 10;
        maxCoolTime = 1.35f;
    }
}
