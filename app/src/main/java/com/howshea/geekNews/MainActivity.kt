package com.howshea.geekNews

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.howshea.basemodule.extentions.contentView
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.data.ui.DataFragment
import com.howshea.geekNews.utils.setupWithViewPager
import com.howshea.home.ui.fragment.HomeFragment
import com.howshea.personalcenter.ui.fragment.PersonalFragment
import com.howshea.read.ui.fragment.ReadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusTransAndDarkIcon(Color.WHITE)
        contentView?.topPadding = getStatusBarHeight()
//        bottom_nav.disableShiftMode()
        view_pager.adapter = MainPagerAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 3
        bottom_nav.setupWithViewPager(view_pager)
    }

    inner class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> ReadFragment()
                2 -> DataFragment()
                else -> PersonalFragment()
            }
        }

        override fun getCount() = 4
    }
}
