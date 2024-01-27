package com.roshanadke.weekwatch.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.weekwatch.common.BottomNavigationScreens
import com.roshanadke.weekwatch.presentation.viewmodels.PeopleViewModel

@Composable
fun PeopleListScreen(
    navController: NavController,
    viewModel: PeopleViewModel = hiltViewModel()
) {


    val peopleListUiState = viewModel.peopleListUiState

    LazyColumn {
        peopleListUiState.value.peopleListMainDto?.personDtoList?.let {
            items(it) {
                Text(text = it.name ?: "", Modifier.padding(12.dp))


            }
        }
    }


}