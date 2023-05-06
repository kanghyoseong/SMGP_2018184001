package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.view.MotionEvent;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.CollisionChecker;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.FireWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.KingBible;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.LightningRing;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.MagicWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.Whip;

public class MainScene extends BaseScene {
    private Joystick joystick;

    public enum Layer {
        bg, enemy, bullet, weapon, player, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(1, 1);
        initLayers(Layer.COUNT);

        add(Layer.controller, new CollisionChecker());
        add(Layer.controller, new EnemyGenerator());

        Object background = new Object(0, 0,
                SpriteSize.BACKGROUND_SIZE, SpriteSize.BACKGROUND_SIZE,
                R.mipmap.background);
        add(Layer.bg, background);

        player = new Player(0, 0,
                SpriteSize.PLAYER_SIZE, SpriteSize.PLAYER_SIZE,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        player.setcolliderSize(SpriteSize.PLAYER_SIZE * 0.6f, SpriteSize.PLAYER_SIZE * 0.8f);
        add(Layer.player, player);

        //Whip whip = new Whip(player);
        //add(Layer.weapon, whip);
        //MagicWand w = new MagicWand(player);
        //add(Layer.weapon, w);
        //KingBible kb = new KingBible(player);
        //add(Layer.weapon, kb);
        //FireWand f = new FireWand(player);
        //add(Layer.weapon, f);
        LightningRing r = new LightningRing(player);
        add(Layer.weapon, r);

        camera = new Camera(player);
        add(Layer.controller, camera);

        joystick = new Joystick();
        add(Layer.controller, joystick);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                joystick.touchDown(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                joystick.drag(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                joystick.touchUp();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
