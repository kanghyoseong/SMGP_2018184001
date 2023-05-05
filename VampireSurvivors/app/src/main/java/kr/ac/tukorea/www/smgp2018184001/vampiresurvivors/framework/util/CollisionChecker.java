package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.MagicWandBullet;

public class CollisionChecker implements IGameObject {
    @Override
    public void update(float eTime) {
        BaseScene scene = BaseScene.getTopScene();
        Player p = scene.getPlayer();
        if (scene == null || p == null) return;

        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> weapons = scene.getObjectsAt(MainScene.Layer.weapon);
        ArrayList<IGameObject> bullets = scene.getObjectsAt(MainScene.Layer.bullet);

        for (IGameObject e : enemies) {
            // Player <-> Enemy
            if (!p.isInvincible() && collides(p, (ICollidable) e)) {
                p.getDamage(((IAttackable) e).getAtk());
            }
            for (IGameObject w : weapons) {
                if (!((IAttackable) w).isAttacking()) continue;
                // Weapon <-> Enemy
                if (collides((ICollidable) w, (ICollidable) e)) {
                    ((Enemy) e).getDamage(((IAttackable) w).getAtk());
                    if (w instanceof MagicWandBullet) {
                        ((Bullet) w).remove();
                    }
                }
            }
        }
        for (IGameObject b : bullets) {
            // Player <-> Bullet
            if (collides(p, (ICollidable) b)) {
                p.getDamage(((IAttackable) b).getAtk());
                ((Bullet) b).remove();
            }
            // Weapon <-> Bullet
            for (IGameObject w : weapons) {
                if (!((IAttackable) w).isAttacking()) continue;
                // Item(Weapon) <-> Enemy
                if (collides((ICollidable) w, (ICollidable) b)) {
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
