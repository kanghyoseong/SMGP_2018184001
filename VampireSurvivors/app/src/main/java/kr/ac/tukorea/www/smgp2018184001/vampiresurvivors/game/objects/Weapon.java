package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;

public class Weapon extends Object implements IAttackable, ICollidable {
    protected int atk;
    protected float elapsedCoolTime = 0;
    protected float maxCoolTime;
    private float duration = 0;
    private int projectileCount = 1;
    private boolean isAttacking = false;

    public Weapon(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedCoolTime > 0) {
            elapsedCoolTime -= eTime;
        } else if (!isAttacking) {
            aSprite.setCurFrame(1);
            isAttacking = true;
        } else if (aSprite.getCurFrame() == aSprite.getFrameCount()) {
            elapsedCoolTime = maxCoolTime;
            isAttacking = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (isAttacking) {
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
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public boolean isAttacking() {
        return isAttacking;
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        reconstructColliderRect();
    }
}
