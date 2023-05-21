package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IAttackable;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Character;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Exp;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;

public class Enemy extends Character implements IAttackable {
    protected float atk;
    protected AtkType atkType;
    protected int dropExp;
    protected EEnemyType type;

    protected Object target = null;

    public Enemy(float posX, float posY, float sizeX, float sizeY,
                 int resId, int spriteCountX, int spriteCountY, float secToNextFrame, Object target) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        setTarget(target);
        INVINCIBLETIME = 0.5f;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        followTarget();
    }

    private void followTarget() {
        if (target != null) {
            float dx = target.getPosX() - posX;
            float dy = target.getPosY() - posY;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            if (length > 0.01f) {
                dx = dx / length;
                dy = dy / length;
                move(dx, dy);
            }
        }
    }

    @Override
    public void killThis() {
        BaseScene scene = BaseScene.getTopScene();
        scene.remove(MainScene.Layer.enemy, this);

        //Spawn Exp
        Exp e = Exp.get(posX, posY, dropExp);
        scene.add(MainScene.Layer.item, e);
    }

    @Override
    public float getAtk() {
        return atk;
    }

    @Override
    public boolean isAttacking() {
        return true;
    }

    @Override
    public void getDamage(float damage) {
        super.getDamage(damage);
        Sound.playEffect(R.raw.enemyhit);
    }
}
