package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;

public class Character extends Object implements ICollidable {
    protected float dx = 0, dy = 0;
    protected float curHp = 20;
    protected float maxHp = 20;
    protected float movementSpeed;
    protected float INVINCIBLETIME = 1f;
    protected float elapsedInvincibleTime = 0;

    public Character(float posX, float posY, float sizeX, float sizeY,
                     int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedInvincibleTime > 0) { // 0보다 크면 무적
            elapsedInvincibleTime -= eTime;
        } else { // 0보다 작거나 같으면 무적 아님
            aSprite.setIsInvincible(false);
        }
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

    public void move(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        aSprite.setIsDirLeft(dx < 0);
        float newX = posX + movementSpeed * dx * GameView.frameTime;
        float newY = posY + movementSpeed * dy * GameView.frameTime;
        //Log.d(TAG, "posX: " + posX + ", posY: " + posY);
        if (newX - sizeX / 2 > boundary.left &&
                newX + sizeX / 2 < boundary.right) {
            posX = newX;
        }
        if (newY - sizeY / 2 > boundary.top &&
                newY + sizeY / 2 < boundary.bottom) {
            posY = newY;
        }
        reconstructRect();
        reconstructColliderRect();
    }

    public void recoverHp(float hp) {
        this.curHp += hp;
        if (curHp > maxHp) {
            curHp = maxHp;
        }
    }

    public void getDamage(float damage) {
        if (!isInvincible()) {
            aSprite.setIsInvincible(true);
            curHp -= damage;
            elapsedInvincibleTime = INVINCIBLETIME;
            if (curHp <= 0) {
                killThis();
            }
        }
    }

    public void killThis() {
    }

    public boolean isInvincible() { // 0보다 크면 무적
        return elapsedInvincibleTime > 0;
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        reconstructColliderRect();
    }

    public float getCurHp() {
        return curHp;
    }

    public float getMaxHp() {
        return maxHp;
    }
}
