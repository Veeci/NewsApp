package com.example.newsapp.util

import android.util.Log
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateTimeUtils {
    fun formatNewsApiDate(isoDateTimeString: String?): String {
        if (isoDateTimeString.isNullOrBlank()) {
            return ""
        }

        return try {
            val offsetDateTime = OffsetDateTime.parse(isoDateTimeString)

            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault())

            offsetDateTime.format(formatter)
        } catch (e: Exception) {
            Log.e("DateTimeUtils", "formatNewsApiDate: Failed to parse date - $e")
            ""
        }
    }
}