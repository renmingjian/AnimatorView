package com.erge.animatorview.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.R

class ShareElementActivity : AppCompatActivity() {

    private lateinit var v_share: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_element)
        v_share = findViewById(R.id.v_share)
    }

    fun start(view: View) {
        // 共享shareElement这个View
        startActivity(
            Intent(this@ShareElementActivity, ShareElementActivity2::class.java),
            ActivityOptions.makeSceneTransitionAnimation(
                this@ShareElementActivity, v_share,
                "shareElement"
            ).toBundle()
        )
    }
}