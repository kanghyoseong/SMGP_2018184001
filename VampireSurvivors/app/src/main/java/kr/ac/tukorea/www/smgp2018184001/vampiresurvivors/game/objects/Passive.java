package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public class Passive extends Object implements ICollidable {
    private Player player;
    PassiveType type;

    public enum PassiveType {
        Inc_Atk, Dec_Cooltime, Inc_BulletSpd, COUNT;

        public static PassiveType getRandomPassiveType(Random random) {
            int randomIndex = random.nextInt(PassiveType.COUNT.ordinal());
            return PassiveType.values()[randomIndex];
        }
    }

    private static int[] passiveResIds = {
            R.mipmap.spinach, R.mipmap.emptytome, R.mipmap.bracer,
    };

    public Passive(float posX, float posY, Player player, PassiveType type) {
        super(posX, posY, SpriteSize.PASSIVE_SIZE, SpriteSize.PASSIVE_SIZE, passiveResIds[type.ordinal()]);
        setcolliderSize(SpriteSize.PASSIVE_SIZE * 0.8f, SpriteSize.PASSIVE_SIZE * 0.8f);
        this.player = player;
        this.type = type;
    }

    public void remove() {
        BaseScene.getTopScene().remove(MainScene.Layer.item, this);
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
}
