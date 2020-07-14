package com.erge.animatorview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.erge.animatorview.R;
import com.erge.animatorview.bean.CarControlInfo;

import java.util.List;

/**
 * Created by erge 2020/5/18 7:45 PM
 */
public class CarControlAdapter {

    private List<CarControlInfo> mData;
    private Context mContext;

    public CarControlAdapter(List<CarControlInfo> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public CarControlInfo getItem(int position) {
        return mData.get(position);
    }

    public int getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_car_control, null);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
    }

}
