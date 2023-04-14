package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static Resources res;
    private Player player;
    private Joystick joystick;
    private long previousNanos = 0;
    public static float frameTime = 0;
    public static float scale;

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
        //Log.d(TAG, "Ratio: " + String.valueOf(viewRatio));
        if (viewRatio < 1) { // h > w
            scale = w;
            player.setPos(0.5f, h / scale / 2.f);
            //Log.d(TAG, "w: " + String.valueOf(w) + ", h: " + String.valueOf(h) + ", Scale: " + String.valueOf(scale));
            //Log.d(TAG, String.valueOf(h / scale / 2.f));
        } else { // h <= w
            scale = h;
            player.setPos(w / scale / 2.f, 0.5f);
            //Log.d(TAG, "w: " + String.valueOf(w) + ", h: " + String.valueOf(h) + ", Scale: " + String.valueOf(scale));
            //Log.d(TAG, String.valueOf(w / scale / 2.f));
        }

    }

    private void init(AttributeSet attrs, int defStyle) {
        res = getResources();
        player = new Player(5, 5, 0.12f, 0.12f,
                R.mipmap.player_anim_4x1, 4, 1, 0.2f);
        joystick = new Joystick();

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
        //player.move(1.f, 0);
        player.update(frameTime);
        joystick.update(player);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(scale, scale);
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

    public void movePlayer(int dx, int dy) {
        player.move(dx, dy);
    }
}
