package com.example.newsapp.compose.screens.articleDetail.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(saved: Boolean, onSaveArticle: () -> Unit, onShareArticle: () -> Unit) {
    BottomAppBar(
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
        modifier = Modifier.clip(AbsoluteRoundedCornerShape(topLeft = 40.dp, topRight = 40.dp)),
        actions = {
            Spacer(Modifier.weight(1f))
            IconToggleButton(
                checked = saved,
                onCheckedChange = { _ -> onSaveArticle() }
            ) {
                Icon(
                    imageVector = if (saved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Save Article",
                    tint = if (saved) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary
                )
            }
            IconButton(onClick = onShareArticle) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share Article",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
    )
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(true, onSaveArticle = {}, onShareArticle = {})
}
