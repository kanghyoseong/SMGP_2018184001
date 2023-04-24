package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;

public class CollisionChecker {
    public void update() {
        Player p = GameView.getPlayer();
        if (p != null && p.isInvincible()) {
            Enemy e = GameView.getBat();
            if (e != null) {
                if (collides(p, e)) {
                    p.getDamage(e.getAtk());
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
}
