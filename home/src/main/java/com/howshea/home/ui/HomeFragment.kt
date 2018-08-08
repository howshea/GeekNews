package com.howshea.home.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.home.R
import com.howshea.home.viewModel.DailyViewModel
import kotlinx.android.synthetic.main.frg_home.*

/**
 * Created by Howshea
 * on 2018/6/15.
 */
class HomeFragment : LazyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_home, container, false)
    }

    override fun getData() {}

    override fun initView() {
        toolbar.topPadding = activity?.getStatusBarHeight() ?: 0
        val model = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        model.getTodayData().observe(this, Observer {

        })
    }
}