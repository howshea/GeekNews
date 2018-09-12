package com.howshea.home.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.webkit.*
import com.howshea.basemodule.utils.setDarkStatusIcon
import com.howshea.home.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.dialog_web.view.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var webSetting: WebSettings
    private val menuDialog: BottomSheetDialog by lazy { initDialog() }

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
        setDarkStatusIcon(true)
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
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack())
            web_view.goBack()
        else {
            super.onBackPressed()
        }
    }
}
