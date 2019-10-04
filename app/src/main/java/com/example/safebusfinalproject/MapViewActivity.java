package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


public class MapViewActivity extends AppCompatActivity {
    // 마커 아이콘
    Bitmap bitmap;
    Bitmap startbit;
    Bitmap endbit;

    TextView res_textview;
    LinearLayout Tmap;
    TMapView tMapView;
    TMapTapi tMapTapi;
    List<String> arriveTimes = new ArrayList<String>();

    class DrawPolyLine implements Runnable {

        private TMapPoint start;
        private TMapPoint end;
        private ArrayList<TMapPoint> passList;
        private String state;

        String starttime;
        String arrivetime;

        String startMsg = "";
        String endMsg = "";

        public DrawPolyLine(TMapPoint start, TMapPoint end, ArrayList<TMapPoint> passList, String state) {
            this.start = start;
            this.end = end;
            this.passList = passList;
            this.state = state;
        }

        public void run(){
            // 경로 그리기
            try {
                TMapData tmapdata = new TMapData();
                TMapPolyLine tMapPolyLine = new TMapPolyLine();
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(3);
                Log.d("uuuuuuu", start.toString());
                Log.d("uuuuuuu", end.toString());
                for(int i=0; i<passList.size(); i++){
                    Log.d("uuuuuuu", passList.get(i).toString());
                }
                tMapPolyLine = tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, start, end, passList, 0);

                tMapView.addTMapPolyLine("Line1", tMapPolyLine);

            }catch(Exception e) {
                e.printStackTrace();
            }


/*            TMapData startdata = new TMapData();
            TMapData enddata = new TMapData();

            TMapPoint endpoint2 = new TMapPoint(passList.get(0).getLatitude(), passList.get(0).getLongitude());
            TMapPoint startpoint2 = new TMapPoint(passList.get(passList.size()-1).getLatitude(), passList.get(passList.size()-1).getLongitude());
            try {
                TMapPolyLine startline = startdata.findPathData(start, endpoint2);
                TMapPolyLine endline = enddata.findPathData(startpoint2, end);
                tMapView.addTMapPolyLine("Line"+"start", startline);
                tMapView.addTMapPolyLine("Line"+"end", endline);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            try {

                Log.d("ttttttttt",passList.toString());

                for(int i=0; i<passList.size()-1; i++){
                    //경로
                    TMapData tmapdata = new TMapData();
                    TMapPoint startpoint = new TMapPoint(passList.get(i).getLatitude(), passList.get(i).getLongitude());
                    TMapPoint endpoint = new TMapPoint(passList.get(i+1).getLatitude(), passList.get(i+1).getLongitude());
                    Log.d("tttttttt",startpoint.toString()+"  "+endpoint.toString());
                    TMapPolyLine polyline = tmapdata.findPathData(startpoint, endpoint);
                    tMapView.addTMapPolyLine("Line"+i, polyline);
                }
                //Tmap.addView(tMapView);
            }catch (Exception e){
                e.printStackTrace();
            }*/


            TMapMarkerItem startItem = new TMapMarkerItem();
            TMapPoint startPoint = start;
            startItem.setIcon(startbit); // 마커 아이콘 지정
            startItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
            startItem.setTMapPoint( startPoint ); // 마커의 좌표 지정
            startItem.setName("sPoint"); // 마커의 타이틀 지정
            tMapView.addMarkerItem("startItem", startItem); // 지도에 마커 추가
            //도착점
            TMapMarkerItem endItem = new TMapMarkerItem();
            TMapPoint endPoint = end;
            endItem.setIcon(endbit); // 마커 아이콘 지정
            endItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
            endItem.setTMapPoint( endPoint ); // 마커의 좌표 지정
            endItem.setName("ePoint"); // 마커의 타이틀 지정
            tMapView.addMarkerItem("endItem", endItem); // 지도에 마커 추가
            try {
                //마커찍기
                for (int i=0; i<passList.size(); i++) {

                    TMapMarkerItem markerItem = new TMapMarkerItem();
                    TMapPoint tMapPoint = passList.get(i);

                    markerItem.setIcon(bitmap); // 마커 아이콘 지정
                    markerItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
                    markerItem.setTMapPoint( tMapPoint ); // 마커의 좌표 지정
                    markerItem.setName("markerItem"+i); // 마커의 타이틀 지정
                    tMapView.addMarkerItem("markerItem"+i, markerItem); // 지도에 마커 추가
            }
            }catch (Exception e){
                e.printStackTrace();
            }


