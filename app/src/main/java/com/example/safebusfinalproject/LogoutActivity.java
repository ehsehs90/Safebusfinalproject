package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        final Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            String result;

            @Override
            public void onClick(View v) {
                try {

                    LogoutActivity.Logout logout = new LogoutActivity.Logout();
                    result = logout.execute().get();

                    if (result.equals("logout")){
                        Log.i("DBtest","토스트");
                        Toast.makeText(LogoutActivity.this,
                                "로그아웃 성공!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("DBtest","토스트실패");
                        Toast.makeText(LogoutActivity.this,
                                "로그아웃 실패!",
                                Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent();
                    ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                            "com.example.safebusfinalproject.LoginActivity");
                    i.setComponent(cname);

                    startActivity(i);


                }catch (Exception e){
                    Log.i("DBtest", "Logout Error!");
                }

            }
        });
    }

    class Logout extends AsyncTask<Void, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String str;

                //URL url = new URL("http://70.12.115.78:80/bustest2/logout.do");
                URL url = new URL("http://70.12.115.78:80/safebus/logout.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    // jsp에서 보낸 값을 받는 부분
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("DBtest",receiveMsg);

                } else {
                    // 통신 실패
                    Log.i("error",receiveMsg);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }



    }
}
