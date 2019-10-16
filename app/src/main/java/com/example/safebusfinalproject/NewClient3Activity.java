//package com.example.safebusfinalproject;
//
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class NewClient3Activity extends AppCompatActivity {
//
//    private Button mButton;
//    private ProgressBar mProgress;
//    private AsyncTask<Void, Integer, Void> mTask;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_businfo);
//
//        mButton = (Button) findViewById(R.id.button);
//        mProgress = (ProgressBar) findViewById(R.id.progress);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        // 시작 버튼
//        if (mButton.getText().equals("start")) {
//            // AsyncTask는 재활용할 수 없습니다. 매번 새롭게 생성
//            mTask = new AsyncTask<Void, Integer, Void>() {
//                // 작업 취소시 사용하기 위한 플래그
//                private boolean isCanceled = false;
//
//                // 작업을 시작하기 직전에 호출되는 메서드
//                @Override
//                protected void onPreExecute() {
//                    publishProgress(0);
//                    isCanceled = false;
//                }
//
//                // 백그라운드에서 작업
//                @Override
//                protected Void doInBackground(Void... params) {
//                    // 0.1초마다 100단계로 구성된 프로그래스바를 1씩 증가시킵니다.
//                    for (int i = 1; i <= 100 && !isCanceled; i++) {
//                        try {
//                            publishProgress(i);
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    return null;
//                }
//
//                // publishProgress() 메서드를 통해 호출됩니다. 진행사항을 표시하는데에 쓰입니다.
//                @Override
//                protected void onProgressUpdate(Integer... progress) {
//                    mProgress.setProgress(progress[0]);
//                }
//
//                // 작업 완료 직후에 호출되는 메소드
//                @Override
//                protected void onPostExecute(Void result) {
//                    Toast.makeText(AsyncTaskExampleActivity.this, "완료됨", Toast.LENGTH_SHORT).show();
//                    mButton.setText("start");
//                }
//
//                // 외부에서 강제로 취소할때 호출되는 메소드
//                @Override
//                protected void onCancelled() {
//                    isCanceled = true;
//                    publishProgress(0);
//                    Toast.makeText(AsyncTaskExampleActivity.this, "취소됨", Toast.LENGTH_SHORT).show();
//                }
//            };
//
//            // 작업 시작
//            mTask.execute();
//            mButton.setText("cancel");
//        }
//        // 취소 버튼
//        else if (mButton.getText().equals("cancel")) {
//            mTask.cancel(false);
//            mButton.setText("start");
//        }
//    }
//}