package com.demo.demologgin.dagger

import android.content.Context
import com.demo.demologgin.MyApplication
import com.demo.demologgin.ui.FistFragment
import com.demo.demologgin.ui.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [Modul::class])
interface Conponent {

    fun getMainActiviti(mainActivity: MainActivity)

    fun appLication(myApplication: MyApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): Conponent
    }


}