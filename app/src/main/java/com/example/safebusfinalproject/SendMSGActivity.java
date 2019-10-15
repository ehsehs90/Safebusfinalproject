package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SendMSGActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> MyArrList;
    ArrayList<String> PhoneNo;
    ListView lisView1;


    JSONArray json = null;

    EditText textSMS;
    String sendMsg, receiveMsg;

    final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            lisView1.setAdapter(new CountryAdapter(SendMSGActivity.this));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        // listView1
        lisView1 = (ListView)findViewById(R.id.listView1);
        MyArrList = new ArrayList<HashMap<String, String>>();
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int cnt = 0;
                try {
                    String str;

                    // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
                    URL url = new URL("http://70.12.115.78:80/safebus/parentsList.do");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

                    conn.setRequestMethod("POST");


                    //jsp와 통신 성공 시 수행
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();

                        // jsp에서 보낸 값을 받는 부분
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                    } else {
                        // 통신 실패
                        Log.i("error", receiveMsg);
                    }
                    // json
                    json = new JSONArray(receiveMsg);
                    JSONObject resultObject = null;
                    while(json.getJSONObject(cnt)!=null) {
                        resultObject = json.getJSONObject(cnt);
                        //for (int j = 0; j < resultObject.length(); j++) { //Json의 Object 숫자만큼 for문을 돌립니다.

                        HashMap<String, String> map = new HashMap<String, String>();
                        //Json의 키와 밸류 값을 맵에 집어넣습니다.

                        map.put(resultObject.names().getString(4), resultObject.getString(resultObject.names().getString(4)));
                        map.put(resultObject.names().getString(5), resultObject.getString(resultObject.names().getString(5)));

//                            if(resultObject.names().getString(j).equals("parent_name")) {
//                                map.put("ID", "1");
//                                map.put(resultObject.names().getString(j), resultObject.getString(resultObject.names().getString(j)));
//                            }
//                            if(resultObject.names().getString(j).equals("parent_phone")) {
//                                map.put(resultObject.names().getString(j), resultObject.getString(resultObject.names().getString(j)));
//                            }
                        MyArrList.add(map);
                        //}
                        Log.i("sendmsgactivity3", resultObject.toString());
                        cnt++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };
        Thread t = new Thread(runnable);
        t.start();



        // 서버에서 JSON을 가져와서
        // 그 데이터로 밑에있는놈한테 적용시키면 되요!

        // Check All
        Button btnCheckAll = (Button) findViewById(R.id.btnCheckAll);
        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    checkbox.setChecked(true);
                }
            }
        });

        // Clear All
        Button btnClearAll = (Button) findViewById(R.id.btnClearAll);
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    checkbox.setChecked(false);
                }
            }
        });

        // Get Item Checked
        Button btnGetItem = (Button) findViewById(R.id.btnGetItem);
        btnGetItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                String sms = textSMS.getText().toString();

                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    if(checkbox.isChecked())
                    {
                        //Log.i("phone",checkbox.getTag().toString());
                        //Log.i("phone"+String.valueOf(i), checkbox.getTag().toString());

                        String phone2 = checkbox.getTag().toString();
                        Log.i("phone",phone2);

                        //PhoneNo = new ArrayList<String>();


                        //PhoneNo.add(check);
                        //Log.i("phone",PhoneNo.get(i));

                        //PhoneNo.add(checkbox.getTag().toString());
                        //Log.i("phone",PhoneNo.get(i));

                        try {
                            //전송
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phone2, null, sms, null, null);
                            Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }




                        //Toast.makeText(SendMSGActivity3.this,checkbox.getTag().toString() ,Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

//        buttonSend.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //입력한 값을 가져와 변수에 담는다
//
//                //String phoneNo = textPhoneNo.getText().toString();
//                String sms = textSMS.getText().toString();
//
//                for(int i=0;i<PhoneNo.size();i++) {
//
//                    try {
//                        //전송
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(PhoneNo.get(i), null, sms, null, null);
//                        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });

    }


    public class CountryAdapter extends BaseAdapter
    {
        private Context context;

        public CountryAdapter(Context c)
        {
            //super( c, R.layout.activity_column, R.id.rowTextView, );
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_column, null);

            }



            // ColCode
            TextView txtCode = (TextView) convertView.findViewById(R.id.parents_name);
            txtCode.setText(MyArrList.get(position).get("parent_name"));

            // ColCountry
            TextView txtCountry = (TextView) convertView.findViewById(R.id.parents_phone);
            txtCountry.setText(MyArrList.get(position).get("parent_phone"));

            // ColChk
            CheckBox Chk = (CheckBox) convertView.findViewById(R.id.ColChk);
            Chk.setTag(MyArrList.get(position).get("parent_phone"));

            return convertView;

        }



    }

}
