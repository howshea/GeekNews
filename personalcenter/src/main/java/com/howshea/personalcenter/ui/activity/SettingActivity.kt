package com.howshea.personalcenter.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.personalcenter.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setStatusTransAndDarkIcon()
        toolbar.topPadding = getStatusBarHeight()
        toolbar.setOnNavClick {
            onBackPressed()
        }
        ll_github.setOnClickListener {
            ARouter.getInstance().build("/home/webActivity")
                .withString("web_url", "https://github.com/login")
                .withBoolean("close_collection", true)
                .navigation()
        }
        tv_sort.setOnClickListener {
            startActivity(Intent(this, TypeSortActivity::class.java))
        }
    }
}
