package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app.MainActivity;

public class GameOverScene extends BaseScene {
    private float time;
    private int level;

    public enum Layer {
        bg, touch, COUNT
    }

    public GameOverScene(MainScene scene) {
        time = scene.elapsedTime;
        level = scene.player.getLevel();
        initLayers(Layer.COUNT);
        add(Layer.bg, new Sprite(R.mipmap.gameover,
                0.5f, 0.4f, 0.8f, 0.3f));
        // 다시 시작
        add(PausedScene.Layer.touch, new Button(R.mipmap.button, 0.25f, 0.85f + (Metrics.y_offset / Metrics.scale)
                , 0.45f, 0.2f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            BaseScene.restart();
                        }
                        return false;
                    }
                }));
        // 나가기
        add(PausedScene.Layer.touch, new Button(R.mipmap.button, 0.75f, 0.85f + (Metrics.y_offset / Metrics.scale),
                0.45f, 0.2f,
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
    protected void draw(Canvas canvas, int index) {
        super.draw(canvas, index);
        GameView.toScreenScale(canvas);
        int minute = (int) (time / 60f);
        int sec = (int) (time % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        canvas.drawText(String.format("LV %d", level),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);

        canvas.drawText("다시시작",
                Metrics.screenWidth * 0.25f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.85f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        canvas.drawText("나가기",
                Metrics.screenWidth * 0.75f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.85f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        GameView.toGameScale(canvas);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}