package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bullet;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Exp;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Recovery;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.WandBullet;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private float elapsedTime = -0.01f;
    private final float SOUND_INTERVAL = 0.1f;

    @Override
    public void update(float eTime) {
        BaseScene scene = BaseScene.getTopScene();
        Player p = MainScene.player;
        if (scene == null || p == null) return;

        if (elapsedTime >= 0) elapsedTime -= eTime;

        boolean isEnemyHitSoundPlayed = false;
        boolean isGetExpSoundPlayed = false;
        boolean isHealSoundPlayed = false;
        boolean isPlayerHitSoundPlayed = false;

        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> weapons = scene.getObjectsAt(MainScene.Layer.weapon);
        ArrayList<IGameObject> bullets = scene.getObjectsAt(MainScene.Layer.bullet);
        ArrayList<IGameObject> items = scene.getObjectsAt(MainScene.Layer.item);

        for (IGameObject e : enemies) {
            // Player <-> Enemy
            if (!p.isInvincible() && collides(p, (ICollidable) e)) {
                p.getDamage(((IAttackable) e).getAtk());
                if (!isPlayerHitSoundPlayed) {
                    Sound.playEffect(R.raw.playerhit);
                    isPlayerHitSoundPlayed = true;
                }
            }
            for (IGameObject w : weapons) {
                if (!((IAttackable) w).isAttacking()) continue;
                // Weapon <-> Enemy
                if (collides((ICollidable) w, (ICollidable) e)) {
                    ((Enemy) e).getDamage(((IAttackable) w).getAtk());
                    if (w instanceof WandBullet) {
                        ((Bullet) w).remove();
                    }
                    if (!isEnemyHitSoundPlayed && elapsedTime < 0) {
                        Sound.playEffect(R.raw.enemyhit);
                        isEnemyHitSoundPlayed = true;
                        elapsedTime += SOUND_INTERVAL;
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
        for (IGameObject i : items) {
            if (collides(p, (ICollidable) i)) {
                if (i instanceof Recovery) {
                    Recovery.healPlayer();
                    ((Recovery) i).remove();
                    if (!isHealSoundPlayed) {
                        Sound.playEffect(R.raw.heal);
                        isHealSoundPlayed = true;
                    }
                } else {
                    p.addExp(((Exp) i).getExp());
                    ((Exp) i).remove();
                    if (!isGetExpSoundPlayed) {
                        Sound.playEffect(R.raw.getexp);
                        isGetExpSoundPlayed = true;
                    }
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
