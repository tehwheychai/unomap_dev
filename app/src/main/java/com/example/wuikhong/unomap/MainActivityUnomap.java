package com.example.wuikhong.unomap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.security.AccessController.getContext;

public class MainActivityUnomap extends Activity {

    EditText editText_email;
    EditText editText_password;
    TextView textView_unomap;
    TextView textView_name;
    TextView textView_name2;
    TextView textView_name3;
    Button button_signIn;
    Button button_signUp;
    Button button_openCamera;
    private static final String licenseFileName = "AbbyyRtrSdk.license";
    private CompoundButton autoFocus;
    String unomap_pin;
    Thread splashthread;
    ImageView splash_image;
    CheckBox rmb_me;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    LinearLayout container;
    RelativeLayout container2;
    AnimationDrawable anim;
    Typeface ikaros;


    EditText sign_in_page_editText_pinnumber;
    EditText sign_in_page_editText_user_email;
    Button sign_in_page_button_fingerprint;
    Button sign_in_page_button_pin;
    Button sign_in_page_button_signin;

    String login_respond="one";
    String vemail="no";
    EditText signin_username;
    EditText signin_pinnumber;
    String signin_username_string;
    String signin_pinnumber_string;
    String SharedPreference_name=new String("Unomap");

    String responseString="love you";
    final static String boundary="*****";

