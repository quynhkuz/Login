package com.demo.demologgin

import android.app.Application
import com.demo.demologgin.dagger.Conponent
import com.demo.demologgin.dagger.DaggerConponent

import dagger.Component

class MyApplication:Application() {

    lateinit var component : Conponent
    override fun onCreate() {
        super.onCreate()
        component = DaggerConponent.factory().create(applicationContext)
        component.appLication(this)
    }


}