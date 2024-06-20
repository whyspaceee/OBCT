package com.obcteam.obct.domain.utils

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.format.DateTimeFormatter

object OBCTFormatters {
    fun formatRelativeDateFromISO(isoString: String, context: Context? = null): String {
        val ta = DateTimeFormatter.ISO_INSTANT.parse(isoString)
        val i = Instant.from(ta)
        return DateUtils.getRelativeDateTimeString(
            context,
            i.toEpochMilli(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    }

    fun formatDMMMFromISO(isoString: String, context: Context? = null): String {
        val ta = DateTimeFormatter.ISO_INSTANT.parse(isoString)
        val i = Instant.from(ta)
        return DateUtils.formatDateTime(
            context,
            i.toEpochMilli(),
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH
        )
    }

    fun formatPredictionToString(prediction: Int): String {
        return when (prediction) {
            0 -> return "Insufficient Weight"
            1 -> return "Normal Weight"
            2 -> return "Overweight 1"
            3 -> return "Overweight 2"
            4 -> return "Obesity Level 1"
            5 -> return "Obesity Level 2"
            6 -> return "Obesity Level 3"
            else -> ""
        }
    }

    fun formatPredictionToColor(prediction: Int): Color {
        return when (prediction) {
            0 -> return Color(0xFF80D8FF)
            1 -> return Color(0xFFB9F6CA)
            2 -> return Color(0xFFF4FF81)
            3 -> return Color(0xFFFFE57F)
            4 -> return Color(0xFFFF9E80)
            5 -> return Color(0xFFFC6A5D)
            6 -> return Color(0xFFFF0000)
            else -> Color(0xFFFF0000)
        }
    }

}