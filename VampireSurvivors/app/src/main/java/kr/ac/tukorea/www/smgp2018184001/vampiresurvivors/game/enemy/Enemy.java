package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app.Score;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Character;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.effect.DeathEffect;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Exp;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Recovery;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class Enemy extends Character implements IAttackable {
    protected float atk;
    protected AtkType atkType;
    protected int dropExp;
    protected EEnemyType type;
    public static Random random = new Random();

    protected Object target = null;
    protected static Player player;

    public Enemy(float posX, float posY, float sizeX, float sizeY,
                 int resId, int spriteCountX, int spriteCountY, float secToNextFrame, Object target) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        setTarget(target);
        INVINCIBLETIME = 0.5f;
    }

    protected void init(float posX, float posY, Object target) {
        this.posX = posX;
        this.posY = posY;
        this.target = target;
        this.elapsedInvincibleTime = 0;
        this.curHp = maxHp;
        this.movementSpeed = type.getSpeed();
        if (dstRect == null) {
            dstRect = new RectF();
            reconstructRect();
        }
        if (colliderRect == null) {
            colliderRect = new RectF();
            reconstructColliderRect();
        }
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public static void setPlayer(Player player) {
        Enemy.player = player;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (target != null) {
            followTarget();
        } else {
            move();
        }
    }

    private void followTarget() {
        float dx = target.getPosX() - posX;
        float dy = target.getPosY() - posY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length > 0.01f) {
            dx = dx / length;
            dy = dy / length;
            move(dx, dy);
        }
    }

    public void move() {
        aSprite.setIsDirLeft(dx < 0);
        posX = posX + movementSpeed * dx * GameView.frameTime;
        posY = posY + movementSpeed * dy * GameView.frameTime;
        reconstructRect();
        reconstructColliderRect();

        if (!boundary.contains(colliderRect)) {
            BaseScene scene = BaseScene.getTopScene();
            ((MainScene) scene).enemyGenerator.enemyDestroyed();
            scene.remove(MainScene.Layer.enemy, this);
        }
    }

    @Override
    public void killThis() {
        //Add Score
        Score.curScore += dropExp;

        // Remove From Scene
        BaseScene scene = BaseScene.getTopScene();
        scene.remove(MainScene.Layer.enemy, this);

        //Spawn Exp or Recovery
        if (random.nextInt(100) < 1) {
            Recovery r = Recovery.get(player, posX, posY);
            scene.add(MainScene.Layer.item, r);
        } else {
            Exp e = Exp.get(posX, posY, dropExp);
            scene.add(MainScene.Layer.item, e);
        }
        ((MainScene) scene).enemyGenerator.enemyDestroyed();
        player.increaseKilledEnemies();
        BaseScene.getTopScene().add(MainScene.Layer.effect, DeathEffect.get(posX, posY, type, aSprite.getIsDirLeft()));
    }

    @Override
    public float getAtk() {
        return atk;
    }

    @Override
    public boolean isAttacking() {
        return true;
    }
}
