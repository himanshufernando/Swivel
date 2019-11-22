package com.himanshu.project.myapplication.viewmodels

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.himanshu.project.myapplication.Swivel
import com.himanshu.project.myapplication.data.db.AppDatabase
import com.himanshu.project.myapplication.data.db.UserDao
import com.himanshu.project.myapplication.data.model.Article
import com.himanshu.project.myapplication.data.model.CustomNews
import com.himanshu.project.myapplication.data.model.News
import com.himanshu.project.myapplication.repo.NewsRepository
import com.himanshu.project.myapplication.services.api.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel (private val client: APIInterface, val userDOA : UserDao) : ViewModel() {

    var repo = NewsRepository(client,userDOA)



    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article> = _selectedArticle


    private val _newsHeadlineStatus = MutableLiveData<Boolean>()
    val newsHeadlineStatus: LiveData<Boolean> = _newsHeadlineStatus



    private val _customNewsPreferences = MutableLiveData<CustomNews>()
    val customNewsPreferences: LiveData<CustomNews> = _customNewsPreferences


    private val _userPreferences = MutableLiveData<String>()
    val userPreferences: LiveData<String> = _userPreferences


    init {
        refreshHeadlineNews()
        _customNewsPreferences.value = CustomNews("bitcoin",true)


    }


    val headlineNewsList = newsHeadlineStatus.switchMap { _ ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                emit(Result.success(repo.getHeadlineNews()))
            } catch(ioException: Throwable) {
                emit(Result.failure(ioException))
            }
        }
    }

    fun refreshHeadlineNews() {
        _newsHeadlineStatus.value =true
    }





    // get custom
    val customNewsList = customNewsPreferences.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                emit(Result.success(repo.getCustomNews(id.userPref)))
            } catch(ioException: Throwable) {
                emit(Result.failure(ioException))
            }
        }
    }

    fun refreshCustomNews() {
        _customNewsPreferences.value = CustomNews(_userPreferences.value!!,true)
    }





    fun onSelectUserPreferences(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var pref = parent!!.getItemAtPosition(position) as String
        _userPreferences.value = pref
        _customNewsPreferences.value = CustomNews(pref,true)

    }

    fun setSelectedArticle(article: Article){
        _selectedArticle.value = article
    }





    object LiveDataVMFactory : ViewModelProvider.Factory {
        private val dataSource = APIInterface.create()
        var app : Context = Swivel.applicationContext()
        private val userDOA = AppDatabase.getInstance(app).UserDao()

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(dataSource,userDOA) as T

        }
    }

}