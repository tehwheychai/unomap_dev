package com.example.wuikhong.unomap;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.wuikhong.unomap.camera.Card;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guest on 12/25/2017.
 */

public class WVclass  extends Activity {

    private static final int ITEM_COUNT = 8;
    String[] names = {"one", "two", "three", "four", "five", "six", "seven", "eight"};
    String[] singers = {"card 1", "card 2", "card 3", "card 4", "card 5", "card 6", "7", "8"};
    //int [] pics={R.drawable.card1,R.drawable.card2,R.drawable.card3,R.drawable.front_page,R.drawable.identifyslogo,R.drawable.colorunomap};
    int[] pics = {R.drawable.user,
            R.drawable.user1,
            R.drawable.globe,
            R.drawable.globe1,
            R.drawable.menu,
            R.drawable.menu1,
            R.drawable.qr,
            R.drawable.qr1};

    List<Card> cardList;
    CardViewAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheelview);

        WheelView wheelView = (WheelView) findViewById(R.id.wheelview22);

        /*
        List<Map.Entry<String, Integer> > entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        Map.Entry<String, Integer> entry;
        for (int i = 0; i < ITEM_COUNT; i++) {
            //Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            String key=names[i];
            Integer value=pics[i];
            Map.Entry<String, Integer> entry=Map.Entry<key,value>;
            entry.
            entries.add();
        }
*/

        List<Map<String, Integer>> entries = new ArrayList<Map<String, Integer>>(ITEM_COUNT);

        //Map.Entry<String, Integer> entry;
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map<String, Integer> entry = new HashMap<String, Integer>();
            entry.put(names[i], pics[i]);


            entries.add(entry);
        }



        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                Drawable my = getResources().getDrawable(pics[position]);

                return my;
            }

            @Override
            public int getCount() {
                return 8;
            }
        });


    }

}
