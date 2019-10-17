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
import org.w3c.dom.Text;

public class SeatActivity extends AppCompatActivity {

    Button  button02;
    TextView button01 , button13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        button01 = (TextView) findViewById(R.id.button01);
        button13 = (TextView)findViewById(R.id.button13);
        button02 = (Button)findViewById(R.id.button02);


        final String id = "1234"; // 로그인 액티비티에서 받아온 id값

        button02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {

                    String result;

                    BusseatDBActivity task = new BusseatDBActivity();
                    result = task.execute(id).get();

                    Log.i("result1",result);

                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String button01str = jsonObject.getString("감자");
                        String button13str = jsonObject.getString("고구마");

                        button01.setText(button01str);
                        button13.setText(button13str);
                    }

                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

    }
}