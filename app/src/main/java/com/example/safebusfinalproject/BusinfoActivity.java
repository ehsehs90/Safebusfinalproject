package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.safebusfinalproject.trash.RegisterActivity;

public class BusinfoActivity extends AppCompatActivity {


    Button infoBtn;
    TextView humidity, temperature, location, velocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        infoBtn = (Button)findViewById(R.id.infoBtn);

        humidity = (TextView)findViewById(R.id.humidity);
        temperature = (TextView)findViewById(R.id.temperature);
        location = (TextView)findViewById(R.id.location);
        velocity = (TextView)findViewById(R.id.velocity);


        final String id = "1234"; // 로그인 액티비티에서 받아온 id값

        infoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {


                    humidity.setText("안녕1?");
                    temperature.setText("안녕2?");
                    location.setText("안녕3?");
                    velocity.setText("안녕4?");

                    String result;

                    BusinfoDBActivity task = new BusinfoDBActivity();
                    result = task.execute(id).get();

                    Log.i("result",result);

                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

    }
}
