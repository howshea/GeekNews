package com.howshea.read.ui

import android.arch.lifecycle.ViewModelProviders
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.setLogo
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.read.R
import com.howshea.read.viewModel.ReadViewModel
import kotlinx.android.synthetic.main.read_fragment.*


class ReadFragment : LazyFragment() {
    private val model by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(ReadViewModel::class.java)
    }
    override fun getLayoutId() = R.layout.read_fragment

    override fun getData() {
    }

    override fun initView() {
        toolbar.topPadding = activity!!.getStatusBarHeight()
        toolbar.title = toolbar.title.setLogo()
    }


}
