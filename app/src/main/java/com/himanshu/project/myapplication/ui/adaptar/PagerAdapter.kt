package com.himanshu.project.myapplication.ui.adaptar

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.himanshu.project.myapplication.ui.fragment.CustomNewsFragment
import com.himanshu.project.myapplication.ui.fragment.HeadlineNewsFragment
import com.himanshu.project.myapplication.ui.fragment.ProfileFragment

const val HEADLINE_PAGE_INDEX = 0
const val CUSTOM_NEWS_PAGE_INDEX = 1
const val PROFILE_PAGE_INDEX = 2
class PagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        HEADLINE_PAGE_INDEX to { HeadlineNewsFragment() },
        CUSTOM_NEWS_PAGE_INDEX to { CustomNewsFragment() },
        PROFILE_PAGE_INDEX to { ProfileFragment() }
    )
    override fun getItemCount() = tabFragmentsCreators.size
    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}