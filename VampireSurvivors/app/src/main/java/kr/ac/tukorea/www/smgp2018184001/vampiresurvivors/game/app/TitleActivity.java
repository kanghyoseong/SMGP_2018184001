package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;

public class TitleActivity extends AppCompatActivity {
    public static boolean isSoundClassLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, MainActivity.class));
        isSoundClassLoaded = true;
    }

    public void onBtnSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onBtnExit(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}