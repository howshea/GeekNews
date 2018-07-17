package com.howshea.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.howshea.basemodule.component.LazyFragment
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.home.R
import kotlinx.android.synthetic.main.frg_home.*

/**
 * Created by Howshea
 * on 2018/6/15.
 */
class HomeFragment : LazyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_home, container, false)
    }


    override fun getData() {

    }

    override fun initView() {
        toolbar.topPadding = activity?.getStatusBarHeight() ?: 0
        toolbar.setNavOnClick {
            Toast.makeText(context,"啦啦啦",Toast.LENGTH_SHORT).show()
        }
    }
}