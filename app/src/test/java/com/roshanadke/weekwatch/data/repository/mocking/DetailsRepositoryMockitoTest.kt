package com.roshanadke.weekwatch.data.repository.mocking

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.repository.DetailsRepositoryImpl
import com.roshanadke.weekwatch.data.repository.TrendingShowRepositoryImpl
import com.roshanadke.weekwatch.data.repository.TvShowDaoFake
import com.roshanadke.weekwatch.data.utils.getTrendingItemDto
import com.roshanadke.weekwatch.data.utils.getTrendingResponseDto
import com.roshanadke.weekwatch.data.utils.getTvShowDetailsMainDto
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class DetailsRepositoryMockitoTest {

    @Mock
    private lateinit var apiServiceMock: TrendingShowApiService

    @Mock
    private lateinit var tvShowDaoFake: TvShowDaoFake

    private lateinit var detailsRepositoryImpl: DetailsRepositoryImpl


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        detailsRepositoryImpl = DetailsRepositoryImpl(apiServiceMock, tvShowDaoFake)
    }

    @Test
    fun verifyTvShowDetails_validResponse() = runBlocking {
        `when`(apiServiceMock.getTvShowDetails(anyString())).thenReturn(
            getTvShowDetailsMainDto(1234).copy(numberOfSeasons = 5)
        )

        detailsRepositoryImpl.getTvShowDetails("1234").test {
            awaitItem() //ignore first loading emission

            val emission1 = awaitItem()

            assertThat(emission1.data?.numberOfSeasons).isEqualTo(5)

            cancelAndIgnoreRemainingEvents()
        }


    }

    @Test
    fun verifyTvShowDetails_throwsException(): Unit = runBlocking {
        //`when`(apiServiceMock.getTvShowDetails(anyString())).thenThrow(RuntimeException::class.java)
        `when`(apiServiceMock.getTvShowDetails(anyString())).thenThrow(RuntimeException::class.java)

        detailsRepositoryImpl.getTvShowDetails("1234").test {
            awaitItem()

            val emission1 = awaitItem()
            assertThat(emission1.data).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `Test fetching similar shows`(): Unit = runBlocking {
        val similarItemDtoList =
            listOf(
                getTrendingItemDto(126, "Similar Show One"),
                getTrendingItemDto(127, "Similar Show Two"),
            )
        val trendingSimilarResponseDto = getTrendingResponseDto(similarItemDtoList)
        `when`(apiServiceMock.getSimilarShows(anyString())).thenReturn(trendingSimilarResponseDto)

        detailsRepositoryImpl.getSimilarShows("316").test {
            assertThat(awaitItem() is UiState.Loading)

            val emission = awaitItem()
            assertThat(emission.data?.trendingItemDtoList?.size).isEqualTo(2)
            assertThat(emission.data?.trendingItemDtoList?.get(0)?.id).isEqualTo(126)
            assertThat(emission.data?.trendingItemDtoList?.get(1)?.id).isEqualTo(127)

            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `Test fetching similar shows, throws Error`(): Unit = runBlocking {
        `when`(apiServiceMock.getSimilarShows(anyString())).thenThrow(RuntimeException("test exception"))

        detailsRepositoryImpl.getSimilarShows("316").test {
            assertThat(awaitItem() is UiState.Loading)

            val emission = awaitItem()

            assertThat(emission is UiState.Error)
            assertThat(emission.message).isEqualTo("test exception")

            cancelAndConsumeRemainingEvents()
        }
    }



}




