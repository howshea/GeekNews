package com.howshea.data.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.setLogo
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.data.R
import kotlinx.android.synthetic.main.data_fragment.*

/**
 * Created by Howshea
 * on 2018/10/28.
 */
class DataFragment : LazyFragment() {
    private val typeList = arrayOf("Android", "前端", "iOS", "App", "拓展资源", "瞎推荐")

    override fun getData() {
        initViewPager()
    }

    override fun initView() {
        toolbar.title = toolbar.title.setLogo()
    }

    override fun getLayoutId() = R.layout.data_fragment


    private fun initViewPager() {
        view_pager.adapter = DataPagerAdapter(childFragmentManager, typeList)
        view_pager.offscreenPageLimit = typeList.size - 1
        tab_layout.setupWithViewPager(view_pager)
    }

    private inner class DataPagerAdapter(fm: FragmentManager?, private val types: Array<String>) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DataTypeFragment.newInstance(types[position])
        }

        override fun getCount() = types.size

        override fun getPageTitle(position: Int): CharSequence? {
            return types[position]
        }
    }
}