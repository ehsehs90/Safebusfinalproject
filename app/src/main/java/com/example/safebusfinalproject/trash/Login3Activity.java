package com.example.safebusfinalproject.trash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safebusfinalproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login3Activity extends AppCompatActivity{

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    TextView text1,text2;
    RadioGroup rGroup1, rGroupuser, rGroupgender;
    RadioButton Rdo50, Rdo60, Rdo70, driver, teacher, parents;
    Button btnRegister;
    ImageView imgPet,driverimage;
    EditText babyName,myname,phonenum,idet,pwet,address,license,carNum;

    RadioButton userArray[] = new RadioButton[3];
    RadioButton rArray[] = new RadioButton[3];

    BaseVO base = new BaseVO();
    RegisterParentsVO voparents = new RegisterParentsVO();  // 객체 분리해, 공통가입폼이랑 따로.(공통에는 5개)
    RegisterDriverVO vodriver = new RegisterDriverVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        setTitle("회원가입");

        // 위젯을 변수에 대입

        idet = (EditText) findViewById(R.id.idText);
        pwet = (EditText) findViewById(R.id.passwordText);

        text1 = (TextView) findViewById(R.id.Text1);
        text2 = (TextView) findViewById(R.id.Text2);

        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);

        rArray[0] = (RadioButton) findViewById(R.id.Rdo50);
        rArray[1] = (RadioButton) findViewById(R.id.Rdo60);
        rArray[2] = (RadioButton) findViewById(R.id.Rdo70); //이미지

        //  rGroupuser = (RadioGroup) findViewById(R.id.Rgroupuser);

        userArray[0] = (RadioButton) findViewById(R.id.driver);
        userArray[1] = (RadioButton) findViewById(R.id.teacher);
        userArray[2] = (RadioButton) findViewById(R.id.parents);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        imgPet = (ImageView) findViewById(R.id.ImgPet);
        driverimage = (ImageView) findViewById(R.id.driverimage);

        myname = (EditText) findViewById(R.id.name);
        phonenum = (EditText) findViewById(R.id.phoneNum);
        babyName = (EditText) findViewById(R.id.babyName);
        address = (EditText) findViewById(R.id.address);
        license = (EditText) findViewById(R.id.license);
        carNum = (EditText) findViewById(R.id.carNum);

        rGroupgender = (RadioGroup) findViewById(R.id.Rgroupgender);

        rGroupgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton select = (RadioButton) findViewById(id);

                String strgender = select.getText().toString();
                String[] gender = {"남아", "여아"};

                Log.i("성별",strgender);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String result;
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();
                    String name = myname.getText().toString();
                    String tel = phonenum.getText().toString();

                    base.setMemberID(id);
                    base.setMemberPW(pw);
                    base.setMemberName(name);
                    base.setMemberTel(tel);

                    RegisterActivity task = new RegisterActivity();
                    result = task.execute(id, pw).get();
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.Rgroupuser);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton select = (RadioButton) findViewById(id);

                String struser = select.getText().toString();
                String[] userjob = {"운전기사", "보육교사", "학부모"};


                Log.i("info1", struser);
                Log.i("info2", userjob[0]);

                if (select.isChecked() == true && struser.equals(userjob[0])) { // 운전기사
                    //Log.i("info3","if찍힘;");
                    text2.setVisibility(android.view.View.INVISIBLE);
                    rGroup1.setVisibility(android.view.View.INVISIBLE);
                    imgPet.setVisibility(android.view.View.INVISIBLE);
                    rGroupgender.setVisibility(android.view.View.INVISIBLE);
                    babyName.setVisibility(android.view.View.INVISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    address.setVisibility(View.INVISIBLE);
                    license.setVisibility(View.VISIBLE);
                    carNum.setVisibility(View.VISIBLE);
                } else if (select.isChecked() == true && struser.equals(userjob[1])) { // 보육교사
                    //Log.i("info3","else찍힘;");
                    text2.setVisibility(android.view.View.INVISIBLE);
                    rGroup1.setVisibility(android.view.View.INVISIBLE);
                    imgPet.setVisibility(android.view.View.INVISIBLE);
                    rGroupgender.setVisibility(android.view.View.INVISIBLE);
                    babyName.setVisibility(android.view.View.INVISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    address.setVisibility(View.INVISIBLE);
                    license.setVisibility(View.INVISIBLE);
                    carNum.setVisibility(View.INVISIBLE);
                } else if (select.isChecked() == true && struser.equals(userjob[2])) { // 학부모
                    //Log.i("info3","else찍힘;");

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

                    text2.setVisibility(android.view.View.VISIBLE);
                    rGroup1.setVisibility(android.view.View.VISIBLE);
                    imgPet.setVisibility(android.view.View.VISIBLE);
                    rGroupgender.setVisibility(android.view.View.VISIBLE);
                    babyName.setVisibility(android.view.View.VISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    license.setVisibility(View.INVISIBLE);
                    carNum.setVisibility(View.INVISIBLE);
                }

            }
        });

        String tmp = getTime();

        Log.i("realtime", tmp);

    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void onRadioButtonClicked (View v){
        boolean checked = ((RadioButton) v).isChecked();

        if (checked) {
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "checked",
                    Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "unchecked",
                    Toast.LENGTH_SHORT).show();
        }


}