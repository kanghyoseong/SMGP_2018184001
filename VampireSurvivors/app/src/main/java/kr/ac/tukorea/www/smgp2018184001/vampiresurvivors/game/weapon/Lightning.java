package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon;

import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;

public class Lightning extends Object implements IAttackable {
    public static Lightning get(float posX, float posY) {
        Lightning lightning = (Lightning) RecycleBin.get(Lightning.class);
        posY -= SpriteSize.LIGHTNING_SIZEY / 2f;
        if (lightning == null) {
            lightning = new Lightning(posX, posY);
        } else {
            lightning.posX = posX;
            lightning.posY = posY;
            lightning.aSprite.setCurFrame(1);
        }
        return lightning;
    }

    private Lightning(float posX, float posY) {
        super(posX, posY, SpriteSize.LIGHTNING_SIZEX, SpriteSize.LIGHTNING_SIZEY, R.mipmap.lightning, 5, 1, 0.1f);
        setcolliderSize(SpriteSize.LIGHTNING_SIZEX, SpriteSize.LIGHTNING_SIZEY);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (aSprite.getCurFrame() >= aSprite.getFrameCount()) {
            BaseScene scene = BaseScene.getTopScene();
            if (scene != null) {
                scene.remove(MainScene.Layer.weapon, this);
                //Log.d(null, "Remove Lightning");
            }
        }
    }

    @Override
    public float getAtk() {
        return 0;
    }

    @Override
    public boolean isAttacking() {
        return false;
    }
}
