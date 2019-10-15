package com.example.safebusfinalproject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.safebusfinalproject.mapVO.ViaPointVO;
import com.skt.Tmap.TMapPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LocationURLConnection {
    String resultString = "";
    //String carNum = "123가456";
    TMapPoint point = new TMapPoint(0,0);

    public TMapPoint getNowInfo(String car_num) throws JSONException {
        ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들
        //JSONObject sendJsonObj = new JSONObject();


        // 서버에서 받아온 json데이터로 경유지 list만들기
        String jsonString = "";
        getNowLocInfo task = new getNowLocInfo();
        try {
            jsonString = task.execute(car_num).get();
//            Log.d("sisisisi",jsonString);
            JSONObject result = new JSONObject(jsonString);

            //vo.setViaX(result.getString("longitude"));
            //vo.setViaY(result.getString("latitude"));
            Log.d("sisisisi",result.getString("latitude"));
            Log.d("sisisisi",result.getString("longitude"));
            point.setLatitude(Double.valueOf(result.getString("latitude")));
            point.setLongitude(Double.valueOf(result.getString("longitude")));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return point;
    }


}
// DB에서 현재 차량 위치 정보 가져오기
class getNowLocInfo extends AsyncTask<String, Void, String> {
    String sendMsg;
    String receiveMsg;

    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "onPreExecute");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://70.12.115.59:8080/sendmsg/nowLocInfo.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "carNum=" + strings[0];

            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    Log.d("sisisisi><",str);
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                // 통신 실패
                //Log.i("error", receiveMsg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }
}

