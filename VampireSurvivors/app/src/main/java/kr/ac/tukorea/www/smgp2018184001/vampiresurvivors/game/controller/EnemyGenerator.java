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
    private float elapsedPatternTime = 0;
    private static Random random = new Random();


    public EnemyGenerator() {
        if (BuildConfig.DEBUG) {
            wave = DebugFlag.START_WAVE;
        }
    }

    @Override
    public void update(float eTime) {
        elapsedTime += eTime;
        elapsedPatternTime += eTime;
        if (elapsedPatternTime > TIME_TO_NEXT_PATTERN) {
            elapsedPatternTime = 0;
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
        BatCircle, Crowd, COUNT;

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
            case Crowd:
                patternCrowd(scene, player);
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

            // Boundary 안에 있는지 체크
            RectF collider = new RectF();
            float left = posX - SpriteSize.BAT_SIZE / 2;
            float top = posY - SpriteSize.BAT_SIZE / 2;
            float right = posX + SpriteSize.BAT_SIZE / 2;
            float bottom = posY + SpriteSize.BAT_SIZE / 2;
            collider.set(left, top, right, bottom);
            if (Object.boundary.contains(collider)) {
                enemy = Bat.get(posX, posY, player);
                enemy.setMovementSpeed(EEnemyType.Bat.getSpeed() * 0.5f);
                //Log.v(TAG, "Spawn Enemy (" + posX + ", " + posY + ")");
                scene.add(MainScene.Layer.enemy, enemy);
                numofSpawnedEnemies++;
            }
//            else {
//                Log.v(TAG, "Enemy is out of bound");
//            }
        }
    }

    private void patternCrowd(BaseScene scene, Player player) {
        Log.v(TAG, "patternCrowd");
        Bat enemy;
        float posX, posY;
        final int COUNT_X = 3;
        final float GAP = 0.06f;

        float offsetX, offsetY;
        float distance = 1.0f;

        int direction = random.nextInt(8);
        Log.v(TAG, "Dir: " + direction);
        switch (direction) {
            default:
            case 0:
                offsetX = -distance;
                offsetY = distance;
                break;
            case 1:
                offsetX = 0;
                offsetY = distance;
                break;
            case 2:
                offsetX = distance;
                offsetY = distance;
                break;
            case 3:
                offsetX = -distance;
                offsetY = 0;
                break;
            case 4:
                offsetX = distance;
                offsetY = 0;
                break;
            case 5:
                offsetX = -distance;
                offsetY = -distance;
                break;
            case 6:
                offsetX = 0;
                offsetY = -distance * -1.0f;
                break;
            case 7:
                offsetX = distance;
                offsetY = -distance * -1.0f;
                break;
        }

        if (!Object.boundary.contains(player.getPosX() + offsetX, player.getPosY() + offsetY)) {
            offsetX *= -1;
            offsetY *= -1;
        }

        float width, height;
        width = height = SpriteSize.BAT_SIZE * COUNT_X + GAP * (COUNT_X - 1);
        for (int i = 0; i < COUNT_X * COUNT_X; i++) {
            int x = i % COUNT_X;
            int y = i / COUNT_X;
            posX = offsetX + player.getPosX() + x * (SpriteSize.BAT_SIZE + random.nextFloat() * GAP);
            posY = offsetY + player.getPosY() + y * (SpriteSize.BAT_SIZE + random.nextFloat() * GAP);

            // Boundary 안에 있는지 체크
            RectF collider = new RectF();
            float left = posX - SpriteSize.BAT_SIZE / 2;
            float top = posY - SpriteSize.BAT_SIZE / 2;
            float right = posX + SpriteSize.BAT_SIZE / 2;
            float bottom = posY + SpriteSize.BAT_SIZE / 2;
            collider.set(left, top, right, bottom);
            if (Object.boundary.contains(collider)) {
                enemy = Bat.get(posX, posY, null);

                enemy.setMovementSpeed(EEnemyType.Bat.getSpeed() * 2.0f);
                float dx = player.getPosX() - (offsetX + width * 0.5f);
                float dy = player.getPosY() - (offsetY + height * 0.5f);
                float length = (float) Math.sqrt(dx * dx + dy * dy);
                if (length > 0.01f) {
                    dx = dx / length;
                    dy = dy / length;
                } else {
                    dx = 1.0f;
                    dy = 0;
                }
                enemy.setDxDy(dx, dy);

                //Log.v(TAG, "Spawn Enemy (" + posX + ", " + posY + ")");
                scene.add(MainScene.Layer.enemy, enemy);
                numofSpawnedEnemies++;
            }
//            else {
//                Log.v(TAG, "Enemy is out of bound");
//            }
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
