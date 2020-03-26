package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.erge.animatorview.R
import com.erge.animatorview.fragment.TestFragment
import kotlinx.android.synthetic.main.activity_view_pager.*
import kotlin.concurrent.fixedRateTimer

class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewpager.adapter = TabAdapter(supportFragmentManager)
        tab.setUpWithViewPager(viewpager)
    }


    class TabAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        val titleArray: Array<String> = arrayOf("呵呵哒1", "呵呵哒2", "呵呵哒3")
        val fragmentArray: Array<Fragment> = arrayOf(TestFragment(), TestFragment(), TestFragment())

        override fun getItem(position: Int): Fragment {
            return fragmentArray[position]
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleArray[position] + position
        }

    }
}
