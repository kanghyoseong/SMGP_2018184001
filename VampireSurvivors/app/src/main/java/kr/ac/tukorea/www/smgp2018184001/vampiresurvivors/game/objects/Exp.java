package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
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
