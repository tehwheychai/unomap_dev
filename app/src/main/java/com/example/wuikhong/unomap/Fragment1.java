package com.example.wuikhong.unomap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuikhong.unomap.camera.Card;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;
import com.volokh.danylo.utils.DebugRecyclerView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadServiceBroadcastReceiver;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.CAMERA_SERVICE;

/**
 * Created by Wui Khong on 9/22/2017.
 */

public class Fragment1 extends Fragment implements UploadStatusDelegate{


    CircleImageView cimgView;
    private static final int CAMERA_REQUEST=1888;
    String SharedPreference_name=new String("Unomap");
    Bitmap bitmapsample;
    Handler handler;
    Runnable r;

    String unomap_username;
    String unomap_password;
    String unomap_email;
    String unomap_emailverified;
    String unomap_phone;
    String login_respond="one";
    String responseString="I respond";
    TextView userpage_username;
ViewGroup placeholder;
String json_uniname="45";
String json_unicard="45";
String json_unicourse="45";
String json_unifaculty="45";
String json_uniperiod="45";
String test="eeeee";
CircleImageView c1;
String emailresendString="ddd";

    private static final String TAG = "AndroidUploadService";

    private final UploadServiceBroadcastReceiver uploadReceiver =
            new UploadServiceBroadcastReceiver();



    //RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<Card> cardList;
    CardViewAdapter cardAdapter;

    String [] names={"one","two","three","four","five","six"};
    String [] singers={"card 1","card 2","card 3","card 4","card 5", "card 6"};
    //int [] pics={R.drawable.card1,R.drawable.card2,R.drawable.card3,R.drawable.front_page,R.drawable.identifyslogo,R.drawable.colorunomap};
    int [] pics={R.drawable.circle1,
            R.drawable.circle1,
            R.drawable.circle1,
            R.drawable.circle1,
            R.drawable.circle1,
            R.drawable.circle1};
    ImageButton functionx;
    WheelView wheelView;
    String testString;
    String identityString;
    //DebugRecyclerView mRecyclerView;

    String uni_name_string;
    String uni_course_string;
    String uni_period_string;
    String uni_faculty_string;
    String uni_card_string;

    String orientation2;




