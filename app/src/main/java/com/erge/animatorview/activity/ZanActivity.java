package com.erge.animatorview.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erge.animatorview.R;
import com.erge.animatorview.adapter.TestAdapter;
import com.erge.animatorview.view.ZanLayout;

/**
 * Created by erge 2020-01-03 13:30
 */
public class ZanActivity extends AppCompatActivity implements TestAdapter.OnItemClickListener {

    private ZanLayout zanLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        zanLayout = findViewById(R.id.zan);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter();
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        zanLayout.setVisibility(View.VISIBLE);
        zanLayout.zan(view);
    }
}
