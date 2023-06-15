package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer

import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.xf98

@Composable
fun LayoutCase08() {
    D.i("∆∆∆∆ LayoutCase08 ")
    val rememberScrollState = rememberScrollState()
    var expend by remember {
        mutableStateOf(false)
    }

    var index: Int by remember {
        mutableStateOf(100)
    }

    val transition = updateTransition(targetState = expend, "switch")

    var blendMode1 by remember {
        mutableStateOf(BlendMode.SrcOver)
    }

    var blendMode2 by remember {
        mutableStateOf(BlendMode.SrcOver)
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState)
            .fillMaxSize()
            .padding(16.dp)
            .border(1.dp, Color.Red)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                expend = !expend
            }) {
                Text(text = "切换")
            }

            Button(onClick = {
                if (index < 100) {
                    index += 1
                }
            }) {
                Text(text = "加")
            }
            Text(text = "$index%")

            Button(onClick = {
                if (index > 1) {
                    index -= 1
                }
            }) {
                Text(text = "减")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TestDraw(transition, index / 100f, blendMode1, blendMode2)

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            MDF
                .fillMaxWidth()
                .height(150.dp)
        ) {
            AllBlendModeList(
                modifier = Modifier
                    .weight(1f)
                    .background(x98f)
            ) {
                blendMode1 = this
            }
            AllBlendModeList(
                modifier = Modifier
                    .weight(1f)
                    .background(xf98)
            ) {
                blendMode2 = this
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TestDraw(
    transition: Transition<Boolean>, ratio: Float,
    blendMode1: BlendMode,
    blendMode2: BlendMode,
) {
    val space by transition.animateDp(label = "space", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness =
            Spring.StiffnessLow
        )
    }) {
        if (it) 10.dp else 40.dp
    }

    val sweep by transition.animateFloat(label = "space", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness =
            Spring.StiffnessLow
        )
    }) {
        if (it) 10f else 220f
    }

    val rememberTextMeasurer1 = rememberTextMeasurer()
    val rememberTextMeasurer2 = rememberTextMeasurer()
    val rememberTextMeasurer3 = rememberTextMeasurer()

    Spacer(modifier = MDF
        .background(Color.LightGray)
        .padding(16.dp)
        .drawWithCache {
            onDrawBehind {
                val width = this.size.width
                val height = this.size.height
                val spacePx = space.toPx() * ratio
                val columns = (height / spacePx).toInt()
                val rows = (width / spacePx).toInt()
                (0..columns).forEach { index ->
                    val yOffset = index * spacePx
                    drawLine(
                        brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red)),
                        start = Offset(0f, yOffset),
                        end = Offset(width, yOffset)
                    )
                }
                drawLine(
                    brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red)),
                    start = Offset(0f, height),
                    end = Offset(width, height)
                )

                (0..rows).forEach { index ->
                    val xOffset = index * spacePx
                    drawLine(
                        brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red)),
                        start = Offset(xOffset, 0f),
                        end = Offset(xOffset, height)
                    )
                }

                drawLine(
                    brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red)),
                    start = Offset(width, 0f),
                    end = Offset(width, height)
                )

                drawRoundRect(
                    Brush.horizontalGradient(listOf(xf98, x98f)),
                    size = Size(width / 2, height / 2),
                    cornerRadius = CornerRadius(spacePx), topLeft = Offset(spacePx, spacePx),
                    colorFilter = ColorFilter.tint(Color.Blue),
                    blendMode = blendMode1
                )
                val a = sweep
                drawArc(

                    brush = Brush.horizontalGradient(listOf(xf98, x98f)),
                    startAngle = 0f,
                    sweepAngle = a,
                    useCenter = true,
                    style = Fill,
//                    style = Stroke(2f),
                    size = Size(height / 2, height / 2),
                    topLeft = Offset(width / 2 - spacePx, height / 2 - spacePx),
                    colorFilter = ColorFilter.tint(Color.Red),
                    blendMode = blendMode2
                )

                drawArc(

                    brush = Brush.horizontalGradient(listOf(xf98, x98f)),
                    startAngle = a,
                    sweepAngle = 360f - a,
                    useCenter = true,
                    style = Fill,
//                    style = Stroke(2f),
                    size = Size(height / 2, height / 2),
                    topLeft = Offset(width / 2 - spacePx, height / 2 - spacePx),
                    colorFilter = ColorFilter.tint(Color.Green),
                    blendMode = blendMode2
                )

                drawRect(color = xf98, size = Size(spacePx, spacePx))

                drawCircle(
                    Color.Cyan,
                    radius = spacePx,
                    center = Offset(width - spacePx * 1, height - spacePx * 2)
                )

                val path = Path()
                path.moveTo(spacePx, height - spacePx)
                path.addRoundRect(RoundRect(Rect(Offset(spacePx, height - spacePx), spacePx)))
                path.cubicTo(
                    spacePx,
                    height - spacePx,
                    height - spacePx,
                    width - spacePx,
                    height / 2,
                    width / 2
                )
                path.addOval(Rect(Offset(height - spacePx, width - spacePx), spacePx))
