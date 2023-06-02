package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app.MainActivity;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app.SettingsActivity;

public class PausedScene extends BaseScene {
    MainScene scene;

    public enum Layer {
        bg, touch, COUNT
    }

    public PausedScene(MainScene scene) {
        this.scene = scene;
        initLayers(Layer.COUNT);
        add(GameOverScene.Layer.bg, new Sprite(R.mipmap.halftransparent_black,
                0.5f, 0.5f,
                Metrics.screenWidth / Metrics.scale, Metrics.screenHeight / Metrics.scale));
        // 계속하기
        add(Layer.touch, new Button(R.mipmap.button, 0.5f, 0.4f + (Metrics.y_offset / Metrics.scale)
                , 0.45f, 0.2f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.released) {
                            popScene();
                            if (BaseScene.getTopScene() instanceof MainScene) {
                                MainScene scene = (MainScene) BaseScene.getTopScene();
                                scene.getJoystick().touchUp();
                            }
                        }
                        return false;
                    }
                }));
        // 설정
        add(Layer.touch, new Button(R.mipmap.button, 0.5f, 0.6f + (Metrics.y_offset / Metrics.scale),
                0.45f, 0.2f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.released) {
                            Intent intent = new Intent(GameView.view.context, SettingsActivity.class);
                            GameView.view.context.startActivity(intent);
                        }
                        return false;
                    }
                }));
        // 나가기
        add(Layer.touch, new Button(R.mipmap.button, 0.5f, 0.8f + (Metrics.y_offset / Metrics.scale),
                0.45f, 0.2f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.released) {
                            ((Activity) GameView.view.context).finish();
                        }
                        return false;
                    }
                }));
    }

    @Override
    protected void draw(Canvas canvas, int index) {
        super.draw(canvas, index);
        GameView.toScreenScale(canvas);
        int minute = (int) (scene.elapsedTime / 60f);
        int sec = (int) (scene.elapsedTime % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        canvas.drawText(String.format("LV %d", MainScene.player.getLevel()),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);

        canvas.drawText(GameView.res.getString(R.string.resume),
                Metrics.screenWidth * 0.5f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.4f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        canvas.drawText(GameView.res.getString(R.string.title_settings),
                Metrics.screenWidth * 0.5f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.6f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        canvas.drawText(GameView.res.getString(R.string.title_exit),
                Metrics.screenWidth * 0.5f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.8f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
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
