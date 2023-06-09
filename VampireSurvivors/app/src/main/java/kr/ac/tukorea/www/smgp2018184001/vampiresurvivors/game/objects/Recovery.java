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
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class Recovery extends Object implements ICollidable {
    private static final float HEAL_AMOUNT = 0.1f;
    private Player player;

    public static Recovery get(Player player, float posX, float posY) {
        Recovery obj = (Recovery) RecycleBin.get(Recovery.class);
        if (obj == null) {
            obj = new Recovery(posX, posY);
        }
        obj.init(player, posX, posY);
        return obj;
    }

    private Recovery(float posX, float posY) {
        super(posX, posY);
        this.sizeX = SpriteSize.EXP_SIZEX;
        this.sizeY = SpriteSize.EXP_SIZEY;
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

    private void init(Player player, float posX, float posY) {
        this.player = player;
        this.posX = posX;
        this.posY = posY;
        sprite = new Sprite(R.mipmap.chicken);
        dstRect = new RectF();
        colliderRect = new RectF();
        setcolliderSize(SpriteSize.EXP_SIZEX * 0.8f, SpriteSize.EXP_SIZEY * 0.8f);
        reconstructRect();
        sprite.setDstRect(dstRect);
    }

    public void healPlayer() {
        int amount = (int) ((float) player.getMaxHp() * HEAL_AMOUNT);
        player.recoverHp(amount);
    }

    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.item, this);
    }
}
