package com.example.wuikhong.unomap;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.security.AccessController.getContext;

/**
 * Created by User on 8/3/2017.
 */

public class Sign_up_page extends Activity implements UploadStatusDelegate{
    private static final String TAG = "SignupActivity";
    EditText sign_up_page_editText_name;
    EditText sign_up_page_editText_email;
    //EditText sign_up_page_editText_password;
    //EditText sign_up_page_editText_retypepassword;
    EditText sign_up_page_editText_phonenumber;
    Button sign_up_page_button_signup;

    TextView sign_up_page_textView_temporary_name;
    TextView sign_up_page_textView_temporary_phonenumber;
    TextView sign_up_page_textView_temporary_email;
    EditText sign_up_page_editText_pinnumber;
    EditText sign_up_page_editText_retypepinnumber;
    EditText sign_up_page_editText_firstname;
    EditText sign_up_page_editText_lastname;

    Button verification;

    EditText verification_page_textView_verification_code;
    String user_verification_code;
    String respond;
    String respond1;
    String name;
    String email;
    String phone;
    String pin;
    String retypepin;
    String firstname;
    String lastname;
    URL url;
    String register_respond="register respond???";
    String respond2;

    TextView textView_name;
    TextView textView_name2;
    Typeface ikaros;


    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("centurygothic.ttf").
                setFontAttrId(R.attr.fontPath).
                build());


        setContentView(R.layout.sign_up_page);

        sign_up_page_editText_name=(EditText)findViewById(R.id.sign_up_page_name);
        sign_up_page_editText_email=(EditText)findViewById(R.id.user_email);
        //sign_up_page_editText_password=(EditText)findViewById(R.id.user_password);
        //sign_up_page_editText_retypepassword=(EditText)findViewById(R.id.user_retype_password);
        sign_up_page_button_signup=(Button)findViewById(R.id.sign_up_page_button_signup);
        sign_up_page_editText_phonenumber=(EditText)findViewById(R.id.sign_up_page_phone_number_edittext);
        sign_up_page_editText_firstname=(EditText)findViewById(R.id.sign_up_page_firstname);
        sign_up_page_editText_lastname=(EditText)findViewById(R.id.sign_up_page_lastname);
        textView_name = (TextView)findViewById(R.id.activity_main_unomaplogoname);
        ikaros= Typeface.createFromAsset(getAssets(),"ikaros.otf");
        textView_name.setTypeface(ikaros);






    }




    public void next(View view){

        name = sign_up_page_editText_name.getText().toString();
        phone = sign_up_page_editText_phonenumber.getText().toString();
        email = sign_up_page_editText_email.getText().toString();


        setContentView(R.layout.sign_up_page_pinnumber);

        textView_name2 = (TextView)findViewById(R.id.landing_unomaplogoname);
        ikaros= Typeface.createFromAsset(getAssets(),"ikaros.otf");
        textView_name.setTypeface(ikaros);


        sign_up_page_textView_temporary_name=(TextView)findViewById(R.id.sign_up_page_name_temporary_textview);
        sign_up_page_textView_temporary_phonenumber=(TextView)findViewById(R.id.sign_up_page_phone_number_temporary_textview);
        sign_up_page_textView_temporary_email=(TextView)findViewById(R.id.sign_up_page_email_temporary_textview);

        sign_up_page_editText_pinnumber=(EditText)findViewById(R.id.user_pin_number);
        sign_up_page_editText_retypepinnumber=(EditText)findViewById(R.id.user_retype_pin_number);



        sign_up_page_textView_temporary_name.setText(name);
        sign_up_page_textView_temporary_phonenumber.setText(phone);
        sign_up_page_textView_temporary_email.setText(email);

        startAnimation2();

    }



    public void back(View view){

        setContentView(R.layout.sign_up_page);

    }


    public void tnc(View view){
        setContentView(R.layout.terms_and_condition);
    }

    public void pp(View view){
        setContentView(R.layout.private_policy);
        TextView pp_text=(TextView)findViewById(R.id.private_policy_textview);
    }
    

    public void verify(View view){

        user_verification_code=verification_page_textView_verification_code.getText().toString();
        new Verify().execute();
        final ProgressDialog progressDialog = new ProgressDialog(Sign_up_page.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying Account...");
        progressDialog.show();




        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if(respond2.indexOf("verified")!=-1) {
                            Intent i = new Intent(getApplicationContext(), UserPage.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),respond2+"balbalbalababa",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),respond2,Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }

                }, 2000);

    }



    public void startAnimation2() {
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha_splash);
        anim1.reset();
        sign_up_page_textView_temporary_email.clearAnimation();
        sign_up_page_textView_temporary_email.startAnimation(anim1);
        sign_up_page_textView_temporary_name.clearAnimation();
        sign_up_page_textView_temporary_name.startAnimation(anim1);
        sign_up_page_textView_temporary_phonenumber.clearAnimation();
        sign_up_page_textView_temporary_phonenumber.startAnimation(anim1);

        anim1 = AnimationUtils.loadAnimation(this, R.anim.translate_rightleft3);
        anim1.reset();
        sign_up_page_textView_temporary_email.clearAnimation();
        sign_up_page_textView_temporary_email.startAnimation(anim1);
        anim1 = AnimationUtils.loadAnimation(this, R.anim.translate_rightleft);
        anim1.reset();
        sign_up_page_textView_temporary_name.clearAnimation();
        sign_up_page_textView_temporary_name.startAnimation(anim1);
        anim1 = AnimationUtils.loadAnimation(this, R.anim.translate_rightleft2);
        anim1.reset();
        sign_up_page_textView_temporary_phonenumber.clearAnimation();
        sign_up_page_textView_temporary_phonenumber.startAnimation(anim1);
    }











    public void signup(View view) throws IOException {



        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        sign_up_page_button_signup.setEnabled(false);



        name = sign_up_page_editText_name.getText().toString();
        email = sign_up_page_editText_email.getText().toString();
        firstname=sign_up_page_editText_firstname.getText().toString();
        lastname=sign_up_page_editText_lastname.getText().toString();
        //String password = sign_up_page_editText_password.getText().toString();
        //String retype_password = sign_up_page_editText_retypepassword.getText().toString();
        phone = sign_up_page_editText_phonenumber.getText().toString();
        pin = sign_up_page_editText_pinnumber.getText().toString();
        retypepin = sign_up_page_editText_retypepinnumber.getText().toString();

        String name1=new String("Unomap");
        SharedPreferences settings=getApplicationContext().getSharedPreferences(name1,0);
        SharedPreferences.Editor editor=settings.edit();

        editor.putString("unomap_name",name);
        editor.putString("unomap_email",email);
        editor.putString("unomap_phone",phone);
        editor.putString("unomap_pin",pin);

        editor.apply();




        //new Information().execute();
        new xRegister().execute();
        //toAPI();



        final ProgressDialog progressDialog2 = new ProgressDialog(Sign_up_page.this,
                R.style.AppTheme);
        progressDialog2.setIndeterminate(true);
        progressDialog2.setMessage("Creating Account...");
        progressDialog2.show();




        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if(register_respond.contains("triggered")) {
                        //if(true){
                            onSignupSuccess();
                            // onSignupFailed();
                            Toast.makeText(Sign_up_page.this, register_respond, Toast.LENGTH_LONG).show();
                            progressDialog2.dismiss();
                        }
                        else if(register_respond.contains("Internal")) {
                            //if(true){
                            onSignupSuccess();
                            // onSignupFailed();
                            Toast.makeText(Sign_up_page.this, register_respond, Toast.LENGTH_LONG).show();
                            progressDialog2.dismiss();
                        }
                       else if(register_respond.contains("Username taken")){
                           //if username as already been taken
                            Toast.makeText(Sign_up_page.this,"The username has already been taken. Please try another username.",Toast.LENGTH_LONG).show();
                            Sign_up_page.this.recreate();




                        }
                       else if(register_respond.contains("This email address")){
                            Toast.makeText(Sign_up_page.this,"This email has already been registered. Please log in.",Toast.LENGTH_LONG).show();
                            //Sign_up_page.this.recreate();
                            //String name1=new String("Unomap");
                            //SharedPreferences settings=getApplicationContext().getSharedPreferences(name1,0);

                            //sign_up_page_editText_name.setText(settings.getString("unomap_name","empty"));
                            //sign_up_page_editText_phonenumber.setText(settings.getString("unomap_phone","empty"));
                            Intent i=new Intent(Sign_up_page.this,MainActivityUnomap.class);
                            startActivity(i);



                        }
                       else{
                            {
                                onSignupFailed();

                                progressDialog2.dismiss();
                            }
                        }
                        }
                    
                }, 7000);
    }

    public void onSignupSuccess() {
        //Toast.makeText(getBaseContext(), respond, Toast.LENGTH_LONG).show();


        sign_up_page_button_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        //finish();





        //sign_up_page_textView_temporary_phonenumber.setText(respond);
        //sign_up_page_textView_temporary_name.setText(respond1);
        //sign_up_page_textView_temporary_email.setText("WRONG");
        setContentView(R.layout.verification_page);
        TextView veri=(TextView)findViewById(R.id.verification_page_email);
        veri.setText(phone);
        //Intent i=new Intent(this,UserPage.class);
        //startActivity(i);

        verification_page_textView_verification_code=(EditText)findViewById(R.id.user_verification_code);


        //22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
        //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
        //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
        Intent i = new Intent(getApplicationContext(), UserPage.class);
        startActivity(i);
        //22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
        //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
        //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        sign_up_page_textView_temporary_phonenumber.setText(register_respond);

        sign_up_page_button_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name = sign_up_page_editText_name.getText().toString();
        email = sign_up_page_editText_email.getText().toString();
        //String password = sign_up_page_editText_password.getText().toString();
        //String retype_password = sign_up_page_editText_retypepassword.getText().toString();
        pin = sign_up_page_editText_pinnumber.getText().toString();
        String retypepin = sign_up_page_editText_retypepinnumber.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            sign_up_page_editText_name.setError("at least 3 characters");
            valid = false;
        } else {
            sign_up_page_editText_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sign_up_page_editText_email.setError("enter a valid email address");
            valid = false;
        } else {
            sign_up_page_editText_email.setError(null);
        }

        if (pin.isEmpty() || pin.length() != 6 ) {
            sign_up_page_editText_pinnumber.setError("between 4 and 10 alphanumeric characters");
            if (pin!=retypepin){
                sign_up_page_editText_pinnumber.setError("pin not equal to retype pin");
                valid = false;
            }
            valid = false;
        } else {
            sign_up_page_editText_pinnumber.setError(null);
        }

        /*if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            sign_up_page_editText_password.setError("between 4 and 10 alphanumeric characters");
            if (password!=retype_password){
                sign_up_page_editText_password.setError("password not equal to retype password");
                valid = false;
            }
            valid = false;
        } else {
            sign_up_page_editText_password.setError(null);
        }*/

        return valid;
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


    private class Information extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/register/");
                //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/register/");

                HttpURLConnection urlcon=(HttpURLConnection)url.openConnection();
                urlcon.setRequestProperty("Content-Type","application/json");
                urlcon.setRequestProperty("header-param_3","value-3");
                urlcon.setRequestProperty("header-param_4","value-4");
                urlcon.setRequestProperty("Authorisation","Basic");
                urlcon.setRequestMethod("POST");
                urlcon.connect();

                JSONObject jsonRequest=new JSONObject();
                jsonRequest.put("username",name);
                jsonRequest.put("phone",phone);
                //jsonRequest.put("firstname",firstname);
                //jsonRequest.put("lastname",lastname);
                        jsonRequest.put("email",email);
                        jsonRequest.put("password",pin);


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
                    register_respond=returndata;
                    respond1=statusmsg;
                    return returndata;
                //}else{

                //}


            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class Verify extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/verify/");

                HttpURLConnection urlcon=(HttpURLConnection)url.openConnection();
                urlcon.setRequestProperty("Content-Type","application/json");
                urlcon.setRequestProperty("header-param_3","value-3");
                urlcon.setRequestProperty("header-param_4","value-4");
                urlcon.setRequestProperty("Authorisation","Basic");
                urlcon.setRequestMethod("POST");
                urlcon.connect();

                JSONObject jsonRequest=new JSONObject();
                jsonRequest.put("username",name);

                jsonRequest.put("password",pin);
                jsonRequest.put("code",user_verification_code);


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
                respond2=returndata;
                return returndata;
                //}else{

                //}


            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class xRegister extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {

            //URL url= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/upload/");
            String urll="http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/register/";
            //File mediaStorageDir=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files");
            //String mImageName=unomap_username+".jpg";
            //String pathname=mediaStorageDir.getPath()+File.separator+mImageName;

            //String pathname=Environment.getExternalStorageDirectory()+"/Android/data/"+getContext().getPackageName()+"/Files";

            //File file=new File(filepath,"unomap_profile_pic.jpg");


            try {

                String uploadId="2228";

                new MultipartUploadRequest(Sign_up_page.this,uploadId,urll)
                        .addParameter("username",name)
                        .addParameter("password",pin)
                        .addParameter("email",email)
                        .addParameter("phone",phone)

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

                                register_respond=serverResponse.getBodyAsString();
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

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
