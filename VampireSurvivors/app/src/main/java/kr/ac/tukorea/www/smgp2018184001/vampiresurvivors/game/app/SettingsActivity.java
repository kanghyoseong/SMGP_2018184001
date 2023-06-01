package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onBtnBack(View view) {
        finish();
    }
}