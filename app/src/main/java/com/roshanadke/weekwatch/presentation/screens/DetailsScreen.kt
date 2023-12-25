package com.roshanadke.weekwatch.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.Constants
import com.roshanadke.weekwatch.common.Screen
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.presentation.components.SeasonCard
import com.roshanadke.weekwatch.presentation.components.TrendingItemCard
import com.roshanadke.weekwatch.presentation.viewmodels.DetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    trendingItem: TrendingItem?,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val backdropImage = TrendingShowApiService.BACKDROP_IMAGE_BASE_URL + trendingItem?.backdrop_path
    val halfScreenWidth = LocalConfiguration.current.screenWidthDp / 2
    var isBookmarked by rememberSaveable {
        mutableStateOf(trendingItem?.isFavourite ?: false)
    }

    val tvShowDetailsState = viewModel.tvShowDetailsState.value
    val similarShowListState = viewModel.similarItemListState.value
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val addedToFavourite = stringResource(id = R.string.added_favourite)


    LaunchedEffect(Unit) {
        trendingItem?.id?.let {
            viewModel.getSimilarShows(it.toString())
            viewModel.getTvShowDetails(it.toString())
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isBookmarked = !isBookmarked
                        if (isBookmarked) {
                            viewModel.addToFavourites(trendingItem)
                            scope.launch {
                                snackBarHostState.showSnackbar(addedToFavourite, duration = SnackbarDuration.Short)
                            }
                        } else {
                            viewModel.removeFromFavourites(trendingItem?.id)
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }
                    }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {

                AsyncImage(
                    model = backdropImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = trendingItem?.name,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = trendingItem?.title ?: trendingItem?.name ?: "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = trendingItem?.overview ?: "",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Seasons",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                tvShowDetailsState.showDetails.seasons.forEach { season ->
                    SeasonCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        seasonNo = season.season_number,
                        episodes = season.episode_count
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "More Like This",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    items(similarShowListState.list) { item ->
                        TrendingItemCard(
                            modifier = Modifier
                                .width(halfScreenWidth.dp)
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            imageEndpoint = item.poster_path,
                            showName = item.title ?: item.name,
                            onCardClicked = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = Constants.TRENDING_ITEM,
                                    value = item
                                )
                                navController.navigate(Screen.DetailsScreen.route)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}



