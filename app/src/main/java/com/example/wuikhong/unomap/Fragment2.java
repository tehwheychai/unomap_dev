package com.example.wuikhong.unomap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadServiceBroadcastReceiver;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
/**
 * Created by Wui Khong on 9/22/2017.
 */

public class Fragment2 extends Fragment implements UploadStatusDelegate{

    Button addid;
    Button addid2;
    Button addid3;
    TextView id1_organisation;
    TextView id1_id;
    CircleImageView cimgView;
    private static final int CAMERA_REQUEST=1888;
    String SharedPreference_name=new String("Unomap");
    Bitmap bitmapsample;

    String unomap_username;
    String unomap_password;
    String login_respond="one";
    String responseString="I respond";
    TextView userpage_username;

    Handler handler2;
    Runnable r2;



    private static final String TAG = "AndroidUploadService";

    private final UploadServiceBroadcastReceiver uploadReceiver =
            new UploadServiceBroadcastReceiver();

    //private String unomap_name=getArguments().getString("unomap_name");
    //private String unomap_pin=getArguments().getString("unomap_pin");

    public Fragment2() {

// Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        uploadReceiver.register(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        uploadReceiver.unregister(getContext());
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {



        View view= inflater.inflate(R.layout.fragment3, container, false);
        /*
        handler2 = new Handler();
        r2 = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //Toast.makeText(getContext(), "user inactive fragment 2",Toast.LENGTH_SHORT).show();
                //Intent w=new Intent(getActivity(),MainActivityUnomap.class);
                //startActivity(w);
            }
        };
        startHandler();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    //do something
                    stopHandler();
                    startHandler();
                }
                return true;
            }
        });
        */
        //ListView list1=(ListView)view.findViewById(R.id.individual_identity_list);
        ListView list1=(ListView)view.findViewById(R.id.list2);
        String[] number=new String[]{"Profile","Identity","Menu","Settings","Preferences"};
        ArrayList<String> numberlist=new ArrayList<String>();

        // Toast.makeText(getContext(), test, Toast.LENGTH_LONG).show();
        numberlist.addAll(Arrays.asList(number));
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),R.layout.list1,numberlist);
        list1.setAdapter(adapter1);




        return view;
// Inflate the layout for this fragment

