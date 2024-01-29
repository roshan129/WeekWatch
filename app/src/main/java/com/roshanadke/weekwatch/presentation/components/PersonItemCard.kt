package com.roshanadke.weekwatch.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.Constants
import com.roshanadke.weekwatch.common.getPosterImageUrl
import com.roshanadke.weekwatch.data.network.TrendingShowApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonItemCard(
    modifier: Modifier,
    imageEndpoint: String?,
    personName: String?,
    onCardClicked: () -> Unit,
) {


    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onCardClicked
    ) {

        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {

                AsyncImage(
                    placeholder = painterResource(id = R.drawable.loading_placeholder),
                    model = getPosterImageUrl(imageEndpoint),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )

                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = personName ?: "",
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

    }


}