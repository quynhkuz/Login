package com.demo.demologgin.api

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.demo.demologgin.until.format

import com.globits.mita.data.network.SessionManager
import com.globits.mita.data.network.TokenAuthenticator
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Singleton
class RemoteDataSource() {

    companion object {
        private const val BASE_URL =
            "http://training-api.oceantech.com.vn/cms/"
        private const val DEFAULT_USER_AGENT = "Qlts-Android"
        private const val DEFAULT_CONTENT_TYPE = "application/json"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, UnitEpochDateTypeAdapter())
            .setLenient()
            .create()

        val sessionManager = SessionManager(context.applicationContext)
//        Timber.e("Access token = ${sessionManager.fetchAuthToken()}")

        var authenticator: TokenAuthenticator? =
            if (!sessionManager.fetchAuthToken().isNullOrEmpty()) {
                TokenAuthenticator(sessionManager.fetchAuthToken()!!)
            } else
                TokenAuthenticator("")

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient(authenticator))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(api)
    }


    private fun getRetrofitClient(
        authenticator: Authenticator? = null
    ): OkHttpClient {

        return getUnsafeOkHttpClient()
            .writeTimeout(31, TimeUnit.SECONDS)
            .readTimeout(31, TimeUnit.SECONDS)
            .connectTimeout(31, TimeUnit.SECONDS)
            .cookieJar(cookieJar())
            .addNetworkInterceptor(customInterceptor())
            .also { client ->
                authenticator?.let { client.authenticator(it) }

                    client.addInterceptor(loggingInterceptor())

            }
            .build()
    }

    private fun cookieJar(): CookieJar {
        val cookieManager = CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        }

        return JavaNetCookieJar(cookieManager)
    }

    private fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun customInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()

            // rewrite the request
            val request: Request = original.newBuilder()
                .header("User-Agent", DEFAULT_USER_AGENT)
                .header("Accept", DEFAULT_CONTENT_TYPE)
                .header("Content-Type", DEFAULT_CONTENT_TYPE)
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

    inner class UnitEpochDateTypeAdapter : TypeAdapter<Date>() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun write(out: JsonWriter?, value: Date?) {
            if (value == null) {
                out?.nullValue()
            } else {
                out?.value(value.format("yyyy-MM-dd"))
            }
        }

        override fun read(_in: JsonReader?) =
            if (_in != null) {
                if (_in.peek() == JsonToken.NULL) {
                    _in.nextNull()
                    null
                } else {
                    Date(_in.nextLong())
                }
            } else null
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )
            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}
