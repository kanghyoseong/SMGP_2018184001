package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.app.Activity;
import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;

public class GameOverScene extends BaseScene {
    private float time;
    private int level;
    private int score;

    public enum Layer {
        bg, touch, COUNT
    }

    public GameOverScene(MainScene scene) {
        time = scene.elapsedTime;
        level = scene.player.getLevel();
        initLayers(Layer.COUNT);
        add(Layer.bg, new Sprite(R.mipmap.gameover,
                0.5f, 0.2f, 0.8f, 0.3f));
        add(Layer.bg, new Sprite(R.mipmap.halftransparent_red,
                0.5f, 0.5f,
                Metrics.screenWidth / Metrics.scale, Metrics.screenHeight / Metrics.scale));
        // 나가기
        add(PausedScene.Layer.touch, new Button(R.mipmap.button, 0.25f, 0.85f + (Metrics.y_offset / Metrics.scale),
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
        // 다시 시작
        add(PausedScene.Layer.touch, new Button(R.mipmap.button, 0.75f, 0.85f + (Metrics.y_offset / Metrics.scale)
                , 0.45f, 0.2f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.released) {
                            BaseScene.restart();
                        }
                        return false;
                    }
                }));

        score = MainScene.player.getNumofKilledEnemies() * 10 + (int) (time * 5);
    }

    @Override
    protected void draw(Canvas canvas, int index) {
        super.draw(canvas, index);
        GameView.toScreenScale(canvas);
        int minute = (int) (time / 60f);
        int sec = (int) (time % 60f);
        // 레벨 출력
        canvas.drawText(String.format("LV %d", level),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.5f, BaseScene.textPaint);
        // 시간 출력
        canvas.drawText(String.format(GameView.res.getString(R.string.gameover_time), minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.6f, BaseScene.textPaint);
        // 점수 출력
        canvas.drawText(String.format(GameView.res.getString(R.string.gameover_score), score),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.7f, BaseScene.textPaint);

        canvas.drawText(GameView.res.getString(R.string.title_exit),
                Metrics.screenWidth * 0.25f, Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.85f + Metrics.y_offset + textPaint.getTextSize() * 0.4f,
                BaseScene.textPaint);
        canvas.drawText(GameView.res.getString(R.string.restart),
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
