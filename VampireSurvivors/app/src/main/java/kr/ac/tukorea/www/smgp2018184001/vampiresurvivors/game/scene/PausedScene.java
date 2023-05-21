package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;

public class PausedScene extends BaseScene {
    public enum Layer{
        bg, touch, COUNT
    }

    public PausedScene(){
        initLayers(Layer.COUNT);
    }
}
