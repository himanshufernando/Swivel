package com.himanshu.project.myapplication.services.network

import android.content.Context
import android.net.ConnectivityManager
import com.himanshu.project.myapplication.Swivel

object InternetConnection {
    var app : Context = Swivel.applicationContext()
    fun checkInternetConnection(): Boolean {
        var isConnexted = false
        val connect = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connect.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                isConnexted = true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                isConnexted = true
            }
        } else {
            isConnexted = false
        }
        return isConnexted
    }
}