package com.erge.animatorview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.R

class ShareElementActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_element2)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}