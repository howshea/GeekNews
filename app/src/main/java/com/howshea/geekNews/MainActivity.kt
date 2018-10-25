package com.howshea.geekNews

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.geekNews.utils.disableShiftMode
import com.howshea.geekNews.utils.setupWithViewPager
import com.howshea.home.ui.fragment.HomeFragment
import com.howshea.read.ui.fragment.ReadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusTransAndDarkIcon(Color.WHITE)
        bottom_nav.disableShiftMode()
        view_pager.adapter = MainPagerAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 3
        bottom_nav.setupWithViewPager(view_pager)
    }

    inner class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> ReadFragment()
                else -> HomeFragment()
            }
        }

        override fun getCount() = 4
    }
}
