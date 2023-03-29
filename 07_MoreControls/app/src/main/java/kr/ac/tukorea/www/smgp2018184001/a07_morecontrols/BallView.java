package kr.ac.tukorea.www.smgp2018184001.a07_morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BallView extends View {
    private Paint paint = new Paint();
    private Bitmap ballBitmap;
    private RectF ballRect = new RectF();

    public BallView(Context context) {
        super(context);
        init(null, 0);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);

        Resources res = getResources();
        ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        float scale = getWidth() / 10.0f;
        canvas.scale(scale, scale);

        float height = getHeight()/scale;
        float cx = 5.0f, cy = height/2.0f;
        float radius = 1.25f;
        ballRect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawBitmap(ballBitmap, null, ballRect, null);
    }
}