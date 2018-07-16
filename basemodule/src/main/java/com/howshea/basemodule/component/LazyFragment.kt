package com.howshea.basemodule.component

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.howshea.basemodule.extentions.yes

/**
 * Created by Howshea
 * on 2018/7/16.
 */

abstract class LazyFragment : Fragment() {
    private var isInit = false
    private var isFirstVisible = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        (isVisibleToUser && isInit && isFirstVisible).yes {
            getData()
            isInit = false
        }
    }

    abstract fun getData()
    abstract fun initView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        isInit = true
        userVisibleHint.yes {
            getData()
            isFirstVisible = false
        }
    }
}