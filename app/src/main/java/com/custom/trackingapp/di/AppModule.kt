package com.custom.trackingapp.di

import android.content.Context
import androidx.room.Room
import com.custom.trackingapp.Tools
import com.custom.trackingapp.db.PackageDatabase
import com.custom.trackingapp.network.WorkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context.applicationContext,
    PackageDatabase::class.java,"package.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideDao(packageDatabase: PackageDatabase)  = packageDatabase.getPackageDao()
}