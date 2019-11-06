package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.SkyGalaxyView

class SkyGalaxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sky_galaxy)

        val skyView: SkyGalaxyView = findViewById(R.id.skyView)
        skyView.startAnim()
    }
}
