package com.howshea.home.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.home.R
import kotlinx.android.synthetic.main.activity_past_news.*

class PastNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_news)
        setStatusTransAndDarkIcon()
        toolbar.topPadding = getStatusBarHeight()
    }
}
