package com.howshea.read.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.howshea.read.model.SubCategory
import com.howshea.read.ui.fragment.FeedFragment

/**
 * Created by Howshea
 * on 2018/10/25
 */
class FeedViewPagerAdapter(fm: FragmentManager?, private val categories: List<SubCategory>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FeedFragment.newInstance(categories[position].id)
    }

    override fun getCount() = categories.size

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position].title
    }
}