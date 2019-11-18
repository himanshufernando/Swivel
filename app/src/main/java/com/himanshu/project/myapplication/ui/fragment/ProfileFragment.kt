package com.himanshu.project.myapplication.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels

import com.himanshu.project.myapplication.R
import com.himanshu.project.myapplication.databinding.FragmentProfileBinding
import com.himanshu.project.myapplication.viewmodels.ProfileViewModel


class ProfileFragment : Fragment() {

    private val viewmodel: ProfileViewModel by viewModels { ProfileViewModel.LiveDataProfileVMFactory }
    lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profile = viewmodel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}
