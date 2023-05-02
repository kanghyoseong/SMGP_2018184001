package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Mantichana extends Enemy {
    public static Mantichana get(float posX, float posY, Object target) {
        Mantichana m = (Mantichana) RecycleBin.get(Mantichana.class);
        if (m == null) {
            m = new Mantichana(posX, posY, target);
        } else {
            m.posX = posX;
            m.posY = posY;
            m.target = target;
        }
        return m;
    }

    private Mantichana(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.MANTICHANA_SIZE, SpriteSize.MANTICHANA_SIZE,
                R.mipmap.mantichana, 2, 2, 0.1f, target);
        maxHp = curHp = 20;
        atk = 5;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.2f;
        atkType = AtkType.MELEE;
        dropExp = 10;
        setcolliderSize(SpriteSize.MANTICHANA_SIZE * 0.6f, SpriteSize.MANTICHANA_SIZE * 0.6f);
        type = EEnemyType.Mantichana;
    }
}
