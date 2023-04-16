package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Camera extends Object {
    private static final String TAG = Camera.class.getSimpleName();
    RectF boundary;

    public Camera(Player player) {
        super(player.getPosX(), player.getPosY());
        setBoundary();
    }

    public void update(Player player) {
        float newX = player.getPosX() - Metrics.game_width * 0.5f;
        float newY = player.getPosY() - Metrics.game_height * 0.5f;
        //Log.d(TAG, "posX: " + posX + ", posY: " + posY);
        if (newX - Metrics.game_width / 2 > boundary.left &&
                newX + Metrics.game_width / 2 < boundary.right) {
            posX = newX;
        }
        if (newY - Metrics.game_height / 2 > boundary.top &&
                newY + Metrics.game_height / 2 < boundary.bottom) {
            posY = newY;
        }
    }

    void setBoundary() {
        boundary = new RectF(-SpriteSize.BACKGROUND_SIZE / 2,
                -SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2,
                SpriteSize.BACKGROUND_SIZE / 2);
    }
}
