package com.example.safebusfinalproject;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewClient2Acivity extends AppCompatActivity {

    TextView textarea;
    Button btn,closebtn;
    TextField tf;
    Socket socket;
    BufferedReader br;
    PrintWriter out;


    private void printMsg(String msg) {
        Platform.runLater(() -> {
            textarea.appendText(msg + "\n");
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 화면구성해서 window 띄우는 코드
        // 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
        BorderPane root = new BorderPane();
        //BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
        root.setPrefSize(700, 500);

        // Component 생성해서 BorderPane에 부착
        textarea = new TextArea();
        root.setCenter(textarea);

        btn = new Button("Echo 서버 접속 !!");
        btn.setPrefSize(250,50);
        btn.setOnAction(t->{
            // 버튼에서 Action이 발생(클릭)했을 때 호출!
            // 접속버튼
            try {
                // 클라이언트는 버튼을 누르면 서버쪽에 Socket 접속을 시도.
                // 만약에 접속에 성공하면 socket객체를 하나 획득.
                socket = new Socket("127.0.0.1",7777);
                // Stream 생성
                InputStreamReader isr =
                        new InputStreamReader(socket.getInputStream());

                br = new BufferedReader(isr);

                out =
                        new PrintWriter(socket.getOutputStream());

                printMsg("Echo 서버 접속 성공!!");

            } catch(Exception e) {
                System.out.println("1: "+e);
            }

        });

        tf = new TextField();
        tf.setPrefSize(200, 40);
        tf.setOnAction(t->{
            // 입력상자(TextField)에서 enter key가 입력되면 호출
            String msg = tf.getText();
            out.println(msg);
            out.flush();
            try{
                String result = br.readLine();
                printMsg(result);

            }catch(IOException e) {
                System.out.print("2: ");
                e.printStackTrace();
            }
        });

        closebtn = new Button("E서버 종료 !!");
        closebtn.setPrefSize(250,50);
        closebtn.setOnAction(t->{
            // 버튼에서 Action이 발생(클릭)했을 때 호출!
            // 접속버튼
            try {
                out.println("/EXIT");
                out.flush();
                out.close();
                br.close();
                socket.close();
                printMsg("E서버 종료 성공!!");

                tf.setDisable(true);
                btn.setDisable(true);
                closebtn.setDisable(true);
            } catch(Exception e) {
                System.out.println("3: "+e);
            }

        });


        FlowPane flowpane = new FlowPane();
        flowpane.setPrefSize(700, 50);
        // flowpane에 버튼을 올려요!
        flowpane.getChildren().add(btn);
        flowpane.getChildren().add(closebtn);
        flowpane.getChildren().add(tf);
        root.setBottom(flowpane);

        // Scene 객체가 필요해요.
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Thread 예제입니다.");
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}