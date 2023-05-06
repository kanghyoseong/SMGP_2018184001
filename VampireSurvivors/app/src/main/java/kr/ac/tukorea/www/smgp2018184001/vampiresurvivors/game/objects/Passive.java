package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Passive extends Object implements ICollidable {
    private Player player;

    public enum PassiveType {
        Inc_Atk, Dec_Cooltime, Inc_BulletSpd, COUNT
    }

    private static int[] passiveResIds = {
            R.mipmap.spinach, R.mipmap.emptytome, R.mipmap.bracer,
    };

    public Passive(float posX, float posY, Player player, PassiveType type) {
        super(posX, posY, SpriteSize.PASSIVE_SIZE, SpriteSize.PASSIVE_SIZE, passiveResIds[type.ordinal()]);
        this.player = player;
    }
}
