package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.LvUpButton;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class LevelUpScene extends BaseScene {
    private Random random = new Random();

    public enum Layer {
        bg, touch, COUNT
    }

    public LevelUpScene() {
        initLayers(LevelUpScene.Layer.COUNT);

        int numofButtons = 3;
        float buttonHeight = 0.3f;
        float startY = 0.5f;
        float frameHeight = buttonHeight * numofButtons;
        // add background frame
        add(LevelUpScene.Layer.bg, new Sprite(R.mipmap.levelupframe,
                0.5f, startY + (Metrics.y_offset / Metrics.scale),
                1f, frameHeight + 0.1f));

        // add buttons
        for (int i = 0; i < numofButtons; i++) {
            if (random.nextBoolean()) {
                add(LevelUpScene.Layer.touch, new LvUpButton(0.5f,
                        startY + buttonHeight * i + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                        0.9f, buttonHeight, Weapon.WeaponType.getRandomWeaponType(random)));
            } else {
                add(LevelUpScene.Layer.touch, new LvUpButton(0.5f,
                        startY + buttonHeight * i + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                        0.9f, buttonHeight, Passive.PassiveType.getRandomPassiveType(random)));
            }
        }
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
