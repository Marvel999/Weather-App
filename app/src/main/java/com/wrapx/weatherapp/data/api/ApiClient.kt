package com.wrapx.weatherapp.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wrapx.weatherapp.util.Util.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    private val interceptor = Interceptor { chain ->
        val url =
            chain.request().url().newBuilder().addQueryParameter("access_key", API_KEY).build()
        val request = chain.request().newBuilder().url(url).build()
        chain.proceed(request)
    }

    private val okHttpBuilder = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("http://api.weatherstack.com")
        .addConverterFactory(MoshiConverterFactory.create())


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val API: ApiService = retrofitBuilder
        .client(okHttpBuilder)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiService::class.java)

}