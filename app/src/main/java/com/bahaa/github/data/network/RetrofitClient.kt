package com.bahaa.github.data.network


import com.bahaa.github.MyApplication
import com.bahaa.github.data.network.interfaces.Api
import com.bahaa.github.ui.activities.base.BaseActivity
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val BASE_URL = "https://api.github.com/users/"
    var retrofit: Retrofit? = null
    var okHttpClient: OkHttpClient? = null
    internal lateinit var gson: Gson

    fun webService(): Api {
        BaseActivity.instance?.showProgressDialog()
        if (retrofit == null) {
            gson = Gson()
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(NetworkInterceptor(MyApplication.context))
                    .addInterceptor(logging)
                    .connectTimeout(3600, TimeUnit.SECONDS)
                    .readTimeout(3600, TimeUnit.SECONDS)
                    .writeTimeout(3600, TimeUnit.SECONDS)
                    .build()
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient!!)
                    .build()
        }
        return retrofit!!.create(Api::class.java)
    }

    fun cancelAllRequests() {
        if (okHttpClient != null) {
            okHttpClient!!.dispatcher.cancelAll()
        }
    }

    fun retryLastRequest(): okhttp3.Call? {
        var list: List<okhttp3.Call>? = null
        var call: Call? = null
        if (okHttpClient != null) {
            list = okHttpClient!!.dispatcher.queuedCalls()
            if (list.size != 0) {
                call = list[list.size - 1]
            }
        }
        return call
    }
}

