package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingBtn = (Button)findViewById(R.id.settingBtn);
        Button settingloginBtn = (Button)findViewById(R.id.settingloginBtn);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        Button seatBtn = (Button)findViewById(R.id.seatBtn);

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





        //버튼 ->  리스너


    }


}