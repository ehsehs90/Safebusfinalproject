package com.example.safebusfinalproject;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.trash.Login2Activity;
import com.example.safebusfinalproject.trash.Login3Activity;
import com.example.safebusfinalproject.trash.Login4Activity;
import com.example.safebusfinalproject.LoginActivity;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingBtn = (Button)findViewById(R.id.settingBtn);
        Button settingloginBtn = (Button)findViewById(R.id.settingloginBtn);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        Button seatBtn = (Button)findViewById(R.id.seatBtn);
        Button messageBtn = (Button)findViewById(R.id.messageBtn);
        Button login3Btn = (Button)findViewById(R.id.login3Btn);
        Button mapBtn = (Button)findViewById(R.id.mapBtn);
        Button businfoBtn = (Button)findViewById(R.id.businfoBtn);
//        Button goBtn = (Button)findViewById(R.id.goBtn);
        Button login5Btn = (Button)findViewById(R.id.login5Btn);
        Button login6Btn = (Button)findViewById(R.id.login6Btn);
        Button kungkang = (Button)findViewById(R.id.kungkang);

        //kungkang


//        goBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent();
//                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
//                        "com.example.safebusfinalproject.trash.PredictImageActivity");
//                i.setComponent(cname);
//                startActivity(i);
//
//            }
//        });
        kungkang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, BusinfoActivity2.class);
                startActivity(i);

            }
        });

        // (Android의 전형적인 event처리방식)
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, ActSettings.class);
                startActivity(i);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        seatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, SeatActivity.class);
                startActivity(i);

            }
        });

        settingloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, Login2Activity.class);
                startActivity(i);

            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, SendMSGActivity.class);
                startActivity(i);

            }
        });

        login3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, Login3Activity.class);
                startActivity(i);

            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, MapViewActivity.class);
                startActivity(i);

            }
        });

        businfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, BusinfoActivity.class);
                startActivity(i);

            }
        });
        //버튼 ->  리스너

//        login4Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 버튼을 눌렀을 때 서비스를 생성하고 실행.
//
//                Intent i = new Intent(MainActivity.this, Login4Activity.class);
//                startActivity(i);
//
//            }
//        });

        login5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, Login5Activity.class);
                startActivity(i);

            }
        });

        login6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

                Intent i = new Intent(MainActivity.this, Login6Activity.class);
                startActivity(i);

            }
        });


    }


}