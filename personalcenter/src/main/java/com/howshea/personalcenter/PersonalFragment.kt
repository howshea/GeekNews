package com.howshea.personalcenter

import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.personal_fragment.*

/**
 * Created by Howshea
 * on 2018/10/29
 */
class PersonalFragment : LazyFragment() {
    override fun getData() {
        tv_about.setOnClickListener {

        }
        tv_clear.setOnClickListener {

        }
        tv_collection.setOnClickListener {

        }
        tv_setting.setOnClickListener {  }
    }

    override fun initView() {
        toolbar.topPadding = activity!!.getStatusBarHeight()
    }

    override fun getLayoutId() = R.layout.personal_fragment
}