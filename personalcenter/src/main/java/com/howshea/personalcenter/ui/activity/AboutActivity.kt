package com.howshea.personalcenter.ui.activity

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.component.viewGroup.baseAdapter.SimpleDecoration
import com.howshea.basemodule.extentions.getVersionName
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransparent
import com.howshea.personalcenter.R
import com.howshea.personalcenter.model.Source
import com.howshea.personalcenter.ui.adapter.SourceAdapter
import kotlinx.android.synthetic.main.activity_about.*
import kotlin.math.absoluteValue

class AboutActivity : AppCompatActivity() {
    private val sources = ArrayList<Source>().apply {
        add(Source("rxKotlin2 - ReactiveX", "https://github.com/ReactiveX/RxKotlin"))
        add(Source("rxAndroid - ReactiveX", "https://github.com/ReactiveX/RxAndroid"))
        add(Source("retrofit - Square", "https://github.com/square/retrofit"))
        add(Source("glide - bumptech", "https://github.com/bumptech/glide"))
        add(Source("ARouter - alibaba", "https://github.com/alibaba/ARouter"))
        add(Source("PhotoView - chrisbanes", "https://github.com/chrisbanes/PhotoView"))
        add(Source("NumberProgressBar - daimajia", "https://github.com/daimajia/NumberProgressBar"))
        add(Source("NestedScrollWebView - y_xf", "https://gitee.com/y_xf/NestedScrollWebView"))
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SourceAdapter(sources)
    }

    private enum class CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERMEDIATE
    }

    private var state = CollapsingToolbarLayoutState.EXPANDED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        tv_version.text = getString(R.string.version).format(AppContext.getVersionName())
        setStatusTransparent()
        toolbar.topPadding = getStatusBarHeight()
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED
                    toolbar.title = ""
                }
            } else if (verticalOffset.absoluteValue >= appBarLayout.totalScrollRange) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    state = CollapsingToolbarLayoutState.COLLAPSED
                    toolbar.title = getString(R.string.about)
                }
            }
        })

        ryc_sources.layoutManager = LinearLayoutManager(this)
        ryc_sources.adapter = adapter
        ryc_sources.addItemDecoration(SimpleDecoration(0))
        adapter.setItemClick { item, _ ->
            ARouter.getInstance().build("/home/webActivity")
                .withString("web_url", item.address)
                .withBoolean("close_collection", true)
                .navigation()
        }
    }
}