            //시간출력
            if(state.equals("gohome")){
                startMsg = "유치원출발시간";
                endMsg = "집도착예정시간";
            }else if(state.equals("gokinder")){
                startMsg = "집출발시간";
                endMsg = "유치원도착예정시간";
            }

            //1. 출발시간
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            starttime = formatter.format(date);

            //2. 도착시간
            String arrive= arriveTimes.get(arriveTimes.size()-1);
            DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date tempDate = null;
            try {
                tempDate = sdFormat.parse(arrive);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //SimpleDateFormat formatter=new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
            arrivetime = formatter.format(tempDate);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    res_textview = (TextView)findViewById(R.id.res_textview);
                    String msg = startMsg + " : "+ starttime + "\n"+ endMsg + " : " + arrivetime;
                    res_textview.setText(msg);
                    //res_textview.setText(arriveTime);
                }
            });
        }
    }

    class TMapNav implements Runnable{
        private String state; //등.하원 구분
        private String station_num;

        public TMapNav() {

        }

        public void run(){
            try {
                state ="gokinder"; //하원
                //state ="gokinder"; //등원
                station_num = "5";
                ArrayList<HashMap> result;
                RequestHttpURLConnection requestHttpURLConnection
                        = new RequestHttpURLConnection();
                result = requestHttpURLConnection.request(state,station_num);

                Double start_lat = Double.valueOf(result.get(0).get("latitude").toString());
                Double start_long = Double.valueOf(result.get(0).get("longitude").toString());
                Double end_lat = Double.valueOf(result.get(result.size()-1).get("latitude").toString());
                Double end_long = Double.valueOf(result.get(result.size()-1).get("longitude").toString());

                Log.d("eeeeeeeeeeee","start_lat"+start_lat+"");
                Log.d("eeeeeeeeeeee","start_long"+start_long+"");
                Log.d("eeeeeeeeeeee","end_lat"+end_lat+"");
                Log.d("eeeeeeeeeeee","end_long"+end_long+"");
                ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();

                if(result.size() == 2){ //경유지 없는 list
                    // 총 거리, 총 걸린시간
                    Log.d("kkkkkk", result.get(1).get("totalDistance").toString());
                    Log.d("kkkkkk", result.get(1).get("totalTime").toString());
                    int totalTime = Integer.valueOf(result.get(1).get("totalTime").toString());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    String starttime = formatter.format(date);
                    Log.d("kkkkk",starttime);
                    cal.add(Calendar.SECOND, totalTime);
                    String arrivetime = formatter.format(cal.getTime());
                    Log.d("kkkkk",arrivetime);

                    TMapLine tMapline = new TMapLine(start_lat,start_long,end_lat,end_long, starttime, arrivetime, state);
                    Thread tmapLineThread = new Thread(tMapline);
                    tmapLineThread.start();
                }else{
                    for (int i=1; i<result.size()-1; i++){
                        TMapPoint point = new TMapPoint(Double.valueOf(result.get(i).get("latitude").toString()),
                                Double.valueOf(result.get(i).get("longitude").toString()));
                        Log.d("eeeettteeeeee",point.toString());

                        arriveTimes.add(result.get(i).get("arriveTime").toString());
                        passList.add(point);
                    }


                    // 최종 도착시간
                    arriveTimes.add(result.get(result.size()-1).get("arriveTime").toString());
                    Log.d("eeeeeeeeee",arriveTimes.toString());


                    // 출발, 도착 위치
                    TMapPoint start = new TMapPoint(start_lat,start_long);
                    TMapPoint end = new TMapPoint(end_lat,end_long);

                    DrawPolyLine drawPolyLine = new DrawPolyLine(start,end,passList,state);
                    Thread t = new Thread(drawPolyLine);
                    t.start();
                }


            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    class TMapLine implements Runnable{
        TMapPoint startpoint;
        TMapPoint endpoint;

        Double start_lat;
        Double start_long;
        Double end_lat;
        Double end_long;
        String starttime;
        String arrivetime;
        String state;

        String startMsg = "";
        String endMsg = "";


        public TMapLine(Double start_lat, Double start_long, Double end_lat, Double end_long, String starttime, String arrivetime, String state) {
            this.start_lat = start_lat;
            this.start_long = start_long;
            this.end_lat = end_lat;
            this.end_long = end_long;
            this.starttime = starttime;
            this.arrivetime = arrivetime;
            this.state = state;
        }

        public void run(){
            try {
                TMapData tmapdata = new TMapData();
                startpoint = new TMapPoint(start_lat,start_long);
                endpoint = new TMapPoint(end_lat,end_long);

                TMapPolyLine polyline = tmapdata.findPathData(startpoint, endpoint);
                tMapView.addTMapPolyLine("Line1111", polyline);

                Log.d("eeeeeeeetdtete", start_lat+"  "+start_long);
                Log.d("eeeeeeeetdtete", end_lat+"  "+end_long);

                if(state.equals("gohome")){
                    startMsg = "유치원출발시간";
                    endMsg = "집도착예정시간";
                }else if(state.equals("gokinder")){
                    startMsg = "집출발시간";
                    endMsg = "유치원도착예정시간";
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        res_textview = (TextView)findViewById(R.id.res_textview);
                        String msg = startMsg + " : "+ starttime + "\n"+ endMsg + " : " + arrivetime;
                        res_textview.setText(msg);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            //시작점, 도착점 마커찍기
            TMapMarkerItem startItem = new TMapMarkerItem();
            TMapPoint startPoint = startpoint;
            startItem.setIcon(startbit); // 마커 아이콘 지정
            startItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
            startItem.setTMapPoint( startPoint ); // 마커의 좌표 지정
            startItem.setName("sPoint"); // 마커의 타이틀 지정
            tMapView.addMarkerItem("startItem", startItem); // 지도에 마커 추가
            //도착점
            TMapMarkerItem endItem = new TMapMarkerItem();
            TMapPoint endPoint = endpoint;
            endItem.setIcon(endbit); // 마커 아이콘 지정
            endItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
            endItem.setTMapPoint( endPoint ); // 마커의 좌표 지정
            endItem.setName("ePoint"); // 마커의 타이틀 지정
            tMapView.addMarkerItem("endItem", endItem); // 지도에 마커 추가
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Tmap = (LinearLayout)findViewById(R.id.tmap_view);
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("b7dc6f07-a787-4f42-9d8d-c42b9eee47a1");

        //tMapView.setSKTMapApiKey("87e1a7c5-b8fc-4078-948b-c3c9f00927e1");
        tMapView.setCenterPoint( 127.036174,37.500138); //강남파이낸스 센터
        //tMapTapi = new TMapTapi(this);

        // 마커 아이콘
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker2);
        startbit = BitmapFactory.decodeResource(getResources(), R.drawable.start);
        endbit = BitmapFactory.decodeResource(getResources(), R.drawable.end);


        TMapNav tmapnav = new TMapNav();
        Thread tmapThread = new Thread(tmapnav);
        tmapThread.start();

        Tmap.addView(tMapView);
    }
}
