package com.example.safebusfinalproject.trash;

import android.os.AsyncTask;
import android.util.Log;

import com.example.safebusfinalproject.trash.RegisterParentsVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterparentsActivity extends AsyncTask<RegisterParentsVO, Void, String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(RegisterParentsVO... rp) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://70.12.115.53:8080/sendmsg/parentslogin.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "babyname=" + rp[0].getbabyName() + "&babygender=" + rp[0].getbabyGender()
                    + "&address=" + rp[0].getaddress() + "&station=" + rp[0].getstation();

            Log.i("wpqkf",sendMsg);

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