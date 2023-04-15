package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Camera extends Object {
    private static final String TAG = Camera.class.getSimpleName();

    public Camera(Player player) {
        super(player.getPosX(), player.getPosY());
    }

    public void update(Player player) {
        posX = player.getPosX() - 0.5f;
        posY = player.getPosY() - 0.5f;
        //Log.d(TAG, "posX: " + posX + ", posY: " + posY);
    }
}
