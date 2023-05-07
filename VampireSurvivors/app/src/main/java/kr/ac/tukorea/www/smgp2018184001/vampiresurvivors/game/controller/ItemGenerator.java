package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;

public class ItemGenerator implements IGameObject {
    private BaseScene scene;
    private Player player;

    public ItemGenerator(BaseScene scene, Player player) {
        this.scene = scene;
        this.player = player;
    }

    @Override
    public void update(float eTime) {
    }

    public void spawnPassive(Passive.PassiveType type, float posX, float posY) {
        Passive item = new Passive(posX, posY, player, type);
        scene.add(MainScene.Layer.item, item);
    }

    public void spawnWeapon() {

    }

    @Override
    public void draw(Canvas canvas) {
    }
}
