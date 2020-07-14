package com.erge.animatorview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


}
