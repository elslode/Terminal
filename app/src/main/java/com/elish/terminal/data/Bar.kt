package com.elish.terminal.data

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Date

@Immutable
data class Bar(
   @SerializedName("o") val open: Double,
   @SerializedName("c") val close: Double,
   @SerializedName("h") val high: Double,
   @SerializedName("l") val low: Double,
   @SerializedName("t") val time: Long
) {
    val calendar: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@Bar.time)
            }
        }
}
