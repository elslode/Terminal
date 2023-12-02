package com.elish.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import com.elish.terminal.data.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(bars: List<Bar>) {

    var visibleBarCount by remember {
        mutableStateOf(100)
    }

    var barWidth by remember {
        mutableStateOf(0f)
    }

    var scrolledBar by remember {
        mutableStateOf(0f)
    }

    var terminalWidth by remember {
        mutableStateOf(0f)
    }

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        visibleBarCount = (visibleBarCount / zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        scrolledBar = (scrolledBar + panChange.x)
            .coerceAtLeast(0f)
            .coerceAtMost(bars.size * barWidth - terminalWidth)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .transformable(transformableState)
    ) {
        terminalWidth = size.width
        //ищем максимальную точку из всех максимальных значений
        val max = bars.maxOf { it.high }
        //ищем минимальную точку из всех минимальных значений
        val min = bars.minOf { it.low }

        //вычисляем ширину свечи относительно экрана
        barWidth = size.width / visibleBarCount

        //количество пикселей на один пункт
        val pxPerPoint = size.height / (max - min)

        translate(left = scrolledBar) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - (index * barWidth)
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.high - min) * pxPerPoint)),
                    strokeWidth = 1f
                )
                drawLine(
                    color = if (bar.open < bar.close) Color.Green else Color.Red,
                    start = Offset(offsetX, size.height - ((bar.open - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.close - min) * pxPerPoint)),
                    strokeWidth = barWidth / 2
                )
            }
        }
    }
}