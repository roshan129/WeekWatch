package com.roshanadke.weekwatch.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.Constants
import com.roshanadke.weekwatch.common.Screen
import com.roshanadke.weekwatch.common.UiEvent
import com.roshanadke.weekwatch.presentation.components.TrendingItemCard
import com.roshanadke.weekwatch.presentation.viewmodels.TrendingShowViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TrendingShowScreen(
    navController: NavController,
    viewModel: TrendingShowViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val controller = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val trendingListState = viewModel.trendingItemListState.value
    val searchListState = viewModel.searchListState.value
    val searchQuery = viewModel.searchQuery.collectAsState()
    var inSearchMode by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldShowErrorText by rememberSaveable {
        mutableStateOf(false)
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            shouldShowErrorText = true
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    if (inSearchMode) {
                        BasicTextField(
                            value = searchQuery.value, onValueChange = {
                                viewModel.setSearchQuery(it)
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .padding(start = 12.dp)
                                .fillMaxWidth()
                                .drawBehind {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                        )
                    } else {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        inSearchMode = !inSearchMode
                        if (!inSearchMode) {
                            controller?.hide()
                            viewModel.setSearchQuery("")
                        } else {
                            scope.launch {
                                delay(500)
                                focusRequester.requestFocus()
                            }
                        }

                    }) {
                        Icon(
                            imageVector = if (inSearchMode) Icons.Outlined.Clear else Icons.Outlined.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )

        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        val mainList =
            if (searchQuery.value.isNotEmpty()) searchListState.list else trendingListState.list

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),

                ) {
                items(mainList) { item ->
                    TrendingItemCard(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        imageEndpoint = item.posterPath,
                        showName = item.title ?: item.name,
                        onCardClicked = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = Constants.TRENDING_ITEM,
                                value = item
                            )
                            navController.navigate(Screen.DetailsScreen.route)
                        },
                        isFavourite = item.isFavourite
                    )
                }
            }
        }

        if (trendingListState.isLoading || searchListState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.Yellow)
            }
        }

        if (shouldShowErrorText && mainList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.no_data_found), color = Color.Gray)
            }
        }

    }

}