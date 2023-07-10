package com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar

import android.annotation.SuppressLint
import android.widget.ProgressBar
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintSet.Transform
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.ProgressBarScope.progressBar
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.x9f8
import com.yunext.twins.compose.ui.theme.xf98
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Preview
@Composable
fun LayoutCase12Preview() {
    LayoutCase12()
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LayoutCase12() {
    D.i("∆∆∆∆ LayoutCase12 ")
    val max = 100f
    var progress by remember {
        mutableStateOf(50f)
    }

    var debug by remember {
        mutableStateOf(true)
    }

    var cutAngel by remember {
        mutableStateOf(CutAngel.Big)
    }
    var cutDirection by remember {
        mutableStateOf(CutDirection.Bottom)
    }
    var auto by remember {
        mutableStateOf(false)
    }
    val rememberCoroutineScope = rememberCoroutineScope()
    var job: Job? = null
    fun start() {
        job?.cancel()
        job = rememberCoroutineScope.launch {
            while (true) {
                delay(2000)
                progress = Random.nextInt(100).toFloat()
            }
        }
    }

    fun stop() {
        job?.cancel()
    }
    if (auto) {
        DisposableEffect(key1 = "auto") {
            start()
            onDispose {
                stop()
            }
        }
    } else {
        stop()
    }
    fun checkProgress(progress: Float): Float {
        return if (progress > max) {
            max
        } else if (progress < 0f) {
            0f
        } else {
            progress
        }
    }

    Column() {
        Button(onClick = {
            progress = checkProgress(progress + 1)

        }) {
            Text(text = "+")
        }

        Button(onClick = {
            progress = checkProgress(progress - 1)
        }) {
            Text(text = "-")
        }

        Button(onClick = {
            auto = !auto
        }) {
            Text(text = if (auto) "自动随机" else "停止自动")
        }
        Button(onClick = {
            cutAngel = CutAngel.random()
        }) {
            Text(text = "开口大小随机（0-180）")
        }
        Button(onClick = {
            cutDirection = CutDirection.random()
        }) {
            Text(text = "开口方向随机（0-180）")
        }

        Button(onClick = {
            debug = !debug
        }) {
            Text(text = if (debug) "调试开" else "调试关")
        }


        ProgressBarLayout(
            modifier = Modifier
                .border(1.dp, Color.Yellow)
                .fillMaxSize()
                .border(1.dp, x98f),
            progressBar = { pb, ma, dbug, cutA, cutD, ch, onThumbLastMoveStart, offset ->
                CircleProgressBar(
                    modifier = MDF.progressBar(progress = progress, max = 100f),
                    debug = dbug,
                    progress = pb,
                    max = ma,
                    cutAngel = cutA,
                    cutDirection = cutD,
                    onProgressChanged = ch,
                    onThumbLastMoveStart = onThumbLastMoveStart,
                    onThumbOffsetChanged = offset
                )

//            CircleProgressBar(
//                progress = progress,
//                cutAngel = cutAngel,
//                cutDirection = cutDirection,
//                onProgressChanged = {it,_->
//                    progress = it
//                })
            },
            thumb = {
                Text(
                    text = "$this hello", fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    style = TextStyle.Default,
                    textAlign = TextAlign.Center, modifier = MDF
                        .debugBorder()
                        .progressBar(this, max)
                )
            },
            label = {
                Text(
                    text = "$this",
                    fontSize = 48.sp,//((24 + progress).sp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    style = TextStyle.Default,
                    textAlign = TextAlign.Center,
                    modifier = MDF
                        .wrapContentSize()
//                    .fillMaxSize()
//                    .wrapContentHeight()
//                    .fillMaxWidth()
                        .border(2.dp, Color.Green)
                        .padding(16.dp)
                        .progressBar(progress, max)
                )
            },
            progress = progress,
            max = max,
            debug = debug,
            cutAngel = cutAngel,
            cutDirection = cutDirection
        )
    }
}

private fun Modifier.debugBorder() = this.border(1.dp, Color.Black)

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun ProgressBarLayout(
    modifier: Modifier = Modifier,
    // 进度
    progressBar: @Composable (
        progress: Float, max: Float, debug: Boolean, cutAngel: CutAngel, cutDirection: CutDirection,
        onProgressChanged: (Float,/*是否手动*/Boolean) -> Unit,
        onThumbLastMoveStart: () -> Unit,
        onThumbOffsetChanged: (Offset, Float) -> Unit,
    )
    -> Unit,
    // 滑块
    thumb: @Composable Float.() -> Unit,
    // 文字说明
    label: @Composable Float.() -> Unit,
    progress: Float,
    max: Float,
    debug: Boolean,
    cutAngel: CutAngel,
    cutDirection: CutDirection,
    onProgressChanged: (Float,/*是否手动*/Boolean) -> Unit = { _, _ -> },
) {

    var thumbOffset: Offset by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    var rotation by remember {
        mutableStateOf(false)
    }
    val updateTransition = updateTransition(targetState = rotation, label = "thumb")

    val thumbRotation by updateTransition.animateFloat(label = "thumb_rotation", transitionSpec = {
        tween(1000)
    }) {
        if (it) 0f else 360f
    }

    val labelScale by
    updateTransition.animateFloat(label = "label_size", transitionSpec = {
        spring(
            stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) 1f else 2f
    }

    val progressBarComposable: @Composable () -> Unit = @Composable {
        progressBar(
            progress,
            max,
            debug,
            cutAngel,
            cutDirection,
            onProgressChanged,
            onThumbLastMoveStart = {
                rotation = !rotation
            }
        ) { offset: Offset, ratio ->
            thumbOffset = offset
        }
    }

    val labelComposable: @Composable () -> Unit = @Composable {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .scale(labelScale)
                .wrapContentSize()
                .border(4.dp, Color.Magenta)
                .padding(16.dp)

        ) {
            progress.label()
        }
    }


    val thumbComposable: @Composable () -> Unit = @Composable {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier

                .rotate(thumbRotation)
//                .height(100.dp)
//                .width(60.dp)
                .wrapContentSize()
                .border(4.dp, Color.Magenta)
                .padding(16.dp)


        ) {
            Image(
                painter = painterResource(id = R.drawable.goldengate),
                contentDescription = "",
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            progress.thumb()
        }
    }

    Layout(modifier = modifier,
        contents = listOf(progressBarComposable, labelComposable, thumbComposable),
        measurePolicy = { (progressBarMeasurables, labelMeasurables, thumbMeasurables), constraints ->
            D.i("==== ProgressBarLayout start ====")
            val maxWidth = constraints.maxWidth
            val maxHeight = constraints.maxHeight
            val size = maxWidth.coerceAtMost(maxHeight)
            D.i("   maxWidth    = $maxWidth")
            D.i("   maxHeight   = $maxHeight")
            D.i("   size        = $size")
            D.i("1.measurable progressBar...")
            val progressBarPlaceables = progressBarMeasurables.map { progressBarMeasurable ->
                progressBarMeasurable.measure(
                    constraints.copy(
                        maxWidth = size, maxHeight = size, minWidth = size, minHeight = size
                    )
                )
            }
            val progressBarPlaceable = progressBarPlaceables.first()
            val totalWidth = progressBarPlaceable.width
            val totalHeight = progressBarPlaceable.height
            D.i("2.measurable label...")
            val labelPlaceables = labelMeasurables.map { labelMeasurable ->
                //val parentData = labelMeasurable.parentData as ProgressBarParentData
                //val ratio = 1f * parentData.progress / parentData.max
                //val sz = (totalWidth*ratio).toInt()
                val sz = totalWidth
                labelMeasurable.measure(
                    constraints.copy(
                        maxWidth = sz, maxHeight = sz, minHeight = 0, minWidth = 0
                    )
                )
            }
            val labelPlaceable = labelPlaceables.first()
            D.i("3.measurable thumb...")
            val thumbPlaceables = thumbMeasurables.map { thumbMeasurable ->
                thumbMeasurable.measure(
                    constraints.copy(
                        maxWidth = totalWidth, maxHeight = totalWidth, minHeight = 0, minWidth = 0
                    )
                )
            }
            val thumbPlaceable = thumbPlaceables.first()

            D.i("   progressBarPlaceable    [${progressBarPlaceable.width} , ${progressBarPlaceable.height}]")
            D.i("   labelPlaceable          [${labelPlaceable.width} , ${labelPlaceable.height}]")
            D.i("   thumbPlaceable          [${thumbPlaceable.width} , ${thumbPlaceable.height}]")

            layout(totalWidth, totalHeight) {
                D.i("1.place progressBar")
                progressBarPlaceable.placeRelative(0, 0)
                D.i("2.place label")
                val labelOffset = totalWidth / 2 - labelPlaceable.width / 2
                labelPlaceable.placeRelative(labelOffset, labelOffset)
                D.i("3.place thumb")
                // 如何摆放

//                thumbPlaceable.placeRelative(thumbOffset.x.toInt(),
//                    thumbOffset.y.toInt())

                thumbPlaceable.placeRelative(
                    thumbOffset.x.toInt() - thumbPlaceable.width / 2,
                    thumbOffset.y.toInt() - thumbPlaceable.height / 2
                )


                D.i("==== ProgressBarLayout end ====")

            }
        })
}


data class CutAngel(val angel: Float) {
    companion object {
        val Big = CutAngel(120f)
        val Small = CutAngel(60f)
        val Normal = CutAngel(90f)

        private val list = listOf(
            Big, Normal, Small
        )

        fun random() = (list + CutAngel(Random.nextInt(180).toFloat())).random()
    }
}

@Stable
data class CutDirection(val angel: Float) {
    companion object {
        val Left = CutDirection(180f)
        val Top = CutDirection(-90f)
        val Bottom = CutDirection(90f)
        val Right = CutDirection(0f)
        private val list = listOf(
            Left, Top, Bottom, Right
        )

        fun random() = (list + CutDirection(
            Random.nextInt(180).toFloat() * if (Random.nextBoolean()) {
                1
            } else -1
        )).random()
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalTextApi::class)
@Composable
private fun CircleProgressBar(
    modifier: Modifier = Modifier,
    debug: Boolean = true,
    backgroundColor: Color = Color.White,
    progress: Float,
    max: Float,
    cutAngel: CutAngel = CutAngel.Normal,
    cutDirection: CutDirection = CutDirection.Bottom,
    onProgressChanged: (Float, Boolean) -> Unit,
    onThumbOffsetChanged: (Offset, Float) -> Unit,
    onThumbLastMoveStart: () -> Unit,
) {

//    val lastProgress by produceState(initialValue = -1) {
//        delay(1000)
//        this.value = progress
//    }
    val rememberCoroutineScope = rememberCoroutineScope()
    var lastProgress by remember {
        mutableStateOf(-1f)
    }

    var job: Job? = null
    DisposableEffect(key1 = progress) {
        job = rememberCoroutineScope.launch {
//             D.e("lastProgress开始")
            delay(1000)
//             D.e("lastProgress开始 ok")
            onThumbLastMoveStart.invoke()
            lastProgress = progress

        }
        onDispose {
//            D.e("lastProgress结束")
            job?.cancel()
        }
    }

    val updateTransitionLast = updateTransition(targetState = lastProgress, label = "last_progress")
    val lastPb by updateTransitionLast.animateFloat(label = "last_progress_", transitionSpec = {
//        spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow,
//        )
        tween(1000)
    }) {
        it
    }


    val maxAngel = 360 - cutAngel.angel

    val updateTransition = updateTransition(targetState = progress, label = "_progress")
    val pb by updateTransition.animateFloat(label = "progress_", transitionSpec = {
//        spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow,
//        )
        tween(1000)
    }) {
        it
    }
    // 文字
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    val textCurrentPositionMeasurer: TextMeasurer = rememberTextMeasurer()


    fun progress2Angel(value: Float): Float {
        return maxAngel * value * 1f / max
    }

    val layoutPadding = 16.dp
    Spacer(modifier = modifier
        .border(3.dp, Color.Red)
        .background(Color.Black)
        .padding(layoutPadding)
        .background(Color.LightGray)
        .fillMaxWidth()
        .drawWithCache {

            // 画不大小
            val sizePx = this.size.width
            // 进度条
            val progressBarWidth = 32.dp.toPx()
            // progress和background的偏移值
            val progressBackgroundOffset = 0.dp.toPx()
            // thumb
            val thumbSize = progressBarWidth * 3
            // thumb 半径
            val thumbRadius = thumbSize / 2
            // 除去thumb剩下的位子半径
            val thumbXorRadius = sizePx / 2 - thumbRadius
            // background 半径 比上一个多半个progressBarWidth
            val backgroundRadius = thumbXorRadius + progressBarWidth / 2
            val backgroundSize = backgroundRadius * 2
            // 进度条大小比背景小progressBarWidth/2 不能超出范围。
            val progressBarRadius =
                backgroundRadius - progressBarWidth / 2 - progressBackgroundOffset
            val progressBarSize = 2 * progressBarRadius
            val progressBarSizeSize = Size(progressBarSize, progressBarSize)
            // 画笔style
            val dashPathEffect = PathEffect.dashPathEffect(
                floatArrayOf(2.dp.toPx(), 4.dp.toPx()), 10.dp.toPx()
            )
            val style =
                Stroke(width = progressBarWidth, join = StrokeJoin.Round, cap = StrokeCap.Round)
            val debugStyle = Stroke(
                width = 1.dp.toPx(), pathEffect = dashPathEffect
            )

            fun ContentDrawScope.drawDebug(block: ContentDrawScope.() -> Unit) {
                if (!debug) return
                block(this)
            }

            val drawDebug: ContentDrawScope.(
                topLeft: Offset,
                size: Size,
            ) -> Unit = { topLeft, size ->
                drawDebug {
                    drawRect(
                        brush = Brush.horizontalGradient(listOf(xf98, x98f)),
                        topLeft = topLeft,
                        size = size,
                        style = debugStyle
                    )
                }
            }

            val startAngel: Float = 0 + cutDirection.angel + cutAngel.angel / 2
            val sweepAngel: Float = progress2Angel(pb.toFloat())
            val degree = angel2degree(startAngel, sweepAngel)
            val textResult =
                textMeasurer.measure(AnnotatedString("$startAngel + $sweepAngel = ${startAngel + sweepAngel} / $degree"))

            onDrawWithContent {
                // [background]
                this.drawCircle(backgroundColor, backgroundRadius)

                // [progress]

                // background progress
                val topLeft: Offset = Offset(
                    thumbRadius + progressBackgroundOffset,
                    thumbRadius + progressBackgroundOffset
                )
                drawDebug {
                    drawText(
                        textResult, topLeft = Offset(4.dp.toPx(), 4.dp.toPx())
                    )
                }

                this.drawArc(
                    color = Color.Gray,
                    startAngle = startAngel,
                    sweepAngle = maxAngel,
                    useCenter = false,
                    style = style,
                    topLeft = topLeft,
                    size = progressBarSizeSize
                )
                // foreground progress
                this.drawArc(
                    brush = Brush.horizontalGradient(listOf(x98f, xf98, x9f8)),
                    startAngle = startAngel,
                    sweepAngle = sweepAngel,
                    useCenter = false,
                    style = style,
                    topLeft = topLeft,
                    size = progressBarSizeSize
                )
//                    this.drawArc(color = Color.Yellow, 90f, sweepAngle = 45f, true, style = style,
//                        topLeft = topLeft)

                // 背景的padding
                val backgroundPadding = sizePx / 2 - backgroundRadius
                // thumb
                val thumbCurrentPosition =
                    angel2Offset(thumbXorRadius, startAngel, sweepAngel).let { pre ->
                        pre.copy(
                            pre.x + thumbRadius, pre.y + thumbRadius
                        )
                    }

                drawCircle(
                    Color.White.copy(red = .9f), thumbRadius, center = thumbCurrentPosition
                )
                drawCircle(
                    Color.Red, thumbRadius / 3 * 1.5f, center = thumbCurrentPosition
                )
                drawDebug {
                    val textCurrentPositionResult =
                        textCurrentPositionMeasurer.measure(AnnotatedString("[${thumbCurrentPosition.x} , ${thumbCurrentPosition.y}]"))
                    drawText(textCurrentPositionResult, topLeft = thumbCurrentPosition)
                }

                val layoutPaddingPx = layoutPadding.toPx()
//                onThumbOffsetChanged(
//                    thumbCurrentPosition.copy(
//                        thumbCurrentPosition.x + layoutPaddingPx,
//                        thumbCurrentPosition.y + layoutPaddingPx,
//                    )
//                )
                drawDebug {
                    // thumb debug
                    drawCircle(
                        Color.Black,
                        thumbRadius,
                        center = Offset(sizePx / 2, thumbRadius),
                        style = debugStyle
                    )
                    drawCircle(
                        Color.Red,
                        thumbRadius / 3 * 1.5f,
                        center = Offset(sizePx / 2, thumbRadius),
                        style = debugStyle
                    )
                }


                // last
                if (lastProgress != -1f) {
                    D.e("lastProgress :$lastProgress")
                    val thumbLastPosition = angel2Offset(
                        thumbXorRadius, startAngel, progress2Angel(lastPb.toFloat())
                    ).let { pre ->
                        pre.copy(
                            pre.x + thumbRadius, pre.y + thumbRadius
                        )
                    }
                    drawCircle(
                        Color.Black, thumbRadius, center = thumbLastPosition, style = debugStyle
                    )
                    drawCircle(
                        Color.Red,
                        thumbRadius / 3 * 1.5f,
                        center = thumbLastPosition,
                        style = debugStyle
                    )
                    onThumbOffsetChanged(
                        thumbLastPosition.copy(
                            thumbLastPosition.x + layoutPaddingPx,
                            thumbLastPosition.y + layoutPaddingPx,
                        ), 1f
                    )


                } else {
                    onThumbOffsetChanged(
                        thumbCurrentPosition.copy(
                            thumbCurrentPosition.x + layoutPaddingPx,
                            thumbCurrentPosition.y + layoutPaddingPx,
                        ), 1f
                    )
                }

                drawDebug {


                    val lines = listOf(
                        Offset(backgroundPadding, backgroundPadding) to Offset(
                            sizePx - backgroundPadding, sizePx - backgroundPadding
                        ), Offset(sizePx - backgroundPadding, backgroundPadding) to Offset(
                            backgroundPadding, sizePx - backgroundPadding
                        ), Offset(backgroundPadding, sizePx / 2) to Offset(
                            sizePx - backgroundPadding, sizePx / 2
                        ), Offset(sizePx / 2, backgroundPadding) to Offset(
                            sizePx / 2, sizePx - backgroundPadding
                        )
                    )
                    lines.forEach { (lineStart, lineEnd) ->
                        drawLine(
                            color = Color.Black,
                            start = lineStart,
                            end = lineEnd,
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = dashPathEffect
                        )
                    }

                    this.drawDebug(
                        Offset(
                            backgroundPadding, backgroundPadding
                        ), Size(backgroundRadius * 2, backgroundRadius * 2)
                    )
                    drawCircle(Color.Black, radius = thumbXorRadius, style = debugStyle)
                    drawCircle(Color.Black, radius = 2.dp.toPx())
                    // 当前thumb位置辅助线


                    drawLine(
                        color = Color.Green,
                        start = thumbCurrentPosition,
                        end = center,
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = dashPathEffect
                    )
                    drawLine(
                        color = Color.Green, start = thumbCurrentPosition, end = Offset(
                            thumbCurrentPosition.x, sizePx / 2
                        ), strokeWidth = 1.dp.toPx(), pathEffect = dashPathEffect
                    )
                    drawLine(
                        color = Color.Green, start = thumbCurrentPosition, end = Offset(
                            sizePx / 2, thumbCurrentPosition.y
                        ), strokeWidth = 1.dp.toPx(), pathEffect = dashPathEffect
                    )
                }
            }
        })


}


// 180 / 1pi = 45/x
//  1/180 = x/45
// x = 1*45/180
private fun angel2degree(stareAngel: Float, sweepAngel: Float): Double {
    val all = abs(stareAngel + sweepAngel)
    val real = all % 360
    return Math.PI * real / 180
}

private fun angel2Offset(radius: Float, stareAngel: Float, sweepAngel: Float): Offset {
    val degree = angel2degree(stareAngel, sweepAngel)
    return when {
        degree >= 0f && degree < Math.PI / 2 -> {
            val currentThumbX = radius * cos(degree)
            val currentThumbY = radius * sin(degree)
            Offset(radius + currentThumbX.toFloat(), radius + currentThumbY.toFloat())
        }

        degree >= Math.PI / 2 && degree < Math.PI -> {
            val currentThumbX = radius * cos(Math.PI - degree)
            val currentThumbY = radius * sin(Math.PI - degree)
            Offset(radius - currentThumbX.toFloat(), radius + currentThumbY.toFloat())
        }

        degree >= Math.PI && degree < Math.PI + Math.PI / 2 -> {
            val currentThumbX = radius * cos(degree - Math.PI)
            val currentThumbY = radius * sin(degree - Math.PI)
            Offset(radius - currentThumbX.toFloat(), radius - currentThumbY.toFloat())
        }

        degree >= Math.PI + Math.PI / 2 && degree < 2 * Math.PI -> {
            val currentThumbX = radius * cos(2 * Math.PI - degree)
            val currentThumbY = radius * sin(2 * Math.PI - degree)
            Offset(radius + currentThumbX.toFloat(), radius - currentThumbY.toFloat())
        }

        else -> {
            throw IllegalStateException("angel2Offset fail $degree")
        }


    }
}
