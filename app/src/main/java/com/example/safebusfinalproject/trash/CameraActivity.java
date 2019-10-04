package com.example.safebusfinalproject.trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safebusfinalproject.R;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent i = getIntent();
        String data = getIntent().getStringExtra("result");
        Log.i("result",data);
    }
    public void onClick(View view){
        // TextView lv2 = (TextView)findViewById(R.id.lv2);
    }
}

