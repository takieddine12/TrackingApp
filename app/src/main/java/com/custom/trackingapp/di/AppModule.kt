package com.custom.trackingapp.di

import com.custom.trackingapp.Tools
import com.custom.trackingapp.network.WorkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun getRetrofitInstance() : WorkService {
        val loggingInterceptor = HttpLoggingInterceptor()
        val interceptor  = loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(Tools.BaseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WorkService::class.java)
    }
}