package com.howshea.read.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.setLogo
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.toast
import com.howshea.read.R
import com.howshea.read.model.MainCategory
import com.howshea.read.ui.adapter.FeedViewPagerAdapter
import com.howshea.read.viewModel.ReadViewModel
import kotlinx.android.synthetic.main.read_fragment.*


class ReadFragment : LazyFragment() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(ReadViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.read_fragment

    override fun getData() {
        viewModel.getCategories().observe(this, Observer {
            it?.let { categories ->
                initViewPager(categories)
            }
        })
        viewModel.getError().observe(this, Observer {
            it?.message?.let { msg ->
                toast(msg)
            }
        })
    }

    override fun initView() {
        app_bar.topPadding = activity!!.getStatusBarHeight()
        toolbar.title = toolbar.title.setLogo()
    }

    private fun initViewPager(mainCategories: List<MainCategory.Results>) {
        val pagerAdapter = FeedViewPagerAdapter(childFragmentManager, mainCategories)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = mainCategories.size - 1
        tab_layout.setupWithViewPager(view_pager)
    }

}
