package com.example.safebusfinalproject.trash;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login4Activity extends AppCompatActivity{

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
    RegisterParentsVO voparents = new RegisterParentsVO();
    RegisterDriverVO vodriver = new RegisterDriverVO();

    String chk = null;

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

                voparents.setbabyGender(strgender);

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String resultstr = null,resultstr2 = null,resultstr3 = null;
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();
                    String name = myname.getText().toString();
                    String tel = phonenum.getText().toString();

                    base.setMemberID(id);
                    base.setMemberPW(pw);
                    base.setMemberName(name);
                    base.setMemberTel(tel);

                    if(chk.equals("학부모"))
                        base.setMemberinfo("1");
                    else if(chk.equals("보육교사"))
                        base.setMemberinfo("2");
                    else if(chk.equals("운전기사"))
                        base.setMemberinfo("3");

//                    RegistertestActivity task = new RegistertestActivity();  // 기본 + 보육교사
//                    resultstr = task.execute(base).get();
//

                    String babyname2 = babyName.getText().toString();
                    String address2 = address.getText().toString();

                    voparents.setbabyName(babyname2);
                    voparents.setaddress(address2);

                    String license2 = license.getText().toString();
                    String carnum2 = carNum.getText().toString();

                    vodriver.setDriverLicense(license2);
                    vodriver.setCarNum(carnum2);

                    Log.i("chk?",chk);

                    if(chk.equals("학부모")) {
                        RegisterparentsActivity task = new RegisterparentsActivity();
                        resultstr = task.execute(voparents).get();
                    }
                    else if(chk.equals("보육교사")) {
                        RegisterteacherActivity task2 = new RegisterteacherActivity();
                        resultstr2 = task2.execute(vodriver).get();
                    }
                    else if(chk.equals("운전기사")) {
                        RegisterdriverActivity task3 = new RegisterdriverActivity();
                        resultstr3 = task3.execute(vodriver).get();
                    }

                    Log.i("결과",resultstr);
                    Log.i("결과2",resultstr2);
                    Log.i("결과3",resultstr3);

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

                final String struser = select.getText().toString();
                String[] userjob = {"운전기사", "보육교사", "학부모"};

                chk = struser;

                Log.i("info1", struser);
                Log.i("info2", userjob[0]);

                if (select.isChecked() == true && struser.equals(userjob[0])) { // 운전기사
                    //Log.i("info3","if찍힘;");
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);
                    rGroupgender.setVisibility(View.INVISIBLE);
                    babyName.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    address.setVisibility(View.INVISIBLE);
                    license.setVisibility(View.VISIBLE);
                    carNum.setVisibility(View.VISIBLE);
                } else if (select.isChecked() == true && struser.equals(userjob[1])) { // 보육교사
                    //Log.i("info3","else찍힘;");
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);
                    rGroupgender.setVisibility(View.INVISIBLE);
                    babyName.setVisibility(View.INVISIBLE);
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
                                if(index==0)
                                    voparents.setstation("A");
                                else if(index==1)
                                    voparents.setstation("B");
                                else if(index==2)
                                    voparents.setstation("C");
                            }
                        });
                    }

                    text2.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    imgPet.setVisibility(View.VISIBLE);
                    rGroupgender.setVisibility(View.VISIBLE);
                    babyName.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    license.setVisibility(View.INVISIBLE);
                    carNum.setVisibility(View.INVISIBLE);
                }

            }
        });

        String tmp = getTime();
        base.setRegisterDate(tmp);

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