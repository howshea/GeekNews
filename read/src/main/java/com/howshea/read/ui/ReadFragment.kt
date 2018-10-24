package com.howshea.read.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.component.viewGroup.tabLayout.TabLayout
import com.howshea.basemodule.extentions.setLogo
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.read.R
import com.howshea.read.viewModel.ReadViewModel
import kotlinx.android.synthetic.main.read_fragment.*


class ReadFragment : LazyFragment() {
    val typeList = arrayOf("科技资讯", "趣味软件/游戏", "草根新闻", "Android", "创业新闻", "独立思想", "iOS", "团队博客")

    private val model by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(ReadViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.read_fragment

    override fun getData() {
    }

    override fun initView() {
        app_bar.topPadding = activity!!.getStatusBarHeight()
        toolbar.title = toolbar.title.setLogo()
        initViewPager()
    }

    private fun initViewPager() {
        val pagerAdapter = FeedViewPagerAdapter(childFragmentManager)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = typeList.size - 1
        tab_layout.setupWithViewPager(view_pager)
    }

    private inner class FeedViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return FeedFragment.newInstance(typeList[position])
        }

        override fun getCount() = typeList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return typeList[position]
        }
    }

}
