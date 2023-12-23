package com.roshanadke.weekwatch.di

import android.util.Log
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.repository.TrendingShowRepositoryImpl
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrendingApiService(httpClient: OkHttpClient): TrendingShowApiService {
        return Retrofit.Builder()
            .baseUrl(TrendingShowApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(httpClient)
            .build()
            .create(TrendingShowApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideTrendingRepository(
        apiService: TrendingShowApiService
    ): TrendingShowRepository {
        return TrendingShowRepositoryImpl(apiService)
    }

}