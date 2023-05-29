package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Button;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class LvUpButton extends Button {
    private static final String TAG = LvUpButton.class.getSimpleName();
    private boolean isWeapon;
    private Weapon.WeaponType weaponType;
    private Passive.PassiveType passiveType;
    public static Paint lvupinfoTextPaint = new Paint();

    static {
        lvupinfoTextPaint.setColor(Color.WHITE);
        lvupinfoTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public LvUpButton(float posX, float posY, float width, float height,
                      Weapon.WeaponType weaponType) {
        super(R.mipmap.infoframe, posX, posY, width, height, new Button.Callback() {
            @Override
            public boolean onTouch(Action action) {
                if (action == Button.Action.pressed) {
                    BaseScene.getTopScene().popScene();
                    if (BaseScene.getTopScene() instanceof MainScene) {
                        MainScene scene = (MainScene) BaseScene.getTopScene();
                        scene.getJoystick().touchUp();
                    }
                    Player player = MainScene.player;
                    if (player != null) {
                        player.addWeapon(weaponType);
                    }
                }
                return false;
            }
        });
        this.weaponType = weaponType;
        isWeapon = true;
        lvupinfoTextPaint.setTextSize(Metrics.screenWidth * 0.05f);
    }

    public LvUpButton(float posX, float posY, float width, float height,
                      Passive.PassiveType passiveType) {
        super(R.mipmap.infoframe, posX, posY, width, height, new Button.Callback() {
            @Override
            public boolean onTouch(Action action) {
                if (action == Button.Action.pressed) {
                    BaseScene.getTopScene().popScene();
                    if (BaseScene.getTopScene() instanceof MainScene) {
                        MainScene scene = (MainScene) BaseScene.getTopScene();
                        scene.getJoystick().touchUp();
                    }
                    Player player = MainScene.player;
                    if (player != null) {
                        player.addPassiveItem(passiveType);
                    }
                }
                return false;
            }
        });
        this.passiveType = passiveType;
        isWeapon = false;
        lvupinfoTextPaint.setTextSize(Metrics.screenWidth * 0.05f);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        GameView.toScreenScale(canvas);
        try {
            String id;
            Enum e;
            if (isWeapon) {
                e = weaponType;
                int lv = MainScene.player.getWeaponLevel(weaponType);
                if (lv == 0) {
                    id = weaponType.name();
                } else {
                    id = "lv" + (lv+1); // lv+1하여 다음 레벨에 관한 설명을 출력한다.
                }
            } else {
                e = passiveType;
                id = passiveType.name();
            }
            //Log.d(TAG, "id: " + id);
            int resId = GameView.res.getIdentifier(id, "string", GameView.packageName);
            String info = GameView.res.getString(resId);
            canvas.drawText(e.name() + ": " + info, Metrics.screenWidth / 2f, Metrics.toScreenY(posY), lvupinfoTextPaint);
        } catch (Exception e) {
            canvas.translate(Metrics.x_offset, Metrics.y_offset);
            canvas.scale(Metrics.scale, Metrics.scale);
            return;
        }
        GameView.toGameScale(canvas);
    }
}
