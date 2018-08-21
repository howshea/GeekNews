package com.howshea.home.ui.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.utils.toast
import com.howshea.home.R
import com.howshea.home.ui.CategoryDecoration
import com.howshea.home.ui.adapter.DailyAdapter
import com.howshea.home.viewModel.DailyViewModel
import kotlinx.android.synthetic.main.frg_home.*
import kotlinx.android.synthetic.main.footer_home.view.*

@SuppressLint("InflateParams")
/**
 * Created by Howshea
 * on 2018/6/15.
 */
class HomeFragment : LazyFragment() {
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { DailyAdapter(arrayListOf()) }
    private val footerView by lazy(LazyThreadSafetyMode.NONE) { layoutInflater.inflate(R.layout.footer_home, null, false) }

    override fun getLayoutId(): Int {
        return R.layout.frg_home
    }

    override fun getData() {
        val model = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        model.getTodayData().observe(this, Observer {
            it?.let { data ->
                adapter.setNewData(data)
                ryc_main.addItemDecoration(CategoryDecoration(data,context!!))
            }
        })
        model.getTodayGirls().observe(this, Observer {
            it?.let { data ->
//                Glide.with(this)
//                    .load(data[0].url)
//                    .into(footerView.iv_girl)
            }
        })
        model.refresh()
    }

    override fun initView() {
        toolbar.setOnNavClick { toast("计划开发中...") }
//        adapter.addFooterView(footerView)
        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(activity)
    }
}