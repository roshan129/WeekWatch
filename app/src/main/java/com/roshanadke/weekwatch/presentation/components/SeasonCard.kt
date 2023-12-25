package com.roshanadke.weekwatch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SeasonCard(
    modifier: Modifier,
    seasonNo: Int?,
    episodes: Int?
) {
    Card(
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Season $seasonNo")
            Text(text = "$episodes Episodes")

        }
    }
}