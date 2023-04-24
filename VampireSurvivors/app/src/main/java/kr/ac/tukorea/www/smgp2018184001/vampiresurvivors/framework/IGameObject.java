package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Canvas;

public interface IGameObject {
    public void update(float eTime);

    public void draw(Canvas canvas);
}
