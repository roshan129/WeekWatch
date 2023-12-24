package com.roshanadke.weekwatch.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.weekwatch.common.Screen
import com.roshanadke.weekwatch.presentation.components.TrendingItemCard
import com.roshanadke.weekwatch.presentation.viewmodels.TrendingShowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingShowScreen(
    navController: NavController,
    viewModel: TrendingShowViewModel = hiltViewModel(),
) {

    val trendingListState = viewModel.trendingItemListState.value

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "WeekWatch")
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),

                ) {
                items(trendingListState.list) { item ->
                    TrendingItemCard(
                        imageEndpoint = item.poster_path,
                        showName = item.title ?: item.name,
                        onCardClicked = {
                            navController.navigate(Screen.DetailsScreen.route)
                        }
                    )
                }
            }
        }

        if (trendingListState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.Yellow)
            }
        }


    }


}