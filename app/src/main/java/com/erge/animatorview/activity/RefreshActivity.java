package com.erge.animatorview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.erge.animatorview.R;
import com.erge.animatorview.adapter.RVTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        recyclerView = findViewById(R.id.recyclerView);

        List<String> data = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            data.add("item" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RVTestAdapter adapter = new RVTestAdapter(data);
        recyclerView.setAdapter(adapter);

    }
}