package com.obcteam.obct.presentation.theme.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.ceil
import kotlin.random.Random

class WavyShape(
    private val period: Dp,
    private val amplitude: Dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val wavyPath = Path().apply {
                val halfPeriod = with(density) { period.toPx() } / 2
                val baseAmplitude = with(density) { amplitude.toPx() }
                val randomAmplitude = { baseAmplitude * (0.5f + Random.nextFloat() * 0.5f) }

                moveTo(x = -halfPeriod / 2, y = size.height)
                repeat(ceil(size.width / halfPeriod + 1).toInt()) { i ->
                    relativeQuadraticBezierTo(
                        dx1 = halfPeriod / 2,
                        dy1 = if (i % 2 == 0) randomAmplitude() else -randomAmplitude(),
                        dx2 = halfPeriod,
                        dy2 = 0f,
                    )
                }
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            val boundsPath = Path().apply {
                addRect(Rect(offset = Offset.Zero, size = size))
            }
            op(wavyPath, boundsPath, PathOperation.Union)
        })
    }
}
