package com.howshea.personalcenter.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.component.viewGroup.baseAdapter.SimpleDecoration
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.basemodule.utils.toast
import com.howshea.personalcenter.R
import com.howshea.personalcenter.ui.adapter.CollectionAdapter
import com.howshea.personalcenter.viewModel.CollectionViewModel
import com.howshea.personalcenter.viewModel.Injection
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, Injection.provideViewModelFactory(this)).get(CollectionViewModel::class.java)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionAdapter(arrayListOf(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setStatusTransAndDarkIcon()
        toolbar.topPadding = getStatusBarHeight()
        toolbar.setOnNavClick {
            onBackPressed()
        }
        ryc.layoutManager = LinearLayoutManager(this)
        ryc.adapter = adapter
        ryc.addItemDecoration(SimpleDecoration())
        viewModel.getCollections().observe(this, Observer { flow ->
            flow?.let {
                adapter.setNewData(it)
            }
        })
        viewModel.getError().observe(this, Observer { throwable ->
            throwable?.let { toast("${it.message}") }
        })
        adapter.setItemClick { item, _ ->
            ARouter.getInstance().build("/home/webActivity")
                .withString("web_url", item.url)
                .navigation()
        }
    }
}
