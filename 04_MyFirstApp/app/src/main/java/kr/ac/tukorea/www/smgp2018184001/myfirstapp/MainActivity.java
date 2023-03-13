package kr.ac.tukorea.www.smgp2018184001.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//1번방법에사용implements View.OnClickListener {

    //멤버변수 선언
   /* private View.OnClickListener pushMeEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv = findViewById(R.id.studentNum);
            tv.setText("push me");
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R.layout.activity_main 상수가 알아서 만들어짐,
        // 여기서는130만

        Button btn = findViewById(R.id.pushMeButton);
        Button btn2 = findViewById(R.id.pushMe2Button);

        //click이 되면 입력인자에게 알려줘라, 4가지 방식
        //btn.setOnClickListener(this);//1. 나에게 알려주기, 클릭에 대응할 수 있어야한다.
        //btn2.setOnClickListener(this);

        //2. 나에게 알려주지 말고 나의 멤버변수에게 알려줘
        // btn.setOnClickListener(pushMeEvent);//1. 나에게 알려주기, 클릭에 대응할 수 있어야한다.

        //3. 클릭이 되면 객체 만들어 오출
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = findViewById(R.id.studentNum);
                tv.setText("push me");
            }
        });*/
    }

    /*
        @Override
        public void onClick(View view) {
            //런타임에 디자인 결정
            TextView tv;
            switch(view.getId()){
                case R.id.pushMeButton:
                    tv = findViewById(R.id.studentNum);
                    tv.setText("push me"); // textview의 레퍼런스를 구해서 텍스트 속성을 변경함
                    break;
                case R.id.pushMe2Button:
                    tv = findViewById(R.id.studentNum);
                    tv.setText("push me 2"); // textview의 레퍼런스를 구해서 텍스트 속성을 변경함
                    break;
            }
        }*/
    public void onBtnPushMe(View view) {
        TextView tv = findViewById(R.id.studentNum);
        tv.setText("4th push me");
    }

    public void onBtnPushMe2(View view) {
        TextView tv = findViewById(R.id.studentNum);
        tv.setText("4th push me 2");
    }
}

