package com.example.safebusfinalproject;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RequestHttpURLConnection {

    ArrayList<ViaPointVO> viaPointList; //경유지 ArrayList
    public ArrayList<HashMap> request(String _url) throws IOException, JSONException {
        ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들
        JSONObject sendJsonObj = new JSONObject();

        // 서버에서 받아온 json데이터로 경유지 list만들기
        String result = "";
        getRouteInfo task = new getRouteInfo();
        try {
            result = task.execute("B").get();
            // DB에서 가져온 경유지 정보이용해서 tmap api로 보낼 jsonObject만들기
            sendJsonObj = makeViaList(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // tmap api server 연결
        URL url = new URL("https://apis.openapi.sk.com/tmap/routes/routeOptimization10?version=1&format=json");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST"); // 보내는 타입
        conn.setRequestProperty("Accept", "application/json");
        //conn.setRequestProperty("appKey","b7dc6f07-a787-4f42-9d8d-c42b9eee47a1");
        conn.setRequestProperty("appKey","87e1a7c5-b8fc-4078-948b-c3c9f00927e1");
        conn.setRequestProperty("Content-type","application/json");


        // 전송
        OutputStream os = conn.getOutputStream();

        try {
            os.write(sendJsonObj.toString().getBytes("UTF-8"));
            os.flush();

            // 응답

            // 200 성공코드 // 400 문법에러
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 성공시 처리
                Log.d("connection","성공");
            }else{
                // 실패시 처리
                Log.d("connection","실패");
                Log.d("connection",conn.getResponseCode()+"");
            }


            // tmap server에서 받아온 경로탐색 json파일 parsing
            JSONObject jsonObject = readJsonFromUrl(conn);
            jsonList = jsonListParser(jsonObject,sendJsonObj);
            for (HashMap item: jsonList ) {
                Log.d("aaaaaaaa", item.get("viaPointName").toString());
            }

            // 닫기
            os.close();
            //br.close();

        } catch (MalformedURLException e) {
            Log.d("ssssss","잘못된 url");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.d("ssssss","잘못된 url");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("ssssss","인코딩");
        } catch (IOException e) {
            Log.d("ssssss","입출력문제");
            e.printStackTrace();
            Log.d("ssssss", e.toString());
        }
        return jsonList;
    }


    public JSONObject makeViaList(String jsonString) throws JSONException {

        //경유지ArrayList
        JSONArray viaArray = new JSONArray(jsonString);
        viaPointList = new ArrayList<ViaPointVO>();
        try {
            for (int i = 0; i < viaArray.length(); i++) {
                HashMap map = new HashMap();
                JSONObject jObject = viaArray.getJSONObject(i);
                // 1번 경유지
                ViaPointVO viaVo = new ViaPointVO();
                viaVo.setViaPointName(jObject.getString("station"));
                viaVo.setViaDetailAddress(jObject.getString("description"));
                viaVo.setViaX(jObject.getString("longitude"));
                viaVo.setViaY(jObject.getString("latitude"));
                viaVo.setViaTime(1000);
                viaVo.setViaPointId(jObject.getString("station"));
                viaPointList.add(viaVo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // 경유지 ArrayList -> tmap api 서버로 보낼 jsonObject type으로 만들기
        // tmap서버로 보낼 json object 생성
        JSONObject vObj = new JSONObject();
        try{
            JSONArray vArray = new JSONArray();
            for(int i=0; i<viaPointList.size(); i++){
                JSONObject vObject = new JSONObject();
                Log.d("vvvvvvvvv",viaPointList.get(i).getViaPointId());
                vObject.put("viaPointId",viaPointList.get(i).getViaPointId());
                vObject.put("viaPointName",viaPointList.get(i).getViaPointName());
                vObject.put("viaDetailAddress",viaPointList.get(i).getViaDetailAddress());
                vObject.put("viaX",viaPointList.get(i).getViaX());
                vObject.put("viaY",viaPointList.get(i).getViaY());
                vObject.put("viaTime",viaPointList.get(i).getViaTime());
                vArray.put(vObject);
            }
            vObj.put("reqCoordType","WGS84GEO");
            vObj.put("resCoordType","WGS84GEO");
            vObj.put("startName","출발");
            //vObj.put("startX","127.0393648");
            //vObj.put("startY","37.5014303");
            vObj.put("startX","126.720064");
            vObj.put("startY","37.687624");
            vObj.put("startTime","201909261400");
            vObj.put("endName","도착");
            //vObj.put("endX","127.0393648");
            //vObj.put("endY","37.5014303");
            vObj.put("endX","127.036301");
            vObj.put("endY","37.487888");
            vObj.put("searchOption","0");
            vObj.put("carType","1");
            vObj.put("viaPoints",vArray); //배열을 넣음

        }catch (JSONException e){
            e.printStackTrace();
        }


        return vObj;
    }

    // tmap api server에서 경로탐색 결과 받기
    public static JSONObject readJsonFromUrl(HttpURLConnection conn) throws IOException {
        String jsonText = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));


        //Log.d("line:",br.toString());

        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            jsonText += line;
        }
        // json 파일로 변환
        try {
            JSONObject json = new JSONObject(jsonText);
            br.close();
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<HashMap> jsonListParser(JSONObject jsonObject, JSONObject sendJsonObj) {

        String index = null;
        String viaPointId = null;
        String viaPointName = null;
        String viaDetailAddress = null; //경유지
        String groupKey = null; //경유지
        String arriveTime = null;
        String completeTime = null;
        String distance = null;
        String deliveryTime = null;
        String waitTime = null;
        String pointType = null;
        String longitude = null; //경도
        String latitude = null;  //위도

        ArrayList<HashMap> objList = new ArrayList<>();
        try {
            String totalDistance = jsonObject.getJSONObject("properties").getString("totalDistance");
            String totalTime = jsonObject.getJSONObject("properties").getString("totalTime");

            JSONArray jarray = jsonObject.getJSONArray("features");
            Log.d("aaaaaaaa",jarray.length()+"");
            for (int i = 0; i < jarray.length(); i++) {
                HashMap map = new HashMap();
                JSONObject jObject = jarray.getJSONObject(i);

                // Point만 추출
                if(jObject.getJSONObject("geometry").getString("type").equals("Point")){
                    // jProperties : jarray안에 있는 각 object의 properties object
                    JSONObject jProperties = jObject.getJSONObject("properties");

                    index = jProperties.optString("index");
                    viaPointId = jProperties.optString("viaPointId");
                    viaPointName = jProperties.optString("viaPointName");
                    viaDetailAddress = jProperties.optString("viaDetailAddress");
                    groupKey = jProperties.optString("groupKey");
                    arriveTime = jProperties.optString("arriveTime");
                    completeTime = jProperties.optString("completeTime");
                    distance = jProperties.optString("distance");
                    deliveryTime = jProperties.optString("deliveryTime");
                    waitTime = jProperties.optString("waitTime");
                    pointType = jProperties.optString("pointType");


                    map.put("index",index);
                    map.put("viaPointId",viaPointId);
                    map.put("viaPointName",viaPointName);
                    map.put("viaDetailAddress",viaDetailAddress);
                    map.put("groupKey",groupKey);
                    map.put("arriveTime",arriveTime);
                    map.put("completeTime",completeTime);
                    map.put("distance",distance);
                    map.put("deliveryTime",deliveryTime);
                    map.put("waitTime",waitTime);
                    map.put("pointType",pointType);

                    objList.add(map);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 위도, 경도 추가
        // 출발, 도착 위치는 제외
        for (int i=1; i<objList.size()-1; i++){
            objList.get(i).put("longitude", viaPointList.get(i-1).getViaX());
            objList.get(i).put("latitude", viaPointList.get(i-1).getViaY());
        }

        return objList;
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
            sendMsg = "route=" + strings[0];

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
                Log.i("error",receiveMsg);
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

