package com.roshanadke.weekwatch.data.repository.mocking

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.repository.TrendingShowRepositoryImpl
import com.roshanadke.weekwatch.data.utils.getTrendingDataEntity
import com.roshanadke.weekwatch.data.utils.getTrendingItemDto
import com.roshanadke.weekwatch.data.utils.getTrendingItemDtoList
import com.roshanadke.weekwatch.data.utils.getTrendingResponseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TrendingShowRepositoryMockkTest {


    @MockK
    private lateinit var apiServiceMock: TrendingShowApiService

    @MockK
    private lateinit var tvShowDaoMock: TvShowDao

    private lateinit var trendingShowRepositoryImpl: TrendingShowRepositoryImpl

    lateinit var testDispatcher: TestDispatcher

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        trendingShowRepositoryImpl = TrendingShowRepositoryImpl(apiServiceMock, tvShowDaoMock)
    }

    @Test
    fun `Test fetching all trending shows, valid response`() = runTest {
        coEvery { apiServiceMock.getAllTrending() } returns getTrendingResponseDto(
            listOf(
                getTrendingItemDto(123, "The Boys"),
                getTrendingItemDto(124, "The Boys 2")
            )
        )

        trendingShowRepositoryImpl.getAllTrendingShows().test {
            awaitItem() //ignored emission
            val emission1 = awaitItem()
            assertThat(emission1.data?.trendingItemDtoList?.size).isEqualTo(2)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test fetching trending shows, throws exception`(): Unit = runTest {
        coEvery { apiServiceMock.getAllTrending() } throws NullPointerException("test exception")

        trendingShowRepositoryImpl.getAllTrendingShows().test {
            awaitItem()
            val error = awaitItem()
            assertThat(error).isInstanceOf(UiState.Error::class)
            assertThat(error.message).isEqualTo("test exception")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test fetching and verifying searched shows data`(): Unit = runBlocking {

        val trendingResponseDto = getTrendingResponseDto(getTrendingItemDtoList())

        coEvery { apiServiceMock.fetchSearchedShows("break") } returns
                trendingResponseDto

        trendingShowRepositoryImpl.fetchSearchedShows("break").test {
            assertThat(awaitItem() is UiState.Loading)

            val emission1 =awaitItem()
            assertThat(emission1.data?.trendingItemDtoList?.size).isEqualTo(3)
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `Test searched shows, throws Exception`(): Unit = runBlocking {

        coEvery { apiServiceMock.fetchSearchedShows(any()) } throws RuntimeException("test exception")

        trendingShowRepositoryImpl.fetchSearchedShows("boys").test {
            assertThat(awaitItem() is UiState.Loading)

            val emission = awaitItem()

            assertThat(emission is UiState.Error)
            assertThat(emission.message).isEqualTo("test exception")

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test fetching favourite records from local db`(): Unit = runBlocking {
        every { tvShowDaoMock.getAllRecords() } returns flowOf(listOf(getTrendingDataEntity(126)))

        trendingShowRepositoryImpl.getAllLocalRecords().test {
            val emission = awaitItem()

            assertThat(emission.size).isEqualTo(1)
            assertThat(emission[0].id).isEqualTo(126)
            cancelAndConsumeRemainingEvents()
        }
    }


}