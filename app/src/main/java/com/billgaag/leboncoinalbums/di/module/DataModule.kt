package com.billgaag.leboncoinalbums.di.module

import android.content.Context
import com.billgaag.leboncoin.data.services.AlbumsService
import com.billgaag.leboncoin.data.source.local.AlbumsDataBase
import com.billgaag.leboncoin.data.source.local.dao.AlbumDao
import com.billgaag.leboncoin.data.source.remote.api.AlbumsApi
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import com.billgaag.leboncoinalbums.di.scope.DataScope
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class DataModule(context: Context) {

    private val database = AlbumsDataBase.getInstance(context)

    @Provides
    @DataScope
    fun database() = database

    @Provides
    @DataScope
    fun albumsDao() = database.albumsDao()

    @Provides
    @DataScope
    fun apiRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttp = OkHttpClient.Builder()
        okhttp.callTimeout(30, TimeUnit.SECONDS);
        okhttp.connectTimeout(30, TimeUnit.SECONDS);
        okhttp.addInterceptor(httpLoggingInterceptor)

        return Retrofit.Builder()
                .baseUrl("https://static.leboncoin.fr")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okhttp.build())
                .build()
    }

    @Provides
    @DataScope
    fun albumsApi(retrofit: Retrofit): AlbumsApi {
        return retrofit.create(AlbumsApi::class.java)
    }

    @Provides
    @DataScope
    fun albumsRepository(api: AlbumsApi, dao: AlbumDao): AlbumsRepository {
        return AlbumsService(api, dao, Schedulers.io())
    }
}