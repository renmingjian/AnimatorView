package com.erge.animatorview.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.erge.animatorview.R
import com.erge.animatorview.view.LoadingButton
import kotlinx.android.synthetic.main.activity_loading_button.*

class LoadingButtonActivity : AppCompatActivity() {

    private lateinit var loadingButton: LoadingButton
    private var a: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_button)

        loadingButton = findViewById(R.id.lb)

        lb2.setLoadingListener {
            Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btn_reset).setOnClickListener {
//            lb2.state = LoadingButton2.State.NORMAL
            lb3.resetState()
        }

        lb3.onLoadingListener = {
            Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
//            lb3.postDelayed({ lb3.resetState() }, 3000)
        }

        loadingButton.setOnLoadingListener {
            Toast.makeText(
                this@LoadingButtonActivity,
                "loading",
                Toast.LENGTH_SHORT
            ).show()
        }

        val lottieView: LottieAnimationView = findViewById(R.id.lottieView2)
        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                val drawable = lottieView.drawable
                iv2.setImageDrawable(drawable)
                lottieView.visibility = View.GONE
                iv1.visibility = View.VISIBLE
                iv2.alpha = 0.3f
                lottieView.alpha = 0f
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                iv1.visibility = View.GONE
            }
        })

        loadingButton.setOnClickListener(object : LoadingButton.OnClickListener {
            override fun onCompleteClick() {
                Toast.makeText(this@LoadingButtonActivity, "onCompleteClick", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onErrorClick() {
                Toast.makeText(this@LoadingButtonActivity, "onCompleteClick", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onLoadingClick() {
                Toast.makeText(this@LoadingButtonActivity, "onLoadingClick", Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onNormalClick() {
                loadData()
            }
        })
    }

    fun loadData() {
        Toast.makeText(this@LoadingButtonActivity, "onNormalClick", Toast.LENGTH_SHORT).show()
        loadingButton.postDelayed({
            loadingButton.state =
                if (a % 2 == 0) LoadingButton.State.COMPLETE else LoadingButton.State.ERROR
            a++
        }, 3000)
    }

    fun switchEnable(view: View) {
        lb2.isEnabled = !lb2.isEnabled
    }
}
