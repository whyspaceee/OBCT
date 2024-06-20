import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import kotlin.math.abs
import kotlin.math.roundToInt
import androidx.compose.ui.graphics.Color as ComposeColor

sealed class DegreeLineType {
    data object TenTypeLine : DegreeLineType()
    object FiveTypeLine : DegreeLineType()
    object NormalTypeLine : DegreeLineType()
}

data class PickerStyle(
    var modifier: Modifier = Modifier,
    var pickerWidth: Dp = 150.dp,
    var minValue: Int = 200,
    var maxValue: Int = 300,
    var initialValue: Int = 250,
    val backgroundColor: ComposeColor = ComposeColor.White,
    var normalTypeLineColor: Int = Color.LTGRAY,
    var tenTypeLineColor: Int = Color.BLACK,
    var fiveTypeLineColor: Int = Color.RED,
    var normalTypeLineHeight: Int = 28,
    var tenTypeLineHeight: Int = 50,
    var fiveTypeLineHeight: Int = 38,
    var spaceInterval: Int = 36,
    var numberPadding: Int = 28,
    var lineStroke: Float = 6f
)

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    pickerStyle: PickerStyle,
    onHeightChange: (Int) -> Unit = {}
) {

    var targetDistant by remember {
        mutableStateOf(0f)
    }

    var startDragPoint by remember {
        mutableStateOf(0f)
    }

    var oldDragPoint by remember {
        mutableStateOf(0f)
    }

    var selectedHeight by remember {
        mutableStateOf(0)
    }

    BoxWithConstraints(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.TopCenter)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        startDragPoint = it.x
                    },
                    onDragEnd = {
                        oldDragPoint = targetDistant
                    }
                ) { change, _ ->
                    val newDistance = oldDragPoint + (change.position.x - startDragPoint)
                    targetDistant = newDistance.coerceIn(
                        minimumValue = ((pickerStyle.initialValue) * pickerStyle.spaceInterval - pickerStyle.maxValue * pickerStyle.spaceInterval).toFloat(),
                        maximumValue = ((pickerStyle.initialValue) * pickerStyle.spaceInterval - pickerStyle.minValue * pickerStyle.spaceInterval).toFloat()
                    )
                }
            }
        ) {

            val middlePoint = Offset(x = maxWidth.toPx() / 2f, y = maxHeight.toPx() / 2f)

            drawContext.canvas.nativeCanvas.apply {
                drawRect(
                    0f,
                    0f,
                    constraints.maxWidth.toFloat() / 2,
                    constraints.maxHeight.toFloat(),
                    Paint().apply {
                        this.color = pickerStyle.backgroundColor.copy(alpha = 0.5f).toArgb()
                        this.style = Paint.Style.FILL
                    })

                drawRect(
                    constraints.maxWidth.toFloat() / 2,
                    0f,
                    constraints.maxWidth.toFloat(),
                    constraints.maxHeight.toFloat(),
                    Paint().apply {
                        this.color = pickerStyle.backgroundColor.copy(alpha = 0.25f).toArgb()
                        this.style = Paint.Style.FILL
                    })

                val roundedPolygon = RoundedPolygon(
                    numVertices = 3,
                    radius = size.minDimension /  5,
                    centerX = constraints.maxWidth.toFloat() / 4,
                    centerY = constraints.maxHeight.toFloat() / 2,
                    rounding = CornerRounding(
                        size.minDimension / 24f,
                        smoothing = 0.1f
                    )
                )
                val trianglePath = roundedPolygon.toPath().asComposePath()
                rotate(270f) {
                    drawPath(trianglePath, color = ComposeColor.White)
                }

//
//                drawPath(indicator, Paint().apply {
//                    this.color = Color.RED
//                    this.style = Paint.Style.FILL_AND_STROKE
//                    this.strokeWidth = pickerStyle.lineStroke
//                    this.isAntiAlias = true
//                })
//
//                drawLine(0f, middlePoint.y, constraints.maxWidth.toFloat(), middlePoint.y, Paint().apply {
//                    this.color = pickerStyle.normalTypeLineColor
//                    this.style = Paint.Style.STROKE
//                    this.strokeWidth = pickerStyle.lineStroke
//                    this.isAntiAlias = true
//                })

                for (height in pickerStyle.minValue..pickerStyle.maxValue) {
                    val degreeLineScaleX =
                        middlePoint.x + (pickerStyle.spaceInterval * (height - pickerStyle.initialValue.toFloat()) + targetDistant)

                    if (abs(middlePoint.x - degreeLineScaleX.roundToInt()) < 10) {
                        selectedHeight = height
                        onHeightChange(selectedHeight)
                    }

                    val lineType = when {
                        height % 10 == 0 -> DegreeLineType.TenTypeLine
                        height % 5 == 0 -> DegreeLineType.FiveTypeLine
                        else -> DegreeLineType.NormalTypeLine
                    }

                    val lineHeightSize = when (lineType) {
                        DegreeLineType.TenTypeLine -> pickerStyle.tenTypeLineHeight
                        DegreeLineType.FiveTypeLine -> pickerStyle.fiveTypeLineHeight
                        else -> pickerStyle.normalTypeLineHeight
                    }

                    if (lineType == DegreeLineType.NormalTypeLine) {
                        continue
                    }

                    this.drawLine(degreeLineScaleX,
                        middlePoint.y + lineHeightSize + 16,
                        degreeLineScaleX,
                        middlePoint.y - lineHeightSize + 16,
                        Paint().apply {
                            this.strokeCap = Paint.Cap.ROUND
                            this.style = Paint.Style.STROKE
                            this.strokeWidth = pickerStyle.lineStroke
                            this.color = pickerStyle.normalTypeLineColor
                            this.isAntiAlias = true
                        }
                    )


                    if (lineType == DegreeLineType.TenTypeLine) {
                        val textBound = Rect()
                        Paint().getTextBounds(
                            abs(height).toString(),
                            0,
                            height.toString().length,
                            textBound
                        )

                        drawText(
                            abs(height).toString(),
                            (degreeLineScaleX) - textBound.width() / 2,
                            middlePoint.y - lineHeightSize - pickerStyle.numberPadding,
                            Paint().apply {
                                this.textSize = 20.sp.toPx()
                                this.textAlign = Paint.Align.CENTER
                                this.color = pickerStyle.normalTypeLineColor
                                this.style = Paint.Style.FILL
                                this.isAntiAlias = true
                            }
                        )
                    }
                }
            }
        }
    }
}