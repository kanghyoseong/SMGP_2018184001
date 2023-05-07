package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller.MainScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Passive;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Weapon;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.FireWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.KingBible;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.LightningRing;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.MagicWand;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.weapon.Whip;

public class Player extends Character {
    private static final String TAG = Player.class.getSimpleName();
    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int maxHp_increment = 2;
    public static float PLAYER_MOVEMENTSPEED = 0.5f;
    ArrayList<IGameObject> enemiesInScreen = new ArrayList<>();
    HashMap<Passive.PassiveType, Integer> passiveLevel = new HashMap<>();
    HashMap<Weapon.WeaponType, Integer> weaponLevel = new HashMap<>();
    private float attackRatio = 1.0f;
    private float coolTimeRatio = 1.0f;
    private float bulletSpeedRatio = 1.0f;

    public Player(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
        movementSpeed = PLAYER_MOVEMENTSPEED;
        weaponLevel.put(Weapon.WeaponType.Whip, 1);
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
        if (num >= 5) {
            Log.d(TAG, type + " is Level 5, Increase Exp");
            // -------------------- Increase Exp --------------------
        } else {
            passiveLevel.put(type, num + 1);
            makePassiveEffect(type);
            Log.d(TAG, type + "added, num: " + passiveLevel.get(type) + ", ratio: " + getRatio(type));
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
            BaseScene scene = BaseScene.getTopScene();
            IGameObject weapon;
            switch (type) {
                default:
                case Whip:
                    weapon = new Whip(this);
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
            weaponLevel.put(type, 1);
            Log.d(TAG, "first added Weapon " + type);
            return;
        }
        int num = weaponLevel.get(type);
        if (num >= 8) {
            Log.d(TAG, type + " is Level 8, Increase Exp");
            // -------------------- Increase Exp --------------------
        } else {
            weaponLevel.put(type, num + 1);
            Log.d(TAG, type + "added weapon, num: " + weaponLevel.get(type));
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

    public float getAttackRatio() {
        return attackRatio;
    }

    public float getCoolTimeRatio() {
        return coolTimeRatio;
    }

    public float getBulletSpeedRatio() {
        return bulletSpeedRatio;
    }
}
