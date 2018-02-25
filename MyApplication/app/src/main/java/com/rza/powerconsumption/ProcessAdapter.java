package com.rza.powerconsumption;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rza on 2017/8/30.
 */

public class ProcessAdapter extends BaseAdapter {
    Activity ctx;
    List<ProcessInfo> listPi;
    LayoutInflater inflater;

    public ProcessAdapter(Activity ctx, List<ProcessInfo> listPi) {
        this.ctx = ctx;
        this.listPi = listPi;
        inflater=LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listPi.size();
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
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.layout_process,null);
            holder.tv1=(TextView) convertView.findViewById(R.id.pid_pro);
            holder.tv2=(TextView) convertView.findViewById(R.id.uid);
            holder.tv3=(TextView) convertView.findViewById(R.id.menSize);
            holder.tv4=(TextView) convertView.findViewById(R.id.packageName);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(listPi.get(position).getPid());
        holder.tv2.setText(listPi.get(position).getUid());
        holder.tv3.setText(listPi.get(position).getMemSize());
        holder.tv4.setText(listPi.get(position).getPackageName());
        return convertView;
    }
    class ViewHolder{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
    }
}
