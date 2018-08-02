package com.example.wuikhong.unomap;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Wui Khong on 9/22/2017.
 */

public class Fragment3 extends Fragment {

    Handler handler3;
    Runnable r3;

    public Fragment3() {

// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment3, container, false);
        /*
        handler3 = new Handler();
        r3 = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //Toast.makeText(getContext(), "user inactive fragment 3", Toast.LENGTH_SHORT).show();
                //Intent w=new Intent(getActivity(),MainActivityUnomap.class);
                //startActivity(w);

            }
        };
        startHandler();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){

                    stopHandler();
                    startHandler();
                }
                return true;
            }
        });
        */
        int[] prgmImages={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher_round};
        //String formattedDate=new SimpleDateFormat("dd-MM-yyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String date1= DateFormat.getDateTimeInstance().format(new Date());
        String date2= DateFormat.getDateTimeInstance().format(new Date());
        String date3= DateFormat.getDateTimeInstance().format(new Date());
        String[] date=new String[]{date1,date2,date3};
        String[] settings=new String[]{"Notification1","Notification2","Notification3"};
        //final String[] settings=new String[]{"Notification1","Notification2","Notification3"};
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.notification,settings);


        ListView list2=(ListView)view.findViewById(R.id.list2);
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //params.setMargins(10,10,10,10);
        //list2.setLayoutParams(params);
        list2.setAdapter(new CustomAdapter(this,settings,date,prgmImages));
        return view;
    }

    public void stopHandler() {
        handler3.removeCallbacks(r3);
    }

    public void startHandler() {
        handler3.postDelayed(r3, 30000);
    }

}