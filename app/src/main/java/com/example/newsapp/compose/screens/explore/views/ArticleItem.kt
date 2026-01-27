package com.example.newsapp.compose.screens.explore.views

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.R
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.data.remote.dto.Source
import com.example.newsapp.util.DateTimeUtils
import com.example.newsapp.util.h
import com.example.newsapp.util.w

@Composable
fun ArticleItem(article: ArticlesItem, onClick: (ArticlesItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(article) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(1.h)
        ) {
            Text(
                article.title ?: "",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.w)
            ) {
                Text(
                    article.author ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    DateTimeUtils.formatNewsApiDate(article.publishedAt ?: ""),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(Modifier.width(5.w))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(article.urlToImage)
                .crossfade(true).build(),
            modifier = Modifier
                .width(25.w)
                .height(15.h)
                .clip(RoundedCornerShape(10.dp)),
            placeholder = painterResource(R.drawable.img_news_placeholder),
            error = painterResource(R.drawable.img_news_error),
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xF0000)
@Composable
fun ArticleItemPreview() {
    val mockSource = Source(id = "cnn", name = "CNN")

    val mockArticle = ArticlesItem(
        author = "Jane Doe, CNN",
        title = "Major Tech Breakthrough Changes the Industry Overnight",
        description = "In a stunning development, a new technology has emerged that promises to revolutionize everything we know. Experts are scrambling to understand the implications.",
        urlToImage = "https://picsum.photos/seed/picsum/400/200",
        publishedAt = "2024-09-15T10:30:00Z",
        source = mockSource,
        content = "The full content of the article goes here, providing more detail...",
        url = "https://www.cnn.com"
    )
    ArticleItem(article = mockArticle, onClick = {})
}