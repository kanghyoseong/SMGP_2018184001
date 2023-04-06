package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Player {
    // Sprite Animation
    private Bitmap bitmapFrame[];
    private int curFrame = 1; // starts from 1
    private int frameCount = 1;
    private float secToNextFrame = 0.2f;
    int spriteCountX, spriteCountY;
    private float elapsedTime = 0;

    // Draw Information
    private RectF dstRect = new RectF();
    float posX, posY;
    float sizeX, sizeY;

    // Game Information
    private int level = 1;
    private int expToLevelUp = 5;
    private int expToLevelUp_increment = 10;
    private int hp = 20;
    private int hp_increment = 2;
    private float movementSpeed = 1.0f;
    //private Item items[];

    public Player(float posX, float posY, float sizeX, float sizeY, int resId, int spriteCountX, int spriteCountY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.spriteCountX = spriteCountX;
        this.spriteCountY = spriteCountY;
        setBitmapList(resId, spriteCountX, spriteCountY);
        reconstructRect();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmapFrame[curFrame - 1], null, dstRect, null);
    }

    private void toNextFrame() {
        curFrame++;
        if (curFrame > frameCount) curFrame = 1;
    }

    public void move(float dx, float dy) {
        posX += dx * GameView.frameTime;
        posY += dy * GameView.frameTime;
        reconstructRect();
    }

    private void reconstructRect() {
        dstRect.set(posX - sizeX / 2, posY - sizeY / 2, posX + sizeX / 2, posY + sizeY / 2);
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

    public void update(float eTime) {
        elapsedTime += eTime;
        if (elapsedTime > secToNextFrame) {
            toNextFrame();
            elapsedTime = 0;
        }
    }
}
