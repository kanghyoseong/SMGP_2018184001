package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.util.Random;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {
    private static final String TAG = TitleActivity.class.getSimpleName();
    public static boolean isSoundClassLoaded = false;
    private ActivityTitleBinding binding;
    public static String name = "";
    private SharedPreferences preferences;
    public static SharedPreferences preferences_score;
    private static final String NAME_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NAME_NUMBERS = "0123456789";
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            name = s.toString();
            preferences = getSharedPreferences("playerName", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", name);
            editor.commit();
            Log.v(TAG, "Commit String: " + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        binding.editTextName.addTextChangedListener(textWatcher);
        //name = getRandomName();
        // get name from sharedPreference
        preferences = getSharedPreferences("playerName", MODE_PRIVATE);
        name = preferences.getString("name", "ab123");
        Log.v(TAG, "Name: " + name);
        binding.editTextName.setText(name);
        preferences_score = getSharedPreferences("score", MODE_PRIVATE);
        setContentView(binding.getRoot());
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

    public void onBtnScore(View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    private String getRandomName() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 2; ++i)
            sb.append(NAME_ALPHABETS.charAt(random.nextInt(NAME_ALPHABETS.length())));
        for (int i = 0; i < 3; ++i)
            sb.append(NAME_NUMBERS.charAt(random.nextInt(NAME_NUMBERS.length())));
        return sb.toString();
    }
}