package com.himanshu.project.myapplication.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.himanshu.project.myapplication.R
import com.himanshu.project.myapplication.data.model.NetworkError
import com.himanshu.project.myapplication.databinding.FragmentProfileBinding
import com.himanshu.project.myapplication.services.network.NetworkErrorHandler
import com.himanshu.project.myapplication.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val viewmodel: ProfileViewModel by viewModels { ProfileViewModel.LiveDataProfileVMFactory }
    lateinit var binding: FragmentProfileBinding

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
        getUserDetail()
        getUserSaveResponse()
    }


    private fun getUserDetail() {
        viewmodel.userDetails.observe(viewLifecycleOwner) { news ->
            news.onSuccess {
                it
                edittext_username.setText(it.userName)
                edittext_email.setText(it.userEmail)
                button_btn_action.text = getString(R.string.btn_action_title)
            }
            news.onFailure {
                it
                val networkErrorHandler = NetworkErrorHandler()
                errorAlertDialog(networkErrorHandler(it))
            }
        }
    }

    private fun getUserSaveResponse() {
        viewmodel.userSaveRespons.observe(viewLifecycleOwner) { news ->
            Toast.makeText(requireContext(), news, Toast.LENGTH_SHORT).show()
        }
    }

    fun errorAlertDialog(networkError: NetworkError) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }

}
