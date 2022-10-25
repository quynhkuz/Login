package com.demo.demologgin.dagger

import android.content.Context
import com.demo.demologgin.api.RemoteDataSource
import com.demo.demologgin.maricks.State
import com.demo.demologgin.maricks.ViewModel
import com.demo.demologgin.repository.AuthRepository
import com.globits.mita.data.network.AuthApi
import dagger.Module
import dagger.Provides


@Module
class Modul {




    @Provides
    fun remoteDataSoure():RemoteDataSource
    {
        return RemoteDataSource()
    }

    @Provides
    fun authRepository(
        api :AuthApi
    ):AuthRepository = AuthRepository(api)

    @Provides
    fun authApi(remoteDataSource: RemoteDataSource,context: Context):AuthApi
    {
        return remoteDataSource.buildApi(AuthApi::class.java,context)
    }



}