package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    private static HashMap<Integer, Bitmap[]> bitmapFrames = new HashMap<>();
    private static HashMap<Integer, Bitmap[]> bitmapFrames_inverted = new HashMap<>();
    private static BitmapFactory.Options opts;

    static {
        opts = new BitmapFactory.Options();
        opts.inScaled = false;
    }

    public static Bitmap get(int mipmapResId, boolean isAnimation) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            Resources res = GameView.res;
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }

    public static Bitmap[] getAnimation(int mipmapResId, int spriteCountX, int spriteCountY) {
        Bitmap[] bitmaps = bitmapFrames.get(mipmapResId);
        if (bitmaps == null) {
            bitmaps = setBitmapList(mipmapResId, spriteCountX, spriteCountY);
            bitmapFrames.put(mipmapResId, bitmaps);
        }
        return bitmaps;
    }

    public static Bitmap[] getAnimation_Inverted(int mipmapResId, int spriteCountX, int spriteCountY) {
        Bitmap[] bitmaps = bitmapFrames_inverted.get(mipmapResId);
        if (bitmaps == null) {
            bitmaps = makeInvertedBitmap(mipmapResId, spriteCountX, spriteCountY);
            bitmapFrames_inverted.put(mipmapResId, bitmaps);
        }
        return bitmaps;
    }

    private static Bitmap[] setBitmapList(int resId, int countX, int countY) {
        int frameCount = countX * countY;
        Bitmap bitmap = BitmapFactory.decodeResource(GameView.res, resId, opts);
        //Log.d(null, "Decode " + resId);
        Bitmap[] bitmapFrame = new Bitmap[frameCount];
        int spriteWidth = bitmap.getWidth() / countX;
        int spriteHeight = bitmap.getHeight() / countY;
        int count = 0;
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                bitmapFrame[count] = Bitmap.createBitmap(bitmap, x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                count++;
            }
        }
        return bitmapFrame;
    }

    private static Bitmap[] makeInvertedBitmap(int resId, int countX, int countY) {
        int frameCount = countX * countY;
        Bitmap[] bitmapFrame = getAnimation(resId, countX, countY);
        Bitmap[] bitmapFrame_inverted = new Bitmap[frameCount];

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);
        for (int i = 0; i < frameCount; i++) {
            bitmapFrame_inverted[i] = Bitmap.createBitmap(bitmapFrame[i], 0, 0,
                    bitmapFrame[i].getWidth(), bitmapFrame[i].getHeight(), matrix, true);
        }
        return bitmapFrame_inverted;
    }
}
