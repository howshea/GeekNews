package com.howshea.personalcenter.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        tv_github.setOnClickListener {

        }
        tv_sort.setOnClickListener {

        }
    }
}
