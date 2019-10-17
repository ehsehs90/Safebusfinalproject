package com.example.safebusfinalproject;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.registerVO.RDriverVO;
import com.example.safebusfinalproject.registerVO.RParentsVO;
import com.example.safebusfinalproject.registerVO.RTeacherVO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login7Activity extends AppCompatActivity {

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    String fileName=null;
    String fileName2 = null;

    TextView text1, text2;
    RadioGroup rGroup1, rGroupuser, rGroupgender;
    RadioButton Rdo50, Rdo60, Rdo70, driver, teacher, parents;
    Button btnRegister;
    ImageView imgPet, driverimage;
    EditText babyName, myname, phonenum, idet, pwet, address, license, carNum;
    Button goBtn;

    RadioButton userArray[] = new RadioButton[3];
    RadioButton rArray[] = new RadioButton[3];

    RParentsVO voparents = new RParentsVO();
    RTeacherVO voteacher = new RTeacherVO();
    RDriverVO vodriver = new RDriverVO();

    String chk = null;
    String chk2 = null;

    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login5);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        if (pref.getString("id", "NO").equals("NO")) {
            editor.putString("id", "NO");
            editor.putString("pw", "NO");
            editor.putString("name", "NO");
            editor.putString("phonenum", "NO");
            editor.putString("license", "NO");
            editor.putString("carNum", "NO");
            editor.commit();

        }
        Log.i("??띠용???", pref.getString("id", "NO"));
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

        userArray[0] = (RadioButton) findViewById(R.id.driver);
        userArray[1] = (RadioButton) findViewById(R.id.teacher);
        userArray[2] = (RadioButton) findViewById(R.id.parents);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        imgPet = (ImageView) findViewById(R.id.ImgPet);
        driverimage = (ImageView) findViewById(R.id.driverimage);
        goBtn = (Button) findViewById(R.id.goBtn);

        myname = (EditText) findViewById(R.id.name);
        phonenum = (EditText) findViewById(R.id.phoneNum);
        babyName = (EditText) findViewById(R.id.babyName);
        address = (EditText) findViewById(R.id.address);
        license = (EditText) findViewById(R.id.license);
        carNum = (EditText) findViewById(R.id.carNum);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("id", idet.getText().toString());
                editor.putString("pw", pwet.getText().toString());
                editor.putString("name", myname.getText().toString());
                editor.putString("phonenum", phonenum.getText().toString());
                editor.putString("license", license.getText().toString());
                editor.putString("carNum", carNum.getText().toString());
                editor.commit();

                Log.i("칠판", pref.getString("id", "NO"));

                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                        "com.example.safebusfinalproject.trash.ImageActivity");
                i.setComponent(cname);
                startActivityForResult(i, 3000);

            }
        });

        rGroupgender = (RadioGroup) findViewById(R.id.Rgroupgender);

        rGroupgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton select = (RadioButton) findViewById(id);

                final String strgender = select.getText().toString();
                String[] gender = {"남아", "여아"};

                Log.i("성별", strgender);

                chk2 = strgender;

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String resultstr = null, resultstr2 = null, resultstr3 = null;
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();
                    String name = myname.getText().toString();
                    String tel = phonenum.getText().toString();
                    String babyname2 = babyName.getText().toString();
                    String address2 = address.getText().toString();
                    String license2 = license.getText().toString();
                    String carnum2 = carNum.getText().toString();

                    Log.i("chk?", chk);

                    if (chk.equals("학부모")) {

                        voparents.setMemberID(id);
                        voparents.setMemberPW(pw);
                        voparents.setMemberName(name);
                        voparents.setMemberTel(tel);
                        voparents.setMemberinfo("1");
                        voparents.setRegisterDate(getTime());

                        voparents.setBabyName(babyname2);
                        voparents.setAddress(address2);
                        if(chk2.equals("남아"))
                            voparents.setBabyGender("1");
                        else if(chk2.equals("여아"))
                            voparents.setBabyGender("2");


                        RParentsActivity task = new RParentsActivity();
                        resultstr = task.execute(voparents).get();

                        if(resultstr.equals("회원 가입 성공 !")) {
                            Toast.makeText(getApplicationContext(), "회원 가입 성공",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login7Activity.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr,
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent();
                            ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                                    "com.example.safebusfinalproject.LoginActivity");
                            i.setComponent(cname);

                            startActivity(i);

                        }

                    } else if (chk.equals("보육교사")) {

                        voteacher.setMemberID(id);
                        voteacher.setMemberPW(pw);
                        voteacher.setMemberName(name);
                        voteacher.setMemberTel(tel);
                        voteacher.setMemberinfo("2");
                        voteacher.setRegisterDate(getTime());

                        RTeacherActivity task2 = new RTeacherActivity();
                        resultstr2 = task2.execute(voteacher).get();

                        if(resultstr2.equals("회원 가입 성공 !")) {
                            Toast.makeText(getApplicationContext(), "회원 가입 성공",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login7Activity.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr2,
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent();
                            ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                                    "com.example.safebusfinalproject.LoginActivity");
                            i.setComponent(cname);

                            startActivity(i);

                        }

                    } else if (chk.equals("운전기사")) {

                        vodriver.setMemberID(id);
                        vodriver.setMemberPW(pw);
                        vodriver.setMemberName(name);
                        vodriver.setMemberTel(tel);
                        vodriver.setMemberinfo("3");
                        vodriver.setRegisterDate(getTime());

                        vodriver.setDriverLicense(license2);
                        vodriver.setCarNumber(carnum2);
                        vodriver.setDriverPicture(fileName2);

                        Log.i("운전자사진","nukkk");
                        Log.i("운전자사진",fileName);
                        Log.i("운전자사진","지금은fileName이 넘어가는중..");

                        // /storage/emulated/0/DCIM/MYAPP/IMG_20191008_094733995832093892601433.png

                        String fileName2="";
                        fileName2=fileName.substring(30);
                        Log.i("fileName잘라서 DB에넣기",fileName2);
                        Log.i("운전자사진2",fileName2);


                        // 여기 파일 경로로 사진이 서버를 통해 넘어가게 됩니다다
                        //"C:\\review\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\wtpwebapps\\sendMsg2\\filestorage"+

                        RDriverActivity task3 = new RDriverActivity();
                        resultstr3 = task3.execute(vodriver).get();

                        if(resultstr3.equals("회원 가입 성공 !")) {
                            Toast.makeText(getApplicationContext(), "회원 가입 성공",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login7Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr3,
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent();
                            ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                                    "com.example.safebusfinalproject.LoginActivity");
                            i.setComponent(cname);

                            startActivity(i);

                        }

                    }

                    Log.i("결과", resultstr);
                    Log.i("결과2", resultstr2);
                    Log.i("결과3", resultstr3);

                } catch (Exception e) {
                    Log.i("DBtest", e.toString());
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
                    goBtn.setVisibility(View.VISIBLE);

                    if (!pref.getString("id", "NO").equals("NO")) {
                        idet.setText(pref.getString("id", "NO"));
                        pwet.setText(pref.getString("pw", "NO"));
                        myname.setText(pref.getString("name", "NO"));
                        phonenum.setText(pref.getString("phonenum", "NO"));
                        license.setText(pref.getString("license", "NO"));
                        carNum.setText(pref.getString("carNum", "NO"));

                        Log.i("돼요?", carNum.getText().toString());
                    }
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
                    goBtn.setVisibility(View.INVISIBLE);
                } else if (select.isChecked() == true && struser.equals(userjob[2])) { // 학부모
                    //Log.i("info3","else찍힘;");

                    final int draw[] = {R.drawable.stationa, R.drawable.stationb, R.drawable.stationc};

                    final RadioGroup rstation = (RadioGroup) findViewById(R.id.rstation);
                    final RadioButton select1 = (RadioButton) findViewById(R.id.sta1);
                    final RadioButton select2 = (RadioButton) findViewById(R.id.sta2);
                    final RadioButton select3 = (RadioButton) findViewById(R.id.sta3);
                    final RadioButton select4 = (RadioButton) findViewById(R.id.sta4);
                    final RadioButton select5 = (RadioButton) findViewById(R.id.sta5);

                    // 라디오버튼 클릭시, 이벤트 처리
                    for (int i = 0; i < draw.length; i++) {
                        final int index = i;
                        rArray[index].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imgPet.setImageResource(draw[index]);
                                if(index==0) {

                                    select1.setText("1");
                                    select2.setText("2");
                                    select3.setText("3");
                                    select4.setText("4");
                                    select5.setText("5");

                                    select1.setChecked(false);
                                    select2.setChecked(false);
                                    select3.setChecked(false);
                                    select4.setChecked(false);
                                    select5.setChecked(false);

                                    rstation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int id) {


                                            final RadioButton select = (RadioButton) findViewById(id);
                                            final String struser = select.getText().toString();

                                            if(struser.equals("1"))
                                                voparents.setStation("1");
                                            else if(struser.equals("2"))
                                                voparents.setStation("2");
                                            else if(struser.equals("3"))
                                                voparents.setStation("3");
                                            else if(struser.equals("4"))
                                                voparents.setStation("4");
                                            else if(struser.equals("5"))
                                                voparents.setStation("5");
                                        }


                                    });

                                    rstation.setVisibility(View.VISIBLE);
                                }
                                else if(index==1) {


                                    select1.setText("6");
                                    select2.setText("7");
                                    select3.setText("8");
                                    select4.setText("9");
                                    select5.setText("10");

                                    select1.setChecked(false);
                                    select2.setChecked(false);
                                    select3.setChecked(false);
                                    select4.setChecked(false);
                                    select5.setChecked(false);

                                    rstation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int id) {

                                            RadioButton select = (RadioButton) findViewById(id);
                                            final String struser = select.getText().toString();

                                            if(struser.equals("6"))
                                                voparents.setStation("6");
                                            else if(struser.equals("7"))
                                                voparents.setStation("7");
                                            else if(struser.equals("8"))
                                                voparents.setStation("8");
                                            else if(struser.equals("9"))
                                                voparents.setStation("9");
                                            else if(struser.equals("10"))
                                                voparents.setStation("10");
                                        }

                                    });

                                    rstation.setVisibility(View.VISIBLE);
                                }
                                else if(index==2) {

                                    select1.setText("11");
                                    select2.setText("12");
                                    select3.setText("13");
                                    select4.setText("14");
                                    select5.setText("15");

                                    select1.setChecked(false);
                                    select2.setChecked(false);
                                    select3.setChecked(false);
                                    select4.setChecked(false);
                                    select5.setChecked(false);

                                    rstation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int id) {

                                            RadioButton select = (RadioButton) findViewById(id);
                                            final String struser = select.getText().toString();

                                            if(struser.equals("11"))
                                                voparents.setStation("11");
                                            else if(struser.equals("12"))
                                                voparents.setStation("12");
                                            else if(struser.equals("13"))
                                                voparents.setStation("13");
                                            else if(struser.equals("14"))
                                                voparents.setStation("14");
                                            else if(struser.equals("15"))
                                                voparents.setStation("15");
                                        }

                                    });

                                    rstation.setVisibility(View.VISIBLE);
                                }
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
                    goBtn.setVisibility(View.INVISIBLE);
                }

            }
        });

        Log.i("realtime", getTime());

    }

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        if (checked) {
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "checked",
                    Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), ((RadioButton) v).getText() + "unchecked",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("돈마려94!","돈돈마려94");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
                    String realPath = data.getStringExtra("realPath");
                    fileName = realPath;

                    //여기 3줄 추가함(10-08)
                    fileName2=fileName.substring(30);
                    Log.i("fileName잘라서 DB에넣기",fileName2);
                    Log.i("와꾸223232",fileName2);

                    break;
            }
        }


    }
}