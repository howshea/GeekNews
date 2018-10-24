package com.howshea.read.ui

import android.os.Bundle
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.read.R

/**
 * Created by Howshea
 * on 2018/10/24
 */
class FeedFragment : LazyFragment() {

    companion object {
        fun newInstance(enName: String): FeedFragment {
            val fragment = FeedFragment()
            val arg = Bundle()
            arg.putString("ARG_EN_NAME", enName)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun getData() = Unit

    override fun initView() = Unit

    override fun getLayoutId() = R.layout.feed_fragment
}