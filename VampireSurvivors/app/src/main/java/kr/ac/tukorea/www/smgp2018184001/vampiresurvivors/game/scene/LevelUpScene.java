package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
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
    private MainScene scene;
    private Random random = new Random();
    public static int numofLevelUpSceneToShow = 0;
    private ArrayList<Enum> addedType = new ArrayList<>();

    public enum Layer {
        bg, touch, COUNT
    }

    public LevelUpScene(MainScene scene) {
        this.scene = scene;
        initLayers(LevelUpScene.Layer.COUNT);

        int numofButtons = Math.min(3, MainScene.player.numofItemToUpgrade());
        //Log.v(null, "itemToUpgrade: " + MainScene.player.numofItemToUpgrade());
        //Log.v(null, "numofButtons: " + numofButtons);
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
                if (!addWeaponButton(i, startY, buttonHeight, frameHeight)) {
                    addPassiveButton(i, startY, buttonHeight, frameHeight);
                }
            } else {
                if (!addPassiveButton(i, startY, buttonHeight, frameHeight)) {
                    addWeaponButton(i, startY, buttonHeight, frameHeight);
                }
            }
        }
    }

    private boolean addWeaponButton(int index, float startY, float buttonHeight, float frameHeight) {
        Weapon.WeaponType type = MainScene.player.getRandomWeaponNotMaxLevel(addedType);
        if (type == null) return false;
        add(LevelUpScene.Layer.touch, new LvUpButton(scene, 0.5f,
                startY + buttonHeight * index + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                0.9f, buttonHeight, type));
        addedType.add(type);
        return true;
    }

    private boolean addPassiveButton(int index, float startY, float buttonHeight, float frameHeight) {
        Passive.PassiveType type = MainScene.player.getRandomPassiveNotMaxLevel(addedType);
        if (type == null) return false;
        add(LevelUpScene.Layer.touch, new LvUpButton(scene, 0.5f,
                startY + buttonHeight * index + (Metrics.y_offset / Metrics.scale) - frameHeight / 2f + buttonHeight / 2f,
                0.9f, buttonHeight, type));
        addedType.add(type);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        GameView.toScreenScale(canvas);
        int minute = (int) (scene.elapsedTime / 60f);
        int sec = (int) (scene.elapsedTime % 60f);
        // 시간 출력
        canvas.drawText(String.format("%02d : %02d", minute, sec),
                Metrics.screenWidth / 2, Metrics.screenHeight * 0.1f, BaseScene.textPaint);
        // 레벨 출력
        if (MainScene.player == null) return;
        canvas.drawText(String.format("LV %d", MainScene.player.getLevel()),
                Metrics.screenWidth * 0.9f, 90f, BaseScene.levelTextPaint);
        GameView.toGameScale(canvas);
    }

    @Override
    public boolean handleBackKey() {
        new PausedScene(scene).pushScene();
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
