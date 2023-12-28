package com.roshanadke.weekwatch.data.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.utils.getTrendingDataEntity
import com.roshanadke.weekwatch.data.utils.getTrendingItemDto
import com.roshanadke.weekwatch.data.utils.getTrendingResponseDto
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DetailsRepositoryImplTest {

    private lateinit var apiServiceFake: TrendingShowApiServiceFake
    private lateinit var tvShowDaoFake: TvShowDaoFake
    private lateinit var detailsRepositoryImpl: DetailsRepositoryImpl


    @BeforeEach
    fun setUp() {
        apiServiceFake = TrendingShowApiServiceFake()
        tvShowDaoFake = TvShowDaoFake()
        detailsRepositoryImpl = DetailsRepositoryImpl(apiServiceFake, tvShowDaoFake)
    }

    @Test
    fun `Test fetching tv show details`() = runBlocking {
        detailsRepositoryImpl.getTvShowDetails("1234").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Success).isTrue()
            assertThat(emission2.data?.id).isEqualTo(1234)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test error handling for fetching tv show details`() = runBlocking {
        apiServiceFake.errorToReturn = Exception("Test Exception")
        detailsRepositoryImpl.getTvShowDetails("").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading)

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Error)
            assertThat(emission2.data).isNull()
            assertThat(emission2.message).isEqualTo("Test Exception")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test fetching similar shows`() = runBlocking {
        val similarItemDtoList =
            listOf(
                getTrendingItemDto(126, "Similar Show One"),
                getTrendingItemDto(127, "Similar Show Two"),
            )
        val trendingSimilarResponseDto = getTrendingResponseDto(similarItemDtoList)
        apiServiceFake.trendingResponseDto = trendingSimilarResponseDto
        detailsRepositoryImpl.getSimilarShows("369").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading).isTrue()

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Success).isTrue()
            assertThat(emission2.data?.trendingItemDtoList?.size).isEqualTo(2)
            assertThat(emission2.data?.trendingItemDtoList?.get(0)?.id).isEqualTo(126)
            assertThat(emission2.data?.trendingItemDtoList?.get(1)?.id).isEqualTo(127)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test error handling for fetching similar shows`() = runBlocking {
        apiServiceFake.errorToReturn = Exception("Test Exception")
        detailsRepositoryImpl.getSimilarShows("369").test {
            val emission1 = awaitItem()
            assertThat(emission1 is UiState.Loading).isTrue()

            val emission2 = awaitItem()
            assertThat(emission2 is UiState.Error).isTrue()
            assertThat(emission2.data).isNull()
            assertThat(emission2.message).isEqualTo("Test Exception")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test adding favourites`() = runBlocking {
        val entity = getTrendingItemDto(id = 123, title = "Test").toTrendingItem()
        detailsRepositoryImpl.addToFavourites(entity)
        tvShowDaoFake.getAllRecords().test {
            val emission = awaitItem()
            assertThat(emission.isNotEmpty()).isTrue()
            assertThat(emission.size).isNotEqualTo(0)
            assertThat(emission[0].title).isEqualTo("Test")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test removing favourites`() = runBlocking {
        val entity = getTrendingItemDto(id = 123, title = "Test").toTrendingItem()
        detailsRepositoryImpl.addToFavourites(entity)
        tvShowDaoFake.getAllRecords().test {
            val emission = awaitItem()
            assertThat(emission.isNotEmpty()).isTrue()
            assertThat(emission.size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
        detailsRepositoryImpl.removeFromFavourites(entity.id)
        tvShowDaoFake.getAllRecords().test {
            val emission = awaitItem()
            assertThat(emission.isEmpty()).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }


}