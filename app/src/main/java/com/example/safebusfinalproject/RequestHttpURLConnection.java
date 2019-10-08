package com.example.safebusfinalproject;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.safebusfinalproject.mapVO.ViaPointVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RequestHttpURLConnection {

    String state;
    ViaPointVO startPoint = new ViaPointVO();
    ViaPointVO endPoint = new ViaPointVO();
    RouteOptimization routeOptimization;
    RouteOptimizationVia routeOptimizationVia;

    public ArrayList<HashMap> request(String st, String station, String starttime) throws IOException, JSONException {
        ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들
        //JSONObject sendJsonObj = new JSONObject();
        state = st;

        if(state.equals("gohome")){  //하원하는거면 출발지를 유치원으로 셋팅(강남 파이낸스 빌딩)
            startPoint.setViaX("127.036174");
            startPoint.setViaY("37.500138");
        }else if(state.equals("gokinder")){ //등원하는거면 도착지를 유치원으로 셋팅(강남 파이낸스 빌딩)
            endPoint.setViaX("127.036174");
            endPoint.setViaY("37.500138");
        }


        // 서버에서 받아온 json데이터로 경유지 list만들기
        String jsonString = "";
        getRouteInfo task = new getRouteInfo();
        try {
            jsonString = task.execute(state,station).get();
            JSONArray result = new JSONArray(jsonString);

            if(result.length() == 1){  //결과값이 1개일땐 경유지 최적화 안함
                routeOptimization = new RouteOptimization();
                if(state.equals("gohome")){
                    endPoint.setViaX(result.getJSONObject(0).getString("longitude"));
                    endPoint.setViaY(result.getJSONObject(0).getString("latitude"));
                }else if(state.equals("gokinder")){
                    startPoint.setViaX(result.getJSONObject(0).getString("longitude"));
                    startPoint.setViaY(result.getJSONObject(0).getString("latitude"));
                }
                jsonList = routeOptimization.setConn(startPoint, endPoint);
            }else{
                routeOptimizationVia = new RouteOptimizationVia(startPoint,endPoint,starttime);
                jsonList = routeOptimizationVia.setConn(state,result);

            }

            Log.d("eeeeee",jsonList.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return jsonList;
    }
}

// DB에서 ROUTE 정보 가져오기
class getRouteInfo extends AsyncTask<String, Void, String> {
    String sendMsg;
    String receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://70.12.115.59:8080/sendmsg/routeInfo.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "state=" + strings[0];
            sendMsg += "&station=" + strings[1];

            osw.write(sendMsg);
            osw.flush();

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }
}