    String json_address;
    String json_email;
    String json_emailverified;
    String json_firstname;
    String json_lastname;
    String json_phone;
    String json_username;
    String json_verified;
    String temp_email;
    String json_id;

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("centurygothic.ttf").
                setFontAttrId(R.attr.fontPath).
                build());


        setContentView(R.layout.landing_page);
        button_signIn = (Button)findViewById(R.id.landing_button_signin);
        button_signUp = (Button)findViewById(R.id.landing_button_signup);
        textView_name = (TextView)findViewById(R.id.landing_unomaplogoname);
        ikaros=Typeface.createFromAsset(getAssets(),"ikaros.otf");
        textView_name.setTypeface(ikaros);

        UploadService.NAMESPACE="com.unomap.app";
        /*
        //setContentView(R.layout.splash_screen);
        setContentView(R.layout.activity_main);
        //editText_email = (EditText) findViewById(R.id.user_email);
        //editText_password=(EditText)findViewById(R.id.editText3);


        button_signIn = (Button)findViewById(R.id.sign_up_button);
        button_signUp = (Button)findViewById(R.id.sign_in_button);
        textView_name = (TextView)findViewById(R.id.activity_main_unomaplogoname);
        sign_in_page_editText_user_email=(EditText)findViewById(R.id.user_email);
        sign_in_page_button_fingerprint=(Button)findViewById(R.id.sign_in_page_button_fingerprint);
        sign_in_page_button_pin=(Button)findViewById(R.id.sign_in_page_button_pin);

        container2=(RelativeLayout)findViewById(R.id.container2);

        anim = (AnimationDrawable)container2.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        anim.start();

*/

        //startAnimation();




        signin(button_signIn);
















    }




    public void fingerprint(View view){
        //String name=new String("Unomap");
        //SharedPreferences settings=getApplicationContext().getSharedPreferences(name,0);
        //String unomap_pin=settings.getString("unomap_pin","0");
        signin_username_string=signin_username.getText().toString();
        signin_pinnumber_string=signin_pinnumber.getText().toString();

        SharedPreferences settings =this.getSharedPreferences(SharedPreference_name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("unomap_username",signin_username_string);
        editor.putString("unomap_password",signin_pinnumber_string);
        editor.apply();


        new Login().execute();



        final ProgressDialog progressDialog = new ProgressDialog(MainActivityUnomap.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();




        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        parseJsonDataForLogin(login_respond);

                                        new Verifyemail().execute();
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        SharedPreferences settings = getSharedPreferences(SharedPreference_name, 0);
                                                        SharedPreferences.Editor editor = settings.edit();
                                                        editor.putString("unomap_emailverified", vemail);
                                                        editor.apply();
                                                        //Toast.makeText(MainActivityUnomap.this, vemail, Toast.LENGTH_LONG).show();

                                                        if (login_respond.contains("Login successful"))
                                                        //  if(login_respond.equals("\"Login successful\""))
                                                        {
                                                            //parseJsonDataForLogin(login_respond);

                                                            Toast.makeText(MainActivityUnomap.this, "Welcome", Toast.LENGTH_LONG).show();


                                                            //Toast.makeText(MainActivityUnomap.this, login_respond, Toast.LENGTH_LONG).show();


                                                            //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
                                                            //Intent intent = new Intent(MainActivityUnomap.this, FingerprintActivity.class);
                                                            Intent intent = new Intent(MainActivityUnomap.this, UserPage.class);
                                                            //intent.putExtra("unomap_name",signin_username_string);
                                                            //intent.putExtra("unomap_pin",signin_pinnumber_string);
                                                            startActivity(intent);
                                                            progressDialog.dismiss();
                                                        } else {

                                                            //Toast.makeText(MainActivityUnomap.this, responseString, Toast.LENGTH_LONG).show();

                                                            progressDialog.dismiss();
                                                            Toast.makeText(MainActivityUnomap.this, login_respond, Toast.LENGTH_SHORT).show();
                                                            setContentView(R.layout.landing_page);
                                                        }
                                                        ;
                                                    }
                                                }, 2000);
                                    }
                                },200);
                        /*
                        if(login_respond.indexOf(signin_username_string)!=-1)
                          //  if(login_respond.equals("\"Login successful\""))
                        {
                            parseJsonDataForLogin(login_respond);



                            //Toast.makeText(MainActivityUnomap.this, login_respond, Toast.LENGTH_LONG).show();

                            Intent intent= new Intent(MainActivityUnomap.this, FingerprintActivity.class);
                            //intent.putExtra("unomap_name",signin_username_string);
                            //intent.putExtra("unomap_pin",signin_pinnumber_string);
                            startActivity(intent);
                            progressDialog.dismiss();

                            }




                        else {

                            //Toast.makeText(MainActivityUnomap.this, responseString, Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();
                            Toast.makeText(MainActivityUnomap.this, login_respond, Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.landing_page);
                        }
                    */

                }
                }, 3000);




        /*
        setContentView(R.layout.fingerprint_page);
        container=(LinearLayout)findViewById(R.id.container);

        anim = (AnimationDrawable)container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        anim.start();
        */

    }

    public void pin(View view){
        setContentView(R.layout.sign_in_page_pinnumber);
        sign_in_page_editText_pinnumber=(EditText)findViewById(R.id.user_pin_number);
        sign_in_page_button_signin=(Button)findViewById(R.id.sign_in_page_button_signin);


    }

    public void signin(View view){
        //Intent i=new Intent(MainActivityUnomap.this,UserPage.class);
        //startActivity(i);


        setContentView(R.layout.sign_in_page);
        Button next=(Button)findViewById(R.id.sign_in_page_button_signin);
        //Button back=(Button)findViewById(R.id.sign_in_page_button_back);
        signin_username=(EditText)findViewById(R.id.sign_in_page_edittext_username);
        signin_pinnumber=(EditText)findViewById(R.id.user_pin_number);
        rmb_me=(CheckBox)findViewById(R.id.sign_in_page_checkbox_rmbme);

        textView_name2 = (TextView)findViewById(R.id.activity_main_unomaplogoname);
        ikaros=Typeface.createFromAsset(getAssets(),"ikaros.otf");
        textView_name2.setTypeface(ikaros);

        SharedPreferences settings=getApplicationContext().getSharedPreferences(SharedPreference_name,0);
        String rmb_me_username1=settings.getString("rmb_me_username",null);
        if(rmb_me_username1!=null)
        {
            signin_username.setText(rmb_me_username1);
            rmb_me.setChecked(true);
        }
        rmb_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    String rmb_me_username=signin_username.getText().toString();
                    if(signin_username!=null) {
                        SharedPreferences settings = getApplicationContext().getSharedPreferences(SharedPreference_name, 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putString("rmb_me_username", rmb_me_username);

                        editor.apply();
                    }
                }
            }
        });
    }


    public void call_sign_up_page(View view)
    {
        Intent intent= new Intent(MainActivityUnomap.this, Sign_up_page.class);
        startActivity(intent);
    }

    private class Login extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url1= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/login/5234gj39gg398");
                String stringurl="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/login?user_id="+signin_username_string
                        +"&password="+signin_pinnumber_string+"&role=S";
                URL url=new URL(stringurl);


                HttpURLConnection urlcon=(HttpURLConnection)url.openConnection();

                urlcon.setUseCaches(false);
                urlcon.setDoOutput(true);
                urlcon.setRequestMethod("POST");
                urlcon.setRequestProperty("Content-Type","application/json");
                urlcon.setRequestProperty("Connection","Keep-Alive");
                urlcon.setRequestProperty("Cache-Control","no-cache");
                urlcon.setRequestProperty("header-param_3","value-3");
                urlcon.setRequestProperty("header-param_4","value-4");
                urlcon.setRequestProperty("Authorisation","Basic");

                urlcon.connect();






                JSONObject jsonRequest=new JSONObject();
                //jsonRequest.put("username",signin_username_string);
                //jsonRequest.put("password",signin_pinnumber_string);


                OutputStreamWriter out=new OutputStreamWriter((urlcon.getOutputStream()));
                out.write(jsonRequest.toString());
                out.close();

                int statuscode=urlcon.getResponseCode();
                String statusmsg=urlcon.getResponseMessage();
                //if(statuscode==200){
                InputStream it=new BufferedInputStream(urlcon.getInputStream());
                InputStreamReader read=new InputStreamReader(it);
                BufferedReader buff=new BufferedReader(read);
                StringBuilder dta=new StringBuilder();
                String chunks;
                while((chunks=buff.readLine())!=null) {
                    dta.append(chunks);
                }
                String returndata=dta.toString();
                login_respond=returndata;
                //respond1=statusmsg;
                return returndata;
                //}else{

                //}


            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }



    }


    private class Verifyemail extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

                //URL url2= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/vemail/");
                String urll = "http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/vemail/";


                try {

                    String uploadId = "2222";

                    new MultipartUploadRequest(MainActivityUnomap.this, uploadId, urll)
                            .addParameter("email", json_email)
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

                                    vemail = serverResponse.getBodyAsString();


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

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void parseJsonDataForLogin(String jsonResponse){


        try
        {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            //for(int i=0;i<jsonObject.length();i++) {
             data=jsonObject.optString("data");

            //}
            JSONObject jsonObject1=new JSONObject(data);
            //for(int i=0;i<jsonObject1.length();i++){
                //JSONObject jsonObject=jsonArray.getJSONObject(i);
                json_username=jsonObject1.optString("username");
    json_id=jsonObject1.optString("id");
                json_email=jsonObject1.optString("email");
                temp_email=json_email;
                //json_emailverified=jsonObject.optString("email_verified");

                json_phone=jsonObject1.optString("phone");
           // Toast.makeText(MainActivityUnomap.this, json_email,Toast.LENGTH_LONG).show();
                //}
            SharedPreferences settings =this.getSharedPreferences(SharedPreference_name, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("unomap_email",json_email);
            editor.putString("unomap_id",json_id);
            //editor.putString("unomap_emailverified",json_emailverified);
            editor.putString("unomap_phone",json_phone);
            editor.apply();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {

        finish();
    }

}

