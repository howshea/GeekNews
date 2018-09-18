package com.howshea.home.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.formatStringColor
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.extentions.yes
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.toast
import com.howshea.home.R
import com.howshea.home.ui.activity.ImageActivity
import com.howshea.home.ui.activity.PastNewsActivity
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
    private val model by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(DailyViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.frg_home
    }

    override fun getData() {
        model.getTodayData().observe(this, Observer {
            it?.let { data ->
                layout_refresh.isRefreshing = false
                adapter.setNewData(data)
                //防止重复添加
                if (ryc_main.itemDecorationCount == 0) {
                    ryc_main.addItemDecoration(CategoryDecoration(data, context!!))
                }
            }
        })

        model.getError().observe(this, Observer { it ->
            it?.let {
                layout_refresh.isRefreshing = false
            }
        })

        model.refresh()
        layout_refresh.isRefreshing = true
        adapter.setItemClick {
            it.url.isNotEmpty().yes {
                startActivity(WebViewActivity.newIntent(activity!!, it.url))
            }
        }
        adapter.setOnImageClick { v, position, imageList ->
            val intent = ImageActivity.newIntent(activity!!, imageList, position)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, v, ViewCompat.getTransitionName(v))
            startActivity(intent, options.toBundle())
        }
    }

    override fun initView() {
        toolbar.topPadding = activity!!.getStatusBarHeight()
        toolbar.setOnNavClick {
            toast("计划开发中...")
        }
        toolbar.setOnMenuClick {
            startActivity(Intent(activity!!,PastNewsActivity::class.java))
        }
        toolbar.title = toolbar.title.setLogo()
        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(activity)
        layout_refresh.setColorSchemeResources(R.color.colorAccent)
        layout_refresh.setOnRefreshListener {
            model.refresh()
        }
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