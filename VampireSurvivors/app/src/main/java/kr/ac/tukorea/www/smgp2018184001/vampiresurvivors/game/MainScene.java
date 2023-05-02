package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.CollisionChecker;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bat;

public class MainScene extends BaseScene {
    private Joystick joystick;

    public enum Layer {
        bg, enemy, bullet, item, player, controller, COUNT
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

        Item item = new Item(0, 0, SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y, R.mipmap.whip,
                1, 6, 0.02f);
        item.setcolliderSize(SpriteSize.WHIP_SIZE_X, SpriteSize.WHIP_SIZE_Y);
        add(Layer.item, item);

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
