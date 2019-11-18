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
import com.himanshu.project.myapplication.data.model.News
import com.himanshu.project.myapplication.repo.NewsRepository
import com.himanshu.project.myapplication.services.api.APIInterface
import kotlinx.coroutines.Dispatchers

class NewsViewModel (private val client: APIInterface, val userDOA : UserDao) : ViewModel() {

    var repo = NewsRepository(client,userDOA)

    //headline ProgressBar
    val isNewsListLoading = ObservableField<Boolean>()
    //custom ProgressBar
    val isCustomNewsListLoading = ObservableField<Boolean>()


    private val _userPreferences = MutableLiveData<String>()
    val userPreferences: LiveData<String> = _userPreferences

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article> = _selectedArticle

    init {
        _userPreferences.value = "bitcoin"
    }


    // get headlines
    val headlineNewsList: LiveData<Result<News>> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        isNewsListLoading.set(true)
        try {
            emit(Result.success(repo.getHeadlineNews()))
            isNewsListLoading.set(false)
        } catch(ioException: Throwable) {
            isNewsListLoading.set(false)
            emit(Result.failure(ioException))
        }
    }

    // get custom
    val customNewsList = userPreferences.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            isCustomNewsListLoading.set(true)
            try {
                emit(Result.success(repo.getCustomNews(id)))
                isCustomNewsListLoading.set(false)
            } catch(ioException: Throwable) {
                isCustomNewsListLoading.set(false)
                emit(Result.failure(ioException))
            }
        }
    }

    fun onSelectUserPreferences(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var pref = parent!!.getItemAtPosition(position) as String
        _userPreferences.value = pref
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