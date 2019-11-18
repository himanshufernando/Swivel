package com.himanshu.project.myapplication.repo

import com.himanshu.project.myapplication.BuildConfig
import com.himanshu.project.myapplication.data.db.UserDao
import com.himanshu.project.myapplication.services.api.APIInterface
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRepository  @Inject constructor(var client: APIInterface,var userDao : UserDao) {
    private val apiKey = BuildConfig.API_KEY
    var country = "us"
    var category = "business"
    val pattern = "yyyy-MM-dd"


    suspend fun getHeadlineNews() = client.getTopHeadlines(country,category,apiKey)
    suspend fun getCustomNews(userPref : String) = client.getCustomNews(userPref,"2019-11-10","publishedAt",apiKey)
    suspend fun getUserDetails() =userDao.getUser()
    suspend fun getUserPreferences() =userDao.getUserPreferences()

    fun getCurentDate(): String{
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(Date())
    }
}