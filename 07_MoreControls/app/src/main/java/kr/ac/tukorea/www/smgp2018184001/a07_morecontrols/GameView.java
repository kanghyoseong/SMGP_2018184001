package kr.ac.tukorea.www.smgp2018184001.a07_morecontrols;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Paint paint, facePaint, outlinePaint;
    private Rect rect;
    private RectF ovalRect;

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
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        facePaint = new Paint();
        facePaint.setColor(Color.YELLOW);

        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
    }

    private void calcSize() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        rect = new Rect(paddingLeft, paddingTop, getWidth() - paddingRight, getHeight() - paddingBottom);
        ovalRect = new RectF(rect.left, rect.top, rect.right, rect.bottom);

        outlinePaint.setStrokeWidth(contentWidth/100f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged:" + w + "," + h);
        calcSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, paint);
        drawSmiley(canvas);
    }
    private void drawSmiley(Canvas canvas){
        canvas.drawOval(ovalRect, facePaint);
        canvas.drawOval(ovalRect, outlinePaint);

        float x1 = ovalRect.centerX() - ovalRect.width() / 6;
        float x2 = ovalRect.centerX() + ovalRect.width() / 6;
        float y1 = ovalRect.centerY() - ovalRect.height() / 6;
        float y2 = ovalRect.centerY() + ovalRect.height() / 6;
        float r = x1 / 10;

        canvas.drawCircle(x1, y1, r, outlinePaint);
        canvas.drawCircle(x2, y1, r, outlinePaint);
        canvas.drawArc(x1, y1, x2, y2, 0, 180, false, outlinePaint);
    }
}