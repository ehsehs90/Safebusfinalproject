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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


public class MapViewActivity extends AppCompatActivity {

    TextView res_textview;
    LinearLayout Tmap;
    TMapView tMapView;
    TMapTapi tMapTapi;

    class DrawPolyLine extends Thread{

        private TMapPoint start;
        private TMapPoint end;
        private ArrayList<TMapPoint> passList;

        public DrawPolyLine(TMapPoint start, TMapPoint end, ArrayList<TMapPoint> passList) {
            this.start = start;
            this.end = end;
            this.passList = passList;
        }

        public void run(){
            // 경로 그리기
            try {
                TMapData tmapdata = new TMapData();
                TMapPolyLine tMapPolyLine = new TMapPolyLine();
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(3);
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


            // 마커 아이콘
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker2);
            Bitmap startbit = BitmapFactory.decodeResource(getResources(), R.drawable.start);
            Bitmap endbit = BitmapFactory.decodeResource(getResources(), R.drawable.end);

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

        }
    }

    class TMapNav extends Thread{
        private String url;

        public TMapNav() {

        }

        public void run(){
            try {
                ArrayList<HashMap> result;
                RequestHttpURLConnection requestHttpURLConnection
                        = new RequestHttpURLConnection();
                result = requestHttpURLConnection.request(url);
                ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();

                for (int i=1; i<result.size()-1; i++){
                    TMapPoint point = new TMapPoint(Double.valueOf(result.get(i).get("latitude").toString()),
                                                        Double.valueOf(result.get(i).get("longitude").toString()));
                    passList.add(point);
                }


                // 출발, 도착 위치
                TMapPoint start = new TMapPoint(37.687624,126.720064);
                TMapPoint end = new TMapPoint(37.487888,127.036301);

                DrawPolyLine drawPolyLine = new DrawPolyLine(start,end,passList);
                drawPolyLine.start();

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Tmap = (LinearLayout)findViewById(R.id.tmap_view);
        tMapView = new TMapView(this);

        //tMapView.setSKTMapApiKey("b7dc6f07-a787-4f42-9d8d-c42b9eee47a1");

        tMapView.setSKTMapApiKey("87e1a7c5-b8fc-4078-948b-c3c9f00927e1");
        tMapView.setCenterPoint(127.0393648, 37.5014303); //멀캠 위치


        tMapTapi = new TMapTapi(this);

        boolean isTmapApp = tMapTapi.isTmapApplicationInstalled();

        TMapNav tmapnav = new TMapNav();
        tmapnav.start();

        Tmap.addView(tMapView);
    }
}
