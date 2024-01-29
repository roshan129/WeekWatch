package com.roshanadke.weekwatch.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.weekwatch.common.Constants
import com.roshanadke.weekwatch.common.Screen
import com.roshanadke.weekwatch.presentation.components.PersonItemCard
import com.roshanadke.weekwatch.presentation.viewmodels.PeopleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreen(
    navController: NavController,
    viewModel: PeopleViewModel = hiltViewModel()
) {


    val peopleListUiState = viewModel.peopleListUiState

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Popular People")
            })
        }
    ) {
        Box(Modifier.padding(it)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),

                ) {
                peopleListUiState.value.peopleListMainDto?.personDtoList?.let {
                    items(it) {
                        Text(text = it.name ?: "", Modifier.padding(12.dp))

                        PersonItemCard(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            imageEndpoint = it.profilePath,
                            personName = it.name,
                            onCardClicked = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = Constants.PERSON_ITEM,
                                    value = it
                                )
                                navController.navigate(Screen.PeopleDetailsScreen.route)
                            }
                        )

                    }
                }
            }

        }
    }




}