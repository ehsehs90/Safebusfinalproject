package com.example.safebusfinalproject.trash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.KeyEventDispatcher;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safebusfinalproject.Login5Activity;
import com.example.safebusfinalproject.MainActivity;
import com.example.safebusfinalproject.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private String imagePath;
    private ImageView imageView;
    private Button takePhoto_btn;
    private Button doPredict_btn;
    private String myUri;
    private  TextView tvtv;
    private String realPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_image);

        takePhoto_btn = (Button) findViewById(R.id.takePhoto_btn);
        doPredict_btn = (Button)findViewById(R.id.doPredict_btn);
        imageView = (ImageView) findViewById(R.id.predict_img);
        tvtv = (TextView)findViewById(R.id.tvtv);

        /*
        안드로이드에 카메가 어플리케이션이 설치되어 있는지 확인 후, 카메라 실행
         */
        ////////////////////////////////////////////////////////////////////////////////////////"com.example.safebusfinalproject.trash.fileprovider"
        takePhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExistCameraApplication()) {

                    Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.example.safebusfinalproject.trash.fileprovider",
                            savePictureFile());
                    Intent cApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Log.i("CAMERA", "카메라 실행");
//                    File picture = savePictureFile();
                    if (uri != null) {
                        cApp.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        Log.i("CAMERA", "uri ____ " +  uri.toString());
                        //myUri = uri;
                        //Log.i("경로",myUri.toString());
                        startActivityForResult(cApp, 10000);
                        Log.i("CAMERA", "카메라 실행 끝");
                    }
                } else {
                    Toast.makeText(ImageActivity.this, "카메라 앱이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        doPredict_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myUri != null){
                    //PredictImageService
                    Intent i = new Intent();
                    ComponentName cname = new ComponentName("com.example.safebusfinalproject","com.example.safebusfinalproject.trash.PredictImageService");
                    i.setComponent(cname);
                    i.putExtra("IMG_URI", myUri);

                    startService(i);




                }
            }
        });

    }

    private boolean isExistCameraApplication() {
        /*
        안드로이드에 카메가 어플리케이션이 설치되어 있는지 확인
         */

        PackageManager packageManager = getPackageManager();
        Intent cApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(cApp, PackageManager.MATCH_DEFAULT_ONLY);

        return cameraApps.size() > 0;
    }

    private File savePictureFile() {
        PermissionRequest.Builder request = new PermissionRequest.Builder(this);
        int result = request.create().request(Manifest.permission.WRITE_EXTERNAL_STORAGE, 20000, new PermissionRequest.OnClickDenyButtonListener() {
            @Override
            public void onClick(Activity activity) {

            }
        });

        // 사용자가 권한을 수락한 경우
        if (result == PermissionRequest.ALREADY_GRANTED || result == PermissionRequest.REQUEST_PERMISSION) {

            // 사진 파일의 이름을 만든다.
            // Date는 java.util을 Import
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "IMG_" + timestamp;

            /*
            사진 파일이 저장될 장소를 구한다.
            외장 메모리에서 사진을 저장하는 폴더를 찾아서 그곳에 MYAPP이라는 폴더를 만든다.

             */
            File picStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MYAPP/");
            Log.i("경로 ", picStorage.toString());

            // 만약 저장소가 존재하지 않으면, 새로운 폴더를 생성한다.
            if (!picStorage.exists()) {
                picStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".png", picStorage);

                // ImageView에 보이기 위해 사진 파일의 절대 경로를 얻어온다.
                imagePath = file.getAbsolutePath();
                myUri = imagePath;
                Log.i("경로", "경로2"+imagePath);

                // 찍힌 사진을 "갤러리" App에 추가
                Intent mediaSacnIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imagePath);
                Log.i("경로", "경로3"+f.toString());
                Uri contentUri = Uri.fromFile(f);
                Log.i("경로", "경로4"+contentUri.toString());
                mediaSacnIntent.setData(contentUri);
                this.sendBroadcast(mediaSacnIntent);

                return file;

            } catch (Exception e) {
                Log.i("ERROR : ", "PredictImage :" + e.toString());
            }
        } else {
            // 사용자가 권한을 거부한 경우
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // 사진찍기 버튼을 누른 후 잘 찍고 돌아왔다면
        if (requestCode == 10000 && resultCode == RESULT_OK) {
            // 사진을 ImageView에 보여준다.
            BitmapFactory.Options facOptions = new BitmapFactory.Options();
            facOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, facOptions);
            facOptions.inJustDecodeBounds = false;
            facOptions.inSampleSize = 1;
            facOptions.inPurgeable = true;

            Bitmap bm = BitmapFactory.decodeFile(imagePath, facOptions);
            try {
                imageView.setImageBitmap(getRotatedBitmap(bm,getOrientationOfImage(imagePath)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) throws Exception {
        if(bitmap == null) return null;
        if (degrees == 0) return bitmap;

        Matrix m = new Matrix();
        m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }

    public int getOrientationOfImage(String filepath) {
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

        if (orientation != -1) {
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        }
        return 0;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        String msg = intent.getExtras().getString("realPath");
//        Log.i("하리보" , msg);
//        tvtv.setText(msg);
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//
//        Intent mintent = new Intent();
//        ComponentName cname = new ComponentName("com.example.safebusfinalproject" ,"com.example.safebusfinalproject.Login7Activity");
//        mintent.setComponent(cname);
//        mintent.putExtra("realPath",msg);
//           Log.i("돈마려!",msg);
//        setResult(RESULT_OK,mintent);
        // startActivity(mintent);
        finish();
    }
}