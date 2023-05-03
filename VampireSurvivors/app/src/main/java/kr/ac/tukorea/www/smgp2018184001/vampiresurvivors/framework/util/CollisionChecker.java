package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;

public class CollisionChecker implements IGameObject {
    @Override
    public void update(float eTime) {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return;
        Player p = scene.getPlayer();
        if (p != null && p.isInvincible()) {
            ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
            for (IGameObject e : enemies) {
                if (collides(p, (ICollidable) e)) {
                    p.getDamage(((IAttackable) e).getAtk());
                }
            }
            ArrayList<IGameObject> bullets = scene.getObjectsAt(MainScene.Layer.bullet);
            for (IGameObject b : bullets) {
                if (collides(p, (ICollidable) b)) {
                    p.getDamage(((IAttackable) b).getAtk());
                    ((Bullet) b).remove();
                }
            }
        }
    }

    public boolean collides(ICollidable c1, ICollidable c2) {
        RectF r1 = c1.getcolliderRect();
        RectF r2 = c2.getcolliderRect();

        if (r1.right < r2.left) return false;
        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.bottom < r2.top) return false;
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
