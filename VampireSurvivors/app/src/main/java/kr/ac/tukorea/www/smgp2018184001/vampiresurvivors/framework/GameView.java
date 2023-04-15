package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Camera;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Joystick;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.Player;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static Resources res;
    private Player player;
    public static Camera camera;
    private Object background;
    private Joystick joystick;
    private long previousNanos = 0;
    public static float frameTime = 0;

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
            Metrics.y_offset = (int) ((h - w) / gameRatio) / 2;
            Metrics.scale = w / Metrics.game_width;
            player.setPos(0.5f, h / Metrics.scale / 2.f);
        } else { // viewRatio의 가로가 더 클 때
            Metrics.x_offset = (int) ((w - h) * gameRatio) / 2;
            Metrics.y_offset = 0;
            Metrics.scale = h / Metrics.game_height;
            player.setPos(w / Metrics.scale / 2.f, 0.5f);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        Metrics.setGameSize(1, 1);
        res = getResources();
        player = new Player(0, 0, 0.12f, 0.12f,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        camera = new Camera(player);
        joystick = new Joystick();
        background = new Object(0, 0, 8.0f, 8.0f, R.mipmap.background);

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
        background.update(frameTime);
        joystick.update(player);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(Metrics.scale, Metrics.scale);
        background.draw(canvas);
        player.draw(canvas);
        joystick.draw(canvas);
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
