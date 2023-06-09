package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.ICollidable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;

public class Weapon extends Object implements IAttackable, ICollidable {
    protected float atk;
    protected float elapsedCoolTime = 0;
    protected float maxCoolTime;
    protected float duration = 0;
    protected float duration_elapsedTime = 0;
    protected int projectileCount = 1;
    protected boolean isAttacking = false;
    protected Player player;
    protected WeaponType type;

    public enum WeaponType {
        Whip, MagicWand, KingBible, FireWand, LightningRing, COUNT;

        public static WeaponType getRandomWeaponType(Random random) {
            int randomIndex = random.nextInt(WeaponType.COUNT.ordinal());
            return WeaponType.values()[randomIndex];
        }

        private static int resId[] = {
                R.mipmap.whipcontroller, R.mipmap.magicwand, R.mipmap.kingbible, R.mipmap.firewand, R.mipmap.lightningring,
        };

        public static int getResId(WeaponType type) {
            if (type == WeaponType.COUNT) return 0;
            return resId[type.ordinal()];
        }
    }

    public Weapon(WeaponType type, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame, Player player) {
        super(0, 0, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        this.player = player;
        this.type = type;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (elapsedCoolTime > 0) {
            elapsedCoolTime -= eTime;
        } else if (!isAttacking) {
            for (int i = 0; i < projectileCount; i++) {
                attack(i);
            }
        } else if (aSprite.getCurFrame() == aSprite.getFrameCount()) {
            elapsedCoolTime = maxCoolTime * player.getCoolTimeRatio();
            isAttacking = false;
        }
    }

    protected void attack(int callIndex) {
        aSprite.setCurFrame(1);
        duration_elapsedTime = 0;
        isAttacking = true;
        setPos(player.getPosX(), player.getPosY());
    }

    @Override
    public void draw(Canvas canvas) {
        if (isAttacking) {
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

    public void setAtk(float atk) {
        this.atk = atk;
    }

    @Override
    public float getAtk() {
        return atk * player.getAttackRatio();
    }

    @Override
    public boolean isAttacking() {
        return isAttacking;
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        reconstructColliderRect();
    }

    public void addProjectileCount(int count) {
        projectileCount += count;
    }

    public void addAtk(float inc) {
        atk += inc;
    }

    public float getMaxCoolTime() {
        return maxCoolTime;
    }

    public void setMaxCoolTime(float time) {
        maxCoolTime = time;
    }
}
