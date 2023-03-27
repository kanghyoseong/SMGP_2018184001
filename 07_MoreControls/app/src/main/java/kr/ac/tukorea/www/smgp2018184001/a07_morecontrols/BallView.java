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
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        canvas.drawRect(paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight, paint);
        canvas.drawText("Hello, Custom View", paddingLeft + 100, paddingTop + 100, paint);

        //cx, cy: center x, y
        int cx = paddingLeft + contentWidth / 2;
        int cy = paddingTop + contentHeight / 2;
        //공 반지름 = 화면 폭 / 8
        int radius = contentWidth / 8;
        ballRect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawBitmap(ballBitmap, null, ballRect, null);
    }
}