package com.example.wuikhong.unomap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Random;

/**
 * Created by Wui Khong on 11/5/2017.
 */

public class Splash extends Activity {
    Thread splashThread;
    ImageView imageView;
    RelativeLayout container2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        startAnimation();
        imageView = (ImageView) findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int[] ids = new int[]{R.drawable.identifyslogo, R.drawable.identifyslogo};
        Random randomGenerator = new Random();
        int r = randomGenerator.nextInt(ids.length);
        //this.imageView.setImageDrawable(getResouces().getDrawable(ids[r]));
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        container2=(RelativeLayout)findViewById(R.id.lin_lay);


        TextView splash1=(TextView)findViewById(R.id.splash_appname);
        TextView splash2=(TextView)findViewById(R.id.splash_appname2);
        Typeface ikaros;
        ikaros= Typeface.createFromAsset(getAssets(),"ikaros.otf");
        splash1.setTypeface(ikaros);
        splash2.setTypeface(ikaros);

        AnimationDrawable anim333 = (AnimationDrawable)container2.getBackground();
        anim333.setEnterFadeDuration(1000);
        anim333.setExitFadeDuration(1000);
        anim333.start();
    }

    public void startAnimation(){
        Animation anim1= AnimationUtils.loadAnimation(this,R.anim.alpha_splash);
        anim1.reset();
        RelativeLayout l=(RelativeLayout)findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim1);

        anim1=AnimationUtils.loadAnimation(this,R.anim.translate_splash);
        anim1.reset();
        ImageView iv=(ImageView)findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim1);

        TextView tv1=(TextView)findViewById(R.id.splash_appname);
        tv1.clearAnimation();
        tv1.startAnimation(anim1);

        TextView tv2=(TextView)findViewById(R.id.splash_appname2);
        tv2.clearAnimation();
        tv2.startAnimation(anim1);

        splashThread = new Thread(){
            @Override
            public void run(){
                try {
                int waited=0;
                while(waited<2500){

                        sleep(100);
                    waited+=100;

                }
                Intent intent=new Intent(Splash.this,MainActivityUnomap.class);
                //intent.setFlag(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                Splash.this.finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                    Splash.this.finish();
                }
                }
        };
        splashThread.start();
    }




}
