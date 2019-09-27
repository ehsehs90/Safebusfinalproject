package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Login3Activity extends AppCompatActivity {

    TextView text1,text2;
    Switch switchAgree;
    RadioGroup rGroup1, rGroupuser;
    RadioButton Rdo50, Rdo60, Rdo70, driver, teacher, parents;
    Button btnFinish, btnInit;
    ImageView imgPet;

    RadioButton userArray[] = new RadioButton[3];
    RadioButton rArray[] = new RadioButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        setTitle("회원가입");

        // 위젯을 변수에 대입

        text1 = (TextView) findViewById(R.id.Text1);
        text2 = (TextView) findViewById(R.id.Text2);

        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);

        rArray[0] = (RadioButton) findViewById(R.id.Rdo50);
        rArray[1] = (RadioButton) findViewById(R.id.Rdo60);
        rArray[2] = (RadioButton) findViewById(R.id.Rdo70); //이미지

        rGroupuser = (RadioGroup) findViewById(R.id.Rgroupuser);

        userArray[0] = (RadioButton) findViewById(R.id.driver);
        userArray[1] = (RadioButton) findViewById(R.id.teacher);
        userArray[2] = (RadioButton) findViewById(R.id.parents);

        btnFinish = (Button) findViewById(R.id.BtnFinish);
        btnInit = (Button) findViewById(R.id.BtnInit);
        imgPet = (ImageView) findViewById(R.id.ImgPet);

//        final int draw[] = {R.drawable.stationa, R.drawable.stationb, R.drawable.stationc};

