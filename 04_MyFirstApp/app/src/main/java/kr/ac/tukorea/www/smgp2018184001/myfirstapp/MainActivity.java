package kr.ac.tukorea.www.smgp2018184001.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R.layout.activity_main 상수가 알아서 만들어짐,
        // 여기서는130만
    }
}