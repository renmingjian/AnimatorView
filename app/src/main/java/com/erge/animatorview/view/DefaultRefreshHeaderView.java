package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erge.animatorview.R;

/**
 * Created by erge 2020/10/22 11:39 AM
 */
public class DefaultRefreshHeaderView extends RefreshHeaderView {

    private TextView tv_refresh;

    public DefaultRefreshHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public DefaultRefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.default_refresh_header, this);
        tv_refresh = view.findViewById(R.id.tv_refresh);
    }

    @Override
    public void setPullDistance(int pullDistance) {

    }
}
