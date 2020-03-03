package com.howshea.home.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.core.app.ShareCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.howshea.basemodule.database.Collection
import com.howshea.basemodule.database.CollectionDataBase
import com.howshea.basemodule.extentions.contentView
import com.howshea.basemodule.extentions.copyToClipBoard
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.*
import com.howshea.home.R
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.dialog_web.view.*

@Route(path = "/home/webActivity")
class WebViewActivity : AppCompatActivity() {
    private val url by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra("web_url")
    }
    private val coverUrl by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra("cover_url")
    }
    private val title by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra("title")
    }
    private lateinit var webSetting: WebSettings
    private val menuDialog: BottomSheetDialog by lazy(LazyThreadSafetyMode.NONE) { initDialog() }
    private val disposes = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setStatusTransAndDarkIcon()
        contentView?.topPadding = getStatusBarHeight()
        toolbar.apply {
            setOnNavClick { onBackPressed() }
            setOnMenuClick { menuDialog.show() }
        }
        setWebView()
    }

    private fun initDialog(): BottomSheetDialog {
        val view = layoutInflater.inflate(R.layout.dialog_web, null)
        if (intent.getBooleanExtra("close_collection", false)) {
            view.tv_collect.visibility = View.GONE
        } else {
            checkIsCollected(view)
            view.tv_collect.setOnClickListener {
                if (it.isSelected) {
                    deleteInCollection(url, view)
                } else {
                    val time = "${getYear()}/${getMonth()}/${getDay()} ${getHour()}:${getMinute()}"
                    insertCollection(Collection(title, url, time, coverUrl ?: ""), view)
                }
            }
        }
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
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                ?.setBackgroundColor(resources.getColor(android.R.color.transparent))
        }
    }

    //查询是否已被收藏
    private fun checkIsCollected(view: View) {
        CollectionDataBase.getInstance(this).collectionDao().checkIsCollected(url)
            .dispatchDefault()
            .subscribeBy(
                onError = {
                    view.tv_collect.isSelected = false
                },
                onSuccess = {
                    view.tv_collect.isSelected = true
                }
            )
            .addDispose()
    }

    //加入收藏
    private fun insertCollection(collection: Collection, view: View) {
        Completable.fromAction {
            CollectionDataBase.getInstance(this).collectionDao().insertCollection(collection)
        }
            .dispatchDefault()
            .subscribeBy(
                onError = {
                    toast("收藏失败")
                },
                onComplete = {
                    view.tv_collect.isSelected = true
                }
            )
            .addDispose()
    }

    //取消收藏
    private fun deleteInCollection(url: String, view: View) {
        Completable.fromAction {
            CollectionDataBase.getInstance(this).collectionDao().delete(url)
        }
            .dispatchDefault()
            .subscribeBy(
                onError = {
                    toast("取消收藏失败")
                },
                onComplete = {
                    view.tv_collect.isSelected = false
                }
            )
            .addDispose()
    }

    private fun setWebView() {
        web_view.run {
            removeJavascriptInterface("searchBoxJavaBridge_")
            removeJavascriptInterface("accessibilityTraversal")
            removeJavascriptInterface("accessibility")
        }
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
            if (Build.VERSION.SDK_INT >= 21) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        web_view.loadUrl(url)
        web_view.webViewClient = object : WebViewClient() {
            @Suppress("DEPRECATION", "OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                url ?: return true
                return if (url.isOpenApp()) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                    }
                    true
                } else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
            }

        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String) {
                //如果是read module 启动的，就不显示title
                if (!intent.getBooleanExtra("isArticle", false))
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

    override fun onStop() {
        disposes.clear()
        super.onStop()
    }

    private fun Disposable.addDispose() {
        disposes.add(this)
    }
}
