package com.elish.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.elish.terminal.data.Bar

@Composable
fun Terminal(bars: List<Bar>) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        //ищем максимальную точку из всех максимальных значений
        val max = bars.maxOf { it.high }
        //ищем минимальную точку из всех минимальных значений
        val min = bars.minOf { it.low }

        //вычисляем ширину свечи относительно экрана
        val barWidth = size.width / bars.size
        //количество пикселей на один пункт
        val pxPerPoint = size.height / (max - min)

        bars.forEachIndexed() { index, bar ->
            val offsetX = index * barWidth
            drawLine(
                color = Color.White,
                start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                end = Offset(offsetX, size.height - ((bar.high - min) * pxPerPoint)),
                strokeWidth = 1f
            )
        }
    }
}