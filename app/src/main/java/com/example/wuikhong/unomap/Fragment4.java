package com.example.wuikhong.unomap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.CameraSource;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by Wui Khong on 9/22/2017.
 */

public class Fragment4 extends Fragment implements UploadStatusDelegate{

    SurfaceView cameraView;
    Camera mCamera;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    String unomap_username;
    String unomap_password;
    String SharedPreference_name=new String("Unomap");
    String qr_respond;
    boolean restrict_api=false;
String respondString;
String unomap_id;
String urll;
    public Fragment4() {

// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment4, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cameraView = (SurfaceView) getView().findViewById(R.id.camera_view);
        barcodeInfo = (TextView) getView().findViewById(R.id.code_info);

        SharedPreferences settings=getContext().getSharedPreferences(SharedPreference_name,0);
        unomap_username=settings.getString("unomap_username","empty");
        unomap_password=settings.getString("unomap_password","empty");
        unomap_id=settings.getString("unomap_id","empty");

        barcodeDetector = new BarcodeDetector.Builder(this.getContext()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this.getContext(), barcodeDetector).setFacing(CameraSource.CAMERA_FACING_BACK).setAutoFocusEnabled(true).setRequestedPreviewSize(640, 480).build();




        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());

                    //mCamera.startPreview();
                    barcodeInfo.setText("This is okay");
                }catch (IOException ie){
                    Log.e("CAMERA SOURCE",ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder){
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release(){

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections){


                final SparseArray<Barcode> barcodes=detections.getDetectedItems();

                if(barcodes.size()!=0){


                    barcodeInfo.post(new Runnable(){
                        @Override
                        public void run() {
                            qr_respond=barcodes.valueAt(0).displayValue;
                            barcodeInfo.setText(
                                    barcodes.valueAt(0).displayValue
                            );
                            barcodeInfo.setText(urll);
                            if(restrict_api==false) {
                                new Attendance().execute();
                                final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                        R.style.AppTheme);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Wait a sec...");
                                progressDialog.show();




                                // TODO: Implement your own signup logic here.
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                // On complete call either onSignupSuccess or onSignupFailed
                                                // depending on success


                                                progressDialog.dismiss();
                                                //Toast.makeText(getContext(),respondString,Toast.LENGTH_LONG).show();
                                                Toast.makeText(getContext(),"Attendance successfully signed",Toast.LENGTH_LONG).show();
                                                getActivity().finish();
                                                Intent w=new Intent(getContext(),UserPage.class);
                                                w.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(w);
                                            }

                                        }, 3000);

                                restrict_api=true;
                            }




                        }
                    });

                }
            }
        });
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

    private class Attendance extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

            //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
            urll = "http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/mark_attendance?qr=" + qr_respond+"&id="+unomap_id;
            //File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");
            //String mImageName=unomap_username+".jpg";
            //String pathname=mediaStorageDir.getPath()+File.separator+mImageName;

            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {
                URL url = new URL(urll);


                HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();

                urlcon.setUseCaches(false);
                urlcon.setDoOutput(true);
                urlcon.setRequestMethod("POST");
                urlcon.setRequestProperty("Content-Type", "application/json");
                urlcon.setRequestProperty("Connection", "Keep-Alive");
                urlcon.setRequestProperty("Cache-Control", "no-cache");
                urlcon.setRequestProperty("header-param_3", "value-3");
                urlcon.setRequestProperty("header-param_4", "value-4");
                urlcon.setRequestProperty("Authorisation", "Basic");

                urlcon.connect();


                JSONObject jsonRequest = new JSONObject();
                //jsonRequest.put("username",signin_username_string);
                //jsonRequest.put("password",signin_pinnumber_string);


                OutputStreamWriter out = new OutputStreamWriter((urlcon.getOutputStream()));
                out.write(jsonRequest.toString());
                out.close();

                int statuscode = urlcon.getResponseCode();
                String statusmsg = urlcon.getResponseMessage();
                //if(statuscode==200){
                InputStream it = new BufferedInputStream(urlcon.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                String returndata = dta.toString();
                respondString = returndata;

                //respond1=statusmsg;
                return returndata;
                //}else{

                //}


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                /*
                String uploadId="2222";

                new MultipartUploadRequest(getContext(),uploadId,urll)
                       // .addParameter("username",unomap_username)
                       // .addParameter("password",unomap_password)
                       // .addParameter("string",qr_respond)

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

                                String responseString=serverResponse.getBodyAsString();
                                Toast.makeText(getContext(),responseString,Toast.LENGTH_LONG).show();
                                //JSONObject j=new JSONObject(serverResponse);
                                //   responseString=String.valueOf(serverResponse);
                                //responseString="on complete method called";


                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {

                            }
                        })
                        .setMaxRetries(1).startUpload();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            */


            return null;
        }

    }
/*
    public void surfaceCreated(SurfaceHolder holder){
        try{
            if(mCamera!=null){
                mCamera.setPreviewDisplay(holder);
                mCamera.setPreviewCallback(new Camera.PreviewCallback(){
                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera){

                    }
                });
            }
        }catch (IOException exception){
            Log.e(TAG,"IOException caused by setPreviewDisplay()",exception);
        }
    }
    */
}