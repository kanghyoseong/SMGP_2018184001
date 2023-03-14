package kr.ac.tukorea.www.smgp2018184001.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();//MainActivity 클래스 Simple 이름
    int curPage = 1;
    private ImageView mainImageView;
    private TextView pageTextView;
    private TextView prevBtn;
    private TextView nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        setContentView(R.layout.activity_main);
        //setContentView 후에 findViewById 실행 한다.
        mainImageView = findViewById(R.id.mainImageView);
        pageTextView = findViewById(R.id.pageTextView);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn.setEnabled(false);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev Pressed");
        setPage(curPage - 1);
    }

    public void onBtnNext(View view) {
        Log.d(TAG, "Next Pressed");
        setPage(curPage + 1);
    }

    private static final int [] IMG_RES_IDS = new int[]{
            R.mipmap.cat_1,
            R.mipmap.cat_2,
            R.mipmap.cat_3,
            R.mipmap.cat_4,
            R.mipmap.cat_5,
            R.mipmap.cat_6,
    };

    private void setPage(int page){
        if(page < 1 || page > IMG_RES_IDS.length) return;
        curPage = page;
        int resId = IMG_RES_IDS[curPage - 1];
        mainImageView.setImageResource(resId);
        pageTextView.setText(curPage + " / " + IMG_RES_IDS.length);

        prevBtn.setEnabled(curPage > 1);
        nextBtn.setEnabled(curPage < IMG_RES_IDS.length);
    }
}