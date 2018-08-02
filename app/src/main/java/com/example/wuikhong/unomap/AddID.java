package com.example.wuikhong.unomap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.games.leaderboard.Leaderboards;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Date;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static android.content.ContentValues.TAG;

/**
 * Created by Wui Khong on 10/18/2017.
 */

public class AddID extends Activity implements UploadStatusDelegate,SurfaceHolder.Callback{

    Button camera;
    Button addid;
    ImageView addid_imgview;
    EditText addid_identification;
    EditText addid_id;
    EditText addid_organisation;
    EditText addid_period;
    private static final int CAMERA_REQUEST=1888;
    //EditText uni_edittext;
    EditText uni_course_edittext;
    EditText uni_period_edittext;
    EditText uni_faculty_edittext;
    EditText uni_card_edittext;
    String add_id_respond="Add ID";
    String uni_name_string;
    String uni_course_string;
    String uni_period_string;
    String uni_faculty_string;
    String uni_card_string;
    String name;
    String password;
    String data;

    Camera cam;
    String SharedPreference_name=new String("Unomap");


    SurfaceView cameraView;
    SurfaceView transparentView;
    SurfaceHolder holder;
    SurfaceHolder holderTransparent;

    float RectLeft;
    float RectTop;
    float RectRight;
    float RectBottom;
    Paint paint;

    Canvas canvas;

