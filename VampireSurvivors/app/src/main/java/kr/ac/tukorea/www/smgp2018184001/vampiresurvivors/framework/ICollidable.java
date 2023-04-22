package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.RectF;

public interface ICollidable {
    public void setcolliderSize(float sizeX, float sizeY);
    public RectF getcolliderRect();
    public void reconstructColliderRect();
}