//        final RadioGroup rg = (RadioGroup) findViewById(R.id.Rgroupuser);
//        Button b = (Button)findViewById(R.id.button1);
//        final TextView tv = (TextView)findViewById(R.id.textView2);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = rg.getCheckedRadioButtonId();
//                //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
//                RadioButton rb = (RadioButton) findViewById(id);
//                tv.setText("결과: " + rb.getText().toString());
//
//                String struser = rb.getText().toString();
//
//                Log.i("user",struser);
//
//                if(struser == "운전기사" && rb.isChecked() == true){
//                    text2.setVisibility(android.view.View.VISIBLE);
//                    rGroup1.setVisibility(android.view.View.VISIBLE);
//                    btnFinish.setVisibility(android.view.View.VISIBLE);
//                    btnInit.setVisibility(android.view.View.VISIBLE);
//                    imgPet.setVisibility(android.view.View.VISIBLE);
//                }
//                else if(struser == "보육교사"){
//                    text2.setVisibility(android.view.View.VISIBLE);
//                    rGroup1.setVisibility(android.view.View.VISIBLE);
//                    btnFinish.setVisibility(android.view.View.VISIBLE);
//                    btnInit.setVisibility(android.view.View.VISIBLE);
//                    imgPet.setVisibility(android.view.View.INVISIBLE);
//                }
//                else{
//                    text2.setVisibility(android.view.View.VISIBLE);
//                    rGroup1.setVisibility(android.view.View.VISIBLE);
//                    btnFinish.setVisibility(android.view.View.INVISIBLE);
//                    btnInit.setVisibility(android.view.View.INVISIBLE);
//                    imgPet.setVisibility(android.view.View.INVISIBLE);
//                }
//
//                // 라디오버튼 클릭시, 이벤트 처리
//                for (int i = 0; i < draw.length; i++) {
//                    final int index = i;
//                    rArray[index].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            imgPet.setImageResource(draw[index]);
//                        }
//                    });
//
//                    // 처음부터 다시 버튼을 클릭하면,
//                    btnInit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            text2.setVisibility(android.view.View.INVISIBLE);
//                            rGroup1.setVisibility(android.view.View.INVISIBLE);
//                            btnFinish.setVisibility(android.view.View.INVISIBLE);
//                            btnInit.setVisibility(android.view.View.INVISIBLE);
//                            imgPet.setVisibility(android.view.View.INVISIBLE);
//
//                            // 라디오 그룹 초기화
//                            rGroup1.clearCheck();
//
//                            // 스위치를 off로
//                            //switchAgree.setChecked(false);
//                        }
//                    });
//                }
//
//            } // end onClick()
//        });  // end Listener
    }

    public void onRadioButtonClicked (View v){
        boolean checked = ((RadioButton) v).isChecked();

        if (checked) {
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "checked",
                    Toast.LENGTH_SHORT).show();

            String struser = ((RadioButton) v).getText().toString();

                if(struser == "운전기사"){
                    text2.setVisibility(android.view.View.INVISIBLE);
                    rGroup1.setVisibility(android.view.View.INVISIBLE);
                    btnFinish.setVisibility(android.view.View.INVISIBLE);
                    btnInit.setVisibility(android.view.View.INVISIBLE);
                    imgPet.setVisibility(android.view.View.INVISIBLE);
                    text2.setVisibility(android.view.View.VISIBLE);
                    rGroup1.setVisibility(android.view.View.VISIBLE);
                    btnFinish.setVisibility(android.view.View.VISIBLE);
                    btnInit.setVisibility(android.view.View.VISIBLE);
                    imgPet.setVisibility(android.view.View.VISIBLE);
                }
                else if(struser == "보육교사"){
                        text2.setVisibility(android.view.View.INVISIBLE);
                        rGroup1.setVisibility(android.view.View.INVISIBLE);
                        btnFinish.setVisibility(android.view.View.INVISIBLE);
                        btnInit.setVisibility(android.view.View.INVISIBLE);
                        imgPet.setVisibility(android.view.View.INVISIBLE);
                    text2.setVisibility(android.view.View.VISIBLE);
                    rGroup1.setVisibility(android.view.View.VISIBLE);
                    btnFinish.setVisibility(android.view.View.VISIBLE);
                    btnInit.setVisibility(android.view.View.VISIBLE);
                    imgPet.setVisibility(android.view.View.VISIBLE);
                }
                else{
                    text2.setVisibility(android.view.View.INVISIBLE);
                    rGroup1.setVisibility(android.view.View.INVISIBLE);
                    btnFinish.setVisibility(android.view.View.INVISIBLE);
                    btnInit.setVisibility(android.view.View.INVISIBLE);
                    imgPet.setVisibility(android.view.View.INVISIBLE);
                    text2.setVisibility(android.view.View.VISIBLE);
                    rGroup1.setVisibility(android.view.View.VISIBLE);
                    btnFinish.setVisibility(android.view.View.VISIBLE);
                    btnInit.setVisibility(android.view.View.VISIBLE);
                    imgPet.setVisibility(android.view.View.VISIBLE);
                }

            final int draw[] = {R.drawable.stationa, R.drawable.stationb, R.drawable.stationc};

            // 라디오버튼 클릭시, 이벤트 처리
                for (int i = 0; i < draw.length; i++) {
                    final int index = i;
                    rArray[index].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imgPet.setImageResource(draw[index]);
                        }
                    });
                }
        }
        else
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "unchecked",
                    Toast.LENGTH_SHORT).show();
        }
}



//        // 체크박스의 체크가 변경되면
//        switchAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // 체크되면 모두 보이도록 설정
//                if (switchAgree.isChecked() == true) {
//                    text2.setVisibility(android.view.View.VISIBLE);
//                    rGroup1.setVisibility(android.view.View.VISIBLE);
//                    btnFinish.setVisibility(android.view.View.VISIBLE);
//                    btnInit.setVisibility(android.view.View.VISIBLE);
//                    imgPet.setVisibility(android.view.View.VISIBLE);
//                } else {
//                    text2.setVisibility(android.view.View.INVISIBLE);
//                    rGroup1.setVisibility(android.view.View.INVISIBLE);
//                    btnFinish.setVisibility(android.view.View.INVISIBLE);
//                    btnInit.setVisibility(android.view.View.INVISIBLE);
//                    imgPet.setVisibility(android.view.View.INVISIBLE);
//                }
//            }
//        });