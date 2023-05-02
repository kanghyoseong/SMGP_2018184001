package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;

public class Bullet extends Object implements IAttackable, ICollidable {
    private float dx, dy;
    private float movementSpeed;
    private int atk;
    private float degrees;

    public Bullet(float posX, float posY, int resId, int spriteCountX, int spriteCountY) {
        super(posX, posY, SpriteSize.BULLET_SIZE, SpriteSize.BULLET_SIZE,
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

    public void remove() {
        EnemyGenerator.removeEnemy(this);
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
}
