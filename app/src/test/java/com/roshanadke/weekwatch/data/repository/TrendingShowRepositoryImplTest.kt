package com.roshanadke.weekwatch.data.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.utils.getTrendingDataEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TrendingShowRepositoryImplTest  {

    private lateinit var apiServiceFake: TrendingShowApiServiceFake
    private lateinit var tvShowDaoFake: TvShowDaoFake
    private lateinit var trendingShowRepositoryImpl: TrendingShowRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiServiceFake = TrendingShowApiServiceFake()
        tvShowDaoFake = TvShowDaoFake()
        trendingShowRepositoryImpl = TrendingShowRepositoryImpl(apiServiceFake, tvShowDaoFake)
    }

    @Test
    fun `Test fetching all trending shows`() = runBlocking {
        trendingShowRepositoryImpl.getAllTrendingShows().test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Success)
            assertThat(emission2.data?.trendingItemDtoList?.size).isEqualTo(3)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test fetching trending shows, throws exception`() = runBlocking {
        apiServiceFake.errorToReturn = Exception("Test Exception")
        trendingShowRepositoryImpl.getAllTrendingShows().test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Error)
            assertThat(emission2.data).isNull()
            assertThat(emission2.message).isEqualTo("Test Exception")

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test fetching and verifying searched shows data`() = runBlocking {
        trendingShowRepositoryImpl.fetchSearchedShows("break").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Success)
            assertThat(emission2.data?.trendingItemDtoList?.size).isEqualTo(2)
            assertThat(emission2.data?.trendingItemDtoList?.get(0)?.title?.contains("break"))
            assertThat(emission2.data?.trendingItemDtoList?.get(1)?.title?.contains("break"))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test searched shows, throws Exception`() = runBlocking {
        apiServiceFake.errorToReturn = Exception("Test Search Exception")
        trendingShowRepositoryImpl.fetchSearchedShows("break").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Error)
            assertThat(emission2.data).isNull()
            assertThat(emission2.message).isEqualTo("Test Search Exception")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test fetching favourite records from local db`() = runBlocking {
        val entity = getTrendingDataEntity(125)
        tvShowDaoFake.insert(entity)
        trendingShowRepositoryImpl.getAllLocalRecords().test {
            val emission1 = awaitItem()
            assertThat(emission1)

            assertThat(emission1.size).isEqualTo(1)
            assertThat(emission1[0].id).isEqualTo(125)

            cancelAndIgnoreRemainingEvents()
        }
    }

}