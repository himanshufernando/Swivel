package com.himanshu.project.myapplication

import android.app.Application
import android.content.Context

class Swivel : Application(){
    init {
        instance = this
    }
    companion object {
        private var instance: Swivel? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = Swivel.applicationContext()

    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)


    }

}