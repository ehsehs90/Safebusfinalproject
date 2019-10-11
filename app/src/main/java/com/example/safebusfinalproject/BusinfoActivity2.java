package com.example.safebusfinalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BusinfoActivity2 extends AppCompatActivity {


    class businforunnable implements Runnable{

        private Handler handler;
        String memberID = "admin";


        public businforunnable(Handler handler) {
            this.handler = handler;
        }

//        Bitmap bmImg = null;


        @Override
        public void run() {
            URL url =null;
            try{
                // 스트링 주소를 url 형식으로 변환
                url =new URL("http://70.12.115.54:8090/sendmsg/NewFile.jsp");
                // url에 접속 시도
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                // 스트림 생성
                InputStream is = conn.getInputStream();
                // 스트림에서 받은 데이터를 비트맵 변환
                // 인터넷에서 이미지 가져올 때는 Bitmap을 사용해야함
                bmImg = BitmapFactory.decodeStream(is);

                // 핸들러에게 화면 갱신을 요청한다.
                handler.sendEmptyMessage(0);
                // 연결 종료
                is.close();
                conn.disconnect();

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
    Bitmap bmImg = null;
    Button infoBtn;
    TextView humidity, temperature, location, velocity;
    //★
    ImageView image_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        infoBtn = (Button) findViewById(R.id.infoBtn);
        humidity = (TextView) findViewById(R.id.humidity);
        temperature = (TextView) findViewById(R.id.temperature);
        location = (TextView) findViewById(R.id.location);
        velocity = (TextView) findViewById(R.id.velocity);

        //★
        image_pic = (ImageView) findViewById(R.id.image_pic);
        // image_pic = null;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 서버에서 받아온 이미지를 핸들러를 경유해 이미지뷰에 비트맵 리소스 연결
                image_pic.setImageBitmap(bmImg);
            }
        };

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businforunnable runnable = new businforunnable(handler);
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }

}


