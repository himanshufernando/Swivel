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
import com.himanshu.project.myapplication.databinding.FragmentCustomNewsBinding
import com.himanshu.project.myapplication.services.network.NetworkErrorHandler
import com.himanshu.project.myapplication.ui.adaptar.CustomNewsAdapter
import com.himanshu.project.myapplication.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_custom_news.view.*


class CustomNewsFragment : Fragment() {

    private val viewmodel: NewsViewModel by viewModels { NewsViewModel.LiveDataVMFactory }
    lateinit var binding:FragmentCustomNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_news, container,false)
        binding.custom=viewmodel

        binding.root.swipe_news_custom_refresh_layout.setOnRefreshListener {
            viewmodel.refreshCustomNews()
        }


        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = CustomNewsAdapter()
        binding.root.recyclerview_custom_news_list.adapter = adapter
        subscribeCustomNewsToUi(adapter)
    }

    private fun subscribeCustomNewsToUi(adapter: CustomNewsAdapter) {
        binding.root.swipe_news_custom_refresh_layout.isRefreshing = true
        viewmodel.customNewsList.observe(viewLifecycleOwner){news ->
            news.onSuccess {it
                adapter.submitList(it.articles)
            }
            news.onFailure {it
                val networkErrorHandler = NetworkErrorHandler()
                errorAlertDialog(networkErrorHandler(it))
            }

            binding.root.swipe_news_custom_refresh_layout.isRefreshing = false
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
