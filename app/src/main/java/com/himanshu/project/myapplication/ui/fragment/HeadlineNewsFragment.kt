package com.himanshu.project.myapplication.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.himanshu.project.myapplication.R
import com.himanshu.project.myapplication.data.model.NetworkError
import com.himanshu.project.myapplication.databinding.FragmentHeadlineNewsBinding
import com.himanshu.project.myapplication.services.network.NetworkErrorHandler
import com.himanshu.project.myapplication.ui.adaptar.HeadlineNewsAdapter
import com.himanshu.project.myapplication.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_headline_news.view.*


class HeadlineNewsFragment : Fragment() {

    private val viewmodel: NewsViewModel by viewModels { NewsViewModel.LiveDataVMFactory }
    lateinit var binding: FragmentHeadlineNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_headline_news, container, false)
        binding.headline = viewmodel


        binding.root.swipe_news_headline_refresh_layout.setOnRefreshListener {
            viewmodel.refreshHeadlineNews()
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = HeadlineNewsAdapter()
        binding.root.recyclerview_headline_news_list.adapter = adapter
        subscribeHeadlineNewsToUi(adapter)
    }

    private fun subscribeHeadlineNewsToUi(adapter: HeadlineNewsAdapter) {
        binding.root.swipe_news_headline_refresh_layout.isRefreshing = true
        viewmodel.headlineNewsList.observe(viewLifecycleOwner) { news ->
            news.onSuccess {
                it
                adapter.submitList(it.articles)
            }
            news.onFailure {
                it
                val networkErrorHandler = NetworkErrorHandler()
                errorAlertDialog(networkErrorHandler(it))
            }
            binding.root.swipe_news_headline_refresh_layout.isRefreshing = false
        }
    }

    private fun errorAlertDialog(networkError: NetworkError) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }


}
