package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText input_ID, input_PW;
        final CheckBox Auto_LogIn;

        SharedPreferences setting;
         final SharedPreferences.Editor editor;

        input_ID = (EditText) findViewById(R.id.input_ID);
        input_PW = (EditText) findViewById(R.id.input_PW);
        Auto_LogIn = (CheckBox) findViewById(R.id.Auto_LogIn);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)){
            input_ID.setText(setting.getString("ID", ""));
            input_PW.setText(setting.getString("PW", ""));
            Auto_LogIn.setChecked(true);
        }

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}


