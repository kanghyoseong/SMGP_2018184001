package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Bullet extends Object implements IAttackable, ICollidable {
    protected float dx, dy;
    protected float movementSpeed;
    protected int atk;
    protected float degrees;

    public static Bullet get(float posX, float posY, int resId, int spriteCountX, int spriteCountY, float sizeX, float sizeY) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet(posX, posY, resId, spriteCountX, spriteCountY, sizeX, sizeY);
        } else {
            bullet.aSprite.setBitmapFrame(resId, spriteCountX, spriteCountY, 0.1f);
            bullet.posX = posX;
            bullet.posY = posY;
            bullet.sizeX = sizeX;
            bullet.sizeY = sizeY;
        }
        return bullet;
    }

    protected Bullet(float posX, float posY, int resId, int spriteCountX, int spriteCountY, float sizeX, float sizeY) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, 0.1f);
        movementSpeed = Player.PLAYER_MOVEMENTSPEED * 0.4f;
        setcolliderSize(SpriteSize.BULLET_SIZE * 0.6f, SpriteSize.BULLET_SIZE * 0.6f);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        move();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        Camera camera = BaseScene.getTopScene().getCamera();
        if (camera != null) {
            canvas.rotate(degrees, posX - camera.getPosX(), posY - camera.getPosY());
            super.draw(canvas);
            canvas.restore();
            if (BuildConfig.DEBUG && DebugFlag.DRAW_COLLISIONRECT) {
                RectF collider = new RectF(colliderRect);
                collider.offset(-camera.getPosX(),
                        -camera.getPosY());
                canvas.drawRect(collider, GameView.colliderPaint);
            }
        }
    }

    public void move() {
        float newX = posX + movementSpeed * dx * GameView.frameTime;
        float newY = posY + movementSpeed * dy * GameView.frameTime;
        if (newX - sizeX / 2 > boundary.left &&
                newX + sizeX / 2 < boundary.right) {
            posX = newX;
        } else {
            remove();
        }
        if (newY - sizeY / 2 > boundary.top &&
                newY + sizeY / 2 < boundary.bottom) {
            posY = newY;
        } else {
            remove();
        }
        reconstructRect();
        reconstructColliderRect();
    }

    public void setSprite(int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        aSprite.setBitmapFrame(resId, spriteCountX, spriteCountY, secToNextFrame);
    }

    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.bullet, this);
    }

    public void setDir(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        double radian = Math.atan2(dy, dx);
        degrees = (float) Math.toDegrees(radian);
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public boolean isAttacking() {
        return true;
    }
}
