package com.example.safebusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safebusfinalproject.mapVO.ViaPointVO;
import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;


public class MapViewActivity extends AppCompatActivity {
    // 마커 아이콘
    Bitmap bitmap;
    Bitmap startbit;
    Bitmap endbit;
    Bitmap nowbit;
    TMapMarkerItem nowItem;


    TextView res_textview;
    LinearLayout Tmap;
    TMapView tMapView;
    TMapTapi tMapTapi;
    List<String> arriveTimes = new ArrayList<String>();



    String seat="absent";
    class LoginDB extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String result;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                String description;

                // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
                //URL url = new URL("http://70.12.115.78:80/bustest2/login.do");
                URL url = new URL("http://70.12.115.78:80/safebus/absent.do");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                description = "hello";
                // 전송할 데이터. GET 방식으로 작성
                sendMsg = "station=" + strings[0] + "&pw=" + strings[1];
                Log.i("absent",sendMsg);

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
                    Log.i("absent",receiveMsg);


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
            //return null;
        }
    }

    class LoginDB2 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String result;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                String description;

                // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
                //URL url = new URL("http://70.12.115.78:80/bustest2/login.do");
                URL url = new URL("http://70.12.115.78:80/safebus/present.do");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                description = "hello";
                // 전송할 데이터. GET 방식으로 작성
                sendMsg = "station=" + strings[0] + "&pw=" + strings[1];
                Log.i("present",sendMsg);

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
                    Log.i("present",receiveMsg);


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
            //return null;
        }
    }

    class Logout extends AsyncTask<Void, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String str;

                //URL url = new URL("http://70.12.115.78:80/bustest2/logout.do");
                URL url = new URL("http://70.12.115.78:80/safebus/logout.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    // jsp에서 보낸 값을 받는 부분
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("DBtest", receiveMsg);

                } else {
                    // 통신 실패
                    Log.i("error", receiveMsg);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }
    }



    class DrawPolyLine implements Runnable {

        private TMapPoint start;
        private TMapPoint end;
        private ArrayList<TMapPoint> passList;
        private String state;

        Date starttime;
        String arrivetime;

        String startMsg = "";
        String endMsg = "";

        public DrawPolyLine(TMapPoint start, TMapPoint end, ArrayList<TMapPoint> passList, String state, Date starttime) {
            this.start = start;
            this.end = end;
            this.passList = passList;
            this.state = state;
            this.starttime = starttime;
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

/*            //1. 출발시간
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            starttime = formatter.format(date);*/
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
            SimpleDateFormat formatter = new SimpleDateFormat("MM월 dd일 HH시 mm분");

            final String startTime = formatter.format(starttime);

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
                    String msg = startMsg + " : "+ startTime + "\n"+ endMsg + " : " + arrivetime;
                    res_textview.setText(msg);
                    //res_textview.setText(arriveTime);
                }
            });
        }
    }

    class TMapNav implements Runnable{
        private String state; //등.하원 구분
        private String station_num;
        private Date start_time;

        public TMapNav(String station_num, Date start_time, String state) {
            this.station_num = station_num;
            this.start_time = start_time;
            this.state = state;
        }

        public void run(){
            try {
                //state ="gohome"; //하원
                //state ="gokinder"; //등원
                //station_num = "5";
                ArrayList<HashMap> result;
                RequestHttpURLConnection requestHttpURLConnection
                        = new RequestHttpURLConnection();


                SimpleDateFormat sdtime = new SimpleDateFormat("yyyyMMddHHmm");
                String starttime = sdtime.format(start_time);
                Log.d("sisisisisi",starttime);
                result = requestHttpURLConnection.request(state,station_num, starttime);

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

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(start_time);
                    String startTime = formatter.format(start_time);
                    cal.add(Calendar.SECOND, totalTime);
                    String arrivetime = formatter.format(cal.getTime());

                    TMapLine tMapline = new TMapLine(start_lat,start_long,end_lat,end_long, startTime, arrivetime, state);
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

                    DrawPolyLine drawPolyLine = new DrawPolyLine(start,end,passList,state,start_time);
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


        Intent intent = getIntent(); /*데이터 수신*/
        Bundle bundle = intent.getExtras();
        final String carNum = bundle.getString("carNumber");
        String station = bundle.getString("station");
        String state = "";


        Button businfoBtn = (Button) findViewById(R.id.businfoBtn);      //버스정보 activity
        businfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBusInfo = new Intent(MapViewActivity.this, NewClient2Activity.class);
                //Intent goBusInfo = new Intent();
                goBusInfo.putExtra("carNum", carNum);
                startActivity(goBusInfo);
            }
        });


        Button nowLoc = (Button) findViewById(R.id.nowloc);
        // 마커 아이콘
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker2);
        startbit = BitmapFactory.decodeResource(getResources(), R.drawable.start);
        endbit = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        nowbit = BitmapFactory.decodeResource(getResources(), R.drawable.bus);

        Tmap = (LinearLayout) findViewById(R.id.tmap_view);
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("39b46490e6b56fa0382ead666ea1a95d921b9915");
        tMapView.setCenterPoint(127.036174, 37.500138); //강남파이낸스 센터

        // 강남파이낸스 근처에 있으면 유치원에서 출발, 아니면 도착점
        TMapPoint sPoint = new TMapPoint(0, 0);
        LocationURLConnection locationURLConnection =
                new LocationURLConnection();
        Log.d("sisisisi", "hi");
        try {
            sPoint = locationURLConnection.getNowInfo(carNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Double sPointX = sPoint.getLongitude();
        Double sPointY = sPoint.getLatitude();

        //Double sPointX = 127.036174;
        //Double sPointY = 37.500138;
        if (sPointX >= 127.035680 && sPointX <= 127.037660 && sPointY >= 37.499000 && sPointY <= 37.500620) { //유치원에서 출발
            state = "gohome";
            Log.d("sisisisi", state);
        } else {
            state = "gokinder";
            Log.d("sisisisi", state);
        }

        //1. 출발시간(db에서 가져오던가 can에서 바로 가져오던가)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        //String starttime = formatter.format(new Date());
        Date starttime = new Date();

        //Log.d("sisisisisi", starttime);

        TMapNav tmapnav = new TMapNav(station, starttime, state);
        Thread tmapThread = new Thread(tmapnav);
        tmapThread.start();

        nowLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 누르면 현재 위치 마커로 표시
                TMapPoint nowPoint = new TMapPoint(0, 0);
                LocationURLConnection locationURLConnection =
                        new LocationURLConnection();
                //carNum = "123가456";
                Log.d("sisisisi", "hi");
                try {
                    nowPoint = locationURLConnection.getNowInfo(carNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //nowPoint.setLatitude(37.501572);
                //nowPoint.setLongitude(127.039659);

                //시작점, 도착점 마커찍기
                tMapView.removeMarkerItem("nowItem");
                nowItem = new TMapMarkerItem();
                nowItem.setIcon(nowbit); // 마커 아이콘 지정
                nowItem.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
                nowItem.setTMapPoint(nowPoint); // 마커의 좌표 지정
                nowItem.setName("nPoint"); // 마커의 타이틀 지정
                tMapView.addMarkerItem("nowItem", nowItem); // 지도에 마커 추가
            }
        });

        Tmap.addView(tMapView);

        // 메세지
        final Button messageBtn = findViewById(R.id.messageBtn);
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                        "com.example.safebusfinalproject.SendMSGActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        // 환경설정
        final Button settingBtn = findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                        "com.example.safebusfinalproject.ActSettings");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        // 좌석
        final Button seatBtn = findViewById(R.id.seatBtn);
        seatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                        "com.example.safebusfinalproject.SeatActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        final Button LogoutBtn = findViewById(R.id.LogoutBtn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {

            String result;

            @Override
            public void onClick(View v) {
                try {


                    MapViewActivity.Logout logout = new MapViewActivity.Logout();

                    result = logout.execute().get();

                    if (result.equals("logout")){
                        Log.i("DBtest","토스트");
                        Toast.makeText(MapViewActivity.this,
                                "로그아웃 성공!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("DBtest","토스트실패");
                        Toast.makeText(MapViewActivity.this,
                                "로그아웃 실패!",
                                Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent();
                    ComponentName cname = new ComponentName("com.example.safebusfinalproject",
                            "com.example.safebusfinalproject.LoginActivity");
                    i.setComponent(cname);

                    startActivity(i);


                }catch (Exception e){
                    Log.i("DBtest", "Logout Error!");
                }

            }
        });



        // station 받아온다
        final String st = station;

        final Button absentBtn = (Button) findViewById(R.id.absentBtn);
        absentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (seat.equals("absent")) {
                    String result = "";
                    String resultAbsent;
                    String station = st;  // st = Login 할때 받은 station 정보
                    String pw = "1";

                    MapViewActivity.LoginDB logindb = new MapViewActivity.LoginDB();

                    try {

                        result = logindb.execute(station, pw).get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resultAbsent = result;


                    if (resultAbsent.equals("success")) {
                        Log.i("resultAbsent", "결석 성공");
                        Toast.makeText(MapViewActivity.this,
                                "결석 성공!",
                                Toast.LENGTH_SHORT).show();

                        absentBtn.setText("결석");

                    } else {
                        Log.i("resultAbsent", "결석 실패");
                        Toast.makeText(MapViewActivity.this,
                                "결석 실패!",
                                Toast.LENGTH_SHORT).show();
                    }
                    seat = "present";
                    Log.i("seat", seat);

                } else if (seat.equals("present")) {
                    String result = "";
                    String resultAbsent;
                    String station = st; // st = Login 할때 받은 station 정보
                    String pw = "1";


                    MapViewActivity.LoginDB2 logindb2 = new MapViewActivity.LoginDB2();

                    try {

                        result = logindb2.execute(station, pw).get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resultAbsent = result;


                    if (resultAbsent.equals("success")) {
                        Log.i("resultAbsent", "결석취소 성공");
                        Toast.makeText(MapViewActivity.this,
                                "결석취소 성공!",
                                Toast.LENGTH_SHORT).show();

                        absentBtn.setText("출석");

                    } else {
                        Log.i("resultAbsent", "결석취소 실패");
                        Toast.makeText(MapViewActivity.this,
                                "결석취소 실패!",
                                Toast.LENGTH_SHORT).show();
                    }
                    seat = "absent";
                    Log.i("seat", seat);
                }

            }
        });

    }
}
