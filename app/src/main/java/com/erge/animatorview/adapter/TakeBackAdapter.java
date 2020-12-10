package com.erge.animatorview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erge.animatorview.R;
import com.erge.animatorview.listener.ListAdapter;

import java.util.List;


/**
 * Created by erge 11/29/20 4:16 PM
 */
public class TakeBackAdapter implements ListAdapter {

    private final List<String> list;

    public TakeBackAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_test, null);
        }
        TextView tvTest = convertView.findViewById(R.id.tv_test);
        tvTest.setText(list.get(position));
        return convertView;
    }
}
