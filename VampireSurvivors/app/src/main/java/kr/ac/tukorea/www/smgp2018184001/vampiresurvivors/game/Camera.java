package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Camera extends Object {
    private static final String TAG = Camera.class.getSimpleName();

    public Camera(Player player) {
        super(player.getPosX(), player.getPosY());
        setBoundary();
    }

    public void update(Player player) {
        float newX = player.getPosX() - Metrics.game_width * 0.5f;
        float newY = player.getPosY() - Metrics.game_height * 0.5f;
        //Log.d(TAG, "newX: " + newX + ", newY: " + newY);
        if (player.getPosX() - Metrics.game_width * 0.5f > boundary.left &&
                player.getPosX() + Metrics.game_width * 0.5f < boundary.right) {
            posX = newX;
        }
        if (player.getPosY() - Metrics.game_height * 0.5f - Metrics.y_offset / Metrics.scale > boundary.top &&
                player.getPosY() + Metrics.game_height * 0.5f + Metrics.y_offset / Metrics.scale < boundary.bottom) {
            posY = newY;
        }
    }
}
