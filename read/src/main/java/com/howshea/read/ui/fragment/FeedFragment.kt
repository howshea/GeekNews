package com.howshea.read.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.utils.toast
import com.howshea.read.R
import com.howshea.read.ui.adapter.FeedAdapter
import com.howshea.basemodule.component.viewGroup.baseAdapter.SimpleDecoration
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
    private val typeId by lazy { arguments?.getString(ARG_TYPE_ID) ?: "" }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) { FeedAdapter(arrayListOf(), this) }
    private var page: Int = 1


    companion object {
        private const val ARG_TYPE_ID = "typeId"
        fun newInstance(typeId: String): FeedFragment {
            val fragment = FeedFragment()
            val arg = Bundle()
            arg.putString(ARG_TYPE_ID, typeId)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.feed_fragment

    override fun getData() {
        viewModel.getFeed().observe(this, Observer {
            it?.let { data ->
                if (page == 1) {
                    adapter.setNewData(data)
                } else {
                    adapter.addData(data)
                    adapter.setLoadComplete()
                }
            }
            layout_refresh.isRefreshing = false
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
        viewModel.refresh(typeId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layout_refresh.isRefreshing = true
        adapter.setLoadMoreListener(ryc_main) {
            page++
            viewModel.requestData(page)
            layout_refresh.isRefreshing = true
        }
        layout_refresh.setOnRefreshListener {
            page = 1
            viewModel.refresh(typeId)
        }
        adapter.setItemClick { item, _ ->
            ARouter.getInstance().build("/home/webActivity")
                .withString("web_url", item.url)
                .withString("title", item.title)
                .withString("cover_url", item.cover)
                .withBoolean("isArticle", true)
                .navigation()
        }
    }

    override fun initView() {
        ryc_main.layoutManager = LinearLayoutManager(activity)
        ryc_main.adapter = adapter
        layout_refresh.setColorSchemeResources(R.color.colorAccent)
        ryc_main.addItemDecoration(SimpleDecoration())
    }
}