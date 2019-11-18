package com.himanshu.project.myapplication.viewmodels

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.project.myapplication.Swivel
import com.himanshu.project.myapplication.data.db.AppDatabase
import com.himanshu.project.myapplication.data.db.UserDao
import com.himanshu.project.myapplication.services.api.APIInterface

class ProfileViewModel (private val client: APIInterface, val userDOA : UserDao) : ViewModel() {

    var editTextUserName = MutableLiveData<String>()
    var editTextEmail = MutableLiveData<String>()


    private val _userPreferences = MutableLiveData<String>()
    val userPreferences: LiveData<String> = _userPreferences


    fun onSelectUserPreferences(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var pref = parent!!.getItemAtPosition(position) as String
        _userPreferences.value = pref
    }


    object LiveDataProfileVMFactory : ViewModelProvider.Factory {
        private val dataSource = APIInterface.create()
        var app : Context = Swivel.applicationContext()
        private val userDOA = AppDatabase.getInstance(app).UserDao()


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(dataSource,userDOA) as T

        }
    }
}