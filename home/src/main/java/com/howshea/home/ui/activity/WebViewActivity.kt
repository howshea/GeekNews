package com.howshea.home.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.ShareCompat
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.howshea.basemodule.extentions.copyToClipBoard
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.home.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.dialog_web.view.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var url: String
    private lateinit var webSetting: WebSettings
    private val menuDialog: BottomSheetDialog by lazy(LazyThreadSafetyMode.NONE) { initDialog() }

    companion object {
        private const val EXTRA_URL = "web_url"
        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        url = intent.getStringExtra(EXTRA_URL)
        setStatusTransAndDarkIcon(Color.WHITE)
        toolbar.topPadding = getStatusBarHeight()
        setWebView()
        toolbar.apply {
            setOnNavClick { onBackPressed() }
            setOnMenuClick { menuDialog.show() }
        }
    }

    private fun initDialog(): BottomSheetDialog {
        val view = layoutInflater.inflate(R.layout.dialog_web, null)
        view.tv_cancel.setOnClickListener {
            menuDialog.cancel()
        }
        view.tv_refresh.setOnClickListener {
            web_view.reload()
            menuDialog.cancel()
        }
        view.tv_copy_link.setOnClickListener {
            menuDialog.cancel()
            copyToClipBoard(url)
        }
        view.tv_share.setOnClickListener {
            menuDialog.cancel()
            ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText("${toolbar.title} $url")
                .setChooserTitle(R.string.share)
                .startChooser()
        }
        return BottomSheetDialog(this).apply {
            setContentView(view)
            //去除自带的白色背景
            delegate
                .findViewById<View>(android.support.design.R.id.design_bottom_sheet)
                ?.setBackgroundColor(resources.getColor(android.R.color.transparent))
        }
    }

    private fun setWebView() {
        webSetting = web_view.settings
        webSetting.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            defaultTextEncodingName = "utf-8"
            domStorageEnabled = true
            databaseEnabled = true
            setAppCacheEnabled(true)
            pluginState = WebSettings.PluginState.ON
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        web_view.loadUrl(intent.getStringExtra(EXTRA_URL))
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
            }

        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String) {
                toolbar.title = title
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progress_bar.alpha = 0f
                } else {
                    progress_bar.alpha = 1f
                    progress_bar.progress = newProgress
                }
            }
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack())
            web_view.goBack()
        else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        (web_view.parent as ViewGroup?)?.removeView(web_view)
        web_view?.removeAllViews()
        web_view?.destroy()
        super.onDestroy()
    }
}
