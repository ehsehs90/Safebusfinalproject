package com.example.safebusfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewClient2Activity extends AppCompatActivity {

    TextView textview;
    Button btn,closebtn;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclient);

        final Button btn = (Button) findViewById(R.id.Button01); // 보내기버튼

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 버튼에서 Action이 발생(클릭)했을 때 호출!
                // 접속버튼
                try {
                    // 클라이언트는 버튼을 누르면 서버쪽에 Socket 접속을 시도.
                    // 만약에 접속에 성공하면 socket객체를 하나 획득.
                    socket = new Socket("70.12.115.53", 8090);
                    // Stream 생성
                    InputStreamReader isr =
                            new InputStreamReader(socket.getInputStream());

                    br = new BufferedReader(isr);  // 쓰기 ?? 반대 아님?

                    String str = br.readLine();
                    Log.i("strdd",str);

                    out = new PrintWriter(socket.getOutputStream()); // 읽기
                    Log.i("outout",out.toString());

                    Log.i("msg11","Echo 서버 접속 성공!!");

                } catch (Exception e) {
                    System.out.println("1: " + e);
                }

            }
        });
    }
}