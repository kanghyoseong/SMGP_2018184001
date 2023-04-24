package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.CollisionChecker;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Bat;

public class MainScene extends BaseScene {
    private Joystick joystick;
    public ArrayList<Enemy> enemies = new ArrayList<>(); // 임시

    public MainScene() {
        add(new CollisionChecker());

        Object background = new Object(0, 0,
                SpriteSize.BACKGROUND_SIZE, SpriteSize.BACKGROUND_SIZE,
                R.mipmap.background);
        add(background);

        player = new Player(0, 0,
                SpriteSize.PLAYER_SIZE, SpriteSize.PLAYER_SIZE,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        player.setcolliderSize(SpriteSize.PLAYER_SIZE * 0.6f, SpriteSize.PLAYER_SIZE * 0.8f);
        add(player);

        Bat bat = new Bat(0, 0, SpriteSize.BAT_SIZE, SpriteSize.BAT_SIZE, R.mipmap.bat, 2, 2, 0.1f);
        bat.setTarget(player);
        bat.setcolliderSize(SpriteSize.BAT_SIZE * 0.6f, SpriteSize.BAT_SIZE * 0.6f);
        add(bat);
        enemies.add(bat); // 임시

        camera = new Camera(player);
        add(camera);

        joystick = new Joystick();
        add(joystick);
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
