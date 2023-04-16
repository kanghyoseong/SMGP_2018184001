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
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Joystick;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.SpriteSize;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static Resources res;
    private Player player;
    private Object bat;
    public static Camera camera;
    private Object background;
    private Joystick joystick;
    private long previousNanos = 0;
    public static float frameTime = 0;
    private Paint borderPaint;
    private Paint fpsPaint;


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
        player = new Player(0, 0,
                SpriteSize.PLAYER_SIZE, SpriteSize.PLAYER_SIZE,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        bat=new Object(0, 0, 0.1f, 0.1f, R.mipmap.bat, 2, 2, 0.1f);
        camera = new Camera(player);
        joystick = new Joystick();
        background = new Object(0, 0,
                SpriteSize.BACKGROUND_SIZE, SpriteSize.BACKGROUND_SIZE,
                R.mipmap.background);

        if (BuildConfig.DEBUG) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.WHITE);
            fpsPaint.setTextSize(100.0f);

            borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.02f);
        }

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long curNanos) {
        if (previousNanos != 0) {
            long elapsedNanos = curNanos - previousNanos;
            frameTime = elapsedNanos / 1_000_000_000f;
            update(frameTime);
        }
        previousNanos = curNanos;
        //Log.d(TAG, "FrameTime: " + String.valueOf(frameTime));
        invalidate();
        if (isShown()) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    private void update(float frameTime) {
        player.update(frameTime);
        camera.update(player);
        bat.update(frameTime);
        background.update(frameTime);
        joystick.update(player);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(Metrics.x_offset, Metrics.y_offset);
        canvas.scale(Metrics.scale, Metrics.scale);
        background.draw(canvas);
        player.draw(canvas);
        bat.draw(canvas);
        joystick.draw(canvas);
        if (BuildConfig.DEBUG) {
            canvas.drawRect(0, 0, Metrics.game_width, Metrics.game_height, borderPaint);
        }
        canvas.restore();
        if (BuildConfig.DEBUG && frameTime > 0) {
            int fps = (int) (1.0f / frameTime);
            canvas.drawText("FPS: " + fps, 100f, 150f, fpsPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                joystick.touchDown(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                joystick.drag(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                joystick.touchUp();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
