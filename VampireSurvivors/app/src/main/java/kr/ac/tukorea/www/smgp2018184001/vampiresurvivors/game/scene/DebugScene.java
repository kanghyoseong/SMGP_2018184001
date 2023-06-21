package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene;

import android.graphics.Canvas;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;

public class DebugScene extends BaseScene {
    private MainScene scene;

    public enum Layer {
        touch, COUNT
    }

    public DebugScene(MainScene scene) {
        this.scene = scene;
        initLayers(Layer.COUNT);
        // Back
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.0f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            popScene();
                        }
                        return false;
                    }
                }));
        // Add Whip
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.1f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            scene.getPlayer().addWeapon(Weapon.WeaponType.Whip);
                        }
                        return false;
                    }
                }));
        // Add FireWand
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.2f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            scene.getPlayer().addWeapon(Weapon.WeaponType.FireWand);
                        }
                        return false;
                    }
                }));
        // Add KingBible
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.3f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            scene.getPlayer().addWeapon(Weapon.WeaponType.KingBible);
                        }
                        return false;
                    }
                }));
        // Add MagicWand
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.4f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            scene.getPlayer().addWeapon(Weapon.WeaponType.MagicWand);
                        }
                        return false;
                    }
                }));
        // Add Lightning
        add(Layer.touch, new Button(R.mipmap.button, 0.15f, 0.5f
                , 0.3f, 0.1f,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            scene.getPlayer().addWeapon(Weapon.WeaponType.LightningRing);
                        }
                        return false;
                    }
                }));
    }

    protected void draw(Canvas canvas, int index) {
        super.draw(canvas, index);
        GameView.toScreenScale(canvas);
        canvas.drawText("Back",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset,
                BaseScene.levelTextPaint);
        canvas.drawText("Add Whip",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.1f,
                BaseScene.levelTextPaint);
        canvas.drawText("Add FireWand",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.2f,
                BaseScene.levelTextPaint);
        canvas.drawText("Add KingBible",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.3f,
                BaseScene.levelTextPaint);
        canvas.drawText("Add MagicWand",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.4f,
                BaseScene.levelTextPaint);
        canvas.drawText("Add Lightning",
                Metrics.screenWidth * 0.15f,
                Metrics.y_offset + (Metrics.screenHeight - Metrics.y_offset * 2) * 0.5f,
                BaseScene.levelTextPaint);
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
}
