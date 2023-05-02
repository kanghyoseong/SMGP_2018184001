package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Item extends Object {
    private int atk;
    private float elapsedCoolTime = 0;
    private float maxCoolTime = 999f;
    private float duration = 0;
    private int projectileCount = 1;

    public Item(float posX, float posY, float sizeX, float sizeY,
                int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        atk = 10;
        maxCoolTime = 1.35f;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (BuildConfig.DEBUG && DebugFlag.DRAW_COLLISIONRECT) {
            RectF collider = new RectF(colliderRect);
            Camera camera = BaseScene.getTopScene().getCamera();
            if (camera != null) {
                collider.offset(-camera.getPosX(),
                        -camera.getPosY());
                canvas.drawRect(collider, GameView.colliderPaint);
            }
        }
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        reconstructColliderRect();
    }

    public float getAtk() {
        return atk;
    }
}