    Spinner static_spinner_uni_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_id_page2);
        //uni_edittext=(EditText)findViewById(R.id.uni_name);
        uni_card_edittext=(EditText)findViewById(R.id.uni_number);
        uni_course_edittext=(EditText)findViewById(R.id.uni_course);
        uni_faculty_edittext=(EditText)findViewById(R.id.uni_faculty);
        uni_period_edittext=(EditText)findViewById(R.id.uni_period);

        static_spinner_uni_name=(Spinner)findViewById(R.id.static_spinner_uni_name);

        ArrayAdapter<CharSequence> staticAdapter=ArrayAdapter.createFromResource(this,R.array.brew_array,android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        static_spinner_uni_name.setAdapter(staticAdapter);

        ArrayList<String> defaults=new ArrayList<>();
        defaults.add(0, getString(R.string.university_1));
        defaults.add(1,getString(R.string.university_2));
        defaults.add(2,getString(R.string.university_Others));

        HintSpinner hintSpinner=new HintSpinner<>(
            static_spinner_uni_name,
            new me.srodrigo.androidhintspinner.HintAdapter<>(this,"Name of your university",defaults),
            new HintSpinner.Callback<String>(){

                @Override
                public void onItemSelected(int i, String s) {


                    uni_name_string=s;
                }
            });

        hintSpinner.init();







        SharedPreferences settings=this.getSharedPreferences(SharedPreference_name,0);
        name=settings.getString("unomap_username","empty");
        password=settings.getString("unomap_password","empty");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("centurygothic.ttf").
                setFontAttrId(R.attr.fontPath).
                build());




    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==CAMERA_REQUEST&&resultCode==Activity.RESULT_OK){
            Bitmap photo=(Bitmap)data.getExtras().get("data");
            addid_imgview.setImageBitmap(photo);

            //storeImage(photo);


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void storeImage(Bitmap image){
        File picturefile=getOutputMediaFile();
        if(picturefile==null){
            Log.d(TAG,"Error creating media files, check storage permissions");
            return;

        }
        try{
            FileOutputStream fos=new FileOutputStream(picturefile);
            image.compress(Bitmap.CompressFormat.PNG,90,fos);
            fos.close();
        }catch (FileNotFoundException e){
            Log.d(TAG,"File not found");

        }catch (IOException e){
            Log.d(TAG,"Error accessing file");
        }
    }

    public void add(View view){
        //uni_name_string=uni_edittext.getText().toString();
        uni_course_string=uni_course_edittext.getText().toString();
        uni_faculty_string=uni_faculty_edittext.getText().toString();
        uni_card_string=uni_card_edittext.getText().toString();
        uni_period_string=uni_period_edittext.getText().toString();


        SharedPreferences settings=this.getSharedPreferences(SharedPreference_name,0);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("unomap_uniname",uni_name_string);
        editor.putString("unomap_unicard",uni_card_string);
        editor.putString("unomap_unifaculty",uni_faculty_string);
        editor.putString("unomap_unicourse",uni_course_string);
        editor.putString("unomap_uniperiod",uni_period_string);
        editor.apply();

        //new Sign_up_page.Information().execute();
        new Add_id().execute();
        //new Register().execute();
        //toAPI();



        final ProgressDialog progressDialog2 = new ProgressDialog(AddID.this,
                R.style.AppTheme);
        progressDialog2.setIndeterminate(true);
        progressDialog2.setMessage("Adding ID...");
        progressDialog2.show();




        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if(add_id_respond.contains("added")) {
                            //if(true){
                            //onSignupSuccess();
                            // onSignupFailed();
                            Toast.makeText(AddID.this, add_id_respond, Toast.LENGTH_LONG).show();
                            //Toast.makeText(AddID.this, data, Toast.LENGTH_LONG).show();
                            progressDialog2.dismiss();
                        }
                        else {
                            //onSignupFailed();

                            progressDialog2.dismiss();
                        }
                    }

                }, 5000);



    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File getOutputMediaFile(){
        File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getApplicationContext().getPackageName()+"/Files");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdir())
                return null;
        }
        String timestam=new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+timestam+".jpg";
        mediaFile=new File(mediaStorageDir.getPath()+File.separator+mImageName);
        return mediaFile;
    }

    @Override
    public void onProgress(Context context, UploadInfo uploadInfo) {

    }

    @Override
    public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

    }

    @Override
    public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

    }

    @Override
    public void onCancelled(Context context, UploadInfo uploadInfo) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            cam = Camera.open();
        }catch(RuntimeException e){
            //Log.e(tag, "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = cam.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(1280, 720);

        param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        cam.setParameters(param);
        try {
            cam.setPreviewDisplay(holder);
            cam.startPreview();
            //cameraView.setCamera(cam);
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            //Log.e(tag, "init_camera: " + e);
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //Camera.Parameters parameters = cam.getParameters();
        //parameters.setPreviewSize(80, 60);
        //cam.setParameters(parameters);

        //cam.startPreview();

    }




    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        cam.stopPreview();
        cam = null;
    }

    private class Add_id extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

            //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
            String urll="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/add/userinfo/";
            //File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");
            //String mImageName=unomap_username+".jpg";
            //String pathname=mediaStorageDir.getPath()+File.separator+mImageName;

            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {

                String uploadId="2228";

                JSONObject data1=new JSONObject();
                try{

                    data1.put("University",uni_name_string);
                    data1.put("University card",uni_card_string);
                    data1.put("University faculty",uni_faculty_string);
                    data1.put("University course",uni_course_string);
                    data1.put("University period",uni_period_string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String data2=data1.toString();
                String tablename="unomap_student";
                data="{ \"University\":\""+uni_name_string
                        +"\",\"University card\":\""+uni_card_string
                        +"\",\"University faculty\":\""+uni_faculty_string
                        +"\",\"University course\":\""+uni_course_string
                        +"\",\"University period\":\""+uni_period_string+"\"}";
                //Toast.makeText(this,data,Toast.LENGTH_LONG).show();
                new MultipartUploadRequest(AddID.this,uploadId,urll)
                        .addParameter("username",name)
                        .addParameter("password",password)
                        .addParameter("json",data)
                        .addParameter("table",tablename)

                        .setMethod("POST")
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                                add_id_respond=serverResponse.getBodyAsString();

                                //JSONObject j=new JSONObject(serverResponse);
                                //   responseString=String.valueOf(serverResponse);
                                //responseString="on complete method called";


                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {

                            }
                        })
                        .setMaxRetries(2).startUpload();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public void openCamera(View view){
        setContentView(R.layout.surfaceview1);
        // Create first surface with his holder(holder)
        cameraView = (SurfaceView)findViewById(R.id.CameraView);
        cameraView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RectLeft = motionEvent.getX() - 100;
                RectTop = motionEvent.getY() - 100 ;
                RectRight = motionEvent.getX() + 100;
                RectBottom = motionEvent.getY() + 100;
                DrawFocusRect(RectLeft , RectTop , RectRight , RectBottom , Color.BLUE);
                return false;
            }
        });



        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // Create second surface with another holder (holderTransparent)
        transparentView = (SurfaceView)findViewById(R.id.TransparentView);

        holderTransparent = transparentView.getHolder();
        holderTransparent.setFormat(PixelFormat.TRANSPARENT);
        holderTransparent.addCallback(this);
        holderTransparent.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

/*
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(2222, info);
        int rotation = this.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result;
        result = (info.orientation - degrees + 360) % 360;
        */
        //cam.setDisplayOrientation((90+360)%360);
        //setCameraDisplayOrientation(this,6654,cam);
    }


    public void DrawFocusRect(float RectLeft, float RectTop, float RectRight, float RectBottom, int color)
    {

        canvas = holderTransparent.lockCanvas();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        //border's properties
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(3);
        canvas.drawRect(RectLeft, RectTop, RectRight, RectBottom, paint);


        holderTransparent.unlockCanvasAndPost(canvas);
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
