package com.himanshu.project.myapplication.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator

import com.himanshu.project.myapplication.R
import com.himanshu.project.myapplication.databinding.FragmentHomeViewPagerBinding
import com.himanshu.project.myapplication.ui.adaptar.CUSTOM_NEWS_PAGE_INDEX
import com.himanshu.project.myapplication.ui.adaptar.HEADLINE_PAGE_INDEX
import com.himanshu.project.myapplication.ui.adaptar.PROFILE_PAGE_INDEX
import com.himanshu.project.myapplication.ui.adaptar.PagerAdapter
import kotlinx.android.synthetic.main.fragment_home_view_pager.view.*


class HomeViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPagerHome

        viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            HEADLINE_PAGE_INDEX -> R.drawable.ic_news_headline_white
            CUSTOM_NEWS_PAGE_INDEX -> R.drawable.ic_news_custom_white
            PROFILE_PAGE_INDEX -> R.drawable.ic_person_white
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            HEADLINE_PAGE_INDEX -> getString(R.string.headline_news_title)
            CUSTOM_NEWS_PAGE_INDEX -> getString(R.string.custom_news_title)
            PROFILE_PAGE_INDEX -> getString(R.string.profile_title)
            else -> null
        }
    }

}
