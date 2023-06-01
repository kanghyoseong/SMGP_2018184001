package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.res;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view.GameView;

public class Sound {
    private static HashMap<Integer, Integer> soundIdMap = new HashMap<>();
    protected static MediaPlayer mediaPlayer;
    protected static SoundPool soundPool;
    public static float volume_bgm;
    public static float volume_sfx;

    public static void initSoundVolume() {
        SharedPreferences preferences = GameView.view.context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        volume_bgm = (float) preferences.getInt("volume_bgm", 100) / 100.0f;
        volume_sfx = (float) preferences.getInt("volume_sfx", 100) / 100.0f;
    }

    public static void playMusic(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), resId);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(volume_bgm, volume_bgm);
        mediaPlayer.start();
    }

    public static void stopMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.stop();
        mediaPlayer = null;
    }

    public static void pauseMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
    }

    public static void resumeMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
    }

    public static void playEffect(int resId) {
        SoundPool pool = getSoundPool();
        int soundId;
        if (soundIdMap.containsKey(resId)) {
            soundId = soundIdMap.get(resId);
        } else {
            soundId = pool.load(GameView.view.getContext(), resId, 1);
            soundIdMap.put(resId, soundId);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    pool.play(soundId, volume_sfx, volume_sfx, 1, 0, 1f);
                }
            });
        }
        pool.play(soundId, volume_sfx, volume_sfx, 1, 0, 1f);
    }

    /**
     * @param progress: 0 ~ 100, integer
     */
    public static void setVolume_bgm(int progress) {
        volume_bgm = (float) progress / 100.0f;
        mediaPlayer.setVolume(volume_bgm, volume_bgm);
    }

    /**
     * @param progress: 0 ~ 100, integer
     */
    public static void setVolume_sfx(int progress) {
        volume_sfx = (float) progress / 100.0f;
    }

    private static SoundPool getSoundPool() {
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
