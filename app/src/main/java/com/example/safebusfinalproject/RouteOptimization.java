package com.example.safebusfinalproject;

import android.util.Log;

import com.example.safebusfinalproject.mapVO.ViaPointVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteOptimization {
    ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;


    String reqCoordType = "WGS84GEO";
    String resCoordType = "WGS84GEO";
    int tollgateFareOption = 1;
    int roadType = 32;
    int directionOption = 0;
    String endPoiId = "67516";
    int gpsTime = 10000;
    int angle = 90;
    int speed = 60;
    int uncetaintyP = 3;
    int uncetaintyA = 3;
    int uncetaintyAP = 12;
    int camOption = 0;
    int carType = 0;
    String startName = "start";
    String endName = "end";
    int searchOption = 0;
    int totalValue = 2;


    public ArrayList<HashMap> setConn(ViaPointVO startPoint, ViaPointVO endPoint) {
        startX = Double.valueOf(startPoint.getViaX());
        startY = Double.valueOf(startPoint.getViaY());
        endX = Double.valueOf(endPoint.getViaX());
        endY = Double.valueOf(endPoint.getViaY());

        Log.d("wwwwwwwwwwwww",startX+"  "+startY+"  "+endX+"  "+endY);

        // tmap api server 연결
        URL url = null;
        try {
            url = new URL("https://apis.openapi.sk.com/tmap/routes?version=1&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("appKey","39b46490e6b56fa0382ead666ea1a95d921b9915");
            conn.setRequestProperty("Accept-Language","ko");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            String body = "endX="+endX
                    +"&endY="+endY
                    +"&startX="+startX
                    +"&startY="+startY
                    +"&reqCoordType="+reqCoordType
                    +"&resCoordType="+resCoordType
                    +"&tollgateFareOption="+tollgateFareOption
                    +"&roadType="+roadType
                    +"&directionOption="+directionOption
                    +"&angle="+angle
                    +"&speed="+speed
                    +"&uncetaintyP="+uncetaintyP
                    +"&carType="+carType
                    +"&startName="+startName
                    +"&endName="+endName
                    +"&searchOption="+searchOption
                    +"&totalValue="+totalValue;

            // 전송
            OutputStream os = conn.getOutputStream();

            try {
                //보낼거
                Log.d("connection", body);
                os.write(body.getBytes("UTF-8"));
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
                jsonList = jsonListParser(jsonObject, startPoint, endPoint);

                // 닫기
                os.close();
                return jsonList;

            } catch (
                    MalformedURLException e) {
                Log.d("ssssss","잘못된 url");
                e.printStackTrace();
            } catch (
                    ProtocolException e) {
                Log.d("ssssss","잘못된 url");
                e.printStackTrace();
            } catch (
                    UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("ssssss","인코딩");
            } catch (
                    IOException e) {
                Log.d("ssssss","입출력문제");
                e.printStackTrace();
                Log.d("ssssss", e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    // tmap api server에서 경로탐색 결과 받기
    public static JSONObject readJsonFromUrl(HttpURLConnection conn) throws IOException {
        String jsonText = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));


        //Log.d("line:",br.toString());

        String line = null;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
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


    public ArrayList<HashMap> jsonListParser(JSONObject jsonObject, ViaPointVO startPoint, ViaPointVO endPoint) {

        String totalDistance;
        String totalTime;

        ArrayList<HashMap> objList = new ArrayList<>();
        try {
            HashMap start = new HashMap();
            start.put("longitude", startPoint.getViaX());
            start.put("latitude", startPoint.getViaY());
            objList.add(start);


            HashMap map = new HashMap();
            JSONObject jObject = jsonObject.getJSONArray("features").getJSONObject(0);
            JSONObject jProperties = jObject.getJSONObject("properties");

            totalDistance = jProperties.optString("totalDistance");
            totalTime = jProperties.optString("totalTime");

            map.put("longitude", endPoint.getViaX());
            map.put("latitude", endPoint.getViaY());
            map.put("totalDistance",totalDistance);
            map.put("totalTime",totalTime);
            objList.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objList;
    }


}
