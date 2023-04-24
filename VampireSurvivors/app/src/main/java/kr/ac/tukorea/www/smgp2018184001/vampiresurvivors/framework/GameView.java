package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

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
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.EnemyGenerator;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.MainScene;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static Resources res;
    private long previousNanos = 0;
    public static float frameTime = 0;
    public static Paint borderPaint;
    public static Paint colliderPaint;
    private Paint fpsPaint;
    private boolean isRunning = true;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
    }

    private void init(AttributeSet attrs, int defStyle) {
        Metrics.setGameSize(1, 1);
        res = getResources();

        if (BuildConfig.DEBUG) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.WHITE);
            fpsPaint.setTextSize(90.0f);

            borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.01f);

            colliderPaint = new Paint();
            colliderPaint.setColor(Color.YELLOW);
            colliderPaint.setStyle(Paint.Style.STROKE);
            colliderPaint.setStrokeWidth(0.005f);
        }

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long curNanos) {
        if (previousNanos != 0) {
            long elapsedNanos = curNanos - previousNanos;
            frameTime = elapsedNanos / 1_000_000_000f;
            BaseScene scene = BaseScene.getTopScene();
            scene.update(frameTime);
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
        if (BuildConfig.DEBUG) {
            canvas.drawRect(0, 0, Metrics.game_width, Metrics.game_height, borderPaint);
        }
        canvas.restore();
        if (BuildConfig.DEBUG && frameTime > 0) {
            int fps = (int) (1.0f / frameTime);
            int wave = EnemyGenerator.wave;
            float time = EnemyGenerator.elapsedTime;
            canvas.drawText("FPS: " + fps + ", Wave: " + wave + ", Time: " + time
                    , 100f, 150f, fpsPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = BaseScene.getTopScene().onTouchEvent(event);
        if (handled) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void pauseGame() {
        isRunning = false;
    }

    public void resumeGame() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        previousNanos = 0;
        Choreographer.getInstance().postFrameCallback(this);
    }
}
