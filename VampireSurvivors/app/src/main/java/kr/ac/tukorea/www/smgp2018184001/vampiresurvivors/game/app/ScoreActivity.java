package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
    private static final String TAG = ScoreActivity.class.getSimpleName();
    private ActivityScoreBinding binding;
    private TextView[] textView_name;
    private TextView[] textView_score;

    public static Score[] scores = new Score[3];

    static {
        for (int i = 0; i < scores.length; i++) {
            scores[i] = new Score();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        textView_name = new TextView[]{
                binding.name01,
                binding.name02,
                binding.name03,
        };
        textView_score = new TextView[]{
                binding.score01,
                binding.score02,
                binding.score03,
        };
        getScore();
        initializeTextView();
        setContentView(binding.getRoot());
    }

    public void onBtnReturn(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private static void getScore() {
        for (int i = 0; i < scores.length; i++) {
            scores[i].name = TitleActivity.preferences_score.getString("name" + i + 1, "-");
            scores[i].score = TitleActivity.preferences_score.getInt("score" + i + 1, -1);

            Log.v(TAG, "Get Score " + (i + 1) + ": " + scores[i].score);
        }
    }

    private void initializeTextView() {
        for (int i = 0; i < scores.length; i++) {
            textView_name[i].setText(scores[i].name);
            if (scores[i].score == -1) {
                textView_score[i].setText("-");
            } else {
                textView_score[i].setText(String.valueOf(scores[i].score));
            }
        }
    }

    public static void saveScore() {
        getScore();
        if (Score.curScore == 0) return;
        for (int i = 0; i < scores.length; i++) {
            if (Score.curScore > scores[i].score) {
                for (int j = scores.length - 1; j > i; j--) {
                    scores[j].name = scores[j - 1].name;
                    scores[j].score = scores[j - 1].score;
                    Log.v(TAG, (j + 1) + ": " + scores[j].score);
                }
                scores[i].name = TitleActivity.name;
                scores[i].score = Score.curScore;
                break;
            }
        }
        SharedPreferences.Editor editor = TitleActivity.preferences_score.edit();
        for (int i = 0; i < scores.length; i++) {
            Log.v(TAG, "Save Score " + (i + 1) + ": " + scores[i].score);
            editor.putString("name" + i + 1, scores[i].name);
            editor.putInt("score" + i + 1, scores[i].score);
            editor.commit();
        }
    }

    private void resetScores() {
        SharedPreferences.Editor editor = TitleActivity.preferences_score.edit();
        for (int i = 0; i < scores.length; i++) {
            scores[i].name = "-";
            scores[i].score = -1;
            editor.putString("name" + i + 1, "-");
            editor.putInt("score" + i + 1, -1);
            editor.commit();
        }
    }

    public void reset(View view) {
        resetScores();
        initializeTextView();
    }
}