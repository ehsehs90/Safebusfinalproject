package com.example.safebusfinalproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImgDriverActivity extends AppCompatActivity {

    ImageView imView;
    String imgurl = "C://review//.metadata//.plugins//org.eclipse.wst.server.core//tmp0/wtpwebapps//sendMsg2//filestorage";
    Bitmap bm;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);
        task = new back();

        imView = (ImageView) findViewById(R.id.img);

        task.execute(imgurl+"img1");

    }

    private class back extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{

                URL url = new URL("http://70.12.115.53:8080/sendmsg/Carinfo.jsp");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                conn.setRequestMethod("POST");

                InputStream is = conn.getInputStream();

                bm = BitmapFactory.decodeStream(is);


            }catch(IOException e){
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bm);
        }

    }
}