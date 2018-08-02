package com.example.wuikhong.unomap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Wui Khong on 9/27/2017.
 */

public class CustomAdapter extends BaseAdapter {

    String [] result;
    Context context;
    String [] timinglist;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Fragment3 mainActivity, String [] prgmNameList, String [] timing,int[] prgmImages){
        result=prgmNameList;
        context=mainActivity.getContext();
        imageId=prgmImages;
        timinglist=timing;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView=inflater.inflate(R.layout.notification,null);
        holder.tv=(TextView)rowView.findViewById(R.id.textView1);
        holder.img=(ImageView)rowView.findViewById(R.id.imageView1);
        holder.time=(TextView)rowView.findViewById(R.id.timeView);
        holder.tv.setText(result[i]);
        holder.time.setText(timinglist[i]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Clicked "+result[i],Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public class Holder{
        TextView tv;
        ImageView img;
        TextView time;

    }
}
