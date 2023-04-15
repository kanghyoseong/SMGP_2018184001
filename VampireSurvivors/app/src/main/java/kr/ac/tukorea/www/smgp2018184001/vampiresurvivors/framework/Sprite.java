package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.GameView;

public class Sprite {
    private Bitmap bitmapFrame[];
    private int curFrame = 1; // starts from 1
    private int frameCount = 1;
    private float secToNextFrame;
    int spriteCountX, spriteCountY;
    private float elapsedTime = 0;
    private RectF dstRect;

    public Sprite(int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        this.secToNextFrame = secToNextFrame;
        this.spriteCountX = spriteCountX;
        this.spriteCountY = spriteCountY;
        setBitmapList(resId, spriteCountX, spriteCountY);
    }

    public void update(float eTime) {
        elapsedTime += eTime;
        if (elapsedTime > secToNextFrame) {
            toNextFrame();
            elapsedTime = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmapFrame[curFrame - 1], null, dstRect, null);
    }

    private void toNextFrame() {
        curFrame++;
        if (curFrame > frameCount) curFrame = 1;
    }

    public void setBitmapList(int resId, int countX, int countY) {
        frameCount = countX * countY;
        Bitmap bitmap = BitmapFactory.decodeResource(GameView.res, resId);
        bitmapFrame = new Bitmap[frameCount];
        int width = bitmap.getWidth() / countX;
        int height = bitmap.getHeight() / countY;
        int count = 0;
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                bitmapFrame[count] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height);
                count++;
            }
        }
    }

    public void setDstRect(RectF rect) {
        dstRect = rect;
    }
}
