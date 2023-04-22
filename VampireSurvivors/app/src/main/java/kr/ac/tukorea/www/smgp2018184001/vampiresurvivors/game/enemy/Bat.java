package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;

public class Bat extends Enemy {

    public Bat(float posX, float posY, float sizeX, float sizeY,
                 int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        maxHp = curHp = 1;
        atk = 1;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.5f;
        atkType = AtkType.MELEE;
        dropExp = 1;
        spawnWave = 1;
    }
}
