package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erge.animatorview.R;

/**
 * Created by erge 2020/10/22 11:36 AM
 */
public abstract class RefreshHeaderView extends FrameLayout {

    public RefreshHeaderView(@NonNull Context context) {
        this(context, null);

    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void setPullDistance(int pullDistance);
}
