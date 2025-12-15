package com.example.newsapp.compose.screens.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.util.h
import com.example.newsapp.util.w

@Composable
fun ArticleItemView(
    article: ArticlesItem, modifier: Modifier = Modifier, onNewsClick: (ArticlesItem) -> Unit
) {
    return Column(
        modifier = modifier
            .clickable(onClick = { onNewsClick(article) })
            .width(60.w),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(article.urlToImage)
                .crossfade(true).build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(25.h)
                .clip(RoundedCornerShape(10.dp)),
            placeholder = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = modifier.padding(5.dp)
        ) {
            Text(
                text = article.title ?: "",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = article.author ?: "",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemViewPreview() {
    ArticleItemView(
        article = ArticlesItem(
            urlToImage = "https://media.wired.com/photos/6913b909f757bec53ccf7811/191:100/w_1280,c_limit/Bitcoin-Heist-Business-1304706668.jpg",
            title = "Test",
            author = "Joel Khalili"
        ),
        onNewsClick = {},
    )
}