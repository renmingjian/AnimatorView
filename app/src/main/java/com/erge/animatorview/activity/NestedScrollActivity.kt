package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erge.animatorview.R
import com.erge.animatorview.adapter.RVTestAdapter

class NestedScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RVTestAdapter(getData())
    }

    private fun getData(): List<String> {
        return mutableListOf<String>().apply {
            for (i in 1..50) {
                add("item--$i")
            }
        }
    }
}