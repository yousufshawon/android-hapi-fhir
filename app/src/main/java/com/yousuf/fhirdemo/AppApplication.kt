package com.yousuf.fhirdemo

import android.app.Application
import timber.log.Timber

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setUpConfig()
    }

    private fun setUpConfig(){
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{

        }
    }
}