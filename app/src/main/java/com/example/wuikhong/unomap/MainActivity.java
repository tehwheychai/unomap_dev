package com.example.wuikhong.unomap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText editText_email;
    EditText editText_password;
    TextView textView_unomap;
    TextView textView_name;
    Button button_signIn;
    Button button_signUp;
    Button button_openCamera;
    private static final String licenseFileName = "AbbyyRtrSdk.license";
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //editText_email = (EditText) findViewById(R.id.editText2);
        //editText_password=(EditText)findViewById(R.id.editText3);
        button_signIn = (Button)findViewById(R.id.sign_up_button);
        button_signUp = (Button)findViewById(R.id.sign_in_button);
        textView_name = (TextView)findViewById(R.id.activity_main_unomaplogoname);



    }

    public void back(View view){
        setContentView(R.layout.activity_main);
    }

    public void call_sign_up_page(View view)
    {
        Intent intent= new Intent(MainActivity.this,Sign_up_page.class);
        startActivity(intent);
    }


    }



