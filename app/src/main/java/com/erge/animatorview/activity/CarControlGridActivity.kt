package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.erge.animatorview.R
import com.erge.animatorview.adapter.CarControlAdapter
import com.erge.animatorview.bean.CarControlInfo
import kotlinx.android.synthetic.main.activity_car_control_grid.*

class CarControlGridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_control_grid)

        val list = ArrayList<CarControlInfo>()
        for (index in 0 until 20) {
            list.add(CarControlInfo("第 $index 个"))
        }
        val carControlAdapter = CarControlAdapter(list, this)
        horizontalScrollView.setAdapter(carControlAdapter)
        horizontalScrollView.setOnItemClickListener { position, itemView ->
            Toast.makeText(
                this@CarControlGridActivity,
                "position $position",
                Toast.LENGTH_SHORT
            ).show()
        }

        temperature_view.setOnTemperatureChooseListener { temperature: String? -> println("温度值：$temperature") }

    }
}