    public Fragment1() {

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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences settings=getContext().getSharedPreferences(SharedPreference_name,0);
        unomap_username=settings.getString("unomap_username","empty");
        unomap_password=settings.getString("unomap_password","empty");
        unomap_email=settings.getString("unomap_email","empty");
        unomap_emailverified=settings.getString("unomap_emailverified","empty");
        unomap_phone=settings.getString("unomap_phone","empty");
        uni_name_string=settings.getString("unomap_uniname","empty");
        uni_card_string=settings.getString("unomap_unicard","empty");
        uni_faculty_string=settings.getString("unomap_unifaculty","empty");
        uni_course_string=settings.getString("unomap_unicourse","empty");
        uni_period_string=settings.getString("unomap_uniperiod","empty");

        if(uni_name_string!="empty"){
            pics[1]=R.drawable.um_logo;
            pics[0]=R.drawable.mykad;
            pics[2]=R.drawable.help_logo;
        }

        //Toast.makeText(getContext(),Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files",Toast.LENGTH_LONG).show();
        //String profile_pic_path=settings.getString("profile_pic_path",null);
        //loadImageFromStorage(profile_pic_path);

// Inflate the layout for this fragment
/*
        View view= inflater.inflate(R.layout.fragment2, container, false);
        final String[] settings=new String[]{"Name","Email","Phone","Identity","Contact Us","Explore","Credits","Logs","Log out"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.list_setting,settings);
        ListView list=(ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
        return view;
        */

        View view=inflater.inflate(R.layout.fragment_cardview,container,false);
        /*
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "user inactive fragment 1",Toast.LENGTH_SHORT).show();
                Intent w=new Intent(getActivity(),MainActivityUnomap.class);
                startActivity(w);
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

        cimgView=(CircleImageView)view.findViewById(R.id.profile_image2);

        final WheelView wheelView = (WheelView) view.findViewById(R.id.wheelview23);

        cimgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.USE_FRONT_CAMERA",true);
                cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT",true);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_FRONT);
                //setCameraDisplayOrientation(cameraIntent,cameraId,camera);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });


        TextView user_email=(TextView)view.findViewById(R.id.user_page_useremail);
        user_email.setText(unomap_email);
        TextView user_phone=(TextView)view.findViewById(R.id.user_page_phonenumber);
        user_phone.setText(unomap_phone);
        TextView user_name=(TextView)view.findViewById(R.id.user_page_username);
        user_name.setText(unomap_username);
        ImageView user_page_emailverified=(ImageView)view.findViewById(R.id.user_page_emailverified);
        if(unomap_emailverified.contains("now")){
            user_page_emailverified.setImageResource(R.drawable.tick);
        }else {
            user_page_emailverified.setImageResource(R.drawable.notick);
            user_page_emailverified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    new resendEmail().execute();
                    Toast.makeText(getContext(), "A verification email has been sent to " + unomap_email + ". Please verify your email now.", Toast.LENGTH_LONG).show();

                }
            });
        }



        loadImageFromStorage(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");



        functionx=(ImageButton)view.findViewById(R.id.functionxbutton);
        functionx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(),AddID.class);
                //float x=wheelView.getWheelOffsetY();
                //wheelView.setWheelOffsetY(100);
                //setContentView(R.layout.add_id_page2);
                //View view2=inflater.inflate(R.layout.add_id_page2,container,false);
                //Intent i=new Intent(getContext(),WVclass.class);
                startActivity(i);


            }
        });

        //wheelView=(WheelView)view.findViewById(R.id.wheelview);


        //===========================================================================================================================

        //===========================================================================================================================
        //===========================================================================================================================
        //===========================================================================================================================
        //===========================================================================================================================
/*
        Integer ITEM_COUNT=14;
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map.Entry<String, Integer> entry = MaterialColor.random(getContext(), "\\D*_500$");
            entries.add(entry);
        }
        wheelView.setAdapter(new MaterialColorAdapter(entries));



        wheelView.setSelectionColor(getContrastColor(entries.get(0)));


        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {

                String msg = String.valueOf(position) + " " + isSelected;
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"wheelview selected",Toast.LENGTH_SHORT).show();
            }
        });
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent.getAdapter()).getItem(position);
                parent.setSelectionColor(getContrastColor(selectedEntry));

            }
        });
        wheelView.setOnWheelAngleChangeListener(new WheelView.OnWheelAngleChangeListener() {
            @Override
            public void onWheelAngleChange(float angle) {

            }
        });


*/
//=========================================================================================================================================
        //==========================================================================================================================================
        //=============================================================================================================================================

        /*
        mRecyclerView=(DebugRecyclerView)view.findViewById(R.id.recycle_view);
        if(mRecyclerView!=null){
            mRecyclerView.setHasFixedSize(true);
        }

        int circleRadius=getActivity().getResources().getDisplayMetrics().widthPixels*2/3;
        int xOrigin=-200;
        int yOrigin=0;
        ViewGroup.LayoutParams params;

        mRecyclerView.setParameters(circleRadius,xOrigin,yOrigin);
        //mLayoutManager=new LinearLayoutManager(this.getContext());
        //mLayoutManager=new GridLayoutManager(this.getContext(),3);
        mLayoutManager=new LondonEyeLayoutManager(
          circleRadius,
                xOrigin,
                yOrigin,
                mRecyclerView,
                IScrollHandler.Strategy.NATURAL
        );
        //mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //mLayoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);


        cardList=new ArrayList<>();

        for(int i=0;i<names.length;i++){
            Card card=new Card(names[i],singers[i],i+1,pics[i]);
            cardList.add(card);
        }

        cardAdapter=new CardViewAdapter(cardList);
        mRecyclerView.setAdapter(cardAdapter);
        //cardAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"Card at "+position+" is clicked",Toast.LENGTH_SHORT).show();
            }
        }));

        */

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                if(position==0){

                    View newView=inflater.inflate(R.layout.individual_identity,container,false);
                    TextView t1=(TextView)newView.findViewById(R.id.individual_identity_cardtitle);
                    ImageView i1=(ImageView)newView.findViewById(R.id.individual_identity_cardimage);
                    Button b1=(Button)newView.findViewById(R.id.individual_identity_back);
                    //c1=(CircleImageView)newView.findViewById(R.id.individual_identity_picture);
                    //loadImageFromStorage2(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().recreate();
                        }
                    });
                    t1.setText("MyKad");
                    i1.setImageResource(R.drawable.mykad);
                    placeholder.removeAllViews();
                    placeholder.addView(newView);

                    ListView list1=(ListView)newView.findViewById(R.id.individual_identity_list);
                    String[] number=new String[]{"University: "+uni_name_string,
                            "Card Number: "+uni_card_string,
                            "Course: "+uni_course_string,
                            "Faculty: "+uni_faculty_string,
                            "Period: "+uni_period_string};
                    ArrayList<String> numberlist=new ArrayList<String>();

                   // Toast.makeText(getContext(), test, Toast.LENGTH_LONG).show();
                    numberlist.addAll(Arrays.asList(number));
                    ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),R.layout.list1,numberlist);
                    list1.setAdapter(adapter1);


                    new Fragment1.getUserIdentity().execute();


                    final ProgressDialog progressDialog2 = new ProgressDialog(getContext(),
                            R.style.AppTheme);
                    progressDialog2.setIndeterminate(true);
                    progressDialog2.setMessage("Getting id information...");
                    progressDialog2.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    int position=0;
                    //
                                    //
                                    //
                                    //
                                    //
                                    //
                                    //
                                    Toast.makeText(getActivity(),identityString,Toast.LENGTH_SHORT).show();
                    View newView=inflater.inflate(R.layout.individual_identity,container,false);
                    TextView t1=(TextView)newView.findViewById(R.id.individual_identity_cardtitle);
                    ImageView i1=(ImageView)newView.findViewById(R.id.individual_identity_cardimage);
                                    Button b1=(Button)newView.findViewById(R.id.individual_identity_back);
                                    b1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getActivity().recreate();
                                        }
                                    });
                    t1.setText("MyKad");
                    i1.setImageResource(R.drawable.mykad);
                    placeholder.removeAllViews();
                    placeholder.addView(newView);

                    ListView list1=(ListView)newView.findViewById(R.id.individual_identity_list);
                    String[] number2=new String[]{"University: University of Malaya",
                            "Card Number: KEE110048",
                            "Course: Electrical Engineering",
                            "Faculty: Faculty of Engineering",
                    "Period: 2011-2015"};
                    String[] number=new String[]{
                      "University: "+json_uniname,
                            "Card Number: "+json_unicard,
                            "Course: "+json_unicourse,
                            "Faculty: "+json_unifaculty,
                            "Period: "+json_uniperiod
                    };
                    ArrayList<String> numberlist=new ArrayList<String>();
                    //number[0]=json_uniname[position];
                    //number[1]=json_unicard[position];
                    //number[2]=json_unifaculty[position];
                    //number[3]=json_unicourse[position];
                    //number[4]=json_uniperiod[position];
                    //numberlist.add(1,json_unicard[position]);
                    //numberlist.add(2,json_unifaculty[position]);
                    //numberlist.add(3,json_unicourse[position]);
                    //numberlist.add(4,json_uniperiod[position]);
                                    //Toast.makeText(getContext(), test, Toast.LENGTH_LONG).show();
                    numberlist.addAll(Arrays.asList(number));
                    ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),R.layout.list1,numberlist);
                    list1.setAdapter(adapter1);
                    progressDialog2.dismiss();
                                }

                            }, 3000);






                }else {
                    if (position == 1) {
                        View newView=inflater.inflate(R.layout.individual_identity,container,false);
                        TextView t1=(TextView)newView.findViewById(R.id.individual_identity_cardtitle);
                        ImageView i1=(ImageView)newView.findViewById(R.id.individual_identity_cardimage);
                        Button b1=(Button)newView.findViewById(R.id.individual_identity_back);
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getActivity().recreate();
                            }
                        });

                        t1.setText("University");
                        i1.setImageResource(R.drawable.um_logo);
                        placeholder.removeAllViews();
                        placeholder.addView(newView);

                        ListView list1=(ListView)newView.findViewById(R.id.individual_identity_list);
                        String[] number=new String[]{"University: "+uni_name_string,
                                "Card Number: "+uni_card_string,
                                "Course: "+uni_course_string,
                                "Faculty: "+uni_faculty_string,
                                "Period: "+uni_period_string};
                        ArrayList<String> numberlist=new ArrayList<String>();

                        // Toast.makeText(getContext(), test, Toast.LENGTH_LONG).show();
                        numberlist.addAll(Arrays.asList(number));
                        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),R.layout.list1,numberlist);
                        list1.setAdapter(adapter1);
                    }

                }
            }

        });


        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                Drawable my = getResources().getDrawable(pics[position]);



                return my;
            }

            @Override
            public int getCount() {
                return pics.length;
            }
        });


        placeholder=(ViewGroup)view;
        return placeholder;

    }

    public void onActivityResult(int requestCode, final int resultCode, Intent data){
        if(requestCode==CAMERA_REQUEST&&resultCode== Activity.RESULT_OK){
            Bitmap photo=(Bitmap)data.getExtras().get("data");



            //Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
            //android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            //android.hardware.Camera.getCameraInfo(requestCode, info);
            //Bitmap bitmap = rotate(photo, info.orientation);
            cimgView.setImageBitmap(photo);
            //Integer cameraId=2222;
            //android.hardware.Camera.CameraInfo info=new android.hardware.Camera.CameraInfo();
            //android.hardware.Camera.getCameraInfo(cameraId,info);
           // Bitmap bit=rotate(photo,info.orientation);


            //Bitmap bitmap = rotate(photo, info.orientation, info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT);
            storeImage(photo);




            File f=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files"
                    ,unomap_username+".jpg");
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(f.getPath());



            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,             ExifInterface.ORIENTATION_UNDEFINED);
            orientation=getOrientation(getContext(),getImageUri(getContext(),photo));
            orientation2=ei.getAttribute(ExifInterface.TAG_ORIENTATION);
            Toast.makeText(getContext(),orientation2,Toast.LENGTH_LONG).show();
            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(photo, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(photo, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(photo, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = photo;
            }
            //Matrix matrix=new Matrix();
            //matrix.postRotate(-90);
            storeImage(rotatedBitmap);

            cimgView.setImageBitmap(rotatedBitmap);



            //Bitmap scaledBitmap=Bitmap.createScaledBitmap(photo,width,height,true);
            //Bitmap rotatedBitmap=Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);

            new Fragment1.UploadProfilePicToAPI().execute();

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
            //bitmapsample=rotatedBitmap;

            //SharedPreferences settings = getContext().getSharedPreferences(SharedPreference_name, 0);
            //SharedPreferences.Editor editor = settings.edit();

            //editor.putString("profile_pic_path", saveToInternalStore(photo));

            //editor.apply();

            //saveToInternalStore(photo);
            //cimgView.setImageBitmap(rotatedBitmap);


            //storeImage(photo);


        }

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

    private void loadImageFromStorage2(String path){
        try{
            File f=new File(path,unomap_username+".jpg");
            int file_size=Integer.parseInt(String.valueOf(f.length()/1024));
            //Toast.makeText(getContext(), "The file size is "+String.valueOf(file_size)+"kb", Toast.LENGTH_SHORT).show();
            if(f!=null) {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                c1.setImageBitmap(b);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
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

    private class getUserIdentity extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

            //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
            String urll="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/get/userinfo/";


            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {

                String uploadId="2222";

                new MultipartUploadRequest(getContext(),uploadId,urll)
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

                                identityString=serverResponse.getBodyAsString();
                                //identityString=identityString.replace("["," ");
                                //identityString=identityString.replace("]"," ");
                                identityString=identityString.replace("\\","");
                                identityString=identityString.substring(2,identityString.length()-3);
                                //identityString=identityString.replace("'","\"");
                                testString=identityString;
                                parseJsonDataForUserIdentity(identityString);
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

    private class resendEmail extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

            //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
            String urll="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/vemail/";


            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {

                String uploadId="2222";

                new MultipartUploadRequest(getContext(),uploadId,urll)
                        .addParameter("email",unomap_email)
                        .addParameter("resend","true")
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

                                emailresendString=serverResponse.getBodyAsString();



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


    public void parseJsonDataForUserIdentity(String jsonResponse){
        try
        {
            //JSONArray jsonArray=new JSONArray(jsonResponse);
            //for(int t=0;t<jsonArray.length();t++){
              //  JSONArray jsonArray2=new JSONArray(jsonArray);
                //for(int i=0;i<jsonArray2.length();i++){
            //JSONObject jsonObj1=jsonArray.getJSONObject(0);

            //JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONObject jsonObject=new JSONObject(jsonResponse);
            jsonObject=jsonObject.getJSONObject("data");
            for(int i=0;i<jsonObject.length();i++){
                    //JSONObject jsonObject=jsonArray2.getJSONObject(i);
                    test=jsonObject.optString("Univerisity");
                    json_uniname=jsonObject.optString("University");
                    json_unicard=jsonObject.optString("University card");
                    json_unifaculty=jsonObject.optString("University faculty");
                    json_unicourse=jsonObject.optString("University course");
                    json_uniperiod=jsonObject.optString("University period");
                //}
            }

            /*
            SharedPreferences settings =this.getSharedPreferences(SharedPreference_name, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("unomap_email",json_email);
            editor.putString("unomap_emailverified",json_emailverified);
            editor.putString("unomap_phone",json_phone);
            editor.apply();
*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void idclick(Integer i){

        final ProgressDialog progressDialog2 = new ProgressDialog(getContext(),
                R.style.AppTheme);
        progressDialog2.setIndeterminate(true);
        progressDialog2.setMessage("Getting id information...");
        progressDialog2.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                            progressDialog2.dismiss();

                    }

                }, 3000);
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
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

    public static Bitmap rotate(Bitmap bitmap, int degree, boolean mirror) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        if(mirror)mtx.setScale(1,-1);
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }


    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            cursor.close();
            return -1;
        }

        cursor.moveToFirst();
        int orientation = cursor.getInt(0);
        cursor.close();
        cursor = null;
        return orientation;
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 30000);
    }

}
