package com.roshanadke.weekwatch.di

import android.content.Context
import androidx.room.Room
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.data.local.TvShowDatabase
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.repository.DetailsRepositoryImpl
import com.roshanadke.weekwatch.data.repository.TrendingShowRepositoryImpl
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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
            .client(httpClient)
            .build()
            .create(TrendingShowApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message ->
            Timber.d(message)
        }
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.AUTH_TOKEN}")
                .build()
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun provideTrendingRepository(
        apiService: TrendingShowApiService
    ): TrendingShowRepository {
        return TrendingShowRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(
        apiService: TrendingShowApiService
    ): DetailsRepository {
        return DetailsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TvShowDatabase {
        return Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            "TvShowDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(
        db: TvShowDatabase
    ): TvShowDao {
        return db.getTvShowDao()
    }

}