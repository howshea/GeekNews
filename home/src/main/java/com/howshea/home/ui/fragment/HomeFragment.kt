package com.howshea.home.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.utils.toast
import com.howshea.home.R
import com.howshea.home.ui.adapter.DailyAdapter
import com.howshea.home.viewModel.DailyViewModel
import kotlinx.android.synthetic.main.frg_home.*

/**
 * Created by Howshea
 * on 2018/6/15.
 */
class HomeFragment : LazyFragment() {
    private val adapter by lazy { DailyAdapter(arrayListOf()) }

    override fun getLayoutId(): Int {
        return R.layout.frg_home
    }

    override fun getData() {
        val model = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        model.getTodayData().observe(this, Observer {
            it?.let { data ->
                adapter.setData(data)
            }
        })
        model.refresh()
    }

    override fun initView() {
        toolbar.setOnNavClick { toast("计划开发中...") }
        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(activity)
    }
}