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

public class Login5Activity extends AppCompatActivity {

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
//        if(!pref.getString("id", "NO").equals("NO")){
//            idet.setText(pref.getString("id","NO"));
//            pwet.setText(pref.getString("pw","NO"));
//            myname.setText(pref.getString("name","NO"));
//            phonenum.setText(pref.getString("phonenum","NO"));
//            license.setText(pref.getString("license","NO"));
//            carNum.setText(pref.getString("carNum","NO"));
//        }

//        Intent receiveIntent;
//       // Log.i("푸루울1111", receiveIntent.getStringExtra("realPath"));
//        //String realPath = receiveIntent.getStringExtra("realPath");
//        if ((receiveIntent= getIntent())!=null){
//            String realPath = receiveIntent.getStringExtra("realPath");
//            Log.i("푸루울", realPath);
//        }else{
//            Log.i("푸루울13313313313", "뿡빵");
//
//        }
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
        goBtn = (Button) findViewById(R.id.goBtn);

        myname = (EditText) findViewById(R.id.name);
        phonenum = (EditText) findViewById(R.id.phoneNum);
        babyName = (EditText) findViewById(R.id.babyName);
        address = (EditText) findViewById(R.id.address);
        license = (EditText) findViewById(R.id.license);
        carNum = (EditText) findViewById(R.id.carNum);


        rGroupgender = (RadioGroup) findViewById(R.id.Rgroupgender);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id", idet.getText().toString());
                editor.putString("pw", pwet.getText().toString());
                editor.putString("name", myname.getText().toString());
                editor.putString("phonenum", phonenum.getText().toString());
                editor.putString("license", license.getText().toString());
                editor.putString("carNum", carNum.getText().toString());
                editor.commit();

                Log.i("칠판", pref.getString("id", "NO"));
//
//                if(pref.getString("id", "NO").equals("NO")){
//
//
//                }

                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                        "com.example.safebusfinalproject.trash.ImageActivity");
                i.setComponent(cname);
                // startActivity(i);
                startActivityForResult(i, 3000);

                //출처: https://liveonthekeyboard.tistory.com/entry/안드로이드-startActivityForResult-onActivityResult-사용법 [키위남]

            }
        });


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

                        voparents.setBabyGender(chk2);



                        RParentsActivity task = new RParentsActivity();
                        resultstr = task.execute(voparents).get();
                    } else if (chk.equals("보육교사")) {

                        voteacher.setMemberID(id);
                        voteacher.setMemberPW(pw);
                        voteacher.setMemberName(name);
                        voteacher.setMemberTel(tel);
                        voteacher.setMemberinfo("2");
                        voteacher.setRegisterDate(getTime());

                        RTeacherActivity task2 = new RTeacherActivity();
                        resultstr2 = task2.execute(voteacher).get();
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
                        //vodirver에 fileName2 넣도록 바꿈 (10-08)
                        //vodriver.setDriverPicture(fileName);

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
                                if (index == 0)
                                    voparents.setStation("A");
                                else if (index == 1)
                                    voparents.setStation("B");
                                else if (index == 2)
                                    voparents.setStation("C");
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

//        if(requestCode == 3000 ){
//            Log.i("돈마려!","돈돈마려");
//            Intent in = getIntent();
//            String realPath = in.getStringExtra("realPath");
//            if (resultCode == RESULT_OK) {
//                switch (requestCode) {
//                    // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
//                    case 3000:
//                        Log.i("돈마려!",realPath);
//                        break;
//                }
//            }
//        }

        Log.i("돈마려94!","돈돈마려94");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
//                    data = getIntent();
                    String realPath = data.getStringExtra("realPath");
                    fileName = realPath;
                   //Log.i("돈마려!95","123456");
                    //Log.i("돈마려!realPath",realPath);
                    //Log.i("돈마려!fileName",fileName);


                    //여기 3줄 추가함(10-08)
                    fileName2=fileName.substring(30);
                    Log.i("fileName잘라서 DB에넣기",fileName2);
                    Log.i("와꾸223232",fileName2);

                    break;
            }
        }


    }
}