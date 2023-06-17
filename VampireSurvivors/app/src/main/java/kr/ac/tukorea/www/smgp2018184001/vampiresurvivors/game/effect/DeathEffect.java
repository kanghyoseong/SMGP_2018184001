package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.effect;

import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.AnimatedSprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class DeathEffect extends Object {
    private EEnemyType type = EEnemyType.Count;
    private boolean isLeft = true;
    private float absSize;

    public static DeathEffect get(float x, float y, EEnemyType type, boolean isLeft) {
        DeathEffect obj = (DeathEffect) RecycleBin.get(DeathEffect.class);
        if (obj == null) {
            obj = new DeathEffect(x, y, type, isLeft);
        } else {
            obj.init(x, y, type, isLeft);
        }
        return obj;
    }

    private DeathEffect(float x, float y, EEnemyType type, boolean isLeft) {
        dstRect = new RectF();
        init(x, y, type, isLeft);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        this.sizeX = this.sizeY = absSize * (1.0f + aSprite.getCurFrame() * 0.1f);
        reconstructRect();
        aSprite.setDstRect(dstRect);
        if (aSprite.getCurFrame() > 3) {
            BaseScene.getTopScene().remove(MainScene.Layer.effect, this);
        }
    }

    private void init(float x, float y, EEnemyType type, boolean isLeft) {
        this.posX = x;
        this.posY = y;
        if (this.type != type || this.isLeft != isLeft) {
            this.type = type;
            this.isLeft = isLeft;
            this.absSize = type.getSize();
            aSprite = AnimatedSprite.get(type.getResId(isLeft), 2, 2, 0.08f);
        }
        aSprite.setCurFrame(1);
    }
}
