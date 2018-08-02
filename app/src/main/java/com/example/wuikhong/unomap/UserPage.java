package com.example.wuikhong.unomap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.net.MalformedURLException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Wui Khong on 9/22/2017.
 */

public class UserPage extends FragmentActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    Runnable r;
    Handler handler;

    String logoutrespond;

    //private String unomap_name=getIntent().getStringExtra("unomap_name");
    //private String unomap_pin=getIntent().getStringExtra("unomap_pin");
    // String unomap_name="TEH9";
    //String unomap_pin="123456";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("centurygothic.ttf").
                setFontAttrId(R.attr.fontPath).
                build());
        //Handler handler=new Handler();
        //handler.postDelayed(new Runnable() {
        //  @Override
        //   public void run() {
        //        Intent w=new Intent(UserPage.this,MainActivityUnomap.class);
        //        startActivity(w);
        //   }
        //},45000);

        /*
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //Toast.makeText(UserPage.this, "user inactive",Toast.LENGTH_SHORT).show();
                Intent w=new Intent(UserPage.this,MainActivityUnomap.class);
                startActivity(w);
            }
        };
        */
        //startHandler();



        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.user_page_tab);
// get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
// Create a new Tab named "First"
        final TabLayout.Tab firstTab = tabLayout.newTab();
        //firstTab.setText("First"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.menu1); // set an icon for the
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        final TabLayout.Tab secondTab = tabLayout.newTab();
        //secondTab.setText("Second"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.user); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        final TabLayout.Tab thirdTab = tabLayout.newTab();
        //thirdTab.setText("Third"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.globe1); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout
        final TabLayout.Tab fourthTab = tabLayout.newTab();
        //fourthTab.setText(""); // set the Text for the first Tab
        fourthTab.setIcon(R.drawable.qr1); // set an icon for the first tab
        tabLayout.addTab(fourthTab); // add  the tab at in the TabLayout


       FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.simpleFrameLayout, new Fragment1());
        //Bundle bundle=new Bundle();
        //bundle.putString("unomap_name",unomap_name);
        //bundle.putString("unomap_pin",unomap_pin);

        //Fragment fragment = new Fragment2();
        //fragment.setArguments(bundle);
        //ft.replace(R.id.simpleFrameLayout,fragment);
        ft.commit();




        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        final PageAdapter adapter=new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });


        viewPager.setCurrentItem(secondTab.getPosition());

// perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                //Bundle bundle=new Bundle();
                //bundle.putString("unomap_name",unomap_name);
                //bundle.putString("unomap_pin",unomap_pin);

                Fragment fragment = new Fragment2();
                //fragment.setArguments(bundle);
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Fragment2();
                        firstTab.setIcon(R.drawable.menu);
                        secondTab.setIcon(R.drawable.user1);
                        thirdTab.setIcon(R.drawable.globe1);
                        fourthTab.setIcon(R.drawable.qr1);
                        break;
                    case 1:
                        fragment = new Fragment1();
                        firstTab.setIcon(R.drawable.menu1);
                        secondTab.setIcon(R.drawable.user);
                        thirdTab.setIcon(R.drawable.globe1);
                        fourthTab.setIcon(R.drawable.qr1);
                        break;
                    case 2:
                        fragment = new Fragment3();
                        firstTab.setIcon(R.drawable.menu1);
                        secondTab.setIcon(R.drawable.user1);
                        thirdTab.setIcon(R.drawable.globe);
                        fourthTab.setIcon(R.drawable.qr1);
                        break;
                    case 3:
                        fragment = new Fragment4();
                        firstTab.setIcon(R.drawable.menu1);
                        secondTab.setIcon(R.drawable.user1);
                        thirdTab.setIcon(R.drawable.globe1);
                        fourthTab.setIcon(R.drawable.qr);
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed(){

        int count=getFragmentManager().getBackStackEntryCount();

        if(count==0){
        new AlertDialog.Builder(this)
                .setTitle("Signing out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new Logout().execute();
                        final ProgressDialog progressDialog = new ProgressDialog(UserPage.this,
                                R.style.AppTheme);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Signing out...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Toast.makeText(UserPage.this,"Logout successfully",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                        finish();
                        Intent w=new Intent(UserPage.this,MainActivityUnomap.class);
                        w.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(w);
                                    }
                                }, 2000);
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }else{
        getFragmentManager().popBackStack();
    }
    }


    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        //stopHandler();//stop first and then start
        //startHandler();
    }
    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 30000);
    }


private class Logout extends AsyncTask<String,String,String> {


    @Override
    protected String doInBackground(String... strings) {

        //URL url2= new URL("http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/api/vemail/");
        String urll = "http://ec2-13-229-92-30.ap-southeast-1.compute.amazonaws.com:8000/logout";


        try {

            String uploadId = "2222";

            new MultipartUploadRequest(UserPage.this, uploadId, urll)
                    //.addParameter("email", json_email)
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

                            logoutrespond = serverResponse.getBodyAsString();



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



}