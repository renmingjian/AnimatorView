package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.erge.animatorview.listener.ListAdapter;

/**
 * Created by erge 11/29/20 4:06 PM
 */
public class LinearListView extends LinearLayout implements View.OnClickListener {

    private OnItemClickListener onItemClickListener;
    private ListAdapter adapter;

    public LinearListView(Context context) {
        this(context, null);
        setOrientation(VERTICAL);
    }

    public LinearListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(ListAdapter adapter) {
        setOrientation(VERTICAL);
        this.adapter = adapter;
        addAllViews();
    }

    private void addAllViews() {
        int childCount = adapter.getCount();
        if (childCount == 0) return;
        removeAllViews();
        for (int i = 0; i < childCount; i++) {
            View child = adapter.getView(i, null, this);
            child.setTag(i);
            child.setOnClickListener(this);
            addView(child);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        }, 300);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = (int) v.getTag();
            onItemClickListener.onItemClick(position, v, adapter.getItem(position));
        }
    }

    /**
     * 刷新item数据
     *
     * @param position item位置
     */
    public void notifyItemChanged(int position) {
        adapter.getView(position, getChildAt(position), this);
    }

    /**
     * 刷新所有
     */
    public void notifyDataSetChanged() {
        int adapterCount = adapter.getCount();
        int childCount = getChildCount();
        // 如果数量一致，则只重新给View赋值
        if (adapterCount == childCount) {
            for (int i = 0; i < adapter.getCount(); i++) {
                adapter.getView(i, getChildAt(i), this);
            }
        } else {
            // 如果数量发生改变，则需要重新添加，重新测量大小
            addAllViews();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView, Object o);
    }

}
