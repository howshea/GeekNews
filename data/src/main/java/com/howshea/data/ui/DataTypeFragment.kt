package com.howshea.data.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.component.viewGroup.baseAdapter.SimpleDecoration
import com.howshea.basemodule.utils.toast
import com.howshea.data.R
import com.howshea.data.viewModel.DataTypeViewModel
import kotlinx.android.synthetic.main.data_type_fragment.*

/**
 * Created by Howshea
 * on 2018/10/29.
 */
class DataTypeFragment : LazyFragment() {
    private val repoType by lazy { arguments?.getString(ARG_REPO_TYPE) ?: "" }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { DataTypeAdapter(arrayListOf()) }
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(DataTypeViewModel::class.java)
    }
    private var page: Int = 1

    companion object {
        private const val ARG_REPO_TYPE = "type"
        fun newInstance(type: String): DataTypeFragment {
            val fragment = DataTypeFragment()
            val arg = Bundle()
            arg.putString(ARG_REPO_TYPE, type)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun getData() {
        viewModel.getTypeData().observe(this, Observer {
            it?.let { data ->
                if (page == 1) {
                    adapter.setNewData(data)
                } else {
                    adapter.addData(data)
                    adapter.setLoadComplete()
                }
                layout_refresh.isRefreshing = false
            }
        })
        viewModel.getError().observe(this, Observer {
            it?.message?.let { msg ->
                toast(msg)
            }
            if (page > 1) {
                page--
                adapter.setLoadFail()
            }
            layout_refresh.isRefreshing = false
        })
        viewModel.refresh(repoType)
        layout_refresh.isRefreshing = true

        layout_refresh.setOnRefreshListener {
            page = 1
            viewModel.refresh(repoType)
        }
        adapter.setLoadMoreListener(ryc) {
            page++
            viewModel.requestData(page)
            layout_refresh.isRefreshing = true
        }
        adapter.setItemClick { item, _ ->
            ARouter.getInstance().build("/home/webActivity")
                .withString("web_url", item.url)
                .withString("title", item.desc)
                .navigation()
        }
    }

    override fun initView() {
        ryc.layoutManager = LinearLayoutManager(activity)
        ryc.adapter = adapter
        layout_refresh.setColorSchemeResources(R.color.colorAccent)
        ryc.addItemDecoration(SimpleDecoration())
    }

    override fun getLayoutId() = R.layout.data_type_fragment
}