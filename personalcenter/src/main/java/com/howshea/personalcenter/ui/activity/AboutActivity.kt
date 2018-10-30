package com.howshea.personalcenter.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.extentions.getVersionName
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransparent
import com.howshea.personalcenter.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlin.math.absoluteValue

class AboutActivity : AppCompatActivity() {
    private enum class CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERMEDIATE
    }

    private var state = CollapsingToolbarLayoutState.EXPANDED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        tv_version.text = AppContext.getVersionName()
        setStatusTransparent()
        toolbar.topPadding = getStatusBarHeight()
        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED
                    toolbar.title = ""
                }
            } else if (verticalOffset.absoluteValue >= appBarLayout.totalScrollRange) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    state = CollapsingToolbarLayoutState.COLLAPSED
                    toolbar.title = getString(R.string.about)
                }
            }
        }
    }
}
