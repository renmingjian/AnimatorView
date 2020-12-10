package com.erge.animatorview.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.erge.animatorview.R;
import com.erge.animatorview.adapter.TakeBackAdapter;
import com.erge.animatorview.view.LinearListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erge 11/29/20 4:42 PM
 */
public class VerticleActivity extends AppCompatActivity {

    private LinearListView llv_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verticel);
        llv_test = findViewById(R.id.llv_test);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("hehe" + i);
        }
        TakeBackAdapter adapter = new TakeBackAdapter(list);
        llv_test.setAdapter(adapter);
    }
}
