package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.Gauge;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.LevelUpScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.FireWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.KingBible;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.LightningRing;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.MagicWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.WhipController;

public class Player extends Character {
    private static final String TAG = Player.class.getSimpleName();
    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int maxHp_increment = 2;
    private int curExp = 0;
    public static float PLAYER_MOVEMENTSPEED = 0.5f;
    ArrayList<IGameObject> enemiesInScreen = new ArrayList<>();
    HashMap<Passive.PassiveType, Integer> passiveLevel = new HashMap<>();
    HashMap<Weapon.WeaponType, Integer> weaponLevel = new HashMap<>();
    HashMap<Weapon.WeaponType, Weapon> curWeapons = new HashMap<>();
    private static ArrayList<Enum> weaponToUpgrade = new ArrayList<>();
    private static ArrayList<Enum> passiveToUpgrade = new ArrayList<>();
    public static final int MAX_WEAPON_LEVEL = 8;
    public static final int MAX_PASSIVE_LEVEL = 5;
    private float attackRatio = 1.0f;
    private float coolTimeRatio = 1.0f;
    private float bulletSpeedRatio = 1.0f;
    private Gauge hpGauge = new Gauge(0.1f, R.color.hp_gauge_fg, R.color.hp_gauge_bg);
    private Gauge levelGauge = new Gauge(0.08f, R.color.level_gauge_fg, R.color.level_gauge_bg);
    Random random = new Random();

    public Player(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        movementSpeed = PLAYER_MOVEMENTSPEED;
    }

    public void init(WhipController wc) {
        weaponLevel.put(Weapon.WeaponType.Whip, 1);
        curWeapons.put(Weapon.WeaponType.Whip, wc);

        for (int i = 0; i < Weapon.WeaponType.COUNT.ordinal(); i++) {
            weaponToUpgrade.add(Weapon.WeaponType.values()[i]);
        }
        for (int i = 0; i < Passive.PassiveType.COUNT.ordinal(); i++) {
            passiveToUpgrade.add(Passive.PassiveType.values()[i]);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float width;
        // Draw HP Gauge
        canvas.save();
        width = dstRect.width() * 1.2f;
        canvas.translate(dstRect.left + dstRect.width() / 2 - width / 2, dstRect.bottom + 0.03f);
        canvas.scale(width, width);
        hpGauge.draw(canvas, curHp / maxHp);
        canvas.restore();

        // Draw HP Gauge
        canvas.save();
        float offsetY = -Metrics.y_offset / Metrics.scale;
        canvas.translate(0, offsetY + levelGauge.getWidth() / 2.0f);
        levelGauge.draw(canvas, (float) curExp / (float) expToLevelUp);
        canvas.restore();
    }

    @Override
    public void killThis() {
        Log.d(TAG, "Game Over");
    }

    public Enemy findNearestEnemy() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return null;
        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        if (enemies.isEmpty()) return null;

        Enemy enemy = (Enemy) enemies.get(0);
        float distance = 999.f;
        for (IGameObject curEnemy : enemies) {
            float curDistance = Math.abs(posX - ((Enemy) curEnemy).getPosX())
                    + Math.abs(posY - ((Enemy) curEnemy).getPosY());
            if (distance > curDistance) {
                distance = curDistance;
                enemy = (Enemy) curEnemy;
            }
        }
        return enemy;
    }

    public Enemy findRandomEnemyInScreen() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return null;
        ArrayList<IGameObject> enemies = scene.getObjectsAt(MainScene.Layer.enemy);
        if (enemies.isEmpty()) return null;

