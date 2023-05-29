package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sprite;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.LvUpButton;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class LevelUpScene extends BaseScene {
    private Random random = new Random();

    public enum Layer {
        bg, touch, COUNT
    }

    public LevelUpScene() {
        initLayers(LevelUpScene.Layer.COUNT);

        int numofButtons = 3;
        float buttonHeight = 0.3f;
        float startY = 0.5f;
        float frameHeight = buttonHeight * numofButtons;
        // add background frame
        add(LevelUpScene.Layer.bg, new Sprite(R.mipmap.levelupframe,
                0.5f, startY + (Metrics.y_offset / Metrics.scale),
                1f, frameHeight + 0.1f));

        // add buttons
        for (int i = 0; i < numofButtons; i++) {
            if (random.nextBoolean()) {
                add(LevelUpScene.Layer.touch, new LvUpButton(0.5f,
                        startY + buttonHeight * i + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                        0.9f, buttonHeight, Weapon.WeaponType.getRandomWeaponType(random)));
            } else {
                add(LevelUpScene.Layer.touch, new LvUpButton(0.5f,
                        startY + buttonHeight * i + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                        0.9f, buttonHeight, Passive.PassiveType.getRandomPassiveType(random)));
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        GameView.toScreenScale(canvas);
        int minute = (int) (MainScene.elapsedTime / 60f);
        int sec = (int) (MainScene.elapsedTime % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        if (MainScene.player == null) return;
        levelTextPaint.setTextSize(Metrics.screenWidth * 0.05f);
        canvas.drawText(String.format("LV %d", MainScene.player.getLevel()),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);
        GameView.toGameScale(canvas);
    }

    @Override
    public boolean handleBackKey() {
        new PausedScene().pushScene();
        return true;
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
