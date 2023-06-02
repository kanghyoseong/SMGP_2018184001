package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.BuildConfig;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util.BaseScene;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.DebugFlag;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.scene.MainScene;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public Context context;
    public static Resources res;
    private long previousNanos = 0;
    public static float frameTime = 0;
    public static Paint borderPaint;
    public static Paint colliderPaint;
    private Paint fpsPaint;
    private boolean isRunning = true;
    public static GameView view;
    public static String packageName;

    public GameView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public static void clear() {
        view = null;
        res = null;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.screenWidth = w;
        Metrics.screenHeight = h;
        float viewRatio = (float) w / (float) h;
        float gameRatio = Metrics.game_width / Metrics.game_height;
        if (viewRatio < gameRatio) { // viewRatio의 세로가 더 클 때
            Metrics.x_offset = 0;
            Metrics.y_offset = (int) (h - w / gameRatio) / 2;
            Metrics.scale = w / Metrics.game_width;
        } else { // viewRatio의 가로가 더 클 때
            Metrics.x_offset = (int) (w - h * gameRatio) / 2;
            Metrics.y_offset = 0;
            Metrics.scale = h / Metrics.game_height;
        }
        //Log.d(TAG, "x_offset: "+Metrics.x_offset+", y_offset"+Metrics.y_offset);
        BaseScene.initScenePaints();
        initPaints();
    }

    private void init(AttributeSet attrs, int defStyle) {
        res = getResources();
        view = this;
        initPaints();
        Choreographer.getInstance().postFrameCallback(this);
        Sound.initSoundVolume();
    }

    private void initPaints() {
        if (BuildConfig.DEBUG) {
            if (fpsPaint == null) fpsPaint = new Paint();
            fpsPaint.setColor(Color.WHITE);
            fpsPaint.setTextSize(Metrics.screenWidth * 0.05f);

            if (borderPaint == null) borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.01f);

            if (colliderPaint == null) colliderPaint = new Paint();
            colliderPaint.setColor(Color.YELLOW);
            colliderPaint.setStyle(Paint.Style.STROKE);
            colliderPaint.setStrokeWidth(0.005f);
        }
    }

    @Override
    public void doFrame(long curNanos) {
        if (previousNanos != 0) {
            long elapsedNanos = curNanos - previousNanos;
            frameTime = elapsedNanos / 1_000_000_000f;
            BaseScene scene = BaseScene.getTopScene();
            if (BuildConfig.DEBUG) {
                frameTime *= DebugFlag.FRAMETIME_MULTIPLIER;
            }
            if (scene != null) scene.update(frameTime);
        }
        previousNanos = curNanos;
        //Log.d(TAG, "FrameTime: " + String.valueOf(frameTime));
        invalidate();
        if (isRunning) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(Metrics.x_offset, Metrics.y_offset);
        canvas.scale(Metrics.scale, Metrics.scale);
        BaseScene scene = BaseScene.getTopScene();
        if (scene != null) {
            scene.draw(canvas);
        }
        if (BuildConfig.DEBUG && DebugFlag.DRAW_SCREENBORDER) {
            canvas.drawRect(0, 0, Metrics.game_width, Metrics.game_height, borderPaint);
        }
        canvas.restore();
        if (BuildConfig.DEBUG && DebugFlag.DRAW_GAMEINFO && frameTime > 0) {
            if (scene instanceof MainScene) {
                int fps = (int) (1.0f / frameTime);
                int wave = (((MainScene) scene).enemyGenerator).getWave();
                float time = (((MainScene) scene).enemyGenerator).getElapsedTime();
                canvas.drawText("FPS: " + fps + ", Wave: " + wave + ", Time: " + String.format("%.1f", time)
                        , 100f, 150f, fpsPaint);
                canvas.drawText("Obj: " + BaseScene.getTopScene().getObjectCount()
                        , 100f, 250f, fpsPaint);
                Player p = MainScene.player;
                if (p != null) {
                    int curLevel = p.getLevel();
                    int curExp = p.getCurExp();
                    int enemyNum = ((MainScene) scene).enemyGenerator.getEnemyNum();
                    canvas.drawText("Level: " + curLevel + ", Exp: " + curExp + ", Enemy Num: " + enemyNum
                            , 100f, 350f, fpsPaint);
                    int killedEnemy = MainScene.player.getNumofKilledEnemies();
                    canvas.drawText("Killed: " + killedEnemy, 100f, 450f, fpsPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        BaseScene scene = BaseScene.getTopScene();
        if (scene != null) {
            boolean handled = scene.onTouchEvent(event);
            if (handled) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean handleBackKey() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null) return false;
        return scene.handleBackKey();
    }

    public void pauseGame() {
        isRunning = false;
        BaseScene.getTopScene().pauseScene();
    }

    public void resumeGame() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        BaseScene.getTopScene().resumeScene();
        previousNanos = 0;
        Choreographer.getInstance().postFrameCallback(this);
    }

    public static void toGameScale(Canvas canvas) {
        // Screen Scale -> Game Scale
        canvas.translate(Metrics.x_offset, Metrics.y_offset);
        canvas.scale(Metrics.scale, Metrics.scale);
    }

    public static void toScreenScale(Canvas canvas) {
        // Game Scale -> Screen Scale
        canvas.scale(1f / Metrics.scale, 1f / Metrics.scale);
        canvas.translate(-Metrics.x_offset, -Metrics.y_offset);
    }
}
