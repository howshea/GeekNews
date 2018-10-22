package com.howshea.home.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.home.R
import com.howshea.home.ui.adapter.PastNewsAdapter
import com.howshea.home.viewModel.PastNewsViewModel
import kotlinx.android.synthetic.main.activity_past_news.*


class PastNewsActivity : AppCompatActivity() {

    private var page: Int = 1
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { PastNewsAdapter(arrayListOf(), this) }
    private val model by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(PastNewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_news)
        setStatusTransAndDarkIcon()
        toolbar.topPadding = getStatusBarHeight()
        toolbar.setOnNavClick { onBackPressed() }
        ryc_main.adapter = adapter
        ryc_main.layoutManager = LinearLayoutManager(this)
        model.getHistory().observe(this, Observer {
            it?.let { data ->
                if (adapter.itemCount == 0) {
                    adapter.setNewData(data)
                } else {
                    adapter.addData(data)
                    adapter.setLoadComplete()
                }
            }
        })
        model.requestData(page)
        adapter.setItemClick { item, binding ->
            item.cover?.let {
                val intent = PastNewsDetailActivity.newIntent(this@PastNewsActivity, it, item.title, item.publishedAt)
                val pair1 = Pair(binding.tvTitle as View, ViewCompat.getTransitionName(binding.tvTitle))
                val pair2 = Pair(binding.tvData as View, ViewCompat.getTransitionName(binding.tvData))
                val pair3 = Pair(binding.imgCover as View, ViewCompat.getTransitionName(binding.imgCover))
                val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this@PastNewsActivity, pair1, pair2, pair3)
                ActivityCompat.startActivity(this@PastNewsActivity, intent, activityOptionsCompat.toBundle())
            }
        }
        adapter.setLoadMoreListener(ryc_main) {
            page++
            model.requestData(page)
        }
    }
}
