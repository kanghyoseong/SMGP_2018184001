package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.effect;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.RecycleBin;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class HealEffect extends Object {
    private Player player;
    private static float OFFSETY = -0.05f;

    public static HealEffect get(Player player) {
        HealEffect obj = (HealEffect) RecycleBin.get(HealEffect.class);
        if (obj == null) {
            obj = new HealEffect(player);
        }
        obj.init(player);
        return obj;
    }

    private HealEffect(Player player) {
        super(player.getPosX(), player.getPosY() + OFFSETY, SpriteSize.HEALEFFECT_SIZEX, SpriteSize.HEALEFFECT_SIZEY, R.mipmap.healeffect,
                5, 1, 0.08f);
        this.player = player;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        if (aSprite.getCurFrame() > 4) {
            BaseScene.getTopScene().remove(MainScene.Layer.effect, this);
        }
        this.posX = player.getPosX();
        this.posY = player.getPosY() + OFFSETY;
    }

    private void init(Player player) {
        this.player = player;
        this.posX = player.getPosX();
        this.posY = player.getPosY();
        aSprite.setCurFrame(1);
    }
}
