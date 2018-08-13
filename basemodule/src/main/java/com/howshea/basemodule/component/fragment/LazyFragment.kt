package com.howshea.basemodule.component.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.howshea.basemodule.extentions.yes

/**
 * Created by Howshea
 * on 2018/7/16.
 */
/**
 * 用于ViewPager中的懒加载Fragment
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
    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

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