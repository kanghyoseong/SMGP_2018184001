package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.CollisionChecker;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Gauge;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.Joystick;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.WhipController;

public class MainScene extends BaseScene {
    private Joystick joystick;
    public float elapsedTime = 0;
    public static Player player;
    public MainScene mainScene;
    public EnemyGenerator enemyGenerator;
    private Gauge hpGauge = new Gauge(0.1f, R.color.hp_gauge_fg, R.color.hp_gauge_bg);
    private Gauge levelGauge = new Gauge(0.08f, R.color.level_gauge_fg, R.color.level_gauge_bg);


    public enum Layer {
        bg, enemy, bullet, weapon, player, item, touch, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(1, 1);
        initLayers(Layer.COUNT);

        Object background = new Object(0, 0,
                SpriteSize.BACKGROUND_SIZE, SpriteSize.BACKGROUND_SIZE,
                R.mipmap.background);
        add(Layer.bg, background);

        player = new Player(this, 0, 0,
                SpriteSize.PLAYER_SIZE, SpriteSize.PLAYER_SIZE,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        player.setcolliderSize(SpriteSize.PLAYER_SIZE * 0.6f, SpriteSize.PLAYER_SIZE * 0.8f);
        add(Layer.player, player);
        WhipController wc = new WhipController(player);
        player.init(wc);
        add(Layer.weapon, wc);

        add(Layer.touch, new Button(R.mipmap.pause,
                0.9f, -0.3f,
                0.1f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            new PausedScene(mainScene).pushScene();
                        }
                        return true;
                    }
                }));

        add(Layer.controller, new CollisionChecker());
        enemyGenerator = new EnemyGenerator();
        add(Layer.controller, enemyGenerator);

        camera = new Camera(player);
        add(Layer.controller, camera);

        joystick = new Joystick();
        add(Layer.controller, joystick);

        mainScene = this;
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        elapsedTime += frameTime;
    }

    @Override
    protected void draw(Canvas canvas, int index) {
        super.draw(canvas, index);

        float width;
        // Draw HP Gauge
        canvas.save();
        RectF dstRect = player.getDstRect();
        width = player.getSizeX() * 1.2f;
        canvas.translate(dstRect.left + dstRect.width() / 2 - width / 2, dstRect.bottom + 0.03f);
        canvas.scale(width, width);
        hpGauge.draw(canvas, player.getCurHp() / player.getMaxHp());
        canvas.restore();

        // Draw Exp Gauge
        canvas.save();
        float offsetY = -Metrics.y_offset / Metrics.scale;
        canvas.translate(0, offsetY + levelGauge.getWidth() / 2.0f);
        levelGauge.draw(canvas, (float) player.getCurExp() / (float) player.getExpToLevelUp());
        canvas.restore();

        GameView.toScreenScale(canvas);
        int minute = (int) (elapsedTime / 60f);
        int sec = (int) (elapsedTime % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        levelTextPaint.setTextSize(Metrics.screenWidth * 0.05f);
        canvas.drawText(String.format("LV %d", player.getLevel()),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);
        GameView.toGameScale(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
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

    @Override
    public boolean handleBackKey() {
        new PausedScene(this).pushScene();
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    public Joystick getJoystick() {
        return joystick;
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.bgm);
    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
    }
}
