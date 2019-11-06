package com.example.safebusfinalproject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.safebusfinalproject.mapVO.ViaPointVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class RouteOptimizationVia {
    private ViaPointVO startPoint;
    private ViaPointVO endPoint;
    private String starttime;

    public RouteOptimizationVia() {
    }

    public RouteOptimizationVia(ViaPointVO startPoint, ViaPointVO endPoint, String starttime) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.starttime = starttime;
    }

    ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들
    ArrayList<ViaPointVO> viaPointList; //경유지 ArrayList

    public ArrayList<HashMap> setConn(String state, JSONArray result) {

        JSONObject sendJsonObj = makeViaList(state, result);
        Log.d("ttttttttt3",sendJsonObj.toString());


        // tmap api server 연결
        URL url = null;
        try {
            url = new URL("https://apis.openapi.sk.com/tmap/routes/routeOptimization10?version=1&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("appKey","39b46490e6b56fa0382ead666ea1a95d921b9915");
            conn.setRequestProperty("Content-type","application/json");


            // 전송
            OutputStream os = conn.getOutputStream();

            try {
                os.write(sendJsonObj.toString().getBytes("UTF-8"));
                os.flush();

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
                jsonList = jsonListParser(jsonObject,state);

                //시작점 추가
                jsonList.get(0).put("longitude", startPoint.getViaX());
                jsonList.get(0).put("latitude", startPoint.getViaY());
                //도착점 추가
                jsonList.get(jsonList.size()-1).put("longitude", endPoint.getViaX());
                jsonList.get(jsonList.size()-1).put("latitude", endPoint.getViaY());

                for (HashMap item: jsonList ) {
                    Log.d("aaaaaaaa", item.get("viaPointName").toString()+" : "+item.get("viaDetailAddress").toString());
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonList;
    }


    public JSONObject makeViaList(String state,JSONArray viaarray) {
        int startIdx = 0;
        int viaSize = 0;

        //경유지ArrayList
        JSONArray viaArray = viaarray;

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
                viaVo.setViaTime(60); //경유지 상하자 소요시간
                viaVo.setViaPointId(jObject.getString("station"));
                viaPointList.add(viaVo);
                Log.d("hhhhhhhhhh",viaVo.getViaDetailAddress());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*************************출발,도착점 셋팅*****************************************/

        if(state.equals("gohome")){ //하원시
            int size = viaPointList.size();
            endPoint.setViaX(viaPointList.get(size-1).getViaX());
            endPoint.setViaY(viaPointList.get(size-1).getViaY());

            startIdx = 0;
            viaSize = viaPointList.size()-1;
        }else if(state.equals("gokinder")){
            startPoint.setViaX(viaPointList.get(0).getViaX());
            startPoint.setViaY(viaPointList.get(0).getViaY());
            startIdx = 1;
            viaSize = viaPointList.size();
            Log.d("eeeeeeeeee","sssssssss");
            Log.d("eeeeeeeeee",viaPointList.toString());
        }
        /*************************출발,도착점 셋팅 끝*****************************************/



        // 경유지 ArrayList -> tmap api 서버로 보낼 jsonObject type으로 만들기
        // tmap서버로 보낼 json object 생성
        JSONObject vObj = new JSONObject();
        try{
            JSONArray vArray = new JSONArray();
            for(int i=startIdx; i<viaSize; i++){
                JSONObject vObject = new JSONObject();
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
            vObj.put("startX",startPoint.getViaX());
            vObj.put("startY",startPoint.getViaY());

            //SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
            //String startTime = time.format(new Date());
            vObj.put("startTime",starttime);
            vObj.put("endName","도착");
            vObj.put("endX",endPoint.getViaX());
            vObj.put("endY",endPoint.getViaY());
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

    public ArrayList<HashMap> jsonListParser(JSONObject jsonObject, String state) {

        String index = null;
        String viaPointId = null;
        String viaPointName = null;
        String viaDetailAddress = null; //경유지
        String groupKey = null; //경유지
        String arriveTime = null;
        String completeTime = null;
        String distance = null;
        String deliveryTime = null; //상하차 소요시간
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
                    //map.put("groupKey",groupKey);
                    map.put("arriveTime",arriveTime);
                    map.put("completeTime",completeTime);
                    map.put("distance",distance);
                    map.put("deliveryTime",deliveryTime);
                    //map.put("waitTime",waitTime);
                    map.put("pointType",pointType);

                    objList.add(map);
                    Log.d("gggggggtt", map.toString());

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 위도, 경도 추가
        // 출발, 도착 위치는 제외
        if(state.equals("gohome")){
            for (int i=1; i<objList.size()-1; i++){
                objList.get(i).put("longitude", viaPointList.get(i-1).getViaX());
                objList.get(i).put("latitude", viaPointList.get(i-1).getViaY());
            }

        }else if(state.equals("gokinder")){
            for (int i=1; i<objList.size()-1; i++){
                objList.get(i).put("longitude", viaPointList.get(i).getViaX());
                objList.get(i).put("latitude", viaPointList.get(i).getViaY());
            }
        }

        return objList;
    }

}



