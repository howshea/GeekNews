package com.howshea.home.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.home.R
import kotlinx.android.synthetic.main.frg_home.*

/**
 * Created by 陶海啸
 * on 2018/6/15.
 */
class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.app_name)
        toolbar.setPadding(0, context?.getStatusBarHeight()!!, 0, 0)
    }
}