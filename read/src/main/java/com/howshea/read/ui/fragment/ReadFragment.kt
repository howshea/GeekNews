package com.howshea.read.ui.fragment

import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.setLogo
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.read.R
import com.howshea.read.model.SubCategory
import com.howshea.read.ui.adapter.FeedViewPagerAdapter
import kotlinx.android.synthetic.main.read_fragment.*


class ReadFragment : LazyFragment() {
    private val categories: List<SubCategory> = mutableListOf(
        SubCategory("qdaily", "好奇心日报"),
        SubCategory("36kr", "36氪"),
        SubCategory("dgtle", "数字尾巴"),
        SubCategory("toodaylab", "理想生活实验室"),
        SubCategory("zhihu", "知乎日报"),
        SubCategory("ifanr", "爱范儿"),
        SubCategory("wanqu", "湾区日报")
    )

    override fun getLayoutId() = R.layout.read_fragment

    override fun getData() {
        initViewPager(categories)
    }


    override fun initView() {
        toolbar.title = toolbar.title.setLogo()
    }

    private fun initViewPager(categories: List<SubCategory>) {
        val pagerAdapter = FeedViewPagerAdapter(childFragmentManager, categories)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = categories.size - 1
        tab_layout.setupWithViewPager(view_pager)
    }

}
