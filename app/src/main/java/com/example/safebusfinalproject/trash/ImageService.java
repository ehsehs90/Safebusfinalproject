package com.example.safebusfinalproject.trash;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.safebusfinalproject.MainActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageService extends Service {
    private String line;

    public ImageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // onCreate()안에 onStartCommand method를 자동호출
        // 서비스에서 사용할 resource 준비작업
        Log.i("PredictImageService", "onCreate()호출!!");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 하는 로직처리
        Log.i("PredictImageService", "onStartCommand()호출!!");

        if(intent == null){
            //강제종료되서 안드로이드 시스템에서 강제로 재시작한 케이스
            Intent resultIntent = new Intent(getApplicationContext(),
                    MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            resultIntent.putExtra("ServiceToActivityData",
                    "새로운 Activity가 생성되었어요!");

            startActivity(resultIntent);
        }else{

            // 로직처리가 진행! -> Activity에게
            // 전달해야 하는 최종 결과 데이터
            Intent resultIntent = new Intent(getApplicationContext(),
                    ImageActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Uri uri = intent.getParcelableExtra("IMG_URI");
            final String realPath = intent.getStringExtra("IMG_URI");
            Log.i("스마트폰realPath",realPath.toString());
            //String url = getFilePathForN(uri, PredictImageService.this);
            //Log.i("Predict",url);


            Thread t =new Thread(){
                public void run() {
                    String url = "http://70.12.115.54:8090/sendmsg/NewFile.jsp";
                    HttpFileUpload(url,"",realPath);
                    Log.i("스마트폰realPath로 넘길게요",realPath);

                    //C:\review\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\sendMsg2\sendMsg2\a 여기 저장됨
                }

            };

            try {
                t.start();
                try{
                    t.join();
                }catch(Exception e){
                    e.printStackTrace();
                }
               // Log.i("하리보는 흰색이 맛있쪙", line);
                //resultIntent.putExtra("realPath", realPath);
                //Log.i("하리보는 흰색이 맛있쪙", line);
            }catch (Exception e){
            }

            resultIntent.putExtra("ServiceToActivityData",
                   "새로운 Activity가 생성되었어요!");
            startActivity(resultIntent);
        }
        //return Service.START_NOT_STICKY; //강제종료되면 자동적으로 재시작 안함.
        return Service.START_STICKY; //강제종료되면 자동적으로 재시작.
    }

    @Override
    public void onDestroy() {
        // resource 해제작업
        super.onDestroy();
        Log.i("ServiceExam", "onDestroy()호출!!");
    }

    //Http에 파일업로드 하는 부분
    //
    public void HttpFileUpload(String urlString, String params, String fileName) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        try {
            File sourceFile = new File(fileName);
            DataOutputStream dos;
            if (!sourceFile.isFile()) {
                Log.i("uploadFile", "Source File not exist :" + fileName);
            } else {
                Log.i("Predict", "connection준비");
                FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                URL connectUrl = new URL(urlString);
                // open connection
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                // write data
                dos = new DataOutputStream(conn.getOutputStream());
                Log.i("Predict",""+conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                Log.i("Http_writedata_filename",fileName);
                Log.i("Http_writedata_lineend",lineEnd);
                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }
              //  Log.i("Predict","11");

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                mFileInputStream.close();
               /// Log.i("Predict","22");

                dos.flush(); // finish upload...

                mFileInputStream.close();
                dos.close();
                Log.i("Predict", "HTTPTPTPTP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}