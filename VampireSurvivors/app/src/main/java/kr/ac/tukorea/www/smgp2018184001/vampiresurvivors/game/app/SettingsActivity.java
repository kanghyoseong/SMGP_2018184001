package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initSeekBar();
        binding.seekbarBgm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                preferences = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("volume_bgm", seekBar.getProgress());
                editor.commit();
            }
        });
        binding.seekbarSfx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                preferences = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("volume_sfx", seekBar.getProgress());
                editor.commit();
            }
        });
    }

    private void initSeekBar() {
        preferences = getSharedPreferences("settings", MODE_PRIVATE);
        binding.seekbarBgm.setProgress(preferences.getInt("volume_bgm", 100));
        binding.seekbarSfx.setProgress(preferences.getInt("volume_sfx", 100));
    }

    public void onBtnBack(View view) {
        finish();
    }
}