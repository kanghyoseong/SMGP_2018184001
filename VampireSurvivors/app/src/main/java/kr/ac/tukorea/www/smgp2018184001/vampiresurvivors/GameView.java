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

    private void init(AttributeSet attrs, int defStyle) {
        res = getResources();
        player = new Player(5, 8, 4, 4, R.mipmap.player_anim_4x1, 4, 1);

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
        Log.d(TAG, "FrameTime: " + String.valueOf(frameTime));
        invalidate();
        if (isShown()) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    private void update(float frameTime) {
        player.move(1.f, 0);
        player.update(frameTime);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scale = getWidth() / 9.0f;
        canvas.scale(scale, scale);
        player.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                movePlayer(100, 0);
        }
        return super.onTouchEvent(event);
    }

    public void movePlayer(int dx, int dy) {
        player.move(dx, dy);
    }
}
