package com.yunext.twins.compose.ui.debug.cases

import android.util.Log
import android.widget.TextView
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.data.SleepDayData
import com.yunext.twins.compose.ui.debug.data.SleepPeriod
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.x9f8
import com.yunext.twins.compose.ui.theme.xf98
import kotlinx.coroutines.delay
import java.sql.Time
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LayoutCase06() {
    D.i("∆∆∆∆ LayoutCase06 ")
    val column = 7
    val row = 5
    val hours = List(row) { "${it}" }
    var ratio by remember {
        mutableStateOf(1f)
    }
    LaunchedEffect(key1 = "demo") {
        while (true) {
            delay(1000)
            ratio = Random.nextFloat()
        }
    }

    val rememberScrollState = rememberScrollState()
    Column(modifier = MDF.verticalScroll(rememberScrollState)) {


        TimeGraph(hoursHeader = {
            Row(modifier = MDF.background(Color.Blue.copy(.5f))) {
                hours.forEach { hour ->
                    Text(
                        text = "[12月-${hour}号]", modifier = MDF
                            .width(50.dp)
                            .wrapContentHeight()
                    )
                }
            }
        }, rowCount = column, dayLabel = { index ->
            Text(
                text = "周${index + 1}", modifier = Modifier
                    .background(Color.Cyan.copy(.5f))
                    .height(24.dp)
                    .wrapContentHeight()
            )
        }, sleepBar = { index ->
            val offset = (index * 1f * ratio / column + Random.nextFloat() * 0.5f).coerceAtMost(1f)
            SleepBar(
                sleepDayData = SleepDayData.random(),
                modifier = Modifier.timeGraphSleepBar(offset, 1 - offset)
            )
//            Box(
//                Modifier
//                    .background(Color.Yellow)
//                    .timeGraphSleepBar(offset, 1 - offset)
//                    .height(24.dp)
//            ) {
//
//            }
        }, modifier = MDF
            .padding(16.dp)
            .background(Color.Green.copy(0.2f))
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color.Green.copy(.2f))
                    .size(50.dp, 24.dp)
            ) {

            }
            Text(text = "整个Composable")
        }
        Row() {
            SleepBar(
                SleepDayData.random(), modifier = Modifier
                    .timeGraphSleepBar(1f, 0f)
                    .width(50.dp)
            )
            Text(text = "数据部分")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color.Cyan.copy(.5f))
                    .size(50.dp, 24.dp)
            ) {

            }
            Text(text = "星期部分")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color.Blue.copy(.5f))
                    .size(50.dp, 24.dp)
            ) {

            }
            Text(text = "日期部分")
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

private class TimeGraphParentData2(
    /* need to measure */
    val duration: Float,
    /* need to place */
    val offset: Float,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any? {
        return this@TimeGraphParentData2
    }
}

private fun MDF.timeGraphSleepBar(
    duration: Float,
    offset: Float,
) = this then TimeGraphParentData2(
    duration = duration,
    offset = offset
)


@Composable
private fun TimeGraph(
    hoursHeader: @Composable () -> Unit,
    rowCount: Int,
    dayLabel: @Composable (index: Int) -> Unit,
    sleepBar: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val dayLabels = @Composable {
        repeat(rowCount) {
            dayLabel(it)
        }
    }
    val sleepBars = @Composable {
        repeat(rowCount) {
            sleepBar(it)
        }
    }

    Layout(
        contents = listOf(hoursHeader, dayLabels, sleepBars),
        modifier = modifier,
        measurePolicy = {
            //measures:List<List<Measurable>>,constracts:Constraints ->
                (hoursHeaderMeasurables, dayLabelMeasureables, sleepBarMeasurables), constraints: Constraints ->


            // 1.Measurement step
            // Determine sizes of components  MeasureScope
            D.i("1.Measurement step")
            require(hoursHeaderMeasurables.size == 1) {
                "hoursHeader should only emit one composable!"
            }
            val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints)
            var totalHeight = hoursHeaderPlaceable.height
            D.i("   hoursHeaderPlaceable.height = $totalHeight")
            val dayLabelPlaceables = dayLabelMeasureables.map { measurable ->
                D.i("   ->dayLabelPlaceables")
                measurable.measure(constraints)

            }
            val sleepBarPlaceables = sleepBarMeasurables.map { measurable ->
                val barParentData = measurable.parentData as TimeGraphParentData2
                val barWidth = (barParentData.duration * hoursHeaderPlaceable.width).roundToInt()
                val barPlaceable = measurable.measure(
                    constraints.copy(
                        minWidth = barWidth,
                        maxWidth = barWidth
                    )
                )
                D.e("   ->sleepBarPlaceables.height = ${barPlaceable.height}")
                totalHeight += barPlaceable.height
                D.i("   ->sleepBarPlaceables.height = $totalHeight")
                barPlaceable
            }
            val totalWidth = dayLabelPlaceables.first().width + hoursHeaderPlaceable.width

            D.i("totalHeight:$totalHeight ,totalWidth:$totalWidth")

            // 2.Placement step
            // Determine positions of components PlacementScope
            D.i("2.Placement step")
            layout(totalWidth, totalHeight) {
                var xPosition = dayLabelPlaceables.first().width
                var yPosition = hoursHeaderPlaceable.height
                D.i("   place hoursHeader")
                hoursHeaderPlaceable.placeRelative(x = xPosition, 0)
                D.i("   place sleepBar")
                sleepBarPlaceables.forEachIndexed { index, placeable ->
                    D.i("   ->place sleepBar $index - $yPosition")
                    val barParentData = placeable.parentData as TimeGraphParentData2
                    val barOffset = (barParentData.offset * hoursHeaderPlaceable.width).roundToInt()
                    placeable.place(x = barOffset + xPosition, y = yPosition)
                    D.i("   ->place sleepBar ok")
                    val dayLabelPlaceable = dayLabelPlaceables[index]
                    dayLabelPlaceable.place(x = 0, y = yPosition)
                    D.i("   ->place dayLabel ok")
                    yPosition += placeable.height
                }
            }
        })
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SleepBar(
    sleepDayData: SleepDayData,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(targetState = isExpanded, label = "changed_height")
    val height by transition.animateDp(label = "changed_height") { targetExpanded ->
        if (targetExpanded) 110.dp else 24.dp
    }
    Spacer(modifier = modifier
        .padding(top = 4.dp, bottom = 4.dp)
        .drawWithCache {
            val brush = Brush.verticalGradient(listOf(xf98, x9f8))
            val textResult = textMeasurer.measure(AnnotatedString(sleepDayData.sleepScoreEmoji))
            onDrawBehind {
                // this : DrawScope
                drawRoundRect(brush = brush, cornerRadius = CornerRadius(10.dp.toPx()))
                val y = (height.toPx() - textResult.size.height) / 2
                drawText(textResult, topLeft = Offset(0f, y))
            }
        }
        .fillMaxWidth()
        .height(height = height)
        .clickable {
            isExpanded = !isExpanded
        }

    )

}
