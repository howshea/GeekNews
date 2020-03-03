package com.howshea.home.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.howshea.basemodule.extentions.addFragment
import com.howshea.basemodule.extentions.glide.BlurTransformation
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransparent
import com.howshea.home.R
import com.howshea.home.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_past_news_detail.*
import kotlin.math.absoluteValue

class PastNewsDetailActivity : AppCompatActivity() {
    private val title: String by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra(EXTRA_TITLE) }
    private val time: String by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra(EXTRA_TIME).substring(0, 10).replace('-', '.') }
    private val url: String by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra(EXTRA_URL) }
    //高斯模糊半径
    private val blurRadius = 10

    companion object {
        private const val EXTRA_URL = "coverUrl"
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_TIME = "publishAt"
        fun newIntent(context: Context, coverUrl: String, title: String, publishAt: String): Intent {
            val intent = Intent(context, PastNewsDetailActivity::class.java)
            intent.apply {
                putExtra(EXTRA_URL, coverUrl)
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_TIME, publishAt)
            }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_news_detail)
        setStatusTransparent()
        initToolbar()
        initHeader()
        addFragment(R.id.fragment_container, HomeFragment.newInstance(true, time.replace(".", "")))
    }

    private fun initHeader() {
        Glide.with(this)
            .load(url)
            .into(img_cover)
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(blurRadius)))
            .into(img_cover_blur)
        tv_title.text = title
        tv_title.translationY = dp(56).toFloat()
        tv_info_title.text = title
        tv_info_time.text = time
        var headerIsShow = false
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scale = verticalOffset.absoluteValue.toFloat() / appBarLayout.totalScrollRange
            layout_info.alpha = 1 - scale
            img_cover_blur.alpha = scale
            if (verticalOffset.absoluteValue == appBarLayout.totalScrollRange) {
                headerIsShow = true
                tv_title.animate().translationY(0f).alpha(1f)
            } else if (headerIsShow) {
                headerIsShow = false
                tv_title.animate().translationY(dp(56).toFloat()).alpha(0f)
            }
        })

    }

    private fun initToolbar() {
        toolbar.topPadding = getStatusBarHeight()
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        //为了去掉返回上一页时的共享元素动画
        finish()
    }
}
