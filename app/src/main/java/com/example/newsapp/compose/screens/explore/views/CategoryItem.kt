package com.example.newsapp.compose.screens.explore.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.util.Category
import com.example.newsapp.util.StringUtils
import com.example.newsapp.util.h
import com.example.newsapp.util.w

@Composable
fun CategoryItem(category: Category, isSelecting: Boolean? = false, onClick: (Category) -> Unit) {
    return Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(category) }
            .background(
                color = if (isSelecting == true) MaterialTheme.colorScheme.secondary else Color.Transparent
            )
            .border(
                width = if (isSelecting == false) 0.5.dp else 0.dp,
                shape = RoundedCornerShape(20.dp),
                color = if (isSelecting == false) MaterialTheme.colorScheme.secondary else Color.Transparent
            )
            .padding(horizontal = 4.w, vertical = 1.h)
    ) {
        Text(
            StringUtils.uppercaseFirstLetter(category.name),
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    CategoryItem(isSelecting = false, category = Category.business) {}
}