        /*

        SharedPreferences settings=getContext().getSharedPreferences(SharedPreference_name,0);
        unomap_username=settings.getString("unomap_username","empty");
        unomap_password=settings.getString("unomap_password","empty");
        View view= inflater.inflate(R.layout.fragment1, container, false);



        userpage_username=(TextView)view.findViewById(R.id.user_page_username);

        addid=(Button)view.findViewById(R.id.userpage_button_addID);
        addid2=(Button)view.findViewById(R.id.userpage_button_addID2);
        addid3=(Button)view.findViewById(R.id.userpage_button_addID3);

        id1_id=(TextView)view.findViewById(R.id.id1_id);
        id1_organisation=(TextView)view.findViewById(R.id.id1_organisation);

        cimgView=(CircleImageView)view.findViewById(R.id.profile_image);

        loadImageFromStorage(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");

        //Toast.makeText(getContext(),Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files",Toast.LENGTH_LONG).show();
        //String profile_pic_path=settings.getString("profile_pic_path",null);
        //loadImageFromStorage(profile_pic_path);

        cimgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.USE_FRONT_CAMERA",true);
                cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT",true);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_FRONT);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });



        //String name=new String("Unomap");

        //SharedPreferences settings=getContext().getSharedPreferences(name,0);
        String organisation=settings.getString("Organisation","0");
        String id=settings.getString("ID","0");
        String card_number=settings.getString("Card_number", "0");

        id1_id.setText(id);
        id1_organisation.setText(organisation);

        if(card_number.equals("1"))
        addid.setBackgroundColor(Color.CYAN);

        addid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(getActivity(),AddID.class);
                startActivity(i);
            }
        });

        addid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(getActivity(),AddID.class);
                startActivity(i);
            }
        });

        addid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(getActivity(),AddID.class);
                startActivity(i);
            }
        });
        return view;

        */

    }



    public void add_id(View v){
        //Intent i;
        //i = new Intent(this.getContext(),FingerprintActivity.class);
        //startActivity(i);
    }
    public void onActivityResult(int requestCode, final int resultCode, Intent data){
        if(requestCode==CAMERA_REQUEST&&resultCode== Activity.RESULT_OK){
            Bitmap photo=(Bitmap)data.getExtras().get("data");
            Integer width=photo.getWidth();
            Integer height=photo.getHeight();
            Matrix matrix=new Matrix();
            matrix.postRotate(-90);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(photo,width,height,true);
            Bitmap rotatedBitmap=Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);
            storeImage(rotatedBitmap);
            new UploadProfilePicToAPI().execute();

            final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Uploading file...");
            progressDialog.show();




            // TODO: Implement your own signup logic here.
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success

                                if(responseString.contains("File"))
                            //  if(login_respond.equals("\"Login successful\""))
                            {

                                Toast.makeText(getContext(), responseString, Toast.LENGTH_LONG).show();
                                //userpage_username.setText(responseString);
                                progressDialog.dismiss();
                            }
                            else {


                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Upload failed!", Toast.LENGTH_LONG).show();

                                //userpage_username.setText(responseString+"     failed");
                            }
                        }

                    }, 3000);
            bitmapsample=rotatedBitmap;

            //SharedPreferences settings = getContext().getSharedPreferences(SharedPreference_name, 0);
            //SharedPreferences.Editor editor = settings.edit();

            //editor.putString("profile_pic_path", saveToInternalStore(photo));

            //editor.apply();

            //saveToInternalStore(photo);
            cimgView.setImageBitmap(rotatedBitmap);

            //storeImage(photo);


        }

    }

    private void storeImage(Bitmap image){
        File picture=getOutputMediaFile();
        if(picture==null){
            Log.d(TAG, "Error creating media file, check storage permission");
            return;
        }
        try{
            FileOutputStream fos=new FileOutputStream(picture);
            image.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        }catch (FileNotFoundException e){
            Log.d(TAG,"File not found: "+e.getMessage());
        }catch (IOException e){
            Log.d(TAG,"Error accessing file: "+e.getMessage());
        }

    }

    private File getOutputMediaFile(){
        File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");
        if(!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }

        }
        String timeStamp=new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName=unomap_username+".jpg";
        mediaFile=new File(mediaStorageDir.getPath()+File.separator+mImageName);
        return mediaFile;
    }

    private void loadImageFromStorage(String path){
        try{
            File f=new File(path,unomap_username+".jpg");
            int file_size=Integer.parseInt(String.valueOf(f.length()/1024));
            //Toast.makeText(getContext(), "The file size is "+String.valueOf(file_size)+"kb", Toast.LENGTH_SHORT).show();
            if(f!=null) {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                cimgView.setImageBitmap(b);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



    private String saveToInternalStore(Bitmap bitmap){
        ContextWrapper cw=new ContextWrapper(getContext());
        File directory=cw.getDir("imageDir",Context.MODE_PRIVATE);
        File mypath=new File(directory,"profile.jpg");
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
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


    private class UploadProfilePicToAPI extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

                //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
                String urll="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/";
            File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");
            String mImageName=unomap_username+".jpg";
            String pathname=mediaStorageDir.getPath()+File.separator+mImageName;

            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {

                String uploadId="2222";

                new MultipartUploadRequest(getContext(),uploadId,urll)
                        .addFileToUpload(pathname,"file")
                        .addParameter("username",unomap_username)
                        .addParameter("password",unomap_password)
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

                                responseString=serverResponse.getBodyAsString();
                                //JSONObject j=new JSONObject(serverResponse);
                                 //   responseString=String.valueOf(serverResponse);
                                    //responseString="on complete method called";


                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {

                            }
                        })
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2).startUpload();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public void stopHandler() {
        handler2.removeCallbacks(r2);
    }

    public void startHandler() {
        handler2.postDelayed(r2, 30000);
    }


}

