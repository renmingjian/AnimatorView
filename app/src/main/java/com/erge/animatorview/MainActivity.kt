package com.erge.animatorview

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.activity.*
import com.erge.animatorview.bean.CarControlInfo
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val image: ImageView = findViewById(R.id.image)
//        image.isClickable = true
//        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
//
//            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//                println("gestureDetector: 单击")
//                return super.onSingleTapConfirmed(e)
//            }
//
//            override fun onDoubleTap(e: MotionEvent?): Boolean {
//                println("gestureDetector: 双击")
//                return super.onDoubleTap(e)
//            }
//        })
//        image.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }

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


}
