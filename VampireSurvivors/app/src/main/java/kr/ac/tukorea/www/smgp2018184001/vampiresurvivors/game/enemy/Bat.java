package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class Bat extends Enemy {
    public static Bat get(float posX, float posY, Object target) {
        Bat bat = (Bat) RecycleBin.get(Bat.class);
        if (bat == null) {
            bat = new Bat(posX, posY, target);
        } else {
            bat.posX = posX;
            bat.posY = posY;
            bat.target = target;
        }
        return bat;
    }

    private Bat(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.BAT_SIZE, SpriteSize.BAT_SIZE,
                R.mipmap.bat, 2, 2, 0.1f, target);
        maxHp = curHp = 1;
        atk = 1;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.5f;
        atkType = AtkType.MELEE;
        dropExp = 1;
        setcolliderSize(SpriteSize.BAT_SIZE * 0.6f, SpriteSize.BAT_SIZE * 0.6f);
        type = EEnemyType.Bat;
    }
}
