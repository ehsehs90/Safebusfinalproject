//package com.example.safebusfinalproject;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//public class NewClientActivity extends AsyncTask{
//
//    protected static   String SERV_IP      =   "70.12.115.53"; //server ip
//    protected static   int        PORT      =   8090;
//    String tmp = null;
//
//    @Override
//    protected Object doInBackground(Object... params) {
//        // TODO Auto-generated method stub
//
//        try {
//            Log.d("TCP","server connecting");
//            InetAddress serverAddr = InetAddress.getByName(SERV_IP);
//            Socket sock = new Socket(serverAddr,PORT);
//
//            DataInputStream      input      =   new DataInputStream(sock.getInputStream());
//            DataOutputStream   output   =   new DataOutputStream(sock.getOutputStream());
//            OutputStreamWriter osw = new OutputStreamWriter(sock.getOutputStream());
//
//
//            try{
//                //   데이터 송신 부분!
//                //Log.i("output",output.toString());
//                //output.write();
//                //WriteSocket(output);
//                tmp = "babyname=" + "gurwls" + "&babygender=" + "man";
//                Log.i("tmp",tmp);
//                osw.write(tmp);
//                osw.flush();
//
//            } catch(IOException e){
//                Log.e("TCP","don't send message!");
//                e.printStackTrace();
//            }
//
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch(IOException   e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
////    public void WriteSocket(DataOutputStream data) throws IOException{
////        //   data send
////        data.write('a');
////    }
//    public void ReadSock(DataInputStream   data) throws IOException{
//        //   data recieve
//        byte[] datafile = null;
//
//        data.read(datafile);
//
//    }
//}
package com.example.safebusfinalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safebusfinalproject.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Struct;

public class NewClientActivity extends AppCompatActivity {

    private String msgFromServer;
    private String result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        final EditText et = (EditText) findViewById(R.id.EditText01);
        final Button btn = (Button) findViewById(R.id.infoBtn);

        Intent intent = getIntent(); /*데이터 수신*/
        String carNum = intent.getStringExtra("carNum");
        Log.d("cccccc",carNum);

        et.setText(carNum);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if (et.getText().toString() != null ||
                            !et.getText().toString().equals("")) {

                        final String serverIP = "70.12.115.53";
                        final String serverPort = "8090";

                        // 에디터 창에 입력된 데이터를 서버에 보낸다.
                        TCPclient tp = new TCPclient(et.getText().toString());
                        result = tp.execute(serverIP, serverPort).get();
                        Log.i("result", result);
                        // 서버로부터 받은 데이터를 출력한다.
                        Toast.makeText(getApplicationContext(), msgFromServer, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private class TCPclient extends AsyncTask<String, Void, String> {

        private String msgToServer;

        public TCPclient(String _msg) {
            this.msgToServer = _msg;
        }


        @Override
        protected String doInBackground(String... str) {

            String a = null;

            try {
                // *********************************************************
                // 소켓을 생성하고, 서버에 접속한다.
                // *********************************************************
                InetAddress serverAddr = InetAddress.getByName(str[0]);
                Socket socket = new Socket(serverAddr, Integer.parseInt(str[1]));
                Log.d("MY_TAG", "C: Connect");
                try {
                    // *********************************************************
                    // 서버에 데이터를 보낸다.
                    // *********************************************************
                    PrintWriter out = new PrintWriter(new BufferedWriter
                            (new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(msgToServer);
                    a = "여기1";
                    Log.d("MY_TAG", "C: Send Message To Server -> " + msgToServer);

                    // *********************************************************
                    // 서버로부터 데이터를 받는다.
                    // *********************************************************
                    a = "여기2";
                    Log.i("내용?",socket.getInputStream().toString());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    msgFromServer = in.readLine();
                    a = "여기3";
                    Log.d("MY_TAG", "C: Receive Message From Server -> " + msgFromServer);
                } catch (Exception e) {
                    Log.e("MY_TAG", "C: Error1", e);
                } finally {
                    // *********************************************************
                    // 소켓을 닫는다.
                    // *********************************************************
                    socket.close();
                    Log.d("MY_TAG", "C: Socket Close");
                }
            } catch (Exception e) {
                Log.e("MY_TAG", "C: Error2", e);
            }

            return a;
        }
    }
}