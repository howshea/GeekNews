package com.howshea.home.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.setLogo
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
    private var isComponent = false
    private var date = ""
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { HomeAdapter(arrayListOf(), this) }
    private val model by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(DailyViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.frg_home
    }

    companion object {
        private const val ARG_FLAG = "isComponent"
        private const val ARG_DATE = "date"
        fun newInstance(isComponent: Boolean, date: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putBoolean(ARG_FLAG, isComponent)
            bundle.putString(ARG_DATE, date)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getData() {
        model.getTodayData().observe(this, Observer {
            it?.let { data ->
                layout_refresh.isRefreshing = false
                adapter.setNewData(data)
                val decoration = CategoryDecoration(data, context!!)
                //因为设置中有可能重新排序，所以写移除旧的 Decoration
                if (ryc_main.itemDecorationCount != 0) {
                    ryc_main.removeItemDecorationAt(0)
                }
                ryc_main.addItemDecoration(decoration)
            }
        })

        model.getError().observe(this, Observer { it ->
            it?.let {
                layout_refresh.isRefreshing = false
                toast("${it.message}")
            }
        })

        model.refresh(isComponent, date)
        layout_refresh.isRefreshing = true
        adapter.setItemClick { item, _ ->
            item.url.isNotEmpty().yes {
                ARouter.getInstance().build("/home/webActivity")
                    .withString("web_url", item.url)
                    .withString("title", item.desc)
                    .navigation()
            }
        }
        adapter.setOnImageClick { v, position, imageList ->
            val intent = ImageActivity.newIntent(activity!!, imageList, position)
            val options = ViewCompat.getTransitionName(v)?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, v, it)
            }
            startActivity(intent, options?.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isComponent = arguments?.getBoolean(ARG_FLAG) ?: false
        date = arguments?.getString(ARG_DATE) ?: ""
    }

    override fun initView() {
        if (isComponent) {
            toolbar.visibility = View.GONE
            toolbar_divider.visibility = View.GONE
        } else {
            toolbar.setOnNavClick {
                toast("计划开发中...")
            }
            toolbar.setOnMenuClick {
                startActivity(Intent(activity!!, PastNewsActivity::class.java))
            }
            toolbar.title = toolbar.title.setLogo()
        }

        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(activity)
        layout_refresh.setColorSchemeResources(R.color.colorAccent)
        layout_refresh.setOnRefreshListener {
            model.refresh(isComponent, date)
        }
    }


}