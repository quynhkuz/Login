package com.demo.demologgin.repository

import android.telecom.Call
import com.demo.demologgin.model.Body
import com.demo.demologgin.model.Responsive
import com.demo.oceantechtranning.model.TokenResponse
import com.globits.mita.data.model.UserCredentials
import com.globits.mita.data.network.AuthApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val api:AuthApi
) {
     fun login(username :String, password :String) :Observable<TokenResponse>
     {
            return api.oauth(
                UserCredentials(
                    AuthApi.CLIENT_ID,
                    AuthApi.CLIENT_SECRET,
                    username,
                    password,
                    null,
                    AuthApi.GRANT_TYPE_PASSWORD
                )
            ).subscribeOn(Schedulers.io())
     }


    fun loginsucess(code :String?,name : String?) : Observable<Responsive>
    {
        return api.sucess(
           Body(
               code,
               name
           )
        ).subscribeOn(Schedulers.io())
    }



}