package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EnemyRange;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Ghost extends EnemyRange {
    public static Ghost get(float posX, float posY, Object target) {
        Ghost ghost = (Ghost) RecycleBin.get(Ghost.class);
        if (ghost == null) {
            ghost = new Ghost(posX, posY, target);
        } else {
            ghost.posX = posX;
            ghost.posY = posY;
            ghost.target = target;
        }
        return ghost;
    }

    private Ghost(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.GHOST_SIZE, SpriteSize.GHOST_SIZE,
                R.mipmap.ghost, 2, 2, 0.1f, target,
                R.mipmap.bullet_ghost, 2, 1);
        maxHp = curHp = 10;
        atk = 1;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.55f;
        atkType = AtkType.RANGE;
        dropExp = 5;
        setcolliderSize(SpriteSize.GHOST_SIZE * 0.6f, SpriteSize.GHOST_SIZE * 0.8f);
        type = EEnemyType.Ghost;
    }
}
