package com.howshea.home.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.howshea.basemodule.extentions.addFragment
import com.howshea.basemodule.extentions.glide.BlurTransformation
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransparent
import com.howshea.home.R
import com.howshea.home.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_past_news_detail.*

class PastNewsDetailActivity : AppCompatActivity() {

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
        toolbar.topPadding = getStatusBarHeight()
        setSupportActionBar(toolbar)
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_URL))
            .into(img_cover)
        Glide.with(this)
            .asBitmap()
            .load(intent.getStringExtra(EXTRA_URL))
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10)))
            .into(img_cover_blur)
        addFragment(R.id.fragment_container, HomeFragment())
    }
}
