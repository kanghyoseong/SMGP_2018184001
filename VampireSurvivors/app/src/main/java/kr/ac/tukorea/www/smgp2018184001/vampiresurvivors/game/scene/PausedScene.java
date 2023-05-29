package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;

public class PausedScene extends BaseScene {
    public enum Layer {
        bg, touch, COUNT
    }

    public PausedScene() {
        initLayers(Layer.COUNT);
        add(Layer.touch, new Button(R.mipmap.button, 0.25f, 0.85f + (Metrics.y_offset / Metrics.scale),
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
        add(Layer.touch, new Button(R.mipmap.button, 0.75f, 0.85f + (Metrics.y_offset / Metrics.scale)
                , 0.45f, 0.2f,
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
        textPaint.setTextSize(Metrics.screenWidth * 0.1f);
        int minute = (int) (MainScene.elapsedTime / 60f);
        int sec = (int) (MainScene.elapsedTime % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        levelTextPaint.setTextSize(Metrics.screenWidth * 0.05f);
        canvas.drawText(String.format("LV %d", MainScene.player.getLevel()),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);

        canvas.drawText("계속하기",
                Metrics.screenWidth * 0.25f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.85f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        canvas.drawText("계속하기",
                Metrics.screenWidth * 0.75f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.85f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        GameView.toGameScale(canvas);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
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
