package com.example.safebusfinalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Struct;

public class NewClientActivity extends AppCompatActivity {







    private String msgFromServer;
    private String result;
    TextView humidity, temperature, location, velocity;
    TCPclient tp = null;
    int flag = 0;
    long starttime, endtime;
    String humiditystr = null;
    String temperaturestr = null;
    String locationstr = null;
    String velocitystr = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        final Button btn = (Button) findViewById(R.id.infoBtn);
        humidity = (TextView)findViewById(R.id.humidity);
        temperature = (TextView)findViewById(R.id.temperature);
        location = (TextView)findViewById(R.id.location);
        velocity = (TextView)findViewById(R.id.velocity);

        Intent intent = getIntent(); /*데이터 수신*/
        final String carNum = intent.getStringExtra("carNum");

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                        final String serverIP = "70.12.115.53";
                        final String serverPort = "8090";

                        //System.out.println("걸린 시간: " + starttime + " 밀리초");

                            tp = new TCPclient(carNum);
                            result = tp.execute(serverIP, serverPort).get();
                            Log.i("result", result);
                            // 서버로부터 받은 데이터를 출력한다.
                            Toast.makeText(getApplicationContext(), msgFromServer, Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

//    long pressedTime = 0;
//    @Override
//    public void onBackPressed() {
//        if ( pressedTime == 0 ) {
//            Toast.makeText(NewClientActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
//            pressedTime = System.currentTimeMillis();
//        }
//        else {
//            int seconds = (int) (System.currentTimeMillis() - pressedTime);
//
//            if ( seconds > 2000 ) {
//                Toast.makeText(NewClientActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
//                pressedTime = 0 ;
//            }
//            else {
//                super.onBackPressed();
////                finish(); // app 종료 시키기
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {

            PrintWriter out = new PrintWriter(new BufferedWriter
                    (new OutputStreamWriter(tp.socket.getOutputStream())), true);
            out.println("EXIT!");
            Log.d("MY_TAG", "C: Send Message To Server -> EXIT!");

            tp.socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class TCPclient extends AsyncTask<String, Void, String> {

        private String msgToServer;
        Socket socket;

        public TCPclient(String _msg) {
            this.msgToServer = _msg;
        }

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPreExecute();

            humidity.setText(humiditystr);
            temperature.setText(temperaturestr);
            location.setText(locationstr);
            velocity.setText(velocitystr);
        }

        @Override
        protected String doInBackground(String... str) {

            String a = null;

            try {
                // *********************************************************
                // 소켓을 생성하고, 서버에 접속한다.
                // *********************************************************
                InetAddress serverAddr = InetAddress.getByName(str[0]);
                socket = new Socket(serverAddr, Integer.parseInt(str[1]));

                Log.d("MY_TAG", "C: Connect");
                try {
                    // *********************************************************
                    // 서버에 데이터를 보낸다.
                    // *********************************************************
                    PrintWriter out = new PrintWriter(new BufferedWriter
                            (new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(msgToServer);
                    a = "여기1";
                    Log.d("MY_TAG", "C: Send Message To Server -> " + msgToServer);

                    // *********************************************************
                    // 서버로부터 데이터를 받는다.
                    // *********************************************************
                    a = "여기2";

                            Log.i("내용?", socket.getInputStream().toString());
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            msgFromServer = in.readLine();
                            a = "여기3";
                            Log.d("MY_TAG", "C: Receive Message From Server -> " + msgFromServer);

                            JSONArray jsonArray = new JSONArray(msgFromServer);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                humiditystr = jsonObject.getString("B1");
                                temperaturestr = jsonObject.getString("B2");
                                locationstr = jsonObject.getString("B3") + ", " + jsonObject.getString("B4");
                                velocitystr = jsonObject.getString("B5");

//                                humidity.setText(humiditystr+i);
//                                temperature.setText(temperaturestr);
//                                location.setText(locationstr);
//                                velocity.setText(velocitystr);
                                //driverimg.setImageResource(driverimgstr);
                            }


                } catch (Exception e) {
                    Log.e("MY_TAG", "C: Error1", e);
                }
//                } finally {
//                    // *********************************************************
//                    // 소켓을 닫는다.
//                    // *********************************************************
//                    socket.close();
//                    Log.d("MY_TAG", "C: Socket Close");
//                }
            } catch (Exception e) {
                Log.e("MY_TAG", "C: Error2", e);
            }

            return a;
        }
    }
}