package com.howshea.read.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.read.R
import com.howshea.read.ui.adapter.FeedAdapter
import com.howshea.read.viewModel.FeedViewModel
import kotlinx.android.synthetic.main.feed_fragment.*

/**
 * Created by Howshea
 * on 2018/10/24
 */
class FeedFragment : LazyFragment() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }
    private val enName by lazy { arguments?.getString(ARG_EN_NAME) ?: "" }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) { FeedAdapter(arrayListOf(), this) }

    companion object {
        private const val ARG_EN_NAME = "en_name"
        fun newInstance(enName: String): FeedFragment {
            val fragment = FeedFragment()
            val arg = Bundle()
            arg.putString(ARG_EN_NAME, enName)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.feed_fragment

    override fun getData() {

    }

    override fun initView() {
        ryc_main.layoutManager = LinearLayoutManager(activity)
        ryc_main.adapter = adapter
        layout_refresh.setColorSchemeResources(R.color.colorAccent)
        layout_refresh.setOnRefreshListener {
            viewModel.refresh(enName)
        }
    }
}