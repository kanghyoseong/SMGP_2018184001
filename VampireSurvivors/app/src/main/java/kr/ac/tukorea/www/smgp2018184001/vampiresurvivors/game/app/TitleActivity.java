package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onBtnSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}