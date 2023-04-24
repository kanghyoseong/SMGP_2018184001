package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public class AnimatedSprite extends Sprite {
    private Bitmap bitmapFrame[];
    private Bitmap bitmapFrame_inverted[];
    private int curFrame = 1; // starts from 1
    private int frameCount = 0;
    private float secToNextFrame;
    int spriteCountX, spriteCountY;
    int spriteWidth, spriteHeight;
    private float elapsedTime = 0;
    private boolean isDirLeft = true; // 모든 애니메이션 스프라이트가 보는 방향은 왼쪽이어야 한다.
    private boolean isInvincible = false;
    public static Paint PaintHit = new Paint();

    static {
        ColorFilter cf = new PorterDuffColorFilter(0x88FF0000, PorterDuff.Mode.SRC_ATOP);
        PaintHit.setColorFilter(cf);
    }

    public AnimatedSprite(int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super();
        this.secToNextFrame = secToNextFrame;
        this.spriteCountX = spriteCountX;
        this.spriteCountY = spriteCountY;
        setBitmapList(resId, spriteCountX, spriteCountY);
    }

    public void update(float eTime) {
        if (secToNextFrame > 0) {
            elapsedTime += eTime;
            if (elapsedTime > secToNextFrame) {
                toNextFrame();
                elapsedTime = 0;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (isDirLeft) {
            if (isInvincible)
                canvas.drawBitmap(bitmapFrame[curFrame - 1], null, dstRect, PaintHit);
            else
                canvas.drawBitmap(bitmapFrame[curFrame - 1], null, dstRect, null);

        } else {
            if (isInvincible)
                canvas.drawBitmap(bitmapFrame_inverted[curFrame - 1], null, dstRect, PaintHit);
            else
                canvas.drawBitmap(bitmapFrame_inverted[curFrame - 1], null, dstRect, null);
        }
    }

    private void toNextFrame() {
        curFrame++;
        if (curFrame > frameCount) curFrame = 1;
    }

    public void setBitmapList(int resId, int countX, int countY) {
        frameCount = countX * countY;
        Bitmap bitmap = BitmapFactory.decodeResource(GameView.res, resId);
        bitmapFrame = new Bitmap[frameCount];
        spriteWidth = bitmap.getWidth() / countX;
        spriteHeight = bitmap.getHeight() / countY;
        int count = 0;
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                bitmapFrame[count] = Bitmap.createBitmap(bitmap, x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                count++;
            }
        }
    }

    public void makeInvertedBitmap() {
        bitmapFrame_inverted = new Bitmap[frameCount];
        if (bitmapFrame != null) {
            for (int i = 0; i < frameCount; i++) {
                Matrix matrix = new Matrix();
                matrix.postScale(-1, 1);
                bitmapFrame_inverted[i] = Bitmap.createBitmap(bitmapFrame[i], 0, 0, spriteWidth, spriteHeight, matrix, true);
            }
        }
    }

    public void setIsDirLeft(boolean isDirLeft) {
        this.isDirLeft = isDirLeft;
    }

    public void setIsInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }
}
