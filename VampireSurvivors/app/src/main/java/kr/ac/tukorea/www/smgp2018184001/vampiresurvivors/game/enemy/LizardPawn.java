package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class LizardPawn extends Enemy {

    public LizardPawn(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.LIZARDPAWN_SIZE, SpriteSize.LIZARDPAWN_SIZE,
                R.mipmap.lizardpawn, 2, 2, 0.1f, target);
        maxHp = curHp = 20;
        atk = 6;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.15f;
        atkType = AtkType.MELEE;
        dropExp = 10;
        setcolliderSize(SpriteSize.LIZARDPAWN_SIZE * 0.6f, SpriteSize.LIZARDPAWN_SIZE * 0.8f);
        type = EEnemyType.LizardPawn;
    }
}
