package com.erge.animatorview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erge.animatorview.R;

/**
 * Created by erge 2020-01-03 13:46
 */
public class TestAdapter2 extends RecyclerView.Adapter<TestAdapter2.ViewHolder> {

    private OnItemClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData("data--" + position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTest = itemView.findViewById(R.id.tv_test);
        }

        public void bindData(String data) {
            tvTest.setText(data);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
