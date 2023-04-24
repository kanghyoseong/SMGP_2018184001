package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.MainScene;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        new MainScene().pushScene();
    }

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        BaseScene.popAll();
        super.onDestroy();
    }
}