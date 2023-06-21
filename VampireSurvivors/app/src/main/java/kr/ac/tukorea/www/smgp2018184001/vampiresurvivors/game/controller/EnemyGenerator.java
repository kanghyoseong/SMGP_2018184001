package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.controller;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IGameObject;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.Metrics;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.EEnemyType;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.Enemy;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Bat;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Ghost;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.LizardPawn;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Mantichana;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy.enemyType.Skeleton;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.objects.Object;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class EnemyGenerator implements IGameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private int wave = 0;
    protected static final float TIME_TO_NEXT_WAVE = 20.0f;
    private float elapsedTime = TIME_TO_NEXT_WAVE * 0.95f;
    private final int INITIAL_NUM_OF_ENEMY = 15;
    private final int ENEMY_INCREMENT_PER_WAVE = 2; // 0보다 커야함
    private int numofSpawnedEnemies = -1;
    protected static final float TIME_TO_NEXT_PATTERN = 15.0f;
    private static Random random = new Random();


    public EnemyGenerator() {
        if (BuildConfig.DEBUG) {
            wave = DebugFlag.START_WAVE;
        }
    }

    @Override
    public void update(float eTime) {
        elapsedTime += eTime;
        float mod = elapsedTime % TIME_TO_NEXT_PATTERN;
        //Log.v(TAG, "frameTime: " + eTime + ", elapsedTime: " + elapsedTime + ", mod: " + mod);
        if (wave > 1 && mod > TIME_TO_NEXT_PATTERN * 0.5 - 0.01f && mod < TIME_TO_NEXT_PATTERN * 0.5f + 0.01f) {
            //Log.v(TAG, "frameTime: " + eTime + ", elapsedTime: " + elapsedTime + ", mod: " + mod);
            spawnPattern();
        }
        if (elapsedTime > TIME_TO_NEXT_WAVE ||
                numofSpawnedEnemies == 0) {
            elapsedTime = 0;
            wave++;
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return;
        if (!(BaseScene.getTopScene() instanceof MainScene)) return;
        Player player = ((MainScene) BaseScene.getTopScene()).getPlayer();
        if (player == null) return;
        if (numofSpawnedEnemies == -1) numofSpawnedEnemies = 0;
        for (int i = 0; i < EEnemyType.Count.ordinal(); i++) {
            EEnemyType curType = EEnemyType.values()[i];

            // ENEMY_INCREMENT_PER_WAVE가 0보다 커야 정상 작동
            int spawnNum = (wave - curType.getWave()) * ENEMY_INCREMENT_PER_WAVE;
            spawnNum = spawnNum >= 0 ? spawnNum += INITIAL_NUM_OF_ENEMY : 0;

            //Log.d(null, "Spawn: " + curType + ", count: " + spawnNum);
            Enemy e = null;
            float posX, posY;
            for (int j = 0; j < spawnNum; j++) {
                posX = getRandomPos(random, (MainScene) scene, true);
                posY = getRandomPos(random, (MainScene) scene, false);
                switch (curType) {
                    case Bat:
                        e = Bat.get(posX, posY, player);
                        break;
                    case Skeleton:
                        e = Skeleton.get(posX, posY, player);
                        break;
                    case Ghost:
                        e = Ghost.get(posX, posY, player);
                        break;
                    case Mantichana:
                        e = Mantichana.get(posX, posY, player);
                        break;
                    case LizardPawn:
                        e = LizardPawn.get(posX, posY, player);
                        break;
                    default:
                        break;
                }
                if (e != null) {
                    numofSpawnedEnemies++;
                    scene.add(MainScene.Layer.enemy, e);
                }
            }
        }
    }

    private enum Pattern {
        BatCircle, COUNT;

        public static Pattern getRandomPattern(Random random) {
            return Pattern.values()[random.nextInt(Pattern.COUNT.ordinal())];
        }
    }

    private void spawnPattern() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return;
        if (!(BaseScene.getTopScene() instanceof MainScene)) return;
        Player player = ((MainScene) BaseScene.getTopScene()).getPlayer();
        if (player == null) return;
        if (numofSpawnedEnemies == -1) numofSpawnedEnemies = 0;

        Pattern pattern = Pattern.getRandomPattern(random);
        Log.v(TAG, "Pattern: " + pattern);
        switch (pattern) {
            case BatCircle:
                patternBatCircle(scene, player);
                break;
            default:
                break;
        }
    }

    private void patternBatCircle(BaseScene scene, Player player) {
        Log.v(TAG, "patternBatCircle");
        Bat enemy;
        float posX, posY;
        final float COUNT = 20;
        final float RADIUS = 1.5f;

        float div = 2 * (float) Math.PI / (float) COUNT;

        for (int i = 0; i < COUNT; i++) {
            float offset = div * (float) i;
            posX = player.getPosX() + (float) Math.cos(offset) * RADIUS;
            posY = player.getPosY() + (float) Math.sin(offset) * RADIUS;
            enemy = Bat.get(posX, posY, player);

            // Boundary 안에 있는지 체크
            RectF collider = new RectF();
            float left = posX - SpriteSize.BAT_SIZE / 2;
            float top = posY - SpriteSize.BAT_SIZE / 2;
            float right = posX + SpriteSize.BAT_SIZE / 2;
            float bottom = posY + SpriteSize.BAT_SIZE / 2;
            collider.set(left, top, right, bottom);
            if (Object.boundary.contains(collider)) {
                numofSpawnedEnemies++;
                Log.v(TAG, "Spawn Enemy (" + posX + ", " + posY + ")");
                scene.add(MainScene.Layer.enemy, enemy);
            } else {
                Log.v(TAG, "Enemy is out of bound");
            }
        }
    }

    private float getRandomPos(Random rand, MainScene scene, boolean isPosX) {
        Player player = scene.getPlayer();
        float pos;
        if (isPosX) {
            float width = Object.boundary.right - Object.boundary.left;
            pos = rand.nextFloat() * (width * 0.8f) + width * 0.1f + Object.boundary.left;
            float min = scene.getCamera().getPosX() - Metrics.game_width;
            float max = scene.getCamera().getPosX() + Metrics.game_width;
            if (pos > min && pos < max) {
                if (pos < player.getPosX() && pos > Object.boundary.top + width * 0.1f) pos = min;
                else pos = max;
            }
        } else {
            float height = Object.boundary.bottom - Object.boundary.top;
            pos = rand.nextFloat() * (height * 0.8f) + height * 0.1f + Object.boundary.top;
            float min = scene.getCamera().getPosY() - Metrics.game_height;
            float max = scene.getCamera().getPosY() + Metrics.game_height;
            if (pos > min && pos < max) {
                if (pos < player.getPosY() && pos > Object.boundary.top + height * 0.1f) pos = min;
                else pos = max;
            }
        }
        return pos;
    }

    public int getEnemyNum() {
        return numofSpawnedEnemies;
    }

    public void enemyDestroyed() {
        numofSpawnedEnemies--;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    public int getWave() {
        return wave;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
