package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class LizardPawn extends Enemy {
    public static LizardPawn get(float posX, float posY, Object target) {
        LizardPawn lp = (LizardPawn) RecycleBin.get(LizardPawn.class);
        if (lp == null) {
            lp = new LizardPawn(posX, posY, target);
        } else {
            lp.init(posX, posY, target);
        }
        return lp;
    }

    private LizardPawn(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.LIZARDPAWN_SIZE, SpriteSize.LIZARDPAWN_SIZE,
                R.mipmap.lizardpawn, 2, 2, 0.1f, target);
        maxHp = curHp = 50;
        atk = 6;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.4f;
        atkType = AtkType.MELEE;
        dropExp = 10;
        setcolliderSize(SpriteSize.LIZARDPAWN_SIZE * 0.6f, SpriteSize.LIZARDPAWN_SIZE * 0.8f);
        type = EEnemyType.LizardPawn;
    }
}
