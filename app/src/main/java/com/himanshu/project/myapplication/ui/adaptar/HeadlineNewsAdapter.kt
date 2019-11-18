package com.himanshu.project.myapplication.ui.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.project.myapplication.data.model.Article
import com.himanshu.project.myapplication.databinding.ListHeadlineNewsBinding
import com.himanshu.project.myapplication.ui.fragment.HomeViewPagerFragmentDirections

class HeadlineNewsAdapter : ListAdapter<Article, RecyclerView.ViewHolder>(HeadlineNewsDiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        (holder as NewsViewHolder).bind(news)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(ListHeadlineNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class NewsViewHolder(private val binding: ListHeadlineNewsBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        init { binding.setClickListener { binding.item?.let { news ->
           // val direction = HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(news)
           // it.findNavController().navigate(direction)
        } } }
        fun bind(news: Article) {
            binding.apply { item = news
                executePendingBindings()
            }
        }
    }
}

private class HeadlineNewsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}