package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class Damage extends Object {
    private static final String TAG = Damage.class.getSimpleName();
    protected Rect srcRect;
    private int damage;
    private final int srcCharWidth, srcCharHeight;
    private float elapsedTime = 0;
    private static final float EXPIRE_TIME = 1.0f;
    private static final float SPEED_Y = 0.2f;
    private Paint alphaPaint;

    public static Damage get(float posX, float posY, int damage) {
        Damage dmg = (Damage) RecycleBin.get(Damage.class);
        if (dmg == null) {
            dmg = new Damage();
        }
        dmg.damage = damage;
        dmg.posX = posX;
        dmg.posY = posY;
        dmg.elapsedTime = 0;
        dmg.alphaPaint.setAlpha(255);
        return dmg;
    }

    private Damage() {
        sprite = new Sprite(R.mipmap.numbers);
        dstRect = new RectF();
        sprite.setDstRect(dstRect);
        srcRect = new Rect();
        srcCharWidth = sprite.getBitmap().getWidth() / 10;
        srcCharHeight = sprite.getBitmap().getHeight();
        sizeX = SpriteSize.DAMAGE_SIZE;
        sizeY = SpriteSize.DAMAGE_SIZE * 2f;
        alphaPaint = new Paint();
        alphaPaint.setAlpha(255);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        elapsedTime += eTime;
        posY -= SPEED_Y * eTime;
        int alpha = Math.max(0, (int) (255.f * (EXPIRE_TIME - elapsedTime) / EXPIRE_TIME));
        alphaPaint.setAlpha(alpha);
        if (elapsedTime > EXPIRE_TIME) {
            BaseScene.getTopScene().remove(MainScene.Layer.effect, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int damage = this.damage;
        float x = posX;
        while (damage > 0) {
            int digit = damage % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= sizeX;

            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                Camera camera = scene.getCamera();
                if (camera != null) {
                    float camX = camera.getPosX();
                    float camY = camera.getPosY();
                    dstRect.set(x - camX, posY - camY,
                            x + sizeX - camX, posY + sizeY - camY);
                }
            }

            canvas.drawBitmap(sprite.getBitmap(), srcRect, dstRect, alphaPaint);

            damage /= 10;
        }
    }
}
