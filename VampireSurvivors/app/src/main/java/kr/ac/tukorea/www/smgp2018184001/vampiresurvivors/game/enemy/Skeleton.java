package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class Skeleton extends Enemy {

    public Skeleton(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.SKELETON_SIZE, SpriteSize.SKELETON_SIZE,
                R.mipmap.skeleton, 2, 2, 0.1f, target);
        maxHp = curHp = 15;
        atk = 2;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.3f;
        atkType = AtkType.RANGE;
        dropExp = 1;
        setcolliderSize(SpriteSize.SKELETON_SIZE * 0.6f, SpriteSize.SKELETON_SIZE);
        type = EEnemyType.Skeleton;
    }
}
