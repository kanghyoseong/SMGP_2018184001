package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;

public class LevelUpScene extends BaseScene {
    public enum Layer {
        bg, touch, COUNT
    }

    public LevelUpScene() {
        initLayers(LevelUpScene.Layer.COUNT);
        add(LevelUpScene.Layer.touch, new Button(R.mipmap.levelupframe, 0.5f, 0.25f + (Metrics.y_offset / Metrics.scale),
                1f, 1.4f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            popScene();
                            if (BaseScene.getTopScene() instanceof MainScene) {
                                MainScene scene = (MainScene) BaseScene.getTopScene();
                                scene.getJoystick().touchUp();
                            }
                        }
                        return false;
                    }
                }));
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
