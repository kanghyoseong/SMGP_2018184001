package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EnemyRange;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Skeleton extends EnemyRange {
    public static Skeleton get(float posX, float posY, Object target) {
        Skeleton sk = (Skeleton) RecycleBin.get(Skeleton.class);
        if (sk == null) {
            sk = new Skeleton(posX, posY, target);
        } else {
            sk.posX = posX;
            sk.posY = posY;
            sk.target = target;
        }
        return sk;
    }

    private Skeleton(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.SKELETON_SIZE, SpriteSize.SKELETON_SIZE,
                R.mipmap.skeleton, 2, 2, 0.1f, target,
                R.mipmap.bullet_skeleton, 3, 3);
        maxHp = curHp = 15;
        atk = 2;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.3f;
        atkType = AtkType.RANGE;
        dropExp = 1;
        setcolliderSize(SpriteSize.SKELETON_SIZE * 0.6f, SpriteSize.SKELETON_SIZE);
        type = EEnemyType.Skeleton;
    }
}
