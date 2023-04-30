package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.AtkType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class Ghost extends Enemy {
    private float elapsedShootTime = 0;
    private float SHOOT_COOLTIME = 2f;

    public Ghost(float posX, float posY, Object target) {
        super(posX, posY, SpriteSize.GHOST_SIZE, SpriteSize.GHOST_SIZE,
                R.mipmap.ghost, 2, 2, 0.1f, target);
        maxHp = curHp = 10;
        atk = 1;
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.55f;
        atkType = AtkType.RANGE;
        dropExp = 5;
        setcolliderSize(SpriteSize.GHOST_SIZE * 0.6f, SpriteSize.GHOST_SIZE * 0.8f);
        type = EEnemyType.Ghost;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedShootTime > 0) {
            elapsedShootTime -= eTime;
        } else {
            float distanceX = target.getPosX() - posX;
            float distanceY = target.getPosY() - posY;
            float distance_square = distanceX * distanceX + distanceY + distanceY;
            if (distance_square < 1f) {
                attack();
                elapsedShootTime = SHOOT_COOLTIME;
            }
        }
    }

    public void attack() {
        float dx = target.getPosX() - posX;
        float dy = target.getPosY() - posY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length > 0.001f) {
            dx = dx / length;
            dy = dy / length;
        } else {
            return;
        }
        Bullet bullet = new Bullet(posX, posY);
        bullet.setAtk(atk);
        bullet.setDir(dx, dy);
        BaseScene.getTopScene().add(bullet);
        EnemyGenerator.addEnemy(bullet);
    }
}
