package com.erge.animatorview.listener;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by erge 11/29/20 4:38 PM
 */
public interface ListAdapter {

    int getCount();

    Object getItem(int position);

    long getItemId(int position);

    View getView(int position, View convertView, ViewGroup parent);

}
