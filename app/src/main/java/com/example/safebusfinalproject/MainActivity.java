package com.example.safebusfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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



        //버튼 ->  리스너


    }


}