//               onDrawBehind {
                clipRect {
                    drawPath(path = path, color = Color.Yellow, style = Stroke(10f))
                }
//               }

                val p1 = 0f to 0f
                val p3 = width to height
                val p2 = 2 * spacePx to 3 * spacePx
                drawPoints(
                    listOf(
                        Offset(p1.first, p1.second),
                        Offset(p3.first, p3.second),
                        Offset(p2.first, p2.second),
                    ), pointMode = PointMode.Points, Color.Cyan, strokeWidth = 20f
                )

                drawText(
                    rememberTextMeasurer1.measure("[${p1.first},${p1.second}]"),
                    topLeft = Offset(p1.first, p1.second) + Offset(2f, 2f),
                    color = Color.Yellow
                )
                drawText(
                    rememberTextMeasurer2.measure("[${p2.first},${p2.second}]"),
                    topLeft = Offset(p2.first, p2.second) + Offset(2f, 2f),
                    color = Color.Yellow
                )
                drawText(
                    rememberTextMeasurer3.measure("[${p3.first},${p3.second}]"),
                    topLeft = Offset(p3.first, p3.second) + Offset(2f, 2f),
                    color = Color.Yellow
                )
                val path2 = Path()
                path2.cubicTo(p1.first, p1.second, p2.first, p2.second, p3.first, p3.second)
                drawPath(path = path2, color = Color.Black, style = Stroke(10f))
                val path3 = Path()
                val w1 = Offset(spacePx, height)
                val w2 = Offset(width - spacePx, height)
                val w3 = Offset(spacePx * 2, height / 2)
                val w4 = Offset(width - spacePx * 2, height / 2)
                val w5 = Offset(width / 2, 0f)
                path3.moveTo(w1.x, w1.y)



                path3.lineTo(w5.x, w5.y)
                path3.lineTo(w2.x, w2.y)
                path3.lineTo(w3.x, w3.y)
                path3.lineTo(w4.x, w4.y)
                path3.lineTo(w1.x, w1.y)

                drawPath(
                    path = path3, color = Color.Gray, style = Stroke(
                        5f, pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(1f, 2f), 2f
                        )
                    )
                )

                val path4 = Path().apply {
                    this.moveTo(w1.x,w1.y)
                    this.cubicTo(w1.x, w1.y,center.x,center.y, w5.x, w5.y)
                    this.cubicTo(w5.x, w5.y, center.x,center.y, w2.x, w2.y)
                    this.cubicTo(w2.x, w2.y,center.x,center.y, w3.x, w3.y)
                    this.cubicTo(w3.x, w3.y, center.x,center.y,w4.x, w4.y)
                    this.cubicTo(w4.x, w4.y, center.x,center.y,w1.x, w1.y)
                }
                drawPath(
                    path = path4, color = Color.Magenta, style = Stroke(
                        5f
                    )
                )



            }
        }
        .drawBehind {
            drawCircle(Color.Green, 100f, center.copy(x = size.width - 100f))
        }
        .drawWithContent {
            drawCircle(Color.Yellow, 100f, center.copy(x = size.width - 100f, y = 100f))
        }
        .height(200.dp)
        .fillMaxWidth())
}

