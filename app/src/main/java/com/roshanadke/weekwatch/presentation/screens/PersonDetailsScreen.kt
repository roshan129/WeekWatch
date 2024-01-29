package com.roshanadke.weekwatch.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.roshanadke.weekwatch.common.UiEvent
import com.roshanadke.weekwatch.common.getPosterImageUrl
import com.roshanadke.weekwatch.domain.models.people.PersonModel
import com.roshanadke.weekwatch.presentation.components.TrendingItemCard
import com.roshanadke.weekwatch.presentation.viewmodels.PersonDetailsViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreen(
    navController: NavController,
    personModel: PersonModel?,
    viewModel: PersonDetailsViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    val personDetailsState = viewModel.personDetailsState.value

    val profileImageUrl = getPosterImageUrl(personModel?.profilePath)
    val halfScreenWidth = LocalConfiguration.current.screenWidthDp / 2


    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        personModel?.id?.let {
            viewModel.getPersonDetails(it)
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = personModel?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(start = 12.dp, end = 12.dp)
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        placeholder = painterResource(id = R.drawable.loading_placeholder),
                        model = profileImageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "${personDetailsState?.personDetails?.biography}")
                Spacer(modifier = Modifier.height(12.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Known For",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    personModel?.knownForDtoList?.let {
                        items(it) { item ->
                            TrendingItemCard(
                                modifier = Modifier
                                    .width(halfScreenWidth.dp)
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                imageEndpoint = item.posterPath,
                                showName = item.title ?: item.name,
                                onCardClicked = {
                                    //do nothing
                                }
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

            }

            if (personDetailsState?.isLoading == true) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }



        }

    }


}