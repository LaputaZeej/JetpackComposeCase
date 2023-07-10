package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.jetlagged.JetLaggedHeader
import com.yunext.twins.compose.ui.debug.cases.jetlagged.JetLaggedHeaderTabs
import com.yunext.twins.compose.ui.debug.cases.jetlagged.JetLaggedSleepSummary
import com.yunext.twins.compose.ui.debug.cases.jetlagged.SleepTab
import com.yunext.twins.compose.ui.debug.cases.jetlagged.ui.theme.SmallHeadingStyle
import com.yunext.twins.compose.ui.debug.cases.jetlagged.ui.theme.Yellow
import com.yunext.twins.compose.ui.debug.cases.jetlagged.ui.theme.YellowVariant
import com.yunext.twins.compose.ui.debug.cases.jetlagged.yellowBackground
import com.yunext.twins.compose.ui.debug.data.SleepDayData
import com.yunext.twins.compose.ui.debug.data.SleepGraphData
import com.yunext.twins.compose.ui.debug.data.SleepPeriod
import com.yunext.twins.compose.ui.debug.data.SleepType
import com.yunext.twins.compose.ui.debug.data.sleepData
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt

private fun MDF.xMdf(debug: Boolean) = if (debug) this then border(1.dp, Color.Red) else this
private fun MDF.yMdf(debug: Boolean) = if (debug) this then border(1.dp, Color.Blue) else this
private fun MDF.dataMdf(debug: Boolean) = if (debug) this then border(1.dp, Color.Black) else this

