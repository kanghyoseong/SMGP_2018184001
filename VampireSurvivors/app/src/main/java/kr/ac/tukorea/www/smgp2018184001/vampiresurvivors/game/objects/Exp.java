package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Exp extends Object implements ICollidable {
    int exp;

    public static Exp get(float posX, float posY, int exp) {
        Exp e = (Exp) RecycleBin.get(Exp.class);
        if (e == null) {
            e = new Exp(posX, posY, exp);
        } else {
            e.init(posX, posY, exp);
        }
        return e;
    }

    private Exp(float posX, float posY, int exp) {
        super(posX, posY);
        this.sizeX = SpriteSize.EXP_SIZEX;
        this.sizeY = SpriteSize.EXP_SIZEY;
        init(posX, posY, exp);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Camera camera = BaseScene.getTopScene().getCamera();
        if (camera != null && BuildConfig.DEBUG && DebugFlag.DRAW_COLLISIONRECT) {
            RectF collider = new RectF(colliderRect);
            collider.offset(-camera.getPosX(),
                    -camera.getPosY());
            canvas.drawRect(collider, GameView.colliderPaint);
        }
    }

    private void init(float posX, float posY, int exp) {
        this.exp = exp;
        int resId;
        switch (exp) {
            default:
            case 1:
                resId = R.mipmap.exp1;
                break;
            case 5:
                resId = R.mipmap.exp5;
                break;
            case 10:
                resId = R.mipmap.exp10;
                break;
        }
        this.posX = posX;
        this.posY = posY;
        sprite = new Sprite(resId);
        dstRect = new RectF();
        colliderRect = new RectF();
        setcolliderSize(SpriteSize.EXP_SIZEX * 0.8f, SpriteSize.EXP_SIZEY * 0.8f);
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public int getExp() {
        return exp;
    }

    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.item, this);
    }

}