        float range = (Metrics.game_width + Metrics.game_height) / 2.f;
        for (IGameObject e : enemies) {
            float distance = Math.abs(((Enemy) e).getPosX() - posX) +
                    Math.abs(((Enemy) e).getPosY() - posY);
            if (distance < range) {
                enemiesInScreen.add(e);
            }
        }
        if (enemiesInScreen.isEmpty()) return null;
        //Log.d(TAG, "enemiesinscreen: " + enemiesInScreen.size());
        Random random = new Random();
        Enemy enemy = (Enemy) enemiesInScreen.get(random.nextInt(enemiesInScreen.size()));
        enemiesInScreen.clear();
        return enemy;
    }

    public void addPassiveItem(Passive.PassiveType type) {
        if (passiveLevel.get(type) == null) {
            passiveLevel.put(type, 1);
            makePassiveEffect(type);
            Log.d(TAG, "first added " + type + ", ratio: " + getRatio(type));
            return;
        }
        int num = passiveLevel.get(type);
        if (num >= MAX_PASSIVE_LEVEL) {
            Log.d(TAG, type + " is Level 5, Increase Exp");
            addExp(5);
        } else {
            if (passiveLevel.get(type) == 4) {
                passiveToUpgrade.remove(type);
            }
            passiveLevel.put(type, num + 1);
            makePassiveEffect(type);
            Log.d(TAG, type + "Passive Item Level Up to " + passiveLevel.get(type) + ", ratio: " + getRatio(type));
        }
    }

    private void makePassiveEffect(Passive.PassiveType type) {
        switch (type) {
            case Inc_Atk:
                attackRatio += 0.1f; // maximum 1.5f
                break;
            case Dec_Cooltime:
                coolTimeRatio -= 0.08f; // minimum 0.6f
                break;
            case Inc_BulletSpd:
                bulletSpeedRatio += 0.1f;  // maximum 1.5f
                break;
        }
        //Log.d(TAG, attackRatio+", "+coolTimeRatio+", "+bulletSpeedRatio);
    }

    public void addWeapon(Weapon.WeaponType type) {
        if (weaponLevel.get(type) == null) {
            MainScene scene = MainScene.mainScene;
            IGameObject weapon;
            switch (type) {
                default:
                case Whip:
                    // 게임 시작 시 Whip은 레벨이 1이기 때문에 코드가 불리지 않지만
                    // 일단 적어 놓는다.
                    weapon = new WhipController(this, scene);
                    break;
                case MagicWand:
                    weapon = new MagicWand(this);
                    break;
                case KingBible:
                    weapon = new KingBible(this);
                    break;
                case FireWand:
                    weapon = new FireWand(this);
                    break;
                case LightningRing:
                    weapon = new LightningRing(this);
                    break;
            }
            scene.add(MainScene.Layer.weapon, weapon);
            curWeapons.put(type, (Weapon) weapon);
            weaponLevel.put(type, 1);
            Log.d(TAG, "first added Weapon " + type);
            return;
        }
        int num = weaponLevel.get(type);
        if (num >= MAX_WEAPON_LEVEL) {
            Log.d(TAG, type + " is Level 8, Increase Exp");
            addExp(5);
        } else {
            increaseWeaponLevel(type);
            Log.d(TAG, type + "Weapon Item Level Up to " + weaponLevel.get(type));
        }
    }

    private void increaseWeaponLevel(Weapon.WeaponType type) {
        int num = weaponLevel.get(type);
        weaponLevel.put(type, num + 1);
        Weapon weapon = curWeapons.get(type);
        switch (num + 1) {
            case 2:
                weapon.addProjectileCount(1);
                break;
            case 3:
                weapon.addAtk(10f);
                break;
            case 4:
                weapon.addProjectileCount(1);
                break;
            case 5:
                weapon.addAtk(20f);
                break;
            case 6:
                float coolTime = weapon.getMaxCoolTime();
                weapon.setMaxCoolTime(coolTime * 0.75f);
                Log.d(TAG, "Decrease " + weapon.getClass().getSimpleName() + "'s CoolTime");
                break;
            case 7:
                weapon.addAtk(10f);
                break;
            case 8:
                weapon.addProjectileCount(1);
                weaponToUpgrade.remove(type);
                break;
            default:
                break;
        }
    }

    public void addExp(int exp) {
        this.curExp += exp;
        if (curExp >= expToLevelUp) {
            curExp -= expToLevelUp;
            levelUp();
        }
    }

    private void levelUp() {
        level += 1;
        LevelUpScene.numofLevelUpSceneToShow += 1;
        //Log.d(TAG, "Player level Up to " + level);
        expToLevelUp += expToLevelUp_increment;
        maxHp += maxHp_increment;
        // 모든 아이템 레벨이 max라면 체력 회복
        if (numofItemToUpgrade() == 0) {
            recoverHp(5);
        } else if (LevelUpScene.numofLevelUpSceneToShow == 1) {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Sound.playEffect(R.raw.levelup);
                    new LevelUpScene().pushScene();
                }
            });
        }
    }

    public float getRatio(Passive.PassiveType type) {
        float ratio = 0.0f;
        switch (type) {
            case Inc_Atk:
                ratio = attackRatio;
                break;
            case Dec_Cooltime:
                ratio = coolTimeRatio;
                break;
            case Inc_BulletSpd:
                ratio = bulletSpeedRatio;
                break;
        }
        return ratio;
    }

    public int getWeaponLevel(Weapon.WeaponType type) {
        if (weaponLevel.get(type) == null) {
            return 0;
        }
        return weaponLevel.get(type);
    }

    public int getPassiveLevel(Passive.PassiveType type) {
        if (passiveLevel.get(type) == null) {
            return 0;
        }
        return passiveLevel.get(type);
    }

    public Weapon.WeaponType getRandomWeaponNotMaxLevel(ArrayList<Enum> addedType) {
        if (weaponToUpgrade.size() == 0) {
            Log.v(TAG, "All Weapon is Max Level");
            return null;
        }
        // upgrade가능한 weapon 중 addedType(이미 업그레이드 버튼에 생성된 type)을 제외하고 확인한다.
        ArrayList<Enum> canUpgrade = new ArrayList<>();
        canUpgrade.addAll(weaponToUpgrade);
        canUpgrade.removeAll(addedType);
//        Log.v(null, "----get Random Weapon -------");
//        for (Enum e : addedType) {
//            Log.v(null, "Added Type: " + e);
//        }
//        for (Enum e : weaponToUpgrade) {
//            Log.v(null, "weaponToUpgrade: " + e);
//        }
//        for (Enum e : canUpgrade) {
//            Log.v(null, "canUpgrade: " + e);
//        }
        if (canUpgrade.size() == 0) return null;
        int id = random.nextInt(canUpgrade.size());
        return (Weapon.WeaponType) canUpgrade.get(id);
    }

    public Passive.PassiveType getRandomPassiveNotMaxLevel(ArrayList<Enum> addedType) {
        if (passiveToUpgrade.size() == 0) {
            Log.v(TAG, "All Passive is Max Level");
            return null;
        }
        // upgrade가능한 passive 중 addedType(이미 업그레이드 버튼에 생성된 type)을 제외하고 확인한다.
        ArrayList<Enum> canUpgrade = new ArrayList<>();
        canUpgrade.addAll(passiveToUpgrade);
        canUpgrade.removeAll(addedType);
//        Log.v(null, "----get Random Passive -------");
//        for (Enum e : addedType) {
//            Log.v(null, "Added Type: " + e);
//        }
//        for (Enum e : passiveToUpgrade) {
//            Log.v(null, "passiveToUpgrade: " + e);
//        }
//        for (Enum e : canUpgrade) {
//            Log.v(null, "canUpgrade: " + e);
//        }
        if (canUpgrade.size() == 0) return null;
        int id = random.nextInt(canUpgrade.size());
        return (Passive.PassiveType) canUpgrade.get(id);
    }

    public float getAttackRatio() {
        return attackRatio;
    }

    public float getCoolTimeRatio() {
        return coolTimeRatio;
    }

    public float getBulletSpeedRatio() {
        return bulletSpeedRatio;
    }

    public int getLevel() {
        return level;
    }

    public int getCurExp() {
        return curExp;
    }

    @Override
    public void getDamage(float damage) {
        super.getDamage(damage);
    }

    public int numofItemToUpgrade() {
        return weaponToUpgrade.size() + passiveToUpgrade.size();
    }
}