@Composable
fun LayoutCase07() {
    D.i("∆∆∆∆ LayoutCase07 ")
    // 测试模式
    var debug by remember {
        mutableStateOf(false)
    }

    // Text(text = "12", modifier = Modifier.timeGraphBar(LocalDateTime.now(),LocalDateTime.now(), listOf()))

    // 睡眠数据
    var sleepGraphData by remember {
        mutableStateOf(sleepData)
    }

    // 横坐标 时间点 .. 22 23 0 1 2 3 4 5 6 7..
    val hours = (sleepGraphData.earliestStartHour..23) + (0..sleepGraphData.latestEndHour)
    // 纵坐标 天/星期 周一 周二 周三 周四 周五 周六 周日
    val column = sleepGraphData.sleepDayData.size

    // 滑动状态
    val rememberScrollState = rememberScrollState()
    val rememberTimeGraphScrollState = rememberScrollState()
    val testDataScrollState = rememberScrollState()

    // 主体
    Column(
        modifier = MDF
            .fillMaxSize()
            .verticalScroll(rememberScrollState)
    ) {
        // 背景
        Column(modifier = Modifier.yellowBackground()) {
            JetLaggedHeader(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(32.dp))
            JetLaggedSleepSummary(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
        Spacer(modifier = MDF.height(16.dp))
        // 选择框
        var selectedTab by remember { mutableStateOf(SleepTab.Week) }
        JetLaggedHeaderTabs(
            onTabSelected = { selectedTab = it },
            selectedTab = selectedTab,
        )
        Spacer(modifier = MDF.height(16.dp))
        // 图表
        TimeGraph(
            modifier = MDF
                .horizontalScroll(rememberTimeGraphScrollState) // 横 滑动
                .wrapContentSize()
                .background(Color.Green.copy(0.2f)),
            // 横坐标
            hoursHeader = {
                HourHeader(hours, debug)
                //Text(text = "123")
            },
            rowCount = column,
            // 纵坐标
            dayLabel = { index ->
                DayLabel(sleepGraphData.sleepDayData[index].startDate.dayOfWeek, debug)
            },
            // 数据
            sleepBar = { index ->
                val data = sleepGraphData.sleepDayData[index]
                SleepBar(
                    sleepDayData = data,
                    debug = debug,
                    //modifier = Modifier.timeGraphSleepBar(offset, 1 - offset)
                    modifier = Modifier
                        .dataMdf(debug)
                        .padding(bottom = 8.dp)
                        .timeGraphBar(data.firstSleepStart, data.lastSleepEnd, hours)
                )
            }
        )


        // 说明


        Row(MDF.background(Color.LightGray)) {


            Button(onClick = {
                val list = sleepGraphData.sleepDayData
                sleepGraphData = SleepGraphData(list.shuffled())
            }) {
                Text(text = "打乱数据")
            }

            Button(onClick = {
                debug = !debug
            }) {
                Text(text = "debug")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(MDF.background(Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "整个Composable:")
            Box(
                modifier = Modifier
                    .background(Color.Green.copy(.2f))
                    .size(50.dp, 24.dp)
            ) {

            }

        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            MDF
                .background(Color.LightGray)
                .fillMaxWidth()
                .horizontalScroll(testDataScrollState)
        ) {
            Text(text = "数据部分:")
            val data = sleepData.sleepDayData.random()
            val content: @Composable TimeGraphScope.() -> Unit = {
                SleepBar(
                    data,
                    debug = debug,
                    modifier = Modifier
                        .dataMdf(debug)
                        .padding(bottom = 8.dp)
                        .timeGraphBar(data.firstSleepStart, data.lastSleepEnd, hours)
                        .width(240.dp)
                )
            }
            content(TimeGraphScope)


        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(MDF.background(Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "x坐标:")
            Text(
                text = "${hours.random()}",
                textAlign = TextAlign.Center,
                modifier = MDF
                    .xMdf(debug)
                    .padding(bottom = 16.dp)
                    .drawBehind {
                        val brush = Brush.linearGradient(listOf(YellowVariant, Yellow))
                        drawRoundRect(
                            brush = brush,
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                        )
                    }
                    .width(50.dp)
                    .padding(vertical = 4.dp),
                style = SmallHeadingStyle
            )

        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(MDF.background(Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "y坐标:")
            DayLabel(sleepGraphData.sleepDayData.random().startDate.dayOfWeek, debug)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Drawing in Compose")
        Text(text = "   Modifier.drawBehind")
        Text(text = "       Draws bebind the Composables content", fontWeight = FontWeight.Light)
        Text(text = "   Modifier.drawWithContent")
        Text(text = "       Can specify the order of draw calls", fontWeight = FontWeight.Light)
        Text(text = "   Modifier.drawWithCache")
        Text(
            text = "       Drawn content is cached until size changes or state variables read inside change",
            fontWeight = FontWeight.Light
        )
        Text(text = "   Canvas()")
        Text(
            text = "       Convenient wrapper around Modifier.drawBehind{}",
            fontWeight = FontWeight.Light
        )
        Text(text = "   Modifier.graphicsLayer")
        Text(
            text = "       Applies effects to a Composable.Such as rotationZ,ScaleX ,etc.",
            fontWeight = FontWeight.Light
        )


    }
}

private class TimeGraphParentData3(
    /* need to measure */
    val duration: Float,
    /* need to place */
    val offset: Float,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any? {
        return this@TimeGraphParentData3
    }
}

// 版本3
@LayoutScopeMarker
@Immutable
object TimeGraphScope {

    @Stable
    fun Modifier.timeGraphBar(
        start: LocalDateTime,
        end: LocalDateTime,
        hours: List<Int>,
    ): Modifier {
        val earliestTime = LocalTime.of(hours.first(), 0) // 第一个睡眠时段
        val durationInHours = ChronoUnit.MINUTES.between(start, end) / 60f // 睡眠总的小时=总的分钟/60f 单位小时
        val durationFromEarliestToStartInHours =
            ChronoUnit.MINUTES.between(
                earliestTime,
                start.toLocalTime()
            ) / 60f // 第一个睡眠时段 到 睡眠开始时间 的小时
        val offsetInHours = durationFromEarliestToStartInHours + 0.5f // 消除误差
        return this then TimeGraphParentData3(
            duration = durationInHours / hours.size,
            offset = offsetInHours / hours.size
        )
    }
}

//// 版本2 污染MDF
//@Stable
//@Deprecated("use TimeGraphScope ", ReplaceWith("timeGraphBar(start,end,hours)"))
//private fun MDF.timeGraphBar(start: LocalDateTime, end: LocalDateTime, hours: List<Int>): MDF {
//    val earliestTime = LocalTime.of(hours.first(), 0) // 第一个睡眠时段
//    val durationInHours = ChronoUnit.MINUTES.between(start, end) / 60f // 睡眠总的小时=总的分钟/60f 单位小时
//    val durationFromEarliestToStartInHours =
//        ChronoUnit.MINUTES.between(earliestTime, start.toLocalTime()) / 60f // 第一个睡眠时段 到 睡眠开始时间 的小时
//    val offsetInHours = durationFromEarliestToStartInHours + 0.5f // 消除误差
//    return this.timeGraphSleepBar(
//        duration = durationInHours / hours.size, // 睡眠持续总时间 单位小时 / 总的小时数 = 比例
//        offset = offsetInHours / hours.size // 睡眠offset时间 单位 小时 / 总的小时数 = 比例
//    )
//
//}
//
//// 版本1 污染MDF
//@Deprecated("use timeGraphBar ", ReplaceWith("timeGraphBar(start,end,hours)"))
//private fun MDF.timeGraphSleepBar(
//    duration: Float,
//    offset: Float,
//) = this then TimeGraphParentData3(
//    duration = duration,
//    offset = offset
//)


@Composable
private fun TimeGraph(
    /*横坐标*/
    hoursHeader: @Composable () -> Unit,
    /*纵坐标数量*/
    rowCount: Int,
    /*单个纵坐标*/
    dayLabel: @Composable (index: Int) -> Unit,
    /*单个数据*/
    sleepBar: @Composable TimeGraphScope.(index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    // 日期
    val dayLabels = @Composable {
        repeat(rowCount) {
            dayLabel(it)
        }
    }

    // 数据
    val sleepBars = @Composable {
        repeat(rowCount) {
            TimeGraphScope.sleepBar(it)
        }
    }

    // measure + place
    Layout(
        contents = listOf(hoursHeader, dayLabels, sleepBars),
        modifier = modifier,
        measurePolicy = {
            //measures:List<List<Measurable>>,constracts:Constraints ->
            // 解构->list
                (hoursHeaderMeasurables, dayLabelMeasureables, sleepBarMeasurables), constraints: Constraints ->
            
            // 1.Measurement step
            // Determine sizes of components  MeasureScope 确定大小
            D.i("1.Measurement step")
            require(hoursHeaderMeasurables.size == 1) { // 确保 头部 x轴只有一个Composable
                "hoursHeader should only emit one composable! xpl"
            }
            val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints) // 测量x轴
            var totalHeight = hoursHeaderPlaceable.height // 总的高度
            D.i("   hoursHeaderPlaceable.height = $totalHeight")
            // 开始测量y轴
            val dayLabelPlaceables = dayLabelMeasureables.map { measurable ->
                D.i("   ->dayLabelPlaceables")
                measurable.measure(constraints)

            }
            // 开始测量数据部分
            val sleepBarPlaceables = sleepBarMeasurables.map { measurable ->
                // 从ParentDataModifier中获取数据
                val barParentData = measurable.parentData as TimeGraphParentData3
                // 数据长度 = duration*x轴宽度
                val barWidth = (barParentData.duration * hoursHeaderPlaceable.width).roundToInt()
                val barPlaceable = measurable.measure(
                    constraints.copy(
                        minWidth = barWidth,
                        maxWidth = barWidth
                    )
                )
                D.e("   ->sleepBarPlaceables.height = ${barPlaceable.height}")
                totalHeight += barPlaceable.height // 高度会变化，叠加高度
                D.i("   ->sleepBarPlaceables.height = $totalHeight")
                barPlaceable
            }
            val totalWidth = dayLabelPlaceables.first().width + hoursHeaderPlaceable.width // 总的宽度

            D.i("totalHeight:$totalHeight ,totalWidth:$totalWidth")

            // 2.Placement step
            // Determine positions of components PlacementScope
            D.i("2.Placement step")
            layout(totalWidth, totalHeight) {
                var xPosition = dayLabelPlaceables.first().width // 摆放x轴 和数据的 x轴起点
                var yPosition = hoursHeaderPlaceable.height // 摆放数据的 y轴起点
                D.i("   place hoursHeader")
                hoursHeaderPlaceable.placeRelative(x = xPosition, 0) // 摆放x轴
                D.i("   place sleepBar")
                sleepBarPlaceables.forEachIndexed { index, placeable -> // 依次摆放每一天的数据
                    D.i("   ->place sleepBar $index - $yPosition")
                    val barParentData = placeable.parentData as TimeGraphParentData3 // 从ParentDataModifier中获取数据
                    val barOffset = (barParentData.offset * hoursHeaderPlaceable.width).roundToInt() // 计算偏移量 从开始位置
                    placeable.place(x = barOffset + xPosition, y = yPosition) // 摆放数据
                    D.i("   ->place sleepBar ok")
                    val dayLabelPlaceable = dayLabelPlaceables[index] //
                    dayLabelPlaceable.place(x = 0, y = yPosition) // 摆放y轴
                    D.i("   ->place dayLabel ok")
                    yPosition += placeable.height // 累加y坐标
                }
            }
        })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SleepBar(
    sleepDayData: SleepDayData,
    debug: Boolean,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(targetState = isExpanded, label = "changed_height")

    Column(
        modifier = modifier
            .clickable(
                indication = LocalIndication.current,// 点击指示器 =null
                //indication = CommonRipple(),// 点击指示器
                interactionSource = remember { MutableInteractionSource() } // 点击？
            ) {
                isExpanded = !isExpanded
            }
    ) {
        // 睡眠数据条
        SleepRoundedBar(
            sleepDayData,
            transition
        )

        transition.AnimatedVisibility(
            enter = fadeIn(animationSpec = tween(animationDuration)) + expandVertically(
                animationSpec = tween(4 * animationDuration)
            ),
            exit = fadeOut(animationSpec = tween(animationDuration)) + shrinkVertically(
                animationSpec = tween(2 * animationDuration)
            ),
            content = {
                // 睡眠质量说明
                DetailLegend(debug)
            },
            visible = { it }
        )
    }

}

@Composable
private fun SleepRoundedBar(sleepDayData: SleepDayData, transition: Transition<Boolean>) {

    // 文字
    val textMeasurer: TextMeasurer = rememberTextMeasurer()

    // 高度 100.dp -> 24.dp
    val height: Dp by transition.animateDp(
        label = "height",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )

        }
    ) { targetExpanded ->
        if (targetExpanded) 100.dp else 24.dp
    }

    // 进度 1f-->0f
    val animationProgress by transition.animateFloat(label = "progress", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    }) { targetExpanded ->
        if (targetExpanded) 1f else 0f
    }

    Spacer(modifier = MDF
        .drawWithCache {
            // this:DrawScope

            val width = this.size.width // DrawScope.宽度
            val cornerRadiusStartPx = 2.dp.toPx()
            val collapsedCornerRadiusPx = 10.dp.toPx()
            val lerp = lerp(
                cornerRadiusStartPx,
                collapsedCornerRadiusPx,
                (1 - animationProgress)
            )
            val animatedCornerRadius = CornerRadius(lerp)

            val lineThicknessPx = lineThickness.toPx()
            val roundedRectPath = Path()
            roundedRectPath.addRoundRect(
                RoundRect(
                    rect = Rect(
                        Offset(x = 0f, y = -lineThicknessPx / 2f),
                        Size(
                            this.size.width + lineThicknessPx * 2,
                            this.size.height + lineThicknessPx
                        )
                    ),
                    cornerRadius = animatedCornerRadius
                )
            )
            val roundedCornerStroke = Stroke(
                lineThicknessPx,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.cornerPathEffect(
                    cornerRadiusStartPx * animationProgress
                )
            )

            val barHeightPx = barHeight.toPx()
            val sleepGraphPath = generateSleepPath(
                this.size,
                sleepDayData, width, barHeightPx, animationProgress,
                lineThickness.toPx() / 2f
            )

            val gradientBrush =
                Brush.verticalGradient(
                    colorStops = sleepGradientBarColorStops.toTypedArray(),
                    startY = 0f,
                    endY = SleepType.values().size * barHeightPx
                )

            val textResult = textMeasurer.measure(AnnotatedString(sleepDayData.sleepScoreEmoji))

            onDrawBehind {
                drawSleepBar(
                    roundedRectPath,
                    sleepGraphPath,
                    gradientBrush,
                    roundedCornerStroke,
                    animationProgress,
                    textResult,
                    cornerRadiusStartPx
                )
            }
        }
        .height(height)
        .fillMaxWidth()
    )
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawSleepBar(
    roundedRectPath: Path,
    sleepGraphPath: Path,
    gradientBrush: Brush,
    roundedCornerStroke: Stroke,
    animationProgress: Float,
    textResult: TextLayoutResult,
    cornerRadiusStartPx: Float,
) {
    clipPath(roundedRectPath) {
        drawPath(sleepGraphPath, brush = gradientBrush)
        drawPath(
            sleepGraphPath,
            style = roundedCornerStroke,
            brush = gradientBrush
        )
    }

    translate(left = -animationProgress * (textResult.size.width + textPadding.toPx())) {
        drawText(
            textResult,
            topLeft = Offset(textPadding.toPx(), cornerRadiusStartPx)
        )
    }
}


private fun generateSleepPath(
    canvasSize: Size,
    sleepData: SleepDayData,
    width: Float,
    barHeightPx: Float,
    heightAnimation: Float,
    lineThicknessPx: Float,
): Path {
    val path = Path()

    var previousPeriod: SleepPeriod? = null

    path.moveTo(0f, 0f)

    sleepData.sleepPeriods.forEach { period ->
        val percentageOfTotal = sleepData.fractionOfTotalTime(period)
        val periodWidth = percentageOfTotal * width
        val startOffsetPercentage = sleepData.minutesAfterSleepStart(period) /
                sleepData.totalTimeInBed.toMinutes().toFloat()
        val halfBarHeight = canvasSize.height / SleepType.values().size / 2f

        val offset = if (previousPeriod == null) {
            0f
        } else {
            halfBarHeight
        }
        val offsetY = lerp(
            0f,
            period.type.heightSleepType() * canvasSize.height, heightAnimation
        )
        // step 1 - draw a line from previous sleep period to current
        if (previousPeriod != null) {
            path.lineTo(
                x = startOffsetPercentage * width + lineThicknessPx,
                y = offsetY + offset
            )
        }

        // step 2 - add the current sleep period as rectangle to path
        path.addRect(
            rect = Rect(
                offset = Offset(x = startOffsetPercentage * width + lineThicknessPx, y = offsetY),
                size = canvasSize.copy(width = periodWidth, height = barHeightPx)
            )
        )
        // step 3 - move to the middle of the current sleep period
        path.moveTo(
            x = startOffsetPercentage * width + periodWidth + lineThicknessPx,
            y = offsetY + halfBarHeight
        )

        previousPeriod = period
    }
    return path
}

@Composable
private fun DetailLegend(debug: Boolean) {
    Row(
        modifier = Modifier
            .run {
                if (debug) this.border(1.dp, Color.Blue) else this
            }
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SleepType.values().forEach {
            LegendItem(it)
        }
    }
}

@Composable
private fun LegendItem(sleepType: SleepType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(sleepType.color)
        )
        Text(
            stringResource(id = sleepType.title),
            //style = LegendHeadingStyle,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

// 横坐标
@Composable
private fun HourHeader(hours: List<Int>, debug: Boolean) {
    Row(

        Modifier
            .xMdf(debug)
            .padding(bottom = 16.dp)
            .drawBehind {
                val brush = Brush.linearGradient(listOf(YellowVariant, Yellow))
                drawRoundRect(
                    brush = brush,
                    cornerRadius = CornerRadius(20.dp.toPx(), 10.dp.toPx())
                )
            }) {
        hours.forEach {
            Text(
                text = "$it",
                textAlign = TextAlign.Center,
                modifier = MDF
                    .width(50.dp)
                    .padding(vertical = 4.dp),
                style = SmallHeadingStyle
            )
        }
    }
}

// 纵坐标
@Composable
private fun DayLabel(dayOfWeek: DayOfWeek, debug: Boolean) {
    Text(
        text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        modifier = MDF
            .yMdf(debug)
            .height(24.dp)
            .padding(start = 8.dp, end = 24.dp),
        style = SmallHeadingStyle,
        textAlign = TextAlign.Center
    )
}

private val lineThickness = 2.dp
private val barHeight = 24.dp
private const val animationDuration = 500
private val textPadding = 4.dp

private val sleepGradientBarColorStops: List<Pair<Float, Color>> = SleepType.values().map {
    Pair(
        when (it) {
            SleepType.Awake -> 0f
            SleepType.REM -> 0.33f
            SleepType.Light -> 0.66f
            SleepType.Deep -> 1f
        },
        it.color
    )
}

private fun SleepType.heightSleepType(): Float {
    return when (this) {
        SleepType.Awake -> 0f
        SleepType.REM -> 0.25f
        SleepType.Light -> 0.5f
        SleepType.Deep -> 0.75f
    }
}
