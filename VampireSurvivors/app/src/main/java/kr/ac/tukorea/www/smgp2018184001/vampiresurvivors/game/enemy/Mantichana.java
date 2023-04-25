package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class Mantichana extends Enemy {
    public static int maxNum = 15;
    public static int spawnWave = 10;

    public Mantichana(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.MANTICHANA_SIZE, SpriteSize.MANTICHANA_SIZE,
                R.mipmap.mantichana, 2, 2, 0.1f, target);
        maxHp = curHp = 20;
        atk = 5;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.2f;
        atkType = AtkType.MELEE;
        dropExp = 10;
        setcolliderSize(SpriteSize.MANTICHANA_SIZE * 0.6f, SpriteSize.MANTICHANA_SIZE * 0.6f);
    }
}
