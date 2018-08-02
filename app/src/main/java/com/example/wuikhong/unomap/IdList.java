package com.example.wuikhong.unomap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuikhong.unomap.camera.Card;
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;
import com.volokh.danylo.utils.DebugRecyclerView;

import android.widget.AbsListView.OnScrollListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/12/2017.
 */

public class IdList extends Activity {

    ListView mRecView2;
    List<Card> cardList2;
    RecyclerView.LayoutManager mLayoutManager2;
    CardViewAdapter cardAdapter2;
    String [] names={"one","two","three","four","five","six","seven","eight"};
    String [] singers={"card 1","card 2","card 3","card 4","card 5", "card 6","7","8"};
    //int [] pics={R.drawable.card1,R.drawable.card2,R.drawable.card3,R.drawable.front_page,R.drawable.identifyslogo,R.drawable.colorunomap};
    int [] pics={R.drawable.user,
            R.drawable.user1,
            R.drawable.globe,
            R.drawable.globe1,
            R.drawable.menu,
            R.drawable.menu1,
    R.drawable.qr,
    R.drawable.qr1};


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment2);

        mRecView2=(ListView)findViewById(R.id.recycle_view2);
        /*
        mRecView2=(ListView)findViewById(R.id.recycle_view2);
        if(mRecView2!=null){
            mRecView2.setHasFixedSize(true);
        }
*/

        int circleRadius=this.getResources().getDisplayMetrics().widthPixels/2;
        int height=this.getResources().getDisplayMetrics().heightPixels/2;
        int xOrigin=0;
        int yOrigin=0;
        ViewGroup.LayoutParams params;

        /*
        mRecView2.setParameters(circleRadius,xOrigin,yOrigin);
        //mLayoutManager=new LinearLayoutManager(this.getContext());
        //mLayoutManager=new GridLayoutManager(this.getContext(),3);
        mLayoutManager2=new LondonEyeLayoutManager(
                circleRadius,
                xOrigin,
                height,
                mRecView2,
                IScrollHandler.Strategy.PIXEL_PERFECT
        );
        //mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //mLayoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);

        mRecView2.setLayoutManager(mLayoutManager2);

        */
        cardList2=new ArrayList<>();

        for(int i=0;i<names.length;i++){
            Card card=new Card(names[i],singers[i],i+1,pics[i]);
            cardList2.add(card);
        }

        cardAdapter2=new CardViewAdapter(cardList2);
        //mRecView2.setAdapter(cardAdapter2);
        mRecView2.setAdapter(new MyAdapter());
        mRecView2.setClipToPadding(false);
        mRecView2.setClipChildren(false);
        mRecView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Card at "+i+" is clicked",Toast.LENGTH_SHORT).show();
                if (i==2){
                    setContentView(R.layout.add_id_page2);
                }
            }
        });
        mRecView2.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = 0; i < mRecView2.getChildCount(); i++) {
                    mRecView2.getChildAt(i).invalidate();
                }
            }
        });
        //cardAdapter.notifyDataSetChanged();



        /*
        mRecView2.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(IdList.this,"Card at "+position+" is clicked",Toast.LENGTH_SHORT).show();
            }
        }));
        */
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 99;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                MatrixView m = (MatrixView) LayoutInflater.from(IdList.this).inflate(R.layout.view_list_item, null);
                m.setParentHeight(mRecView2.getHeight());
                convertView = m;
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            imageView.setImageResource(pics[position % pics.length]);
            TextView textView=(TextView)convertView.findViewById(R.id.text);
            textView.setText(names[position%names.length]);

            return convertView;
        }

    }

    @Deprecated
    public void changeGroupFlag(Object obj) throws Exception {
        Field[] f = obj.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredFields(); // 获得成员映射数组
        for (Field tem : f) {
            if (tem.getName().equals("mGroupFlags")) {
                tem.setAccessible(true);
                Integer mGroupFlags = (Integer) tem.get(obj);
                int newGroupFlags = mGroupFlags & 0xfffff8;
                tem.set(obj, newGroupFlags);
            }
        }
    }
}


