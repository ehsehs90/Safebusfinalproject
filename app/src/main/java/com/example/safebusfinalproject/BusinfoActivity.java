package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safebusfinalproject.trash.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class BusinfoActivity extends AppCompatActivity {


    Button infoBtn;
    TextView humidity, temperature, location, velocity;
    ImageView driverimg;
    String imgurl = "C://review//.metadata//.plugins//org.eclipse.wst.server.core//tmp0/wtpwebapps//sendMsg2//filestorage";
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        infoBtn = (Button)findViewById(R.id.infoBtn);

        humidity = (TextView)findViewById(R.id.humidity);
        temperature = (TextView)findViewById(R.id.temperature);
        location = (TextView)findViewById(R.id.location);
        velocity = (TextView)findViewById(R.id.velocity);
        driverimg = (ImageView)findViewById(R.id.img);

        final String id = "1234"; // 로그인 액티비티에서 받아온 id값

        infoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {

                    String result;

                    BusinfoDBActivity task = new BusinfoDBActivity();
                    result = task.execute(id).get();

                    Log.i("result1",result);

                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String humiditystr = jsonObject.getString("B1");
                        String temperaturestr = jsonObject.getString("B2");
                        //String driverimgstr = jsonObject.getString("B3");

                        humidity.setText(humiditystr);
                        temperature.setText(temperaturestr);
                        location.setText("안녕3?");
                        velocity.setText("안녕4?");
                        //driverimg.setImageResource(driverimgstr);
                    }


                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

    }
}