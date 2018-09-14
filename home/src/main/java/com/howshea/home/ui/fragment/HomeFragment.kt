package com.howshea.home.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.formatStringColor
import com.howshea.basemodule.extentions.yes
import com.howshea.basemodule.utils.setUnderApi23StatusBarShade
import com.howshea.basemodule.utils.toast
import com.howshea.home.R
import com.howshea.home.ui.activity.WebViewActivity
import com.howshea.home.ui.adapter.CategoryDecoration
import com.howshea.home.ui.adapter.HomeAdapter
import com.howshea.home.viewModel.DailyViewModel
import kotlinx.android.synthetic.main.frg_home.*

/**
 * Created by Howshea
 * on 2018/6/15.
 */
class HomeFragment : LazyFragment() {
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { HomeAdapter(arrayListOf()) }

    override fun getLayoutId(): Int {
        return R.layout.frg_home
    }

    override fun getData() {
        val model = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        model.getTodayData().observe(this, Observer {
            it?.let { data ->
                adapter.setNewData(data)
                ryc_main.addItemDecoration(CategoryDecoration(data, context!!))
            }
        })
        model.refresh()
        adapter.setItemClick {
            it.url.isNotEmpty().yes {
                startActivity(WebViewActivity.newIntent(activity!!, it.url))
            }
        }
    }

    override fun initView() {
        activity?.setUnderApi23StatusBarShade(toolbar)
        toolbar.setOnNavClick { toast("计划开发中...") }
        toolbar.setOnMenuClick { }
        toolbar.title = toolbar.title.setLogo()
        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(activity)
    }

    /**
     * 换色四连
     */
    private fun CharSequence.setLogo(): CharSequence {
        return this.formatStringColor(R.color.blue, 0, 1)
            .formatStringColor(R.color.red, 1, 2)
            .formatStringColor(R.color.yellow, 2, 3)
            .formatStringColor(R.color.green, 3, 4)
    }
}