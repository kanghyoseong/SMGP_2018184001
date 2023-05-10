package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Exp;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class ItemGenerator implements IGameObject {
    private BaseScene scene;
    private Player player;
    private Random random = new Random();

    public ItemGenerator(BaseScene scene, Player player) {
        this.scene = scene;
        this.player = player;
    }

    @Override
    public void update(float eTime) {
    }

    public void spawnExp(float posX, float posY, int exp) {
        Exp e = Exp.get(posX, posY, exp);
        scene = BaseScene.getTopScene();
        scene.add(MainScene.Layer.item, e);
//        if (random.nextBoolean()) {
//            player.addPassiveItem(Passive.PassiveType.getRandomPassiveType(random));
//        } else {
//            player.addWeapon(Weapon.WeaponType.getRandomWeaponType(random));
//        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
