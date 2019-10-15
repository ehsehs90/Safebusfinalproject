package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    class LoginDB extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
                //URL url = new URL("http://70.12.115.78:80/bustest2/login.do");
                URL url = new URL("http://70.12.115.78:80/safebus/login.do");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                // 전송할 데이터. GET 방식으로 작성
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1];

                osw.write(sendMsg);
                osw.flush();

                //jsp와 통신 성공 시 수행
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

            //jsp로부터 받은 리턴 값
            return receiveMsg;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText input_ID, input_PW;
        final CheckBox Auto_LogIn;
        final Button loginBtn;
        final TextView registerBtn;

        SharedPreferences setting;
        final SharedPreferences.Editor editor;

        input_ID = (EditText) findViewById(R.id.input_ID);
        input_PW = (EditText) findViewById(R.id.input_PW);
        Auto_LogIn = (CheckBox) findViewById(R.id.Auto_LogIn);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)){
            input_ID.setText(setting.getString("ID", ""));
            input_PW.setText(setting.getString("PW", ""));
            Auto_LogIn.setChecked(true);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String result;
                    String resultLogin;
                    String resultStation;
                    String resultCarnumber;

                    String id = input_ID.getText().toString();
                    String pw = input_PW.getText().toString();

                    LoginDB logindb = new LoginDB();

                    Log.i("DBtest", id );
                    Log.i("DBtest", pw );

                    result = logindb.execute(id, pw).get();

                    //resultLogin = result.substring(0, 7);
                   // Log.i("resultLogin",resultLogin);
                    //resultStation = result.substring(7, 8);
                   // Log.i("resultLogin",resultStation);

                    String[] receiveMsg = result.split("/");
                    resultLogin = receiveMsg[0];
                    Log.i("resultLogin",resultLogin);
                    resultStation = receiveMsg[1];
                    Log.i("resultLogin",resultStation);
                    resultCarnumber = receiveMsg[2];
                    Log.i("resultLogin",resultCarnumber);

                    if (resultLogin.equals("success")){
                        Log.i("resultLogin","로그인 성공");
                        Toast.makeText(LoginActivity.this,
                                "로그인 성공!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("resultLogin","로그인 실패");
                        Toast.makeText(LoginActivity.this,
                                "로그인 실패!",
                                Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("station",resultStation);
                    bundle.putString("carNumber",resultCarnumber);

                    //i.putExtra("station",resultStation);
                    i.putExtras(bundle);

                    ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                            "com.example.safebusfinalproject.MapViewActivity");
                    i.setComponent(cname);

                    startActivity(i);

                } catch (Exception e) {
                    Log.i("resultLogin", "로그인 ERROR.....!");
                }

            }
        });


        Auto_LogIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    String ID = input_ID.getText().toString();
                    String PW = input_PW.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                }else{
                    /**
                     * remove로 지우는것은 부분삭제
                     * clear로 지우는것은 전체 삭제 입니다
                     */
//					editor.remove("ID");
//					editor.remove("PW");
//					editor.remove("Auto_Login_enabled");
                    editor.clear();
                    editor.commit();
                }
            }
        });

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Login7Activity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
