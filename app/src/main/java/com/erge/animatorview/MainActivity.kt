package com.erge.animatorview

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var et_test: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }

    fun setData(view: android.view.View) {
        val text = "圣诞节埃/[呲牙里克加料口阿里快递费结案\n率建档立卡附近啊了"
        et_test.setText(text)
        et_test.setSelection(text.length)
    }


    fun loadingButton(view: View) {
        startActivity(Intent(this, LoadingButtonActivity::class.java))
    }

    fun loadingView(view: View) {
        startActivity(Intent(this, LoadingActivity::class.java))
    }

    fun sportView(view: View) {
        startActivity(Intent(this, SportActivity::class.java))
    }

    fun progressBar(view: View) {
        startActivity(Intent(this, ProgressBarActivity::class.java))
    }

    fun pie(view: View) {
        startActivity(Intent(this, PieActivity::class.java))
    }

    fun histogram(view: View) {
        startActivity(Intent(this, HistogramActivity::class.java))
    }

    fun longClickView(view: View) {
        startActivity(Intent(this, LongClickActivity::class.java))
    }

    fun boardView1(view: View) {
        startActivity(Intent(this, BoardActivity1::class.java))
    }

    fun boardView2(view: View) {
        startActivity(Intent(this, BoardViewActivity2::class.java))
    }

    fun charge(view: View) {
        startActivity(Intent(this, ChargeActivity::class.java))
    }

    fun sky(view: View) {
        startActivity(Intent(this, SkyGalaxyActivity::class.java))
    }

    fun test(view: View) {
        startActivity(Intent(this, TestActivity::class.java))
    }

    fun progress(view: View) {
        startActivity(Intent(this, ProgressLayoutActivity::class.java))
    }

    fun change(view: View) {
        startActivity(Intent(this, DragActivity::class.java))
    }

    fun zan(view: View) {
        startActivity(Intent(this, ZanActivity::class.java))
    }

    fun electric(view: View) {
        startActivity(Intent(this, ElectricQualityActivity::class.java))
    }


    fun viewpager(view: View) {
        startActivity(Intent(this, ViewPagerActivity::class.java))
    }

    fun scroll(view: View) {
        startActivity(Intent(this, ScrollActivity2::class.java))
    }

    fun carControl(view: View) {
        startActivity(Intent(this, CarControlGridActivity::class.java))
    }

    fun refresh(view: View) {
        startActivity(Intent(this, RefreshActivity::class.java))
    }

    fun verticle(view: View) {
        startActivity(Intent(this, VerticleActivity::class.java))

    }

    fun animation(view: View) {
        startActivity(Intent(this, ViewGroupAnimationActivity::class.java))
    }

    fun pullToRefresh(view: View) {
        startActivity(Intent(this, VerticalScrollViewActivity::class.java))
    }

    fun transition(view: View) {
        startActivity(Intent(this, TransitionActivity::class.java))
    }

    fun motionLayout(view: View) {
        startActivity(Intent(this, MotionLayoutActivity::class.java))
        overridePendingTransition(0, 0)
    }

    fun shareElement(view: View) {
        startActivity(Intent(this, ShareElementActivity::class.java))
    }

    fun lottie(view: View) {
        startActivity(Intent(this, LottieActivity::class.java))
    }

    fun motionLayout2(view: View) {
        startActivity(Intent(this, MotionLayoutActivity2::class.java))
    }

    fun nestedScroll(view: View) {
        startActivity(Intent(this, NestedScrollActivity::class.java))
    }

    fun zoom(view: View) {
        startActivity(Intent(this, ZoomActivity::class.java))
    }

    fun tag(view: View) {
        startActivity(Intent(this, TagsActivity::class.java))
    }

    fun bannerTest(view: View) {
        startActivity(Intent(this, BannerTestActivity::class.java))
    }

    fun loadMore(view: View) {
        startActivity(Intent(this, HorizontalLoadMoreActivity::class.java))
    }

    fun form(view: View) {
        startActivity(Intent(this, InputActivity::class.java))
    }

    fun password(view: View) {
        startActivity(Intent(this, PasswordActivity::class.java))
    }

    fun videoLoading(view: android.view.View) {
        startActivity(Intent(this, VideoProgressActivity::class.java))
    }

    fun videoDrag(view: android.view.View) {
//        startActivity(Intent(this, VideoDragActivity::class.java))
//        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        val textView = TextView(this)
        textView.inputType = InputType.TYPE_CLASS_NUMBER
        imm.showSoftInput(textView, InputMethodManager.SHOW_FORCED)
        println("videoDrag")
    }

    fun picture(view: View) {}

}
