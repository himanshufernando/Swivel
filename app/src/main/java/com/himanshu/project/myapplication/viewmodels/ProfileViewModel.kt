package com.himanshu.project.myapplication.viewmodels

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.*
import com.himanshu.project.myapplication.R
import com.himanshu.project.myapplication.Swivel
import com.himanshu.project.myapplication.data.db.AppDatabase
import com.himanshu.project.myapplication.data.db.UserDao
import com.himanshu.project.myapplication.data.db.UserTB
import com.himanshu.project.myapplication.data.model.News
import com.himanshu.project.myapplication.repo.NewsRepository
import com.himanshu.project.myapplication.services.api.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel (private val client: APIInterface, val userDOA : UserDao) : ViewModel() {

    var editTextUserName = MutableLiveData<String>()
    var editTextEmail = MutableLiveData<String>()

    var app : Context = Swivel.applicationContext()
    var repo = NewsRepository(client,userDOA)

    private val _userPreferences = MutableLiveData<String>()
    val userPreferences: LiveData<String> = _userPreferences

    private val _userSaveRespons = MutableLiveData<String>()
    val userSaveRespons: LiveData<String> = _userSaveRespons

    var isUserAvailable = MutableLiveData<Boolean>()

    init {


        _userPreferences.value = "bitcoin"
    }



    val userDetails: LiveData<Result<UserTB>> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        try {
            emit(Result.success(repo.getUserDetails()))
        } catch(ioException: Throwable) {
            emit(Result.failure(ioException))
        }
    }



    fun onSelectUserPreferences(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var pref = parent!!.getItemAtPosition(position) as String
        _userPreferences.value = pref
    }




     fun insertUser() {
       viewModelScope.launch {
           var userName = editTextUserName.value.toString()
           var userEmail = editTextEmail.value.toString()

           if((userName.isEmpty()) || (userName== "null")  ){
               _userSaveRespons.value =app.getString(R.string.user_add_messages_name_empty)
           }else if((userEmail.isEmpty()) || (userEmail=="null")){
               _userSaveRespons.value =app.getString(R.string.user_add_messages_email_empty)
           }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
               _userSaveRespons.value =app.getString(R.string.user_add_messages_email_invalid)
           }else{
               var userObject = UserTB(1,userName,userEmail,userSaveRespons.value)
               userDOA.insertUser(userObject)
               _userSaveRespons.value =app.getString(R.string.user_save_successfully)
           }

       }
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