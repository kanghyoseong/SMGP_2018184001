package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.databinding.ActivitySettingsBinding;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res.Sound;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private SharedPreferences preferences;
    protected static SoundPool soundPool;
    private int soundId = -1;
    private float volume_bgm, volume_sfx;

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
                int progress = seekBar.getProgress();
                editor.putInt("volume_bgm", progress);
                editor.commit();
                volume_bgm = progress / 100.0f;
                playEffect(R.raw.getexp, volume_bgm);
                if (TitleActivity.isSoundClassLoaded) {
                    Sound.setVolume_bgm(seekBar.getProgress());
                }
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
                int progress = seekBar.getProgress();
                editor.putInt("volume_sfx", progress);
                editor.commit();
                volume_sfx = progress / 100.0f;
                playEffect(R.raw.getexp, volume_sfx);
                if (TitleActivity.isSoundClassLoaded) {
                    Sound.setVolume_sfx(seekBar.getProgress());
                }
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

    private void playEffect(int resId, float volume) {
        SoundPool pool = getSoundPool();
        if (soundId == -1) {
            soundId = pool.load(this, resId, 1);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                pool.play(soundId, volume, volume, 1, 0, 1f);
            }
        });
        pool.play(soundId, volume, volume, 1, 0, 1f);
    }

    private SoundPool getSoundPool() {
        if (soundPool != null) return soundPool;

        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attrs)
                .setMaxStreams(8)
                .build();
        return soundPool;
    }
}