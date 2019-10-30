package com.erge.animatorview.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.R
import com.erge.animatorview.view.LoadingButton

class LoadingButtonActivity : AppCompatActivity() {

    private lateinit var loadingButton : LoadingButton
    private var a:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_button)

        loadingButton = findViewById(R.id.lb)

        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            loadingButton.state = LoadingButton.State.NORMAL
        }

        loadingButton.setOnLoadingListener { Toast.makeText(this@LoadingButtonActivity, "loading", Toast.LENGTH_SHORT).show() }

        loadingButton.setOnClickListener(object : LoadingButton.OnClickListener {
            override fun onCompleteClick() {
                Toast.makeText(this@LoadingButtonActivity, "onCompleteClick", Toast.LENGTH_SHORT).show()
            }

            override fun onErrorClick() {
                Toast.makeText(this@LoadingButtonActivity, "onCompleteClick", Toast.LENGTH_SHORT).show()
            }

            override fun onLoadingClick() {
                Toast.makeText(this@LoadingButtonActivity, "onLoadingClick", Toast.LENGTH_SHORT).show()

            }

            override fun onNormalClick() {
                loadData()
            }
        })
    }

    fun loadData() {
        Toast.makeText(this@LoadingButtonActivity, "onNormalClick", Toast.LENGTH_SHORT).show()
        loadingButton.postDelayed({
            loadingButton.state = if (a % 2 == 0) LoadingButton.State.COMPLETE else LoadingButton.State.ERROR
            a++
        }, 3000)
    }
}
