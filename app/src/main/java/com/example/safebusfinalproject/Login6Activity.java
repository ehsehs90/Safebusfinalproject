package com.example.safebusfinalproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.registerVO.RDriverVO;
import com.example.safebusfinalproject.registerVO.RParentsVO;
import com.example.safebusfinalproject.registerVO.RTeacherVO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login6Activity extends AppCompatActivity{

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    TextView text1,text2;
    RadioGroup rGroup1, rGroupuser, rGroupgender, rstation;
    RadioButton Rdo50, Rdo60, Rdo70, driver, teacher, parents;
    Button btnRegister;
    ImageView imgPet,driverimage;
    EditText babyName,myname,phonenum,idet,pwet,address,license,carNum;

    RadioButton userArray[] = new RadioButton[3];
    RadioButton rArray[] = new RadioButton[3];

    RParentsVO voparents = new RParentsVO();
    RTeacherVO voteacher = new RTeacherVO();
    RDriverVO vodriver = new RDriverVO();

    String chk = null;
    String chk2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login5);
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

                final String strgender = select.getText().toString();
                String[] gender = {"남아", "여아"};

                Log.i("성별",strgender);

                chk2 = strgender;

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
                    String babyname2 = babyName.getText().toString();
                    String address2 = address.getText().toString();
                    String license2 = license.getText().toString();
                    String carnum2 = carNum.getText().toString();

                    Log.i("chk?",chk);

                    if(chk.equals("학부모")) {

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

                            Intent intent = new Intent(Login6Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(chk.equals("보육교사")) {

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

                            Intent intent = new Intent(Login6Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr2,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(chk.equals("운전기사")) {

                        vodriver.setMemberID(id);
                        vodriver.setMemberPW(pw);
                        vodriver.setMemberName(name);
                        vodriver.setMemberTel(tel);
                        vodriver.setMemberinfo("3");
                        vodriver.setRegisterDate(getTime());

                        vodriver.setDriverLicense(license2);
                        vodriver.setCarNumber(carnum2);
                        //vodriver.setDriverPicture("???");

                        RDriverActivity task3 = new RDriverActivity();
                        resultstr3 = task3.execute(vodriver).get();

                        if(resultstr3.equals("회원 가입 성공 !")) {
                            Toast.makeText(getApplicationContext(), "회원 가입 성공",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login6Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), resultstr3,
                                    Toast.LENGTH_SHORT).show();
                        }
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
                }

            }
        });

        Log.i("realtime", getTime());


        final TextView tv = (TextView)findViewById(R.id.contactTv);
        Button contactBtn = (Button)findViewById(R.id.contactBtn);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보안관련 코드가 나와요!
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 마쉬멜로우 버전 6 보다 높은 경우
                    if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                        // 이전에 허용을 한 적이 없는 경우
                        // 사용자가 허가를 받아야 해요!!
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1111);
                    }else {
                        // 이전에 이미 허용버튼을 누른 경우
                        ContentResolver cr = getContentResolver();
                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // com. 이랑 다르게 정해져 있는것임.
                        // content: // com.text.data/Member
                        Cursor c = cr.query(uri,null,null,
                                null,null);
                        String result = "";
                        while(c.moveToNext()){
                            result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            result += ", ";
                            result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            result += "\n";
                        }
                        final TextView tv = (TextView)findViewById(R.id.contactTv);
                        tv.setText(result);
                    }
                }else{
                    // 마쉬멜로우 버전 6 보다 낮을 경우
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 주소록 접근에 대한 권한 요청에 허용을 누르면
                // ContentResolver를 이용해서 주소록에 접근!!
                ContentResolver cr = getContentResolver();
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // com. 이랑 다르게 정해져 있는것임.
                // content: // com.text.data/Member
                Cursor c = cr.query(uri, null, null,
                        null, null);
                String result = "";
                while (c.moveToNext()) {
                    result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    result += ", ";
                    result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    result += "\n";
                }
                final TextView tv = (TextView) findViewById(R.id.contactTv);
                tv.setText(result);
            }
        }
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