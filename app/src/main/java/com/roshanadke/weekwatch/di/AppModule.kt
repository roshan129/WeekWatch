package com.roshanadke.weekwatch.di

import android.content.Context
import androidx.room.Room
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.common.Constants
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.data.local.TvShowDatabase
import com.roshanadke.weekwatch.data.network.PeopleApiService
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.repository.DetailsRepositoryImpl
import com.roshanadke.weekwatch.data.repository.PeopleRepositoryImpl
import com.roshanadke.weekwatch.data.repository.TrendingShowRepositoryImpl
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.domain.repository.PeopleRepository
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import com.roshanadke.weekwatch.domain.use_case.AddToFavouriteUseCase
import com.roshanadke.weekwatch.domain.use_case.DetailsUseCaseState
import com.roshanadke.weekwatch.domain.use_case.GetFavouriteUseCase
import com.roshanadke.weekwatch.domain.use_case.GetSearchedShowUseCase
import com.roshanadke.weekwatch.domain.use_case.GetSimilarShowsUseCase
import com.roshanadke.weekwatch.domain.use_case.GetTrendingShowUseCase
import com.roshanadke.weekwatch.domain.use_case.GetTvShowDetailsUseCase
import com.roshanadke.weekwatch.domain.use_case.RemoveFromFavouriteUseCase
import com.roshanadke.weekwatch.domain.use_case.TrendingShowUseCaseState
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
    fun providePeopleApiService(
        httpClient: OkHttpClient
    ): PeopleApiService {
        return Retrofit.Builder()
            .baseUrl(PeopleApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PeopleApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: Interceptor
    ): OkHttpClient {
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
        apiService: TrendingShowApiService,
        dao: TvShowDao
    ): TrendingShowRepository {
        return TrendingShowRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(
        apiService: TrendingShowApiService,
        dao: TvShowDao
    ): DetailsRepository {
        return DetailsRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TvShowDatabase {
        return Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(
        db: TvShowDatabase
    ): TvShowDao {
        return db.getTvShowDao()
    }

    @Provides
    @Singleton
    fun provideTrendingShowUseCase(
        repository: TrendingShowRepository
    ): GetTrendingShowUseCase {
        return GetTrendingShowUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTvShowDetailsUseCase(
        repository: DetailsRepository
    ): GetTvShowDetailsUseCase {
        return GetTvShowDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFavouriteUseCase(
        repository: TrendingShowRepository
    ): GetFavouriteUseCase {
        return GetFavouriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddFavouriteUseCase(
        repository: DetailsRepository
    ): AddToFavouriteUseCase {
        return AddToFavouriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromFavouriteUseCase(
        repository: DetailsRepository
    ): RemoveFromFavouriteUseCase {
        return RemoveFromFavouriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSimilarShowsUseCase(
        repository: DetailsRepository
    ): GetSimilarShowsUseCase {
        return GetSimilarShowsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedShowUseCase(
        repository: TrendingShowRepository
    ): GetSearchedShowUseCase {
        return GetSearchedShowUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTvShowUseCaseState(
        trendingShowUseCase: GetTrendingShowUseCase,
        getFavouriteUseCase: GetFavouriteUseCase,
        searchedShowUseCase: GetSearchedShowUseCase
    ): TrendingShowUseCaseState {
        return TrendingShowUseCaseState(
            trendingShowUseCase,
            getFavouriteUseCase,
            searchedShowUseCase
        )
    }

    @Provides
    @Singleton
    fun provideDetailsUseCaseState(
        tvShowDetailsUseCase: GetTvShowDetailsUseCase,
        addToFavouriteUseCase: AddToFavouriteUseCase,
        removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
        similarShowsUseCase: GetSimilarShowsUseCase,
    ): DetailsUseCaseState {
        return DetailsUseCaseState(
            tvShowDetailsUseCase,
            addToFavouriteUseCase,
            removeFromFavouriteUseCase,
            similarShowsUseCase,
        )
    }

    @Provides
    @Singleton
    fun providePeopleRepository(apiService: PeopleApiService): PeopleRepository {
        return PeopleRepositoryImpl(apiService)
    }

}