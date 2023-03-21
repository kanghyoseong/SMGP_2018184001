package kr.ac.tukorea.www.smgp2018184001.a06_cardgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] resIds = new int[]{
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_kd, R.mipmap.card_qh,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_kd, R.mipmap.card_qh,
    };
    private static final int[] BUTTON_IDS = new int[]{
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33,
    };
    private static HashMap<Integer, Integer> idMap;

    static {
        idMap = new HashMap<>();
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            idMap.put(BUTTON_IDS[i], i);
        }
    }

    private static int getIndexWithId(int id) {
        Integer index = idMap.get(id);
        if (index == null) {
            Log.e(TAG, "Cannot Find The Button With ID " + id);
            return -1; // 있어서는 안되는 상황
        }
        return index;//알아서 int로 변환해줌
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById((R.id.scoreTextView));
        startGame();
    }

    private void startGame(){
        setFlips(0);
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            btn.setTag(resIds[i]);
            btn.setVisibility(View.VISIBLE);
            btn.setImageResource(R.mipmap.card_blue_back);
        }
        openCardCount = BUTTON_IDS.length;
    }

    private ImageButton previousImgButton; // 마지막으로 눌렀던 버튼
    private TextView scoreTextView;
    private int flips = 0;
    private int openCardCount = 0;

    public void onBtnCard(View view) {
        Log.d(TAG, "Card ID = " + view.getId());
        int cardIndex = getIndexWithId(view.getId());
        Log.d(TAG, "Card Index = " + cardIndex);

        ImageButton btn = (ImageButton) view;
        if (btn == previousImgButton) {
            //같은 카드가 눌리면 Toast를 보여준다.
            Toast.makeText(this, "Same Card", Toast.LENGTH_SHORT).show();
            return;
        }
        int resId = (Integer) btn.getTag();
        btn.setImageResource(resId);

        if (previousImgButton != null) {
            int prevResId = (Integer) previousImgButton.getTag();

            if (resId == prevResId) {
                btn.setVisibility(View.INVISIBLE);
                previousImgButton.setVisibility((View.INVISIBLE));
                previousImgButton = null;
                openCardCount -= 2;
                if (openCardCount <= 0) {
                    askRetry();
                }
                return;
            } else {
                previousImgButton.setImageResource(R.mipmap.card_blue_back);
            }
        }
        setFlips(flips + 1);
        previousImgButton = btn;
    }

    private void setFlips(int flips) {
        this.flips = flips;
        scoreTextView.setText("Flips: " + flips);
    }

    public void onBtnRestart(View view) {
        askRetry();
    }

    private void askRetry() {
        new AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Do you really want to restart the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Restart Here");
                        startGame();
                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}