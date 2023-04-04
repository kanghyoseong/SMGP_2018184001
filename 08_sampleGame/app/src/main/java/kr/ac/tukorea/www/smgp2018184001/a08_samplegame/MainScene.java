package kr.ac.tukorea.www.smgp2018184001.a08_samplegame;

import android.view.MotionEvent;

import java.util.Random;

public class MainScene extends BaseScene {
    private Fighter fighter;

    public MainScene() {
        Metrics.setGameSize(10,10);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            float dx = r.nextFloat() * 5f + 3f; // 초당 움직이는 속도
            float dy = r.nextFloat() * 5f + 3f; // 초당 움직이는 속도
            add(new Ball(dx, dy));
        }
        fighter = new Fighter();
        add(fighter);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float scale = Metrics.scale;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = (float) event.getX() / scale;
                float y = (float) event.getY() / scale;
                fighter.setTargetPